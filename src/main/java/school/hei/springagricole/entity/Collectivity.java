package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Collectivity {
    private String id;
    private Integer number;
    private String name;
    private String location;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {}

    public Collectivity(String id, Integer number, String name, String location,
                        CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.location = location;
        this.structure = structure;
        this.members = members;
    }

}