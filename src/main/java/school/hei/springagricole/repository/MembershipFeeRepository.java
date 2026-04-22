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


}
