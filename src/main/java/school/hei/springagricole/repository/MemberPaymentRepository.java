package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.MemberPayment;
import school.hei.springagricole.entity.PaymentMode;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemberPaymentRepository {

    private final DataSource dataSource;

    public MemberPaymentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MemberPayment save(MemberPayment payment) {
        if (payment.getId() == null) {
            payment.setId(UUID.randomUUID().toString());
        }
        if (payment.getCreationDate() == null) {
            payment.setCreationDate(LocalDate.now());
        }

        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO member_payment " +
                        "(id, member_id, membership_fee_id, account_credited_id, " +
                        " amount, payment_mode, creation_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, payment.getId());
            stmt.setString(2, payment.getMemberId());
            stmt.setString(3, payment.getMembershipFeeId());
            stmt.setString(4, payment.getAccountCreditedId());
            stmt.setBigDecimal(5, payment.getAmount());
            stmt.setString(6, payment.getPaymentMode().name());
            stmt.setDate(7, Date.valueOf(payment.getCreationDate()));
            stmt.executeUpdate();

            return payment;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du paiement membre", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }


}
