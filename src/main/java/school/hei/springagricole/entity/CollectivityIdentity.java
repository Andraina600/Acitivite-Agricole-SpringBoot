package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectivityIdentity {
    private Integer number;
    private String name;

    public CollectivityIdentity() {}

    public CollectivityIdentity(Integer number, String name) {
        this.number = number;
        this.name = name;
    }
}
