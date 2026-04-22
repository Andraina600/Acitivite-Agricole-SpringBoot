package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateMember {
    private String firstName;
    private String lastName;
    private String birthDate; // Format 'YYYY-MM-DD'
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private String collectivityIdentifier;
    private List<String> referees;
    private boolean registrationFeePaid;
    private boolean membershipDuesPaid;

    public CreateMember(String firstName, String lastName, String birthDate, Gender gender, String address, String profession, Integer phoneNumber, String email, MemberOccupation occupation, String collectivityIdentifier, List<String> referees, boolean registrationFeePaid, boolean membershipDuesPaid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.collectivityIdentifier = collectivityIdentifier;
        this.referees = referees;
        this.registrationFeePaid = registrationFeePaid;
        this.membershipDuesPaid = membershipDuesPaid;
    }

    public CreateMember() {}

}
