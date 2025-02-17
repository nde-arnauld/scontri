package controllers;

import java.time.LocalDate;

import dao.UserDAO;
import models.User;

public class UserController {
	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public boolean inscrireUtilisateur(String nom, String prenom, String telephone, String email, String adresse, 
            String motDePasse, LocalDate dateNaissance, LocalDate dateInscription, String roleSysteme) {
		User user = new User(nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription, roleSysteme);
		return userDAO.addUser(user);
	}
}
