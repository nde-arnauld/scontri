package controllers;

import java.time.LocalDate;

import dao.UserDAO;
import models.User;
import utils.Password;

public class UserController {
	private UserDAO userDAO;

	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public boolean createUser(String nom, String prenom, String telephone, String email, String adresse,
			String motDePasse, LocalDate dateNaissance, LocalDate dateInscription, String roleSysteme) {

		if (userDAO.userExist(email)) {
			return false;
		}

		User user = new User(nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription,
				roleSysteme);

		return userDAO.addUser(user);
	}

	public boolean loginUser(String email, String motDePasse) {
		// On vérifie si un utilisateur a cet 'email'
		if (!userDAO.userExist(email)) {
			return false;
		}

		String userPassword = userDAO.userPassword(email);

		// On compare les mots de passe
		if (!Password.verifierMotDePasse(motDePasse, userPassword)) {
			return false;
		}

		return true;
	}

	public boolean logoutUser(User user) {
		if (user.getIdUser() > 0) {
			user = new User();

			return true;
		}
		return false;
	}
}
