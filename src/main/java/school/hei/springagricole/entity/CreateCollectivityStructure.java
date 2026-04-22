package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCollectivityStructure {
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;

    public CreateCollectivityStructure(String president, String vicePresident, String treasurer, String secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }

    public CreateCollectivityStructure() {}

}
