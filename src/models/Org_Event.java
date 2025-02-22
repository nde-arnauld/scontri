package models;

public class Org_Event {
	
	private int id_user;
    private int id_event;
    
    public Org_Event(int id_user, int id_event) {
    	this.id_user = id_user;
    	this.id_event = id_event;
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
		return "Org_Event {"+
				"id_user=" + id_user + 
				", id_event=" + id_event + 
				"}";
	}



    
	

}
