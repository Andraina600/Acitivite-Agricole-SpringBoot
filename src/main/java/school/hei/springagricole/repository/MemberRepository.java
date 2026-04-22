package school.hei.springagricole.repository;

import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.enums.Gender;
import school.hei.springagricole.entity.Member;
import school.hei.springagricole.entity.enums.MemberOccupation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Member> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, first_name, last_name, birth_date, gender, address, " +
                        "profession, phone_number, email, occupation, collectivity_id, admission_date " +
                        "FROM member WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Member member = mapRowToMember(rs);
                member.setReferees(findRefereesByMemberId(conn, member.getId()));
                return Optional.of(member);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for member with ID: " + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(UUID.randomUUID().toString());
        }

        LocalDate today = LocalDate.now();

        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO member (id, first_name, last_name, birth_date, gender, address, " +
                            "profession, phone_number, email, occupation, collectivity_id, admission_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, member.getId());
                stmt.setString(2, member.getFirstName());
                stmt.setString(3, member.getLastName());
                stmt.setDate(4, Date.valueOf(LocalDate.parse(member.getBirthDate())));
                stmt.setString(5, member.getGender().name());
                stmt.setString(6, member.getAddress());
                stmt.setString(7, member.getProfession());
                stmt.setLong(8, member.getPhoneNumber());
                stmt.setString(9, member.getEmail());
                stmt.setString(10, member.getOccupation().name());
                stmt.setString(11, member.getCollectivityId());
                stmt.setDate(12, Date.valueOf(today));

                stmt.executeUpdate();
            }

            if (member.getReferees() != null && !member.getReferees().isEmpty()) {
                try (PreparedStatement stmtRef = conn.prepareStatement(
                        "INSERT INTO member_referee (member_id, referee_id) VALUES (?, ?)")) {
                    for (Member referee : member.getReferees()) {
                        stmtRef.setString(1, member.getId());
                        stmtRef.setString(2, referee.getId());
                        stmtRef.addBatch();
                    }
                    stmtRef.executeBatch();
                }
            }

            conn.commit();
            member.setAdmissionDate(today);
            return member;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Critical error during member rollback", ex);
            }
            throw new RuntimeException("Error when saving the member", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    private Member mapRowToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getString("id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));

        Date birthDate = rs.getDate("birth_date");
        member.setBirthDate(birthDate != null ? birthDate.toLocalDate().toString() : null);

        String genderStr = rs.getString("gender");
        if (genderStr != null) member.setGender(Gender.valueOf(genderStr));

        member.setAddress(rs.getString("address"));
        member.setProfession(rs.getString("profession"));
        member.setPhoneNumber(rs.getInt("phone_number"));

        member.setEmail(rs.getString("email"));

        String occupationStr = rs.getString("occupation");
        if (occupationStr != null) member.setOccupation(MemberOccupation.valueOf(occupationStr));

        member.setCollectivityId(rs.getString("collectivity_id"));

        Date admissionDate = rs.getDate("admission_date");
        if (admissionDate != null) member.setAdmissionDate(admissionDate.toLocalDate());

        return member;
    }

    private List<Member> findRefereesByMemberId(Connection conn, String memberId) throws SQLException {
        List<Member> referees = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender, m.address, " +
                        "m.profession, m.phone_number, m.email, m.occupation, m.collectivity_id, m.admission_date " +
                        "FROM member m " +
                        "JOIN member_referee mr ON m.id = mr.referee_id " +
                        "WHERE mr.member_id = ?")) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                referees.add(mapRowToMember(rs));
            }
        }
        return referees;
    }
}