package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollectivityStructure {
    private Member president;
    private Member vicePresident;
    private Member treasurer;
    private Member secretary;

    public CollectivityStructure(Member president, Member vicePresident, Member treasurer, Member secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

    public CollectivityStructure() {}

}
