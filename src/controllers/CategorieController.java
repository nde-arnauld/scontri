package controllers;

import dao.CategorieDAO;
import models.Categorie;

public class CategorieController {
    private CategorieDAO categorieDAO;

    public CategorieController(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    public boolean createCategorie(String nom) {
        if (categorieDAO.categorieExist(nom)) {
            return false;
        }

        Categorie categorie = new Categorie(nom);
        return categorieDAO.addCategorie(categorie);
    }

    public boolean categorieExist(String nom) {
        return categorieDAO.categorieExist(nom);
    }

    public boolean updateCategorie(int idCat, String nouveauNom) {
        if (!categorieDAO.categorieExist(idCat)) {
            return false;
        }

        Categorie categorie = new Categorie(idCat, nouveauNom);
        return categorieDAO.updateCategorie(categorie);
    }

    public boolean deleteCategorie(int idCat) {
        return categorieDAO.deleteCategorie(idCat);
    }
}
