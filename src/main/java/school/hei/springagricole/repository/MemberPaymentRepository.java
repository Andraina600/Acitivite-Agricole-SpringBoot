package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.MemberPayment;
import school.hei.springagricole.entity.enums.PaymentMode;

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
            throw new RuntimeException("Error when saving member payment", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public List<MemberPayment> findByMemberId(String memberId) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date " +
                        " FROM member_payment WHERE member_id = ? ORDER BY creation_date DESC")) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            List<MemberPayment> payments = new ArrayList<>();
            while (rs.next()) {
                payments.add(mapRow(rs));
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException("Error loading payments member id=" + memberId, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public Optional<MemberPayment> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date " +
                        " FROM member_payment WHERE id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error loading payment id=" + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private MemberPayment mapRow(ResultSet rs) throws SQLException {
        MemberPayment p = new MemberPayment();
        p.setId(rs.getString("id"));
        p.setMemberId(rs.getString("member_id"));
        p.setMembershipFeeId(rs.getString("membership_fee_id"));
        p.setAccountCreditedId(rs.getString("account_credited_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
        Date d = rs.getDate("creation_date");
        if (d != null) p.setCreationDate(d.toLocalDate());
        return p;
    }
}