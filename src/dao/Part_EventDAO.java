package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Event;
import models.Part_Event;
import models.User;
import utils.enums.PartEventStatus;

public class Part_EventDAO {
    public static final String TB_NAME = "part_evenement";
    public static final String TB_ID_USER = "id_user";
    public static final String TB_ID_EVENT = "id_event";
    public static final String TB_STATUS = "status";
    public static final String TB_PRESENCE = "presence";
    public static final String TB_DATE_PART = "date_part";

    private Connection conn;

    public Part_EventDAO() {
        this.conn = Database.getConnection();
    }

    public boolean addPartEvent(Part_Event partEvent) {
        String sql = "INSERT INTO " + TB_NAME + " (" +
                TB_ID_USER + ", " +
                TB_ID_EVENT + ", " +
                TB_STATUS + ", " +
                TB_PRESENCE + ", " +
                TB_DATE_PART +
                ") VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, partEvent.getIdUser());
            stmt.setInt(2, partEvent.getIdEvent());
            stmt.setString(3, partEvent.getStatus().toString());
            stmt.setString(4, partEvent.getPresence());
            stmt.setObject(5, partEvent.getDatePart());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removePartEvent(int idUser, int idEvent) {
        String sql = "UPDATE " + TB_NAME + " SET " + TB_STATUS + " = ? WHERE " + TB_ID_USER + " = ? AND " + TB_ID_EVENT
                + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, PartEventStatus.ANNULEE.toString());
            stmt.setInt(2, idUser);
            stmt.setInt(3, idEvent);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePartEventStatus(int idUser, int idEvent, String status) {
        String sql = "UPDATE " + TB_NAME + " SET " + TB_STATUS + " = ? WHERE " + TB_ID_USER + " = ? AND " + TB_ID_EVENT
                + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, idUser);
            stmt.setInt(3, idEvent);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Part_Event getPartEvent(int idUser, int idEvent) {
        String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_ID_USER + " = ? AND " + TB_ID_EVENT + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idEvent);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Part_Event(
                        rs.getInt(TB_ID_USER),
                        rs.getInt(TB_ID_EVENT),
                        PartEventStatus.valueOf(rs.getString(TB_STATUS)),
                        rs.getString(TB_PRESENCE),
                        rs.getObject(TB_DATE_PART, LocalDateTime.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Part_Event> getPartsEvent(int idEvent) {
        List<Part_Event> partsEvents = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_ID_EVENT + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEvent);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Part_Event partEvent = new Part_Event(
                        rs.getInt(TB_ID_USER),
                        rs.getInt(TB_ID_EVENT),
                        PartEventStatus.valueOf(rs.getString(TB_STATUS).toUpperCase()),
                        rs.getString(TB_PRESENCE),
                        rs.getObject(TB_DATE_PART, LocalDateTime.class));
                partsEvents.add(partEvent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partsEvents;
    }

    public List<User> getParticipantsFromEvent(int idEvent) {
        List<User> participants = new ArrayList<>();
        String sql = "SELECT u.* FROM " + TB_NAME + " p " +
                "JOIN Utilisateur u ON p.id_user = u.id_user " +
                "WHERE p.id_event = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEvent);
            ResultSet rs = stmt.executeQuery();
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
                participants.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    // récupère les participants et le status de leurs demandes
    public List<Map<String, Object>> getParticipantsWithStatusFromEvent(int idEvent) {
        List<Map<String, Object>> participants = new ArrayList<>();
        String sql = "SELECT u.*, p." + TB_STATUS + ", p." + TB_PRESENCE + ", p." + TB_DATE_PART + " FROM " + TB_NAME
                + " p " +
                "JOIN Utilisateur u ON p.id_user = u.id_user " +
                "WHERE p.id_event = ? AND ( " + TB_STATUS + " = ? OR " + TB_STATUS + " = ? )";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEvent);
            stmt.setString(2, PartEventStatus.VALIDEE.toString());
            stmt.setString(3, PartEventStatus.EN_ATTENTE.toString());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> participantData = new HashMap<>();
                participantData.put("id", rs.getInt("id_user"));
                participantData.put("nom", rs.getString("nom"));
                participantData.put("prenom", rs.getString("prenom"));
                participantData.put("telephone", rs.getString("telephone"));
                participantData.put("email", rs.getString("email"));
                participantData.put("adresse", rs.getString("adresse"));
                participantData.put("mot_de_passe", rs.getString("mot_de_passe"));
                participantData.put("date_naissance", rs.getDate("date_naissance").toLocalDate());
                participantData.put("date_inscription", rs.getDate("date_inscription").toLocalDate());
                participantData.put("role_systeme", rs.getString("role_systeme"));
                participantData.put("status", rs.getString(TB_STATUS));
                participantData.put("presence", rs.getString(TB_PRESENCE));
                participantData.put("datePart", rs.getObject(TB_DATE_PART, LocalDateTime.class));
                participants.add(participantData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public List<Event> getEventsForUser(int idUser) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM " + TB_NAME + " p " +
                "JOIN " + EventDAO.TB_NAME + " e ON p.id_event = e.id_event " +
                "WHERE p.id_user = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
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

    public List<Map<String, Object>> getEventsWithStatusForUser(int idUser) {
        List<Map<String, Object>> eventsWithStatus = new ArrayList<>();
        String sql = "SELECT e.*, p." + TB_STATUS + " as part_status , p." + TB_PRESENCE + ", p." + TB_DATE_PART
                + " FROM " + TB_NAME + " p " +
                "JOIN " + EventDAO.TB_NAME + " e ON p.id_event = e.id_event " +
                "WHERE p.id_user = ?  AND (  p." + TB_STATUS + " = ? OR  p." + TB_STATUS + " = ? )";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setString(2, PartEventStatus.VALIDEE.toString());
            stmt.setString(3, PartEventStatus.EN_ATTENTE.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> eventData = new HashMap<>();
                eventData.put("id", rs.getInt(TB_ID_EVENT));
                eventData.put("nom", rs.getString(EventDAO.TB_NOM));
                eventData.put("description", rs.getString(EventDAO.TB_DESCRIPTION));
                eventData.put("capacite", rs.getInt(EventDAO.TB_CAPACITE));
                eventData.put("prix", rs.getDouble(EventDAO.TB_PRIX));
                eventData.put("dateDebut", rs.getTimestamp(EventDAO.TB_DATE_DEBUT).toLocalDateTime());
                eventData.put("dateFin", rs.getTimestamp(EventDAO.TB_DATE_FIN).toLocalDateTime());
                eventData.put("dateCreation", rs.getTimestamp(EventDAO.TB_DATE_CREATION).toLocalDateTime());
                eventData.put("status", rs.getString(EventDAO.TB_STATUS));
                eventData.put("idLieu", rs.getInt(EventDAO.TB_ID_LIEU));
                eventData.put("idCat", rs.getInt(EventDAO.TB_ID_CAT));
                eventData.put("partStatus", rs.getString("part_status"));
                eventData.put("presence", rs.getString(TB_PRESENCE));
                eventData.put("datePart", rs.getObject(TB_DATE_PART, LocalDateTime.class));
                eventsWithStatus.add(eventData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsWithStatus;
    }

    public boolean userParticipatesInEvent(int idUser, int idEvent) {
        String sql = "SELECT COUNT(*) FROM " + TB_NAME + " WHERE " + TB_ID_USER + " = ? AND " + TB_ID_EVENT + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            stmt.setInt(2, idEvent);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
