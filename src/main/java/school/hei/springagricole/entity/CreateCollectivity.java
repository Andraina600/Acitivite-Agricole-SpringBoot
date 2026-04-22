package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateCollectivity {
    private String location;
    private List<String> members;
    private boolean federationApproval;
    private CreateCollectivityStructure structure;

    public CreateCollectivity(String location, List<String> members, boolean federationApproval, CreateCollectivityStructure structure) {
        this.location = location;
        this.members = members;
        this.federationApproval = federationApproval;
        this.structure = structure;
    }

    public CreateCollectivity() {}

}
