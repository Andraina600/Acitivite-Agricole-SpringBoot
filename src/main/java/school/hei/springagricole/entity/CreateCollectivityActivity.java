package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.ActivityType;
import school.hei.springagricole.entity.enums.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class CreateCollectivityActivity {
    private String label;
    private ActivityType activityType;

    private List<MemberOccupation> memberOccupationConcerned;

    private MonthlyRecurrenceRule recurrenceRule;

    private LocalDate executiveDate;

    public CreateCollectivityActivity() {}
}
