package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Collectivity {
    private String id;
    private String location;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity(String id, String location, CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.location = location;
        this.structure = structure;
        this.members = members;
    }

    public Collectivity() {
    }

}
