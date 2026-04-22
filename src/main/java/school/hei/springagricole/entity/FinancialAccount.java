package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinancialAccount {
    private String id;
    private String collectivityId;
    private String accountType;
    private BigDecimal balance;

    public FinancialAccount() {}

    public FinancialAccount(String id, String collectivityId, String accountType, BigDecimal balance) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.accountType = accountType;
        this.balance = balance;
    }
}
