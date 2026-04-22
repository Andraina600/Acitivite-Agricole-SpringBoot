package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MobileBankingAccount extends FinancialAccount {
    private String holderName;
    private MobileBankingService mobileBankingService;
    private Long mobileNumber;

    public MobileBankingAccount() {
        super();
        setAccountType("MOBILE");
    }

    public MobileBankingAccount(String id, String collectivityId, BigDecimal balance,
                                String holderName, MobileBankingService mobileBankingService,
                                Long mobileNumber) {
        super(id, collectivityId, "MOBILE", balance);
        this.holderName = holderName;
        this.mobileBankingService = mobileBankingService;
        this.mobileNumber = mobileNumber;
    }
}