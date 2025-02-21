package models;

public class Categorie {
    private int idCat;
    private String nom;

    public Categorie(int idCat, String nom) {
        this.idCat = idCat;
        this.nom = nom;
    }

    public Categorie(String nom) {
        this(0, nom);
    }

    public Categorie() {
        idCat = 0;
    }

    // Getters et Setters
    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idCat = " + idCat +
                ", nom = '" + nom + "'}";
    }
}
