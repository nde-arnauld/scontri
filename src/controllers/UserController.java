package controllers;

import java.time.LocalDate;

import dao.UserDAO;
import models.User;
import utils.Password;

public class UserController {
	private UserDAO userDAO;
	private User user;

	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
		user = new User();
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

	public User getLoggedUser() {
		if (user.getIdUser() != 0) {
			return user;
		}
		return null;
	}

	public boolean loginUser(String email, String motDePasse) {
		// On vérifie si un utilisateur a cet 'email'
		if (!userDAO.userExist(email)) {
			return false;
		}

		String userPassword = userDAO.userPassword(email);
		System.out.println("mdp: '"+ motDePasse +"' , hpwd: "+ userPassword);

		// On compare les mots de passe
		if (!Password.verifierMotDePasse(motDePasse, userPassword)) {
			System.out.println("Mot de passe incorrecte");
			return false;
		}

		user = userDAO.getUserByEmail(email);

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
