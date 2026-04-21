package school.hei.springagricole.repository;


import org.springframework.stereotype.Repository;
import school.hei.springagricole.config.DataSource;
import school.hei.springagricole.entity.Collectivity;
import school.hei.springagricole.entity.CollectivityStructure;

import java.sql.*;
import java.time.LocalDate;
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

    /**
     * Sauvegarde une collectivité complète (table collectivity + structure + liaison membres).
     */
    public Collectivity save(Collectivity collectivity) {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);

            // 1. Insertion dans la table collectivity
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO collectivity (id, location, creation_date) VALUES (?, ?, ?)")) {
                stmt.setString(1, collectivity.getId());
                stmt.setString(2, collectivity.getLocation());
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();
            }

            // 2. Insertion de la structure (president, VP, treasurer, secretary)
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

            // 3. Mise à jour du collectivity_id pour chaque membre rattaché
            if (collectivity.getMembers() != null && !collectivity.getMembers().isEmpty()) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE member SET collectivity_id = ? WHERE id = ?")) {
                    for (Member member : collectivity.getMembers()) {
                        stmt.setString(1, collectivity.getId());
                        stmt.setString(2, member.getId());
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                }
            }

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

    /**
     * Charge une collectivité complète depuis la base (avec structure et membres).
     */
    public Optional<Collectivity> findById(String id) {
        Connection conn = dataSource.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM collectivity WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(rs.getString("id"));
                collectivity.setLocation(rs.getString("location"));

                // Chargement de la structure
                collectivity.setStructure(loadStructure(conn, id));

                // Chargement des membres
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

    /**
     * Charge la structure d'une collectivité depuis la DB.
     */
    private CollectivityStructure loadStructure(Connection conn, String collectivityId) throws SQLException {
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

    /**
     * Charge tous les membres d'une collectivité depuis la DB.
     */
    private List<Member> loadMembers(Connection conn, String collectivityId) throws SQLException {
        List<Member> members = new java.util.ArrayList<>();
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