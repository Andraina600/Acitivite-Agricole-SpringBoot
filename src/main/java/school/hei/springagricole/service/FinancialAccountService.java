package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.FinancialAccount;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.CollectivityTransactionRepository;
import school.hei.springagricole.repository.FinancialAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialAccountService {

    private final FinancialAccountRepository financialAccountRepository;
    private final CollectivityRepository collectivityRepository;
    private final CollectivityTransactionRepository transactionRepository;

    public FinancialAccountService(
            FinancialAccountRepository financialAccountRepository,
            CollectivityRepository collectivityRepository,
            CollectivityTransactionRepository transactionRepository) {
        this.financialAccountRepository = financialAccountRepository;
        this.collectivityRepository = collectivityRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<FinancialAccount> getByCollectivityId(String collectivityId, LocalDate at) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        List<FinancialAccount> accounts =
                financialAccountRepository.findByCollectivityId(collectivityId);

        if (at != null && at.isBefore(LocalDate.now())) {
            for (FinancialAccount account : accounts) {
                BigDecimal transactionsAfter =
                        transactionRepository.sumAmountByAccountAfterDate(account.getId(), at);
                BigDecimal balanceAt = account.getBalance().subtract(transactionsAfter);
                account.setBalance(balanceAt);
            }
        }

        return accounts;
    }
}