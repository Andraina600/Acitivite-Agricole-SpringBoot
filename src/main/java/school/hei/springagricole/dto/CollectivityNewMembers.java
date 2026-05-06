package school.hei.springagricole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectivityNewMembers {
    private String collectivityId;
    private Integer newMembersCount;

    public CollectivityNewMembers() {}

    public CollectivityNewMembers(String collectivityId, Integer newMembersCount) {
        this.collectivityId = collectivityId;
        this.newMembersCount = newMembersCount;
    }
}
