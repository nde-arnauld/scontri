package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Part_Event;
import models.User;
import utils.enums.PartEventStatus;

public class Part_EventDAO {
    private final String TB_NAME = "part_event";
    private final String TB_ID_USER = "id_user";
    private final String TB_ID_EVENT = "id_event";
    private final String TB_STATUS = "status";
    private final String TB_PRESENCE = "presence";
    private final String TB_DATE_PART = "date_part";

    private Connection conn;

    public Part_EventDAO(Connection con) {
        this.conn = con;
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

    public List<User> getParticipantsForEvent(int idEvent) {
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

    public List<Part_Event> getEventsForUser(int idUser) {
        List<Part_Event> events = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_ID_USER + " = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Part_Event event = new Part_Event(
                        rs.getInt(TB_ID_USER),
                        rs.getInt(TB_ID_EVENT),
                        PartEventStatus.valueOf(rs.getString(TB_STATUS)),
                        rs.getString(TB_PRESENCE),
                        rs.getObject(TB_DATE_PART, LocalDateTime.class));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
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
