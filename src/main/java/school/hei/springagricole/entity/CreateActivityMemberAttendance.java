package school.hei.springagricole.entity;


import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.AttendanceStatus;

@Getter
@Setter
public class CreateActivityMemberAttendance {
    private String memberIdentifier;
    private AttendanceStatus attendanceStatus;

    public CreateActivityMemberAttendance() {}
}
