package school.hei.springagricole.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.springagricole.entity.enums.AttendanceStatus;

@Getter
@Setter
public class ActivityMemberAttendance {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus attendanceStatus;

    public ActivityMemberAttendance() {}

    public ActivityMemberAttendance(String id, MemberDescription memberDescription,
                                    AttendanceStatus attendanceStatus) {
        this.id = id;
        this.memberDescription = memberDescription;
        this.attendanceStatus = attendanceStatus;
    }
}
