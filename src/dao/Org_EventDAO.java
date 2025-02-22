package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Org_Event;
import models.User;

public class Org_EventDAO {
	public static final String TB_NAME = "Org_evenement";
    public static final String TB_ID_USER = "id_user";
    public static final String TB_ID_EVENT = "id_event";
    
    private Connection conn;

    public Org_EventDAO(Connection con) {
        this.conn = con;
    }
    
    public boolean addOrgEvent(Org_Event org_Event) {
        String sql = "INSERT INTO " + TB_NAME + " (" + TB_ID_USER + ", " + TB_ID_EVENT + ") VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, org_Event.getId_user());
            stmt.setInt(2, org_Event.getId_event());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getOrgsEvent(int idEvent) {
        List<User> organisateurs = new ArrayList<>();
        String sql = "SELECT u.* FROM " + TB_NAME + " o " +
                     "JOIN Utilisateur u ON o.id_user = u.id_user " +
                     "WHERE o.id_event = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEvent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("telephone"),
                            rs.getString("email"),
                            rs.getString("adresse"),
                            rs.getString("mot_de_passe"),
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getDate("date_inscription").toLocalDate(),
                            rs.getString("role_systeme"));
                    organisateurs.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organisateurs;
    }

}
