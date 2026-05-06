package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.ActivityType;
import school.hei.springagricole.entity.enums.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CollectivityActivity {
    private String id;
    private String collectivityId;
    private String label;
    private ActivityType activityType;
    private List<MemberOccupation> memberOccupationConcerned;
    private MonthlyRecurrenceRule recurrenceRule;
    private LocalDate executiveDate;

    public CollectivityActivity() {}

    public CollectivityActivity(String id, String collectivityId, String label,
                                ActivityType activityType,
                                List<MemberOccupation> memberOccupationConcerned,
                                MonthlyRecurrenceRule recurrenceRule,
                                LocalDate executiveDate) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.label = label;
        this.activityType = activityType;
        this.memberOccupationConcerned = memberOccupationConcerned;
        this.recurrenceRule = recurrenceRule;
        this.executiveDate = executiveDate;
    }
}
