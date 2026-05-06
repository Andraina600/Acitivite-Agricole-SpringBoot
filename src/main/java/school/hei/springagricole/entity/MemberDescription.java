package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDescription {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;

    public MemberDescription() {}

    public MemberDescription(String id, String firstName, String lastName,
                             String email, String occupation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
    }
}