package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CollectivityLocalStatistics {
    private MemberDescription memberDescription;
    private BigDecimal earnedAmount;
    private BigDecimal unpaidAmount;

    public CollectivityLocalStatistics() {}

    public CollectivityLocalStatistics(MemberDescription memberDescription,
                                       BigDecimal earnedAmount,
                                       BigDecimal unpaidAmount) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
    }
}
