package school.hei.springagricole.repository;


import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class StatisticsRepository {

    private final DataSource dataSource;

    public StatisticsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, BigDecimal> findEarnedAmountByMember(
            String collectivityId, LocalDate from, LocalDate to) {

        String sql = """
            SELECT mp.member_id, COALESCE(SUM(mp.amount), 0) AS earned
            FROM member_payment mp
            JOIN membership_fee mf ON mf.id = mp.membership_fee_id
            WHERE mf.collectivity_id = ?
              AND mp.creation_date >= ?
              AND mp.creation_date <= ?
            GROUP BY mp.member_id
            """;

        Map<String, BigDecimal> result = new LinkedHashMap<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("member_id"), rs.getBigDecimal("earned"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing earned amounts", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return result;
    }

    public Map<String, BigDecimal> findUnpaidAmountByMember(
            String collectivityId, LocalDate from, LocalDate to) {

        String feesSql = """
                SELECT id, amount
                FROM membership_fee
                WHERE collectivity_id = ?
                  AND status = 'ACTIVE'
                  AND eligible_from >= ?
                  AND eligible_from <= ?
                """;

        Map<String, BigDecimal> activeFees = new LinkedHashMap<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(feesSql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                activeFees.put(rs.getString("id"), rs.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading active membership fees", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        if (activeFees.isEmpty()) {
            return Collections.emptyMap();
        }

        String paymentsSql = """
                SELECT mp.member_id, mp.membership_fee_id, COALESCE(SUM(mp.amount), 0) AS paid
                FROM member_payment mp
                JOIN member m ON m.id = mp.member_id
                WHERE m.collectivity_id = ?
                  AND mp.membership_fee_id IN (%s)
                  AND mp.creation_date >= ?
                  AND mp.creation_date <= ?
                GROUP BY mp.member_id, mp.membership_fee_id
                """.formatted(buildInClause(activeFees.size()));

        Map<String, Map<String, BigDecimal>> paidByMemberAndFee = new LinkedHashMap<>();
        conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(paymentsSql)) {
            int idx = 1;
            stmt.setString(idx++, collectivityId);
            for (String feeId : activeFees.keySet()) {
                stmt.setString(idx++, feeId);
            }
            stmt.setDate(idx++, Date.valueOf(from));
            stmt.setDate(idx, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String memberId = rs.getString("member_id");
                String feeId = rs.getString("membership_fee_id");
                BigDecimal paid = rs.getBigDecimal("paid");
                paidByMemberAndFee
                        .computeIfAbsent(memberId, k -> new LinkedHashMap<>())
                        .put(feeId, paid);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing paid amounts per fee", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        List<String> memberIds = findMemberIdsByCollectivity(collectivityId);

        Map<String, BigDecimal> unpaidByMember = new LinkedHashMap<>();
        for (String memberId : memberIds) {
            BigDecimal totalUnpaid = BigDecimal.ZERO;
            Map<String, BigDecimal> paidForMember =
                    paidByMemberAndFee.getOrDefault(memberId, Collections.emptyMap());

            for (Map.Entry<String, BigDecimal> feeEntry : activeFees.entrySet()) {
                String feeId = feeEntry.getKey();
                BigDecimal feeAmount = feeEntry.getValue();
                BigDecimal paid = paidForMember.getOrDefault(feeId, BigDecimal.ZERO);
                BigDecimal diff = feeAmount.subtract(paid);
                if (diff.compareTo(BigDecimal.ZERO) > 0) {
                    totalUnpaid = totalUnpaid.add(diff);
                }
            }
            unpaidByMember.put(memberId, totalUnpaid);
        }

        return unpaidByMember;
    }

    public List<String> findMemberIdsByCollectivity(String collectivityId) {
        String sql = "SELECT id FROM member WHERE collectivity_id = ?";
        List<String> ids = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading member IDs for collectivity", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return ids;
    }

    public Map<String, Integer> findNewMembersCountByCollectivity(
            LocalDate from, LocalDate to) {

        String sql = """
                SELECT collectivity_id, COUNT(id) AS cnt
                FROM member
                WHERE admission_date >= ?
                  AND admission_date <= ?
                GROUP BY collectivity_id
                """;

        Map<String, Integer> result = new LinkedHashMap<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("collectivity_id"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting new members", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return result;
    }

    public double computeCurrentDuePercentage(
            String collectivityId, LocalDate from, LocalDate to) {

        List<String> memberIds = findMemberIdsByCollectivity(collectivityId);
        if (memberIds.isEmpty()) return 0.0;

        String feesSql = """
            SELECT id, amount
            FROM membership_fee
            WHERE collectivity_id = ?
              AND status = 'ACTIVE'
              AND eligible_from >= ?
              AND eligible_from <= ?
            """;

        Map<String, BigDecimal> activeFees = new LinkedHashMap<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(feesSql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                activeFees.put(rs.getString("id"), rs.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading fees for due percentage", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        if (activeFees.isEmpty()) return 100.0;

        String paymentsSql = """
            SELECT mp.member_id, mp.membership_fee_id, COALESCE(SUM(mp.amount), 0) AS paid
            FROM member_payment mp
            WHERE mp.membership_fee_id IN (%s)
              AND mp.creation_date >= ?
              AND mp.creation_date <= ?
            GROUP BY mp.member_id, mp.membership_fee_id
            """.formatted(buildInClause(activeFees.size()));

        Map<String, Map<String, BigDecimal>> paidMap = new LinkedHashMap<>();
        conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(paymentsSql)) {
            int idx = 1;
            for (String feeId : activeFees.keySet()) {
                stmt.setString(idx++, feeId);
            }
            stmt.setDate(idx++, Date.valueOf(from));
            stmt.setDate(idx, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                paidMap.computeIfAbsent(rs.getString("member_id"), k -> new LinkedHashMap<>())
                        .put(rs.getString("membership_fee_id"), rs.getBigDecimal("paid"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing payments for due percentage", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        Set<String> allRelevantMembers = new HashSet<>(memberIds);
        allRelevantMembers.addAll(paidMap.keySet());

        long upToDateCount = allRelevantMembers.stream().filter(memberId -> {
            Map<String, BigDecimal> paidForMember =
                    paidMap.getOrDefault(memberId, Collections.emptyMap());
            for (Map.Entry<String, BigDecimal> fee : activeFees.entrySet()) {
                BigDecimal paid = paidForMember.getOrDefault(fee.getKey(), BigDecimal.ZERO);
                if (paid.compareTo(fee.getValue()) < 0) {
                    return false;
                }
            }
            return true;
        }).count();

        return (upToDateCount * 100.0) / allRelevantMembers.size();
    }

    public List<String> findAllCollectivityIds() {
        String sql = "SELECT id FROM collectivity";
        List<String> ids = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading collectivity IDs", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return ids;
    }

    private String buildInClause(int size) {
        return String.join(", ", Collections.nCopies(size, "?"));
    }
}