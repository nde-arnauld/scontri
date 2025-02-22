package controllers;

import dao.Part_EventDAO;
import models.Event;
import models.Part_Event;
import models.User;

import java.util.List;

public class Part_EventController {
    private EventController eventController;

    private Part_EventDAO partEventDAO;

    public Part_EventController(EventController eventController,
            Part_EventDAO partEventDAO) {
        this.eventController = eventController;

        this.partEventDAO = partEventDAO;
    }

    public boolean addPartEvent(int idUser, int idEvent) {
        if (partEventDAO.userParticipatesInEvent(idUser, idEvent)) {
            return false;
        }

        Event event = eventController.getEventById(idEvent);

        if (event.getStatus().compareTo("actif") != 0) {
            return false;
        }

        Part_Event partEvent = new Part_Event(idUser, idEvent);

        return partEventDAO.addPartEvent(partEvent);
    }

    public boolean cancelParticipation(int idUser, int idEvent) {
        if (!partEventDAO.userParticipatesInEvent(idUser, idEvent)) {
            return false;
        }

        return partEventDAO.removePartEvent(idUser, idEvent);
    }

    public List<Event> getEventsForUser(int idUser) {
        return partEventDAO.getEventsForUser(idUser);
    }

    public List<User> getUsersForEvent(int idEvent) {
        return partEventDAO.getParticipantsFromEvent(idEvent);
    }
}
