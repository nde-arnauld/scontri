package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import dao.Part_EventDAO;
import utils.enums.PartEventStatus;

public class Part_Event {
    private int id_user;
    private int id_event;
    private PartEventStatus status;
    private String presence;
    private LocalDateTime date_part;

    private Part_EventDAO part_EventDAO;

    public Part_Event(int id_user, int id_event, PartEventStatus status, String presence, LocalDateTime date_part) {
        this.id_user = id_user;
        this.id_event = id_event;
        this.status = status;
        this.presence = presence;
        this.date_part = date_part;

        this.part_EventDAO = new Part_EventDAO();
    }

    public Part_Event(int id_user, int id_event) {
        this(id_user, id_event, PartEventStatus.EN_ATTENTE, "non", LocalDateTime.now());
    }

    public Part_Event() {
        this(0, 0);
    }

    // Getters et Setters
    public int getIdUser() {
        return id_user;
    }

    public int getIdEvent() {
        return id_event;
    }

    public PartEventStatus getStatus() {
        return status;
    }

    public void setStatus(PartEventStatus status) {
        this.status = status;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public LocalDateTime getDatePart() {
        return date_part;
    }

    public void setDatePart(LocalDateTime date_part) {
        this.date_part = date_part;
    }

    @Override
    public String toString() {
        return "PartEvent{" +
                "id_user=" + id_user +
                ", id_event=" + id_event +
                ", status=" + status +
                ", presence='" + presence + '\'' +
                ", date_part=" + date_part +
                '}';
    }

    public boolean enregistrer() {
        return part_EventDAO.addPartEvent(this);
    }

    public boolean participe(int idUser, int idEvent) {
        return part_EventDAO.userParticipatesInEvent(idUser, idEvent);
    }

    public boolean annuler(int idUser, int idEvent) {
        return part_EventDAO.removePartEvent(idUser, idEvent);
    }

    public boolean modifierStatus(int idUser, int idEvent, String status) {
        return part_EventDAO.updatePartEventStatus(idUser, idEvent, status);
    }

    public List<Event> evenementsParticipe(int idUser) {
        return part_EventDAO.getEventsForUser(idUser);
    }

    public List<User> participantsEvenement(int idEvent) {
        return part_EventDAO.getParticipantsFromEvent(idEvent);
    }

    public List<Map<String, Object>> participantsEvenementAvecStatus(int idEvent) {
        return part_EventDAO.getParticipantsWithStatusFromEvent(idEvent);
    }

    public List<Map<String, Object>> evenementsParticipeAvecStatus(int idUser) {
        return part_EventDAO.getEventsWithStatusForUser(idUser);
    }
}
