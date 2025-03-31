package models;

import java.util.List;

import dao.LieuDAO;

public class Lieu {
    private int idLieu;
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;

    private LieuDAO lieuDAO;

    public Lieu(int idLieu, String nom, String adresse, String ville, String codePostal) {
        this.idLieu = idLieu;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;

        this.lieuDAO = new LieuDAO();
    }

    public Lieu(String nom, String adresse, String ville, String codePostal) {
        this(0, nom, adresse, ville, codePostal);
    }

    public Lieu() {
        idLieu = 0;
        this.lieuDAO = new LieuDAO();
    }

    // Getters et Setters
    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "idLieu=" + idLieu +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }

    public boolean existe(String nom) {
        return lieuDAO.lieuExiste(nom);
    }

    public boolean existe(int idLieu) {
        return lieuDAO.lieuExiste(idLieu);
    }

    public boolean enregistrer() {
        return lieuDAO.addLieu(this);
    }

    public boolean modifier() {
        return lieuDAO.updateLieu(this);
    }

    public boolean supprimer(int idLieu) {
        return lieuDAO.deleteLieu(idLieu);
    }

    public List<Lieu> lieuxParNom(String nom) {
        return lieuDAO.getLieuxByName(nom);
    }

    public List<Lieu> tousLesLieux() {
        return lieuDAO.getAllLieux();
    }
}
