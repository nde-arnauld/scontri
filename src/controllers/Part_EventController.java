package controllers;

import models.Event;
import models.Part_Event;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part_EventController {
    private Event event;
    private Part_Event partEvent;

    public Part_EventController() {
        this.event = new Event();
        this.partEvent = new Part_Event();
    }

    public boolean addPartEvent(int idUser, int idEvent) {
        if (partEvent.participe(idUser, idEvent)) {
            return false;
        }

        event = event.evenementParId(idEvent);

        if (event.getStatus().compareTo("actif") != 0) {
            return false;
        }

        Part_Event partEvent = new Part_Event(idUser, idEvent);

        return partEvent.enregistrer();
    }

    public boolean cancelParticipation(int idUser, int idEvent) { // annulation par le participant lui même
        if (!partEvent.participe(idUser, idEvent)) {
            return false;
        }

        return partEvent.annuler(idUser, idEvent);
    }

    public boolean updateParticipation(int idUser, int idEvent, String status) { // modification par l'organisateur de
                                                                                 // l'event
        if (!partEvent.participe(idUser, idEvent)) {
            return false;
        }

        return partEvent.modifierStatus(idUser, idEvent, status);
    }

    public List<HashMap<String, Object>> getEventsForUser(int idUser) {
        List<Event> events = partEvent.evenementsParticipe(idUser);
        List<HashMap<String, Object>> eventList = new ArrayList<>();

        for (Event event : events) {
            HashMap<String, Object> eventInfo = new HashMap<>();
            eventInfo.put("id", event.getIdEvent());
            eventInfo.put("nom", event.getNom());
            eventInfo.put("description", event.getDescription());
            eventInfo.put("capacite", event.getCapacite());
            eventInfo.put("prix", event.getPrix());
            eventInfo.put("dateDebut", event.getDateDebut());
            eventInfo.put("dateFin", event.getDateFin());
            eventInfo.put("dateCreation", event.getDateCreation());
            eventInfo.put("status", event.getStatus());
            eventInfo.put("idLieu", event.getIdLieu());
            eventInfo.put("idCat", event.getIdCat());

            eventList.add(eventInfo);
        }
        return eventList;
    }

    public List<HashMap<String, Object>> getUsersForEvent(int idEvent) {
        List<User> users = partEvent.participantsEvenement(idEvent);
        List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

        for (User user : users) {
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("idUser", user.getIdUser());
            userInfo.put("nom", user.getNom());
            userInfo.put("prenom", user.getPrenom());
            userInfo.put("telephone", user.getTelephone());
            userInfo.put("email", user.getEmail());
            userInfo.put("adresse", user.getAdresse());
            userInfo.put("dateNaissance", user.getDateNaissance());
            userInfo.put("dateInscription", user.getDateInscription());
            userInfo.put("roleSysteme", user.getRoleSysteme());
            userList.add(userInfo);
        }
        return userList;
    }

    public List<Map<String, Object>> getParticipantsInfoForEvent(int idEvent) {
        return partEvent.participantsEvenementAvecStatus(idEvent);
    }

    public List<Map<String, Object>> getEventsInfoForUser(int idUser) {
        return partEvent.evenementsParticipeAvecStatus(idUser);
    }
}
