package school.hei.springagricole.repository;

import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.ActivityStatus;
import school.hei.springagricole.entity.Frequency;
import school.hei.springagricole.entity.MembershipFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MembershipFeeRepository {
    private final DataSource dataSource;

    public MembershipFeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MembershipFee> saveAll(List<MembershipFee> fees) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            List<MembershipFee> saved = new ArrayList<>();
            for (MembershipFee fee : fees) {
                if (fee.getId() == null) {
                    fee.setId(UUID.randomUUID().toString());
                }
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO membership_fee " +
                                "(id, collectivity_id, label, amount, frequency, eligible_from, status) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    stmt.setString(1, fee.getId());
                    stmt.setString(2, fee.getCollectivityId());
                    stmt.setString(3, fee.getLabel());
                    stmt.setBigDecimal(4, fee.getAmount());
                    stmt.setString(5, fee.getFrequency().name());
                    stmt.setDate(6, Date.valueOf(fee.getEligibleFrom()));
                    stmt.setString(7, ActivityStatus.ACTIVE.name());
                    stmt.executeUpdate();
                }
                fee.setStatus(ActivityStatus.ACTIVE);
                saved.add(fee);
            }

            conn.commit();
            return saved;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique rollback membership_fee", ex);
            }
            throw new RuntimeException("Erreur lors de la sauvegarde des cotisations", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, collectivity_id, label, amount, frequency, eligible_from, status" +
                        " FROM membership_fee WHERE collectivity_id = ? ORDER BY eligible_from")) {

            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            List<MembershipFee> fees = new ArrayList<>();
            while (rs.next()) {
                fees.add(mapRow(rs));
            }
            return fees;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des cotisations", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public Optional<MembershipFee> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, collectivity_id, label, amount, frequency, eligible_from, status" +
                        " FROM membership_fee WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement de la cotisation id=" + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private MembershipFee mapRow(ResultSet rs) throws SQLException {
        MembershipFee fee = new MembershipFee();
        fee.setId(rs.getString("id"));
        fee.setCollectivityId(rs.getString("collectivity_id"));
        fee.setLabel(rs.getString("label"));
        fee.setAmount(rs.getBigDecimal("amount"));
        fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
        Date eligible = rs.getDate("eligible_from");
        if (eligible != null) fee.setEligibleFrom(eligible.toLocalDate());
        fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
        return fee;
    }
}
