package school.hei.springagricole.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MemberEarnedAmount {
    private String memberId;
    private BigDecimal earnedAmount;

    public MemberEarnedAmount() {}

    public MemberEarnedAmount(String memberId, BigDecimal earnedAmount) {
        this.memberId = memberId;
        this.earnedAmount = earnedAmount;
    }
}
