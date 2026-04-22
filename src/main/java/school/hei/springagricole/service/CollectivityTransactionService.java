package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.CollectivityTransaction;
import school.hei.springagricole.entity.FinancialAccount;
import school.hei.springagricole.entity.Member;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.CollectivityTransactionRepository;
import school.hei.springagricole.repository.FinancialAccountRepository;
import school.hei.springagricole.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityTransactionService {

    private final CollectivityTransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final MemberRepository memberRepository;

    public CollectivityTransactionService(
            CollectivityTransactionRepository transactionRepository,
            CollectivityRepository collectivityRepository,
            FinancialAccountRepository financialAccountRepository,
            MemberRepository memberRepository) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityTransaction> getTransactions(
            String collectivityId, LocalDate from, LocalDate to) {

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        if (from == null || to == null) {
            throw new BadRequestException("Parameters 'from' and 'to' are mandatory");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException(
                    "The 'from' date cannot be after the 'to' date");
        }

        List<CollectivityTransaction> transactions =
                transactionRepository.findByCollectivityIdAndPeriod(collectivityId, from, to);

        for (CollectivityTransaction transaction : transactions) {
            FinancialAccount account = financialAccountRepository
                    .findById(transaction.getAccountCreditedId())
                    .orElse(null);
            transaction.setAccountCredited(account);

            Member member = memberRepository
                    .findById(transaction.getMemberDebitedId())
                    .orElse(null);
            transaction.setMemberDebited(member);
        }

        return transactions;
    }
}