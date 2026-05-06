package school.hei.springagricole.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectivityOverallStatistics {
    private CollectivityInformation collectivityInformation;
    private Integer newMembersNumber;
    private Double overallMemberCurrentDuePercentage;

    public CollectivityOverallStatistics() {}

    public CollectivityOverallStatistics(CollectivityInformation collectivityInformation,
                                         Integer newMembersNumber,
                                         Double overallMemberCurrentDuePercentage) {
        this.collectivityInformation = collectivityInformation;
        this.newMembersNumber = newMembersNumber;
        this.overallMemberCurrentDuePercentage = overallMemberCurrentDuePercentage;
    }
}
