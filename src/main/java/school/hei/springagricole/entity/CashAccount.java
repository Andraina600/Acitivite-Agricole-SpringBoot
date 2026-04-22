package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashAccount extends FinancialAccount {

    public CashAccount() {
        super();
        setAccountType("CASH");
    }

    public CashAccount(String id, String collectivityId, BigDecimal balance) {
        super(id, collectivityId, "CASH", balance);
    }
}