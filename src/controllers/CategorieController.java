package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Categorie;

public class CategorieController {
    private Categorie categorie;

    public CategorieController() {
        this.categorie = new Categorie();
    }

    public boolean createCategorie(String nom) {
        if (categorie.existe(nom)) {
            return false;
        }

        categorie = new Categorie(nom);
        return categorie.enregistrer();
    }

    public boolean categorieExist(String nom) {
        return categorie.existe(nom);
    }

    public boolean updateCategorie(int idCat, String nouveauNom) {
        if (!categorie.existe(idCat)) {
            return false;
        }

        categorie = new Categorie(idCat, nouveauNom);
        return categorie.modifier();
    }

    public boolean deleteCategorie(int idCat) {
        return categorie.supprimer(idCat);
    }

    public List<HashMap<String, String>> listCategories() {
        List<Categorie> myCategories = categorie.toutesLesCategories();

        List<HashMap<String, String>> categoriesList = new ArrayList<HashMap<String, String>>();

        for (Categorie cat : myCategories) {
            HashMap<String, String> categoryMap = new HashMap<String, String>();
            categoryMap.put("id", String.valueOf(cat.getIdCat()));
            categoryMap.put("nom", cat.getNom());
            categoriesList.add(categoryMap);
        }

        return categoriesList;
    }
}
