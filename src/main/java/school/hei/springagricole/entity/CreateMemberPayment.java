package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.PaymentMode;

import java.math.BigDecimal;
@Getter
@Setter
public class CreateMemberPayment {
    private BigDecimal amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private PaymentMode paymentMode;

    public CreateMemberPayment(BigDecimal amount, String membershipFeeIdentifier, String accountCreditedIdentifier, PaymentMode paymentMode) {
        this.amount = amount;
        this.membershipFeeIdentifier = membershipFeeIdentifier;
        this.accountCreditedIdentifier = accountCreditedIdentifier;
        this.paymentMode = paymentMode;
    }

    public CreateMemberPayment() {}
}
