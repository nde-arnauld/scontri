package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Org_Event;
import models.User;

public class Org_EventDAO {
    public static final String TB_NAME = "Org_evenement";
    public static final String TB_ID_USER = "id_user";
    public static final String TB_ID_EVENT = "id_event";

    private Connection conn;

    public Org_EventDAO() {
        this.conn = Database.getConnection();
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

    public List<Event> getEventsCreatedByUser(int idUser) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM " + TB_NAME + " p " +
                "JOIN " + EventDAO.TB_NAME + " e ON p.id_event = e.id_event " +
                "WHERE p.id_user = ? AND e.status = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setString(2, "actif");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt(TB_ID_EVENT),
                        rs.getString(EventDAO.TB_NOM),
                        rs.getString(EventDAO.TB_DESCRIPTION),
                        rs.getInt(EventDAO.TB_CAPACITE),
                        rs.getDouble(EventDAO.TB_PRIX),
                        rs.getTimestamp(EventDAO.TB_DATE_DEBUT).toLocalDateTime(),
                        rs.getTimestamp(EventDAO.TB_DATE_FIN).toLocalDateTime(),
                        rs.getTimestamp(EventDAO.TB_DATE_CREATION).toLocalDateTime(),
                        rs.getString(EventDAO.TB_STATUS),
                        rs.getInt(EventDAO.TB_ID_LIEU),
                        rs.getInt(EventDAO.TB_ID_CAT));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
