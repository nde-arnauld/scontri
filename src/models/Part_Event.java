package models;

import java.time.LocalDateTime;

import utils.enums.PartEventStatus;

public class Part_Event {
    private int id_user;
    private int id_event;
    private PartEventStatus status;
    private String presence;
    private LocalDateTime date_part;

    public Part_Event(int id_user, int id_event, PartEventStatus status, String presence, LocalDateTime date_part) {
        this.id_user = id_user;
        this.id_event = id_event;
        this.status = status;
        this.presence = presence;
        this.date_part = date_part;
    }

    public Part_Event(int id_user, int id_event) {
        this(id_user, id_event, PartEventStatus.EN_ATTENTE, "non", LocalDateTime.now());
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
}
