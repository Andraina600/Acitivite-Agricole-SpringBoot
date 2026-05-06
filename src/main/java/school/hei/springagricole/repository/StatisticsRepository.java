package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.dto.CollectivityNewMembers;
import school.hei.springagricole.dto.MemberAssiduity;
import school.hei.springagricole.dto.MemberEarnedAmount;
import school.hei.springagricole.dto.MemberUnpaidAmount;

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

    public List<MemberEarnedAmount> findEarnedAmountByMember(
            String collectivityId, LocalDate from, LocalDate to) {

        String sql = """
                SELECT mp.member_id,
                       COALESCE(SUM(mp.amount), 0) AS earned
                FROM member_payment mp
                JOIN membership_fee mf ON mf.id = mp.membership_fee_id
                WHERE mf.collectivity_id = ?
                  AND mp.creation_date >= ?
                  AND mp.creation_date <= ?
                GROUP BY mp.member_id
                """;

        List<MemberEarnedAmount> result = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new MemberEarnedAmount(
                        rs.getString("member_id"),
                        rs.getBigDecimal("earned")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing earned amounts", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return result;
    }

    public List<MemberUnpaidAmount> findUnpaidAmountByMember(
            String collectivityId, LocalDate from, LocalDate to) {

        String sql = """
                SELECT m.id AS member_id,
                       COALESCE(SUM(
                           GREATEST(
                               mf.amount - COALESCE(
                                   (SELECT SUM(mp.amount)
                                    FROM member_payment mp
                                    WHERE mp.member_id = m.id
                                      AND mp.membership_fee_id = mf.id
                                      AND mp.creation_date >= ?
                                      AND mp.creation_date <= ?),
                                   0
                               ),
                               0
                           )
                       ), 0) AS unpaid
                FROM member m
                CROSS JOIN membership_fee mf
                WHERE m.collectivity_id = ?
                  AND mf.collectivity_id = ?
                  AND mf.status = 'ACTIVE'
                  AND mf.eligible_from >= ?
                  AND mf.eligible_from <= ?
                GROUP BY m.id
                """;

        List<MemberUnpaidAmount> result = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            stmt.setString(3, collectivityId);
            stmt.setString(4, collectivityId);
            stmt.setDate(5, Date.valueOf(from));
            stmt.setDate(6, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new MemberUnpaidAmount(
                        rs.getString("member_id"),
                        rs.getBigDecimal("unpaid")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing unpaid amounts", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return result;
    }

    public List<MemberAssiduity> findAssiduityByMember(
            String collectivityId, LocalDate from, LocalDate to) {

        String sql = """
                SELECT aa.member_id,
                       CASE
                           WHEN COUNT(id) FILTER (WHERE aa.status IN ('ATTENDED','MISSING')) = 0
                           THEN 100.0
                           ELSE
                               COUNT(id) FILTER (WHERE aa.status = 'ATTENDED') * 100.0
                               / COUNT(id) FILTER (WHERE aa.status IN ('ATTENDED','MISSING'))
                       END AS assiduity_pct
                FROM activity_attendance aa
                JOIN collectivity_activity ca ON ca.id = aa.activity_id
                WHERE ca.collectivity_id = ?
                  AND aa.member_id IN (
                      SELECT id FROM member WHERE collectivity_id = ?
                  )
                  AND (
                        (ca.executive_date IS NOT NULL
                         AND ca.executive_date >= ?
                         AND ca.executive_date <= ?)
                        OR ca.executive_date IS NULL
                      )
                GROUP BY aa.member_id
                """;

        List<MemberAssiduity> result = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setString(2, collectivityId);
            stmt.setDate(3, Date.valueOf(from));
            stmt.setDate(4, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new MemberAssiduity(
                        rs.getString("member_id"),
                        rs.getDouble("assiduity_pct")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error computing assiduity by member", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return result;
    }

    public List<CollectivityNewMembers> findNewMembersCountByCollectivity(
            LocalDate from, LocalDate to) {

        String sql = """
                SELECT collectivity_id, COUNT(id) AS cnt
                FROM member
                WHERE admission_date >= ?
                  AND admission_date <= ?
                GROUP BY collectivity_id
                """;

        List<CollectivityNewMembers> result = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new CollectivityNewMembers(
                        rs.getString("collectivity_id"),
                        rs.getInt("cnt")
                ));
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

        String countFeesSql = """
                SELECT COUNT(id) FROM membership_fee
                WHERE collectivity_id = ?
                  AND status = 'ACTIVE'
                  AND eligible_from >= ?
                  AND eligible_from <= ?
                """;
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(countFeesSql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) return 100.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking active fees", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        String countMembersSql =
                "SELECT COUNT(id) FROM member WHERE collectivity_id = ?";
        conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(countMembersSql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking members count", e);
        } finally {
            dataSource.closeConnection(conn);
        }

        String sql = """
                SELECT AVG(is_up_to_date) * 100.0 AS due_percentage
                FROM (
                    SELECT member_id,
                           CASE WHEN MIN(is_paid) = 1 THEN 1 ELSE 0 END AS is_up_to_date
                    FROM (
                        SELECT m.id AS member_id,
                               mf.id AS fee_id,
                               CASE
                                   WHEN COALESCE(
                                       (SELECT SUM(mp.amount)
                                        FROM member_payment mp
                                        WHERE mp.member_id = m.id
                                          AND mp.membership_fee_id = mf.id
                                          AND mp.creation_date >= ?
                                          AND mp.creation_date <= ?),
                                       0
                                   ) >= mf.amount
                                   THEN 1 ELSE 0
                               END AS is_paid
                        FROM member m
                        CROSS JOIN membership_fee mf
                        WHERE m.collectivity_id = ?
                          AND mf.collectivity_id = ?
                          AND mf.status = 'ACTIVE'
                          AND mf.eligible_from >= ?
                          AND mf.eligible_from <= ?
                    ) AS member_fee_status
                    GROUP BY member_id
                ) AS member_status
                """;

        conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            stmt.setString(3, collectivityId);
            stmt.setString(4, collectivityId);
            stmt.setDate(5, Date.valueOf(from));
            stmt.setDate(6, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double val = rs.getDouble("due_percentage");
                return rs.wasNull() ? 0.0 : val;
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error computing due percentage", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public List<String> findAllCollectivityIds() {
        String sql = "SELECT id FROM collectivity";
        List<String> ids = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) ids.add(rs.getString("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Error loading collectivity IDs", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return ids;
    }

    public List<String> findMemberIdsByCollectivity(String collectivityId) {
        String sql = "SELECT id FROM member WHERE collectivity_id = ?";
        List<String> ids = new ArrayList<>();
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) ids.add(rs.getString("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Error loading member IDs", e);
        } finally {
            dataSource.closeConnection(conn);
        }
        return ids;
    }
}