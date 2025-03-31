package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Event;

public class EventController {

    private Event event;

    public EventController() {
        this.event = new Event();
    }

    public int createEvent(String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, String status,
            int idLieu, int idCat) {

        event = new Event(0, nom, description, capacite, prix, dateDebut, dateFin,
                LocalDateTime.now(), status, idLieu, idCat);

        return event.enregistrer();
    }

    public boolean updateEvent(int id, String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, String status,
            int idLieu, int idCat) {

        event = new Event(id, nom, description, capacite, prix, dateDebut, dateFin,
                LocalDateTime.now(), status, idLieu, idCat);
        return event.modifier();
    }

    public HashMap<String, Object> getEventById(int idEvent) {
        Event myEvent = event.evenementParId(idEvent);
        if (myEvent == null)
            return null;

        HashMap<String, Object> eventInfos = new HashMap<String, Object>();

        eventInfos.put("id", myEvent.getIdEvent());
        eventInfos.put("nom", myEvent.getNom());
        eventInfos.put("description", myEvent.getDescription());
        eventInfos.put("capacite", myEvent.getCapacite());
        eventInfos.put("prix", myEvent.getPrix());
        eventInfos.put("dateDebut", myEvent.getDateDebut());
        eventInfos.put("dateFin", myEvent.getDateFin());
        eventInfos.put("dateCreation", myEvent.getDateCreation());
        eventInfos.put("status", myEvent.getStatus());
        eventInfos.put("idLieu", myEvent.getIdLieu());
        eventInfos.put("idCat", myEvent.getIdCat());

        return eventInfos;
    }

    public boolean deleteEvent(int idEvent) {
        return event.supprimer(idEvent);
    }

    public List<HashMap<String, Object>> listEvents() {
        List<Event> myEvents = event.tousLesEvenements();
        List<HashMap<String, Object>> eventList = new ArrayList<HashMap<String, Object>>();

        for (Event myEvent : myEvents) {
            HashMap<String, Object> eventInfos = new HashMap<>();
            eventInfos.put("id", myEvent.getIdEvent());
            eventInfos.put("nom", myEvent.getNom());
            eventInfos.put("description", myEvent.getDescription());
            eventInfos.put("capacite", myEvent.getCapacite());
            eventInfos.put("prix", myEvent.getPrix());
            eventInfos.put("dateDebut", myEvent.getDateDebut());
            eventInfos.put("dateFin", myEvent.getDateFin());
            eventInfos.put("dateCreation", myEvent.getDateCreation());
            eventInfos.put("status", myEvent.getStatus());
            eventInfos.put("idLieu", myEvent.getIdLieu());
            eventInfos.put("idCat", myEvent.getIdCat());
            eventList.add(eventInfos);
        }

        return eventList;
    }

    public boolean eventExists(int eventId) {
        return event.existe(eventId);
    }
}
