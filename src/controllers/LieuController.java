package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Lieu;

public class LieuController {
    private Lieu lieu;

    public LieuController() {
        this.lieu = new Lieu();
    }

    public boolean createLieu(String nom, String adresse, String ville, String codePostal) {
        if (lieu.existe(nom)) {
            return false;
        }

        lieu = new Lieu(nom, adresse, ville, codePostal);
        return lieu.enregistrer();
    }

    public boolean updateLieu(int idLieu, String nom, String adresse, String ville, String codePostal) {
        lieu = new Lieu(idLieu, nom, adresse, ville, codePostal);
        return lieu.modifier();
    }

    public boolean deleteLieu(int idLieu) {
        if (lieu.existe(idLieu)) {
            return lieu.supprimer(idLieu);
        } else
            return false;
    }

    public List<HashMap<String, String>> getLieuxByName(String nom) {
        List<Lieu> lieux = lieu.lieuxParNom(nom);
        List<HashMap<String, String>> lieuxList = new ArrayList<HashMap<String, String>>();
        for (Lieu l : lieux) {
            HashMap<String, String> lieuMap = new HashMap<String, String>();
            lieuMap.put("id", String.valueOf(l.getIdLieu()));
            lieuMap.put("nom", l.getNom());
            lieuMap.put("adresse", l.getAdresse());
            lieuMap.put("ville", l.getVille());
            lieuMap.put("code_postal", l.getCodePostal());
            lieuxList.add(lieuMap);
        }
        return lieuxList;
    }

    public List<HashMap<String, String>> listLieux() {
        List<Lieu> lieux = lieu.tousLesLieux();
        List<HashMap<String, String>> lieuxList = new ArrayList<HashMap<String, String>>();
        for (Lieu l : lieux) {
            HashMap<String, String> lieuMap = new HashMap<String, String>();
            lieuMap.put("id", String.valueOf(l.getIdLieu()));
            lieuMap.put("nom", l.getNom());
            lieuMap.put("adresse", l.getAdresse());
            lieuMap.put("ville", l.getVille());
            lieuMap.put("code_postal", l.getCodePostal());
            lieuxList.add(lieuMap);
        }
        return lieuxList;
    }

}
