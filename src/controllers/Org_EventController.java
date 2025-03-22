package controllers;

import java.util.List;

import dao.Org_EventDAO;
import models.Event;
import models.Org_Event;
import models.User;

public class Org_EventController {

    private Org_EventDAO orgEventDAO;

    public Org_EventController(Org_EventDAO orgEventDAO) {
        this.orgEventDAO = orgEventDAO;
    }
    
    public boolean createOrgEvent(int idUser, int idEvent) {

        Org_Event orgEvent = new Org_Event(idUser, idEvent);

        return orgEventDAO.addOrgEvent(orgEvent);
    }
    
    public List<User> listOrgsEvent(int idEvent) {
        return orgEventDAO.getOrgsEvent(idEvent);
    }
    
    public List<Event> getEventCreatedByOrg(int idUser) {
        return orgEventDAO.getEventsCreatedByUser(idUser);
    }
    


}
