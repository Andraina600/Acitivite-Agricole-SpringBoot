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
    private Double assiduityPercentage;

    public CollectivityLocalStatistics() {}

    public CollectivityLocalStatistics(MemberDescription memberDescription,
                                       BigDecimal earnedAmount,
                                       BigDecimal unpaidAmount,
                                       Double assiduityPercentage) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
        this.assiduityPercentage = assiduityPercentage;
    }
}
