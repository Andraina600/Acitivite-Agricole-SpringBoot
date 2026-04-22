package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FinancialAccountRepository {

    private final DataSource dataSource;

    public FinancialAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FinancialAccount save(FinancialAccount account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID().toString());
        }

        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            if ("CASH".equals(account.getAccountType())) {
                if (cashExistsForCollectivity(conn, account.getCollectivityId())) {
                    throw new school.hei.springagricole.exception.BadRequestException(
                            "La collectivité possède déjà une caisse");
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO financial_account (id, collectivity_id, account_type, balance) " +
                            "VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, account.getId());
                stmt.setString(2, account.getCollectivityId());
                stmt.setString(3, account.getAccountType());
                stmt.setBigDecimal(4, account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
                stmt.executeUpdate();
            }

            if (account instanceof BankAccount bankAccount) {
                saveBankDetail(conn, bankAccount);
            } else if (account instanceof MobileBankingAccount mobileAccount) {
                saveMobileDetail(conn, mobileAccount);
            }

            conn.commit();
            return findById(account.getId()).orElseThrow(
                    () -> new RuntimeException("Compte non trouvé après sauvegarde"));

        } catch (school.hei.springagricole.exception.BadRequestException e) {
            throw e;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique rollback financial_account", ex);
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du compte financier", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public Optional<FinancialAccount> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM financial_account WHERE id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(loadFull(conn, rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur chargement compte id=" + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private boolean cashExistsForCollectivity(Connection conn, String collectivityId)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) FROM financial_account " +
                        "WHERE collectivity_id = ? AND account_type = 'CASH'")) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void saveBankDetail(Connection conn, BankAccount account) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO bank_account_detail " +
                        "(id, holder_name, bank_name, bank_code, bank_branch_code, " +
                        " bank_account_number, bank_account_key) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setString(3, account.getBankName().name());
            stmt.setInt(4, account.getBankCode());
            stmt.setInt(5, account.getBankBranchCode());
            stmt.setLong(6, account.getBankAccountNumber());
            stmt.setInt(7, account.getBankAccountKey());
            stmt.executeUpdate();
        }
    }

    private void saveMobileDetail(Connection conn, MobileBankingAccount account) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO mobile_money_account_detail " +
                        "(id, holder_name, mobile_banking_service, mobile_number) " +
                        "VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setString(3, account.getMobileBankingService().name());
            stmt.setLong(4, account.getMobileNumber());
            stmt.executeUpdate();
        }
    }

    private FinancialAccount loadFull(Connection conn, ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String collectivityId = rs.getString("collectivity_id");
        String accountType = rs.getString("account_type");
        BigDecimal balance = rs.getBigDecimal("balance");

        return switch (accountType) {
            case "CASH" -> new CashAccount(id, collectivityId, balance);
            case "BANK" -> loadBankAccount(conn, id, collectivityId, balance);
            case "MOBILE" -> loadMobileAccount(conn, id, collectivityId, balance);
            default -> throw new RuntimeException("Type de compte inconnu : " + accountType);
        };
    }

    private BankAccount loadBankAccount(Connection conn, String id,
                                        String collectivityId, BigDecimal balance)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT holder_name, bank_name, bank_code, bank_branch_code, bank_account_number, bank_account_key FROM bank_account_detail WHERE id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BankAccount(
                        id, collectivityId, balance,
                        rs.getString("holder_name"),
                        Bank.valueOf(rs.getString("bank_name")),
                        rs.getInt("bank_code"),
                        rs.getInt("bank_branch_code"),
                        rs.getLong("bank_account_number"),
                        rs.getInt("bank_account_key")
                );
            }
        }
        BankAccount b = new BankAccount();
        b.setId(id);
        b.setCollectivityId(collectivityId);
        b.setBalance(balance);
        return b;
    }

    private MobileBankingAccount loadMobileAccount(Connection conn, String id,
                                                   String collectivityId, BigDecimal balance)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT holder_name, mobile_banking_service, mobile_number FROM mobile_money_account_detail WHERE id = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MobileBankingAccount(
                        id, collectivityId, balance,
                        rs.getString("holder_name"),
                        MobileBankingService.valueOf(rs.getString("mobile_banking_service")),
                        rs.getLong("mobile_number")
                );
            }
        }
        MobileBankingAccount m = new MobileBankingAccount();
        m.setId(id);
        m.setCollectivityId(collectivityId);
        m.setBalance(balance);
        return m;
    }
}