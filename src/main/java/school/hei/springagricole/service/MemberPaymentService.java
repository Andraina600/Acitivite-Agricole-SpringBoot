package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.*;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.*;
import school.hei.springagricole.validator.MemberPaymentValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberPaymentService {

    private final MemberPaymentRepository memberPaymentRepository;
    private final CollectivityTransactionRepository transactionRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final MemberRepository memberRepository;
    private final DataSource dataSource;
    private final MemberPaymentValidator memberPaymentValidator;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository,
                                CollectivityTransactionRepository transactionRepository,
                                FinancialAccountRepository financialAccountRepository,
                                MembershipFeeRepository membershipFeeRepository,
                                MemberRepository memberRepository,
                                DataSource dataSource,
                                MemberPaymentValidator memberPaymentValidator) {
        this.memberPaymentRepository = memberPaymentRepository;
        this.transactionRepository = transactionRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.membershipFeeRepository = membershipFeeRepository;
        this.memberRepository = memberRepository;
        this.dataSource = dataSource;
        this.memberPaymentValidator = memberPaymentValidator;
    }

    public List<MemberPayment> createPayments(String memberId, List<MemberPayment> payments) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Membre non trouvé : " + memberId));

        for (MemberPayment payment : payments) {
            memberPaymentValidator.validatePayment(payment);
            payment.setMemberId(memberId);
            payment.setCreationDate(LocalDate.now());
        }

        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            List<MemberPayment> saved = new ArrayList<>();
            for (MemberPayment payment : payments) {
                MembershipFee fee = membershipFeeRepository.findById(payment.getMembershipFeeId())
                        .orElseThrow(() -> new NotFoundException(
                                "Cotisation non trouvée : " + payment.getMembershipFeeId()));

                FinancialAccount account = financialAccountRepository
                        .findById(payment.getAccountCreditedId())
                        .orElseThrow(() -> new NotFoundException(
                                "Compte financier non trouvé : " + payment.getAccountCreditedId()));

                MemberPayment savedPayment = memberPaymentRepository.save(payment);

                CollectivityTransaction transaction = new CollectivityTransaction(
                        null,
                        fee.getCollectivityId(),
                        savedPayment.getId(),
                        account.getId(),
                        memberId,
                        payment.getAmount(),
                        payment.getPaymentMode(),
                        LocalDate.now()
                );
                transactionRepository.save(transaction);

                BigDecimal newBalance = account.getBalance().add(payment.getAmount());
                financialAccountRepository.updateBalance(account.getId(), newBalance);

                savedPayment.setAccountCredited(account);
                saved.add(savedPayment);
            }

            conn.commit();
            return saved;

        } catch (NotFoundException | BadRequestException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique rollback paiements", ex);
            }
            throw e;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique rollback paiements", ex);
            }
            throw new RuntimeException("Erreur lors de la création des paiements", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

}
