package controllers;

import java.util.List;

import dao.LieuDAO;
import models.Lieu;

public class LieuController {
    private LieuDAO lieuDAO;

    public LieuController(LieuDAO lieuDAO) {
        this.lieuDAO = lieuDAO;
    }

    public boolean createLieu(String nom, String adresse, String ville, String codePostal) {
        if (lieuDAO.lieuExiste(nom)) {
            return false;
        }

        Lieu lieu = new Lieu(nom, adresse, ville, codePostal);
        return lieuDAO.addLieu(lieu);
    }

    public boolean updateLieu(Lieu lieu) {
        return lieuDAO.updateLieu(lieu);
    }

    public boolean deleteLieu(int idLieu) {
        if (lieuDAO.lieuExiste(idLieu)) {
            return lieuDAO.deleteLieu(idLieu);
        } else
            return false;
    }
    
    public List<Lieu> getLieuxByName(String nom) {
        return lieuDAO.getLieuxByName(nom);
    }
     
    public List<Lieu> listLieux(){
    	return lieuDAO.getAllLieux();
    }

}
