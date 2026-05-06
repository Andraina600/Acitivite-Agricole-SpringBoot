package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.ActivityMemberAttendance;
import school.hei.springagricole.entity.MemberDescription;
import school.hei.springagricole.entity.enums.AttendanceStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ActivityAttendanceRepository {
    private final DataSource dataSource;

    public ActivityAttendanceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ActivityMemberAttendance upsert(String activityId, String memberId,
                                           AttendanceStatus status) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            Optional<ActivityMemberAttendance> existing = findByActivityAndMember(
                    conn, activityId, memberId);

            String attendanceId;
            if (existing.isPresent()) {
                attendanceId = existing.get().getId();
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE activity_attendance SET status = ? " +
                                "WHERE activity_id = ? AND member_id = ?")) {
                    stmt.setString(1, status.name());
                    stmt.setString(2, activityId);
                    stmt.setString(3, memberId);
                    stmt.executeUpdate();
                }
            } else {
                attendanceId = UUID.randomUUID().toString();
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO activity_attendance (id, activity_id, member_id, status) " +
                                "VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, attendanceId);
                    stmt.setString(2, activityId);
                    stmt.setString(3, memberId);
                    stmt.setString(4, status.name());
                    stmt.executeUpdate();
                }
            }

            conn.commit();

            MemberDescription description = loadMemberDescription(conn, memberId);
            return new ActivityMemberAttendance(attendanceId, description, status);

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Critical rollback error attendance", ex);
            }
            throw new RuntimeException("Error saving attendance", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public List<ActivityMemberAttendance> findByActivityId(String activityId) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT aa.id, aa.member_id, aa.status " +
                        "FROM activity_attendance aa " +
                        "WHERE aa.activity_id = ?")) {

            stmt.setString(1, activityId);
            ResultSet rs = stmt.executeQuery();
            List<ActivityMemberAttendance> result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String memberId = rs.getString("member_id");
                AttendanceStatus status = AttendanceStatus.valueOf(rs.getString("status"));
                MemberDescription description = loadMemberDescription(conn, memberId);
                result.add(new ActivityMemberAttendance(id, description, status));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error loading attendance for activity=" + activityId, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public boolean isAlreadyConfirmed(String activityId, String memberId) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT status FROM activity_attendance " +
                        "WHERE activity_id = ? AND member_id = ?")) {
            stmt.setString(1, activityId);
            stmt.setString(2, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                return "ATTENDED".equals(status) || "MISSING".equals(status);
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error checking attendance status", e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private Optional<ActivityMemberAttendance> findByActivityAndMember(
            Connection conn, String activityId, String memberId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, status FROM activity_attendance " +
                        "WHERE activity_id = ? AND member_id = ?")) {
            stmt.setString(1, activityId);
            stmt.setString(2, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new ActivityMemberAttendance(
                        rs.getString("id"),
                        null,
                        AttendanceStatus.valueOf(rs.getString("status"))
                ));
            }
            return Optional.empty();
        }
    }

    private MemberDescription loadMemberDescription(Connection conn, String memberId)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, first_name, last_name, email, occupation " +
                        "FROM member WHERE id = ?")) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MemberDescription(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("occupation")
                );
            }
        }
        return null;
    }
}
