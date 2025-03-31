package models;

import java.util.List;

import dao.Org_EventDAO;

public class Org_Event {

	private int id_user;
	private int id_event;

	private Org_EventDAO org_EventDAO;

	public Org_Event(int id_user, int id_event) {
		this.id_user = id_user;
		this.id_event = id_event;
		this.org_EventDAO = new Org_EventDAO();
	}

	public Org_Event() {
		this(0, 0);
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getId_event() {
		return id_event;
	}

	public void setId_event(int id_event) {
		this.id_event = id_event;
	}

	@Override
	public String toString() {
		return "Org_Event {" +
				"id_user=" + id_user +
				", id_event=" + id_event +
				"}";
	}

	public boolean enregistrer() {
		return org_EventDAO.addOrgEvent(this);
	}

	public List<User> organisateursEvenement(int id_event) {
		return org_EventDAO.getOrgsEvent(id_event);
	}

	public List<Event> evenementsDeOrganisateur(int id_user) {
		return org_EventDAO.getEventsCreatedByUser(id_user);
	}
}
