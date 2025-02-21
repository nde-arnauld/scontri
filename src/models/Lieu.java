package models;

public class Lieu {
    private int idLieu;
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;

    public Lieu(int idLieu, String nom, String adresse, String ville, String codePostal) {
        this.idLieu = idLieu;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public Lieu(String nom, String adresse, String ville, String codePostal) {
        this(0, nom, adresse, ville, codePostal);
    }

    public Lieu() {
        idLieu = 0;
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
}
