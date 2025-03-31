package models;

import java.util.List;

import dao.CategorieDAO;

public class Categorie {

    private int idCat;
    private String nom;

    private CategorieDAO categorieDAO;

    public Categorie(int idCat, String nom) {
        this.idCat = idCat;
        this.nom = nom;
        categorieDAO = new CategorieDAO();
    }

    public Categorie(String nom) {
        this(0, nom);
    }

    public Categorie() {
        this("");
    }

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

    public boolean existe(String nom) {
        return categorieDAO.categorieExist(nom);
    }

    public boolean existe(int idCat) {
        return categorieDAO.categorieExist(idCat);
    }

    public boolean enregistrer() {
        return categorieDAO.addCategorie(this);
    }

    public boolean modifier() {
        return categorieDAO.updateCategorie(this);
    }

    public boolean supprimer(int idCat) {
        return categorieDAO.deleteCategorie(idCat);
    }

    public List<Categorie> toutesLesCategories() {
        return categorieDAO.getAllCategories();
    }
}
