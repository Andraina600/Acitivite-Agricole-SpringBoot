package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.CollectivityTransaction;
import school.hei.springagricole.entity.enums.PaymentMode;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CollectivityTransactionRepository {

    private final DataSource dataSource;

    public CollectivityTransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CollectivityTransaction save(CollectivityTransaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID().toString());
        }
        if (transaction.getCreationDate() == null) {
            transaction.setCreationDate(LocalDate.now());
        }

        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO collectivity_transaction " +
                        "(id, collectivity_id, member_payment_id, account_credited_id, " +
                        " member_debited_id, amount, payment_mode, creation_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getCollectivityId());
            stmt.setString(3, transaction.getMemberPaymentId());
            stmt.setString(4, transaction.getAccountCreditedId());
            stmt.setString(5, transaction.getMemberDebitedId());
            stmt.setBigDecimal(6, transaction.getAmount());
            stmt.setString(7, transaction.getPaymentMode().name());
            stmt.setDate(8, Date.valueOf(transaction.getCreationDate()));
            stmt.executeUpdate();

            return transaction;

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving the transaction", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public List<CollectivityTransaction> findByCollectivityIdAndPeriod(
            String collectivityId, LocalDate from, LocalDate to) {

        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, collectivity_id, member_payment_id, account_credited_id, " +
                        "member_debited_id, amount, payment_mode, creation_date " +
                        "FROM collectivity_transaction " +
                        "WHERE collectivity_id = ? " +
                        "  AND creation_date >= ? " +
                        "  AND creation_date <= ? " +
                        "ORDER BY creation_date DESC")) {

            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();

            List<CollectivityTransaction> transactions = new ArrayList<>();
            while (rs.next()) {
                transactions.add(mapRow(rs));
            }
            return transactions;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error loading community transactions=" + collectivityId, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private CollectivityTransaction mapRow(ResultSet rs) throws SQLException {
        CollectivityTransaction t = new CollectivityTransaction();
        t.setId(rs.getString("id"));
        t.setCollectivityId(rs.getString("collectivity_id"));
        t.setMemberPaymentId(rs.getString("member_payment_id"));
        t.setAccountCreditedId(rs.getString("account_credited_id"));
        t.setMemberDebitedId(rs.getString("member_debited_id"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
        Date d = rs.getDate("creation_date");
        if (d != null) t.setCreationDate(d.toLocalDate());
        return t;
    }
}