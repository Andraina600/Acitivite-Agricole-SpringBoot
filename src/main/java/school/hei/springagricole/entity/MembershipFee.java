package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MembershipFee {
    private String id;
    private String collectivityId;
    private String label;
    private BigDecimal amount;
    private Frequency frequency;
    private LocalDate eligibleFrom;
    private ActivityStatus status;

    public MembershipFee(String id, String collectivityId, String label, BigDecimal amount, Frequency frequency, LocalDate eligibleFrom, ActivityStatus status) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.label = label;
        this.amount = amount;
        this.frequency = frequency;
        this.eligibleFrom = eligibleFrom;
        this.status = status;
    }

    public MembershipFee() {}
}
