package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectivityInformation {
    private String name;
    private Integer number;

    public CollectivityInformation() {}

    public CollectivityInformation(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
