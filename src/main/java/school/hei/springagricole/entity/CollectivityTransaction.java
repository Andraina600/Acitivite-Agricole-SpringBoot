package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CollectivityTransaction {
    private String id;
    private String collectivityId;
    private String memberPaymentId;
    private String accountCreditedId;
    private String memberDebitedId;
    private BigDecimal amount;
    private PaymentMode paymentMode;
    private LocalDate creationDate;

    private FinancialAccount accountCredited;
    private Member memberDebited;

    public CollectivityTransaction(String id, String collectivityId, String memberPaymentId, String accountCreditedId, String memberDebitedId, BigDecimal amount, PaymentMode paymentMode, LocalDate creationDate) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.memberPaymentId = memberPaymentId;
        this.accountCreditedId = accountCreditedId;
        this.memberDebitedId = memberDebitedId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.creationDate = creationDate;
    }

    public CollectivityTransaction() {}
}
