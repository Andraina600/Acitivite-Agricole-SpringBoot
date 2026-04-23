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

    public List<MemberPayment> createPayments(String memberId,
                                              List<CreateMemberPayment> requests) {

        memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        List<MemberPayment> payments = new ArrayList<>();
        for (CreateMemberPayment request : requests) {
            memberPaymentValidator.validatePayment(request);

            MemberPayment payment = new MemberPayment();
            payment.setMemberId(memberId);
            payment.setMembershipFeeId(request.getMembershipFeeIdentifier());
            payment.setAccountCreditedId(request.getAccountCreditedIdentifier());
            payment.setAmount(request.getAmount());
            payment.setPaymentMode(request.getPaymentMode());
            payment.setCreationDate(LocalDate.now());
            payments.add(payment);
        }

        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            List<MemberPayment> saved = new ArrayList<>();
            for (MemberPayment payment : payments) {

                MembershipFee fee = membershipFeeRepository
                        .findById(payment.getMembershipFeeId())
                        .orElseThrow(() -> new NotFoundException(
                                "Membership fee not found: " + payment.getMembershipFeeId()));

                FinancialAccount account = financialAccountRepository
                        .findById(payment.getAccountCreditedId())
                        .orElseThrow(() -> new NotFoundException(
                                "Financial account not found: " + payment.getAccountCreditedId()));

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
                throw new RuntimeException("Critical error during payment rollback", ex);
            }
            throw e;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Critical error during payment rollback", ex);
            }
            throw new RuntimeException("Error occurred while creating payments", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }
}