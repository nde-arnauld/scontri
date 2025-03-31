package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Event;
import models.Org_Event;
import models.User;

public class Org_EventController {

    private Org_Event org_Event;

    public Org_EventController() {
        this.org_Event = new Org_Event();
    }

    public boolean createOrgEvent(int idUser, int idEvent) {
        org_Event = new Org_Event(idUser, idEvent);
        return org_Event.enregistrer();
    }

    public List<HashMap<String, Object>> listOrgsEvent(int idEvent) {
        List<User> organisateurs = org_Event.organisateursEvenement(idEvent);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

        for (User user : organisateurs) {
            HashMap<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("id", user.getIdUser());
            userMap.put("nom", user.getNom());
            userMap.put("prenom", user.getPrenom());
            userMap.put("telephone", user.getTelephone());
            userMap.put("email", user.getEmail());
            userMap.put("adresse", user.getAdresse());
            userMap.put("role_systeme", user.getRoleSysteme());
            result.add(userMap);
        }
        return result;
    }

    public List<HashMap<String, Object>> getEventCreatedByOrg(int idUser) {
        List<Event> events = org_Event.evenementsDeOrganisateur(idUser);
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (Event event : events) {
            HashMap<String, Object> eventMap = new HashMap<>();
            eventMap.put("id", event.getIdEvent());
            eventMap.put("nom", event.getNom());
            eventMap.put("dateDebut", event.getDateDebut());
            eventMap.put("description", event.getDescription());
            eventMap.put("capacite", event.getCapacite());
            eventMap.put("prix", event.getPrix());
            eventMap.put("dateFin", event.getDateFin());
            eventMap.put("status", event.getStatus());
            eventMap.put("idLieu", event.getIdLieu());
            eventMap.put("idCat", event.getIdCat());
            result.add(eventMap);
        }
        return result;
    }
}
