package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.CollectivityActivity;
import school.hei.springagricole.entity.MonthlyRecurrenceRule;
import school.hei.springagricole.entity.enums.ActivityType;
import school.hei.springagricole.entity.enums.MemberOccupation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CollectivityActivityRepository {
    private final DataSource dataSource;

    public CollectivityActivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CollectivityActivity> saveAll(List<CollectivityActivity> activities) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            for (CollectivityActivity activity : activities) {
                if (activity.getId() == null) {
                    activity.setId(UUID.randomUUID().toString());
                }

                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO collectivity_activity " +
                                "(id, collectivity_id, label, activity_type, " +
                                " executive_date, recurrence_week_ordinal, recurrence_day_of_week) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    stmt.setString(1, activity.getId());
                    stmt.setString(2, activity.getCollectivityId());
                    stmt.setString(3, activity.getLabel());
                    stmt.setString(4, activity.getActivityType().name());

                    if (activity.getExecutiveDate() != null) {
                        stmt.setDate(5, Date.valueOf(activity.getExecutiveDate()));
                    } else {
                        stmt.setNull(5, Types.DATE);
                    }

                    if (activity.getRecurrenceRule() != null) {
                        stmt.setInt(6, activity.getRecurrenceRule().getWeekOrdinal());
                        stmt.setString(7, activity.getRecurrenceRule().getDayOfWeek());
                    } else {
                        stmt.setNull(6, Types.INTEGER);
                        stmt.setNull(7, Types.VARCHAR);
                    }

                    stmt.executeUpdate();
                }

                if (activity.getMemberOccupationConcerned() != null
                        && !activity.getMemberOccupationConcerned().isEmpty()) {
                    try (PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO activity_occupation_concerned (activity_id, occupation) " +
                                    "VALUES (?, ?)")) {
                        for (MemberOccupation occ : activity.getMemberOccupationConcerned()) {
                            stmt.setString(1, activity.getId());
                            stmt.setString(2, occ.name());
                            stmt.addBatch();
                        }
                        stmt.executeBatch();
                    }
                }

                if (activity.getExecutiveDate() != null) {
                    initAttendanceForPunctual(conn, activity);
                }
            }

            conn.commit();
            return activities;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Critical rollback error activities", ex);
            }
            throw new RuntimeException("Error saving activities", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public List<CollectivityActivity> findByCollectivityId(String collectivityId) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, collectivity_id, label, activity_type, executive_date, recurrence_week_ordinal, recurrence_day_of_week FROM collectivity_activity WHERE collectivity_id = ?")) {

            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            List<CollectivityActivity> activities = new ArrayList<>();
            while (rs.next()) {
                activities.add(mapRow(conn, rs));
            }
            return activities;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error loading activities for collectivity=" + collectivityId, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public Optional<CollectivityActivity> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, collectivity_id, label, activity_type, executive_date, recurrence_week_ordinal, recurrence_day_of_week FROM collectivity_activity WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(conn, rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error loading activity id=" + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private void initAttendanceForPunctual(Connection conn, CollectivityActivity activity)
            throws SQLException {

        List<String> memberIds = new ArrayList<>();

        if (activity.getMemberOccupationConcerned() == null
                || activity.getMemberOccupationConcerned().isEmpty()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM member WHERE collectivity_id = ?")) {
                stmt.setString(1, activity.getCollectivityId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) memberIds.add(rs.getString("id"));
            }
        } else {
            StringBuilder inClause = new StringBuilder();
            for (int i = 0; i < activity.getMemberOccupationConcerned().size(); i++) {
                if (i > 0) inClause.append(",");
                inClause.append("?");
            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM member WHERE collectivity_id = ? " +
                            "AND occupation IN (" + inClause + ")")) {
                stmt.setString(1, activity.getCollectivityId());
                int idx = 2;
                for (MemberOccupation occ : activity.getMemberOccupationConcerned()) {
                    stmt.setString(idx++, occ.name());
                }
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) memberIds.add(rs.getString("id"));
            }
        }

        if (memberIds.isEmpty()) return;

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) " +
                        "VALUES (?, ?, ?, 'UNDEFINED', ?)")) {
            for (String memberId : memberIds) {
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, activity.getId());
                stmt.setString(3, memberId);
                stmt.setDate(4, Date.valueOf(activity.getExecutiveDate()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private CollectivityActivity mapRow(Connection conn, ResultSet rs) throws SQLException {
        CollectivityActivity activity = new CollectivityActivity();
        activity.setId(rs.getString("id"));
        activity.setCollectivityId(rs.getString("collectivity_id"));
        activity.setLabel(rs.getString("label"));
        activity.setActivityType(ActivityType.valueOf(rs.getString("activity_type")));

        Date execDate = rs.getDate("executive_date");
        if (execDate != null) activity.setExecutiveDate(execDate.toLocalDate());

        int weekOrdinal = rs.getInt("recurrence_week_ordinal");
        String dayOfWeek = rs.getString("recurrence_day_of_week");
        if (!rs.wasNull() && dayOfWeek != null) {
            activity.setRecurrenceRule(new MonthlyRecurrenceRule(weekOrdinal, dayOfWeek));
        }

        activity.setMemberOccupationConcerned(loadOccupations(conn, activity.getId()));
        return activity;
    }

    private List<MemberOccupation> loadOccupations(Connection conn, String activityId)
            throws SQLException {
        List<MemberOccupation> occupations = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT occupation FROM activity_occupation_concerned WHERE activity_id = ?")) {
            stmt.setString(1, activityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                occupations.add(MemberOccupation.valueOf(rs.getString("occupation")));
            }
        }
        return occupations;
    }
}