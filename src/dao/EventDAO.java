package dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Event;

public class EventDAO {
    public static final String TB_NAME = "evenement";
    public static final String TB_ID_EVENT = "id_event";
    public static final String TB_NOM = "nom";
    public static final String TB_DESCRIPTION = "description";
    public static final String TB_CAPACITE = "capacite";
    public static final String TB_PRIX = "prix";
    public static final String TB_DATE_DEBUT = "date_debut";
    public static final String TB_DATE_FIN = "date_fin";
    public static final String TB_DATE_CREATION = "date_creation";
    public static final String TB_STATUS = "status";
    public static final String TB_ID_LIEU = "id_lieu";
    public static final String TB_ID_CAT = "id_cat";

    private Connection conn;

    public EventDAO(Connection con) {

        this.conn = con;

    }

    public boolean addEvent(Event event) {
        String sql = "INSERT INTO " + TB_NAME + " (" +
                TB_NOM + ", " +
                TB_DESCRIPTION + ", " +
                TB_CAPACITE + ", " +
                TB_PRIX + ", " +
                TB_DATE_DEBUT + ", " +
                TB_DATE_FIN + ", " +
                TB_DATE_CREATION + ", " +
                TB_STATUS + ", " +
                TB_ID_LIEU + ", " +
                TB_ID_CAT +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getNom());
            stmt.setString(2, event.getDescription());
            stmt.setInt(3, event.getCapacite());
            stmt.setDouble(4, event.getPrix());
            stmt.setTimestamp(5, Timestamp.valueOf(event.getDateDebut()));
            stmt.setTimestamp(6, Timestamp.valueOf(event.getDateFin()));
            stmt.setTimestamp(7, Timestamp.valueOf(event.getDateCreation()));
            stmt.setString(8, event.getStatus());
            stmt.setInt(9, event.getIdLieu());
            stmt.setInt(10, event.getIdCat());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEvent(Event event) {
        String sql = "UPDATE " + TB_NAME + " SET " +
                TB_NOM + " = ?, " +
                TB_DESCRIPTION + " = ?, " +
                TB_CAPACITE + " = ?, " +
                TB_PRIX + " = ?, " +
                TB_DATE_DEBUT + " = ?, " +
                TB_DATE_FIN + " = ?, " +
                TB_STATUS + " = ?, " +
                TB_ID_LIEU + " = ?, " +
                TB_ID_CAT + " = ? " +
                "WHERE " + TB_ID_EVENT + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getNom());
            stmt.setString(2, event.getDescription());
            stmt.setInt(3, event.getCapacite());
            stmt.setDouble(4, event.getPrix());
            stmt.setTimestamp(5, Timestamp.valueOf(event.getDateDebut()));
            stmt.setTimestamp(6, Timestamp.valueOf(event.getDateFin()));
            stmt.setString(7, event.getStatus());
            stmt.setInt(8, event.getIdLieu());
            stmt.setInt(9, event.getIdCat());
            stmt.setInt(10, event.getIdEvent());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM " + TB_NAME + " WHERE " + TB_ID_EVENT + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Event getEventById(int idEvent) {
        String sql = "SELECT * FROM " + TB_NAME + " WHERE " + TB_ID_EVENT + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEvent);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Event(
                        rs.getInt(TB_ID_EVENT),
                        rs.getString(TB_NOM),
                        rs.getString(TB_DESCRIPTION),
                        rs.getInt(TB_CAPACITE),
                        rs.getDouble(TB_PRIX),
                        rs.getTimestamp(TB_DATE_DEBUT).toLocalDateTime(),
                        rs.getTimestamp(TB_DATE_FIN).toLocalDateTime(),
                        rs.getTimestamp(TB_DATE_CREATION).toLocalDateTime(),
                        rs.getString(TB_STATUS),
                        rs.getInt(TB_ID_LIEU),
                        rs.getInt(TB_ID_CAT));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_NAME + " WHERE status = 'actif'";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt(TB_ID_EVENT),
                        rs.getString(TB_NOM),
                        rs.getString(TB_DESCRIPTION),
                        rs.getInt(TB_CAPACITE),
                        rs.getDouble(TB_PRIX),
                        rs.getTimestamp(TB_DATE_DEBUT).toLocalDateTime(),
                        rs.getTimestamp(TB_DATE_FIN).toLocalDateTime(),
                        rs.getTimestamp(TB_DATE_CREATION).toLocalDateTime(),
                        rs.getString(TB_STATUS),
                        rs.getInt(TB_ID_LIEU),
                        rs.getInt(TB_ID_CAT));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean eventExists(int eventId) {
        String sql = "SELECT COUNT(*) FROM " + TB_NAME + " WHERE " + TB_ID_EVENT + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
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
