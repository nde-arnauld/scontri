package controllers;

import java.time.LocalDateTime;
import java.util.List;
import dao.EventDAO;
import dao.Org_EventDAO;
import models.Event;
import models.Org_Event;

public class EventController {

    private EventDAO eventDAO;

    public EventController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
        
    }

    public int createEvent(String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, String status,
            int idLieu, int idCat) {

        Event event = new Event(0, nom, description, capacite, prix, dateDebut, dateFin,
                LocalDateTime.now(), status, idLieu, idCat);
                        
         return  eventDAO.addEvent(event);
    }

    public boolean updateEvent(int id, String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, String status,
            int idLieu, int idCat) {

        Event event = new Event(id, nom, description, capacite, prix, dateDebut, dateFin,
                LocalDateTime.now(), status, idLieu, idCat);
        return eventDAO.updateEvent(event);
    }

    public Event getEventById(int idEvent) {
        return eventDAO.getEventById(idEvent);
    }

    public boolean deleteEvent(int id) {
        return eventDAO.deleteEvent(id);
    }

    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    public boolean eventExists(int eventId) {
        return eventDAO.eventExists(eventId);
    }
}
