package school.hei.springagricole.repository;


import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.Collectivity;
import school.hei.springagricole.entity.CollectivityStructure;
import school.hei.springagricole.entity.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CollectivityRepository {
    private final DataSource dataSource;
    private final MemberRepository memberRepository;

    public CollectivityRepository(DataSource dataSource, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
    }

    public Collectivity save(Collectivity collectivity) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO collectivity (id, number, name, location, creation_date) " +
                            "VALUES (?, ?, ?, ?, ?)")) {
                stmt.setString(1, collectivity.getId());
                if (collectivity.getNumber() != null) {
                    stmt.setInt(2, collectivity.getNumber());
                } else {
                    stmt.setNull(2, Types.INTEGER);
                }
                if (collectivity.getName() != null) {
                    stmt.setString(3, collectivity.getName());
                } else {
                    stmt.setNull(3, Types.VARCHAR);
                }
                stmt.setString(4, collectivity.getLocation());
                stmt.setDate(5, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO collectivity_structure " +
                            "(collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id) " +
                            "VALUES (?, ?, ?, ?, ?)")) {
                stmt.setString(1, collectivity.getId());
                stmt.setString(2, collectivity.getStructure().getPresident().getId());
                stmt.setString(3, collectivity.getStructure().getVicePresident().getId());
                stmt.setString(4, collectivity.getStructure().getTreasurer().getId());
                stmt.setString(5, collectivity.getStructure().getSecretary().getId());
                stmt.executeUpdate();
            }

            if (collectivity.getMembers() != null && !collectivity.getMembers().isEmpty()) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE member SET collectivity_id = ? WHERE id = ?")) {
                    for (Member member : collectivity.getMembers()) {
                        stmt.setString(1, collectivity.getId());
                        stmt.setString(2, member.getId());
                        stmt.addBatch();
                        member.setCollectivityId(collectivity.getId());
                    }
                    stmt.executeBatch();
                }
            }
            updateStructureMemberCollectivityId(collectivity);

            conn.commit();
            return collectivity;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique lors du rollback collectivité", ex);
            }
            throw new RuntimeException("Erreur lors de la sauvegarde de la collectivité", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public Collectivity assignIdentity(String collectivityId, Integer number, String name) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE collectivity SET number = ?, name = ? WHERE id = ?")) {
                stmt.setInt(1, number);
                stmt.setString(2, name);
                stmt.setString(3, collectivityId);
                stmt.executeUpdate();
            }

            conn.commit();

            return findById(collectivityId).orElseThrow(() ->
                    new RuntimeException("Collectivity not found after identity assignment"));

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {
                throw new RuntimeException("Erreur critique lors du rollback identity", ex);
            }
            throw new RuntimeException("Erreur lors de l'attribution de l'identité", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            dataSource.closeConnection(conn);
        }
    }

    public Optional<Collectivity> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM collectivity WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(rs.getString("id"));

                int number = rs.getInt("number");
                collectivity.setNumber(rs.wasNull() ? null : number);

                collectivity.setName(rs.getString("name"));

                collectivity.setLocation(rs.getString("location"));
                collectivity.setStructure(loadStructure(conn, id));
                collectivity.setMembers(loadMembers(conn, id));
                return Optional.of(collectivity);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement de la collectivité: " + id, e);
        } finally {
            dataSource.closeConnection(conn);
        }
    }

    private void updateStructureMemberCollectivityId(Collectivity collectivity) {
        CollectivityStructure s = collectivity.getStructure();
        if (s == null) return;
        String cid = collectivity.getId();
        if (s.getPresident() != null)     s.getPresident().setCollectivityId(cid);
        if (s.getVicePresident() != null) s.getVicePresident().setCollectivityId(cid);
        if (s.getTreasurer() != null)     s.getTreasurer().setCollectivityId(cid);
        if (s.getSecretary() != null)     s.getSecretary().setCollectivityId(cid);
    }

    private CollectivityStructure loadStructure(Connection conn, String collectivityId)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM collectivity_structure WHERE collectivity_id = ?")) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CollectivityStructure structure = new CollectivityStructure();
                structure.setPresident(
                        memberRepository.findById(rs.getString("president_id")).orElse(null));
                structure.setVicePresident(
                        memberRepository.findById(rs.getString("vice_president_id")).orElse(null));
                structure.setTreasurer(
                        memberRepository.findById(rs.getString("treasurer_id")).orElse(null));
                structure.setSecretary(
                        memberRepository.findById(rs.getString("secretary_id")).orElse(null));
                return structure;
            }
        }
        return null;
    }

    private List<Member> loadMembers(Connection conn, String collectivityId) throws SQLException {
        List<Member> members = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id FROM member WHERE collectivity_id = ?")) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                memberRepository.findById(rs.getString("id")).ifPresent(members::add);
            }
        }
        return members;
    }
}