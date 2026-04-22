package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.Bank;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccount extends FinancialAccount {
    private String holderName;
    private Bank bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Long bankAccountNumber;
    private Integer bankAccountKey;

    public BankAccount() {
        super();
        setAccountType("BANK");
    }

    public BankAccount(String id, String collectivityId, BigDecimal balance,
                       String holderName, Bank bankName,
                       Integer bankCode, Integer bankBranchCode,
                       Long bankAccountNumber, Integer bankAccountKey) {
        super(id, collectivityId, "BANK", balance);
        this.holderName = holderName;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankBranchCode = bankBranchCode;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountKey = bankAccountKey;
    }
}