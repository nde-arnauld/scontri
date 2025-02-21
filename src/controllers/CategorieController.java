package controllers;

import java.util.List;

import dao.CategorieDAO;
import models.Categorie;

public class CategorieController {

	private CategorieDAO categorieDAO;
	private Categorie categorie ;
	
	public CategorieController(CategorieDAO catDAO) {
		this.categorieDAO = catDAO;
	}
	
	public boolean createCategorie(String nomCat) {
		
		if (categorieDAO.categorieExist(nomCat)) {
			return false;
		}
		
		categorie = new Categorie(nomCat);

		return categorieDAO.addCategorie(categorie);
	}
	
	public List<Categorie> listCategories() {
        return categorieDAO.getAllCategories();
    }
	
	public boolean supprimerCategorie(int idCat) {
		
	    if (categorieDAO.categorieIDExist(idCat)) {
	        return categorieDAO.deleteCategorie(idCat);
	    }
	    return false;
	}
	
	
	
}
