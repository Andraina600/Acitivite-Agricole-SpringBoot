package school.hei.springagricole.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MemberUnpaidAmount {
    private String memberId;
    private BigDecimal unpaidAmount;

    public MemberUnpaidAmount() {}

    public MemberUnpaidAmount(String memberId, BigDecimal unpaidAmount) {
        this.memberId = memberId;
        this.unpaidAmount = unpaidAmount;
    }
}
