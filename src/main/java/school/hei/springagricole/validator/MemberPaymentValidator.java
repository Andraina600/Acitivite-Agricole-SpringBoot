package school.hei.springagricole.validator;

import org.springframework.stereotype.Component;
import school.hei.springagricole.entity.CreateMemberPayment;
import school.hei.springagricole.exception.BadRequestException;

import java.math.BigDecimal;

@Component
public class MemberPaymentValidator {
    public void validatePayment(CreateMemberPayment payment) {
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Payment amount cannot be negative");
        }
        if (payment.getMembershipFeeIdentifier() == null || payment.getMembershipFeeIdentifier().isBlank()) {
            throw new BadRequestException("Membership fee identifier is mandatory");
        }
        if (payment.getAccountCreditedIdentifier() == null || payment.getAccountCreditedIdentifier().isBlank()) {
            throw new BadRequestException("Credited account identifier is mandatory");
        }
        if (payment.getPaymentMode() == null) {
            throw new BadRequestException("Payment mode is mandatory");
        }
    }
}