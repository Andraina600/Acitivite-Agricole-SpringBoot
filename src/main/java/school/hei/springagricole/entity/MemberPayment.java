package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MemberPayment {
    private String id;
    private String memberId;
    private String membershipFeeId;
    private String accountCreditedId;
    private BigDecimal amount;
    private PaymentMode paymentMode;
    private LocalDate creationDate;

    private FinancialAccount accountCredited;

    public MemberPayment(String id, String memberId, String membershipFeeId, String accountCreditedId, BigDecimal amount, PaymentMode paymentMode, LocalDate creationDate) {
        this.id = id;
        this.memberId = memberId;
        this.membershipFeeId = membershipFeeId;
        this.accountCreditedId = accountCreditedId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.creationDate = creationDate;
    }

    public MemberPayment() {}
}
