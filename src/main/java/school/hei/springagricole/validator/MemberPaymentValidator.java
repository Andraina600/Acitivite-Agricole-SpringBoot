package school.hei.springagricole.validator;

import org.springframework.stereotype.Component;
import school.hei.springagricole.entity.MemberPayment;
import school.hei.springagricole.exception.BadRequestException;

import java.math.BigDecimal;

@Component
public class MemberPaymentValidator {
    public void validatePayment(MemberPayment payment) {
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Le montant d'un paiement ne peut pas être négatif");
        }
        if (payment.getMembershipFeeId() == null || payment.getMembershipFeeId().isBlank()) {
            throw new BadRequestException("L'identifiant de la cotisation est obligatoire");
        }
        if (payment.getAccountCreditedId() == null || payment.getAccountCreditedId().isBlank()) {
            throw new BadRequestException("L'identifiant du compte crédité est obligatoire");
        }
        if (payment.getPaymentMode() == null) {
            throw new BadRequestException("Le mode de paiement est obligatoire");
        }
    }
}
