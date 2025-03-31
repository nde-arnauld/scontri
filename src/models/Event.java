package models;

import java.time.LocalDateTime;
import java.util.List;

import dao.EventDAO;

public class Event {
    private int idEvent;
    private String nom;
    private String description;
    private int capacite;
    private double prix;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateCreation;
    private String status;
    private int idLieu;
    private int idCat;

    private EventDAO eventDAO;

    public Event(int idEvent, String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, LocalDateTime dateCreation,
            String status, int idLieu, int idCat) {
        this.idEvent = idEvent;
        this.nom = nom;
        this.description = description;
        this.capacite = capacite;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateCreation = dateCreation;
        this.status = status;
        this.idLieu = idLieu;
        this.idCat = idCat;

        this.eventDAO = new EventDAO();
    }

    public Event(String nom, String description, int capacite, double prix,
            LocalDateTime dateDebut, LocalDateTime dateFin, String status,
            int idLieu, int idCat) {
        this(0, nom, description, capacite, prix, dateDebut, dateFin, LocalDateTime.now(),
                status, idLieu, idCat);
    }

    public Event() {
        idEvent = 0;
        this.eventDAO = new EventDAO();
    }

    // Getters et Setters
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    @Override
    public String toString() {
        return "Event{" +
                "[ idEvent = " + idEvent +
                " ], nom='" + nom +
                ", description='" + description +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    /* ====================================================== */
    /* Méthode de gestion du modèle */
    /* ====================================================== */
    public int enregistrer() {
        return eventDAO.addEvent(this);
    }

    public boolean modifier() {
        return eventDAO.updateEvent(this);
    }

    public Event evenementParId(int idEvent) {
        return eventDAO.getEventById(idEvent);
    }

    public boolean supprimer(int idEvent) {
        return eventDAO.deleteEvent(idEvent);
    }

    public List<Event> tousLesEvenements() {
        return eventDAO.getAllEvents();
    }

    public boolean existe(int idEvent) {
        return eventDAO.eventExists(idEvent);
    }
}
