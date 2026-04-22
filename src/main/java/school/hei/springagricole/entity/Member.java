package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.Gender;
import school.hei.springagricole.entity.enums.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private List<Member> referees;

    private LocalDate admissionDate;
    private String collectivityId;

    public Member(String id, String firstName, String lastName, String birthDate, Gender gender, String address, String profession, Integer phoneNumber, String email, MemberOccupation occupation, List<Member> referees, LocalDate admissionDate, String collectivityId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.referees = referees;
        this.admissionDate = admissionDate;
        this.collectivityId = collectivityId;
    }

    public Member() {}

}
