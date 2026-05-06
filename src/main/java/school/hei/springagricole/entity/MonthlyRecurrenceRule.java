package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyRecurrenceRule {
    private Integer weekOrdinal;
    private String dayOfWeek;

    public MonthlyRecurrenceRule() {}

    public MonthlyRecurrenceRule(Integer weekOrdinal, String dayOfWeek) {
        this.weekOrdinal = weekOrdinal;
        this.dayOfWeek = dayOfWeek;
    }
}
