package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.Frequency;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class CreateMembershipFee {
    private String label;
    private BigDecimal amount;
    private Frequency frequency;
    private LocalDate eligibleFrom;

    public CreateMembershipFee(String label, BigDecimal amount, Frequency frequency, LocalDate eligibleFrom) {
        this.label = label;
        this.amount = amount;
        this.frequency = frequency;
        this.eligibleFrom = eligibleFrom;
    }

    public CreateMembershipFee() {}
}
