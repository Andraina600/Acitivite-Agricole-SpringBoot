package school.hei.springagricole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAssiduity {
    private String memberId;
    private Double assiduityPercentage;

    public MemberAssiduity() {}

    public MemberAssiduity(String memberId, Double assiduityPercentage) {
        this.memberId = memberId;
        this.assiduityPercentage = assiduityPercentage;
    }
}
