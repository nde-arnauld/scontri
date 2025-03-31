package controllers;

import java.time.LocalDate;
import java.util.HashMap;

import models.User;
import utils.Password;

public class UserController {
	private User user;

	public UserController() {
		this.user = new User();
	}

	public boolean createUser(String nom, String prenom, String telephone, String email, String adresse,
			String motDePasse, LocalDate dateNaissance, LocalDate dateInscription, String roleSysteme) {

		if (user.existe(email)) {
			return false;
		}

		user = new User(nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription,
				roleSysteme);
		return user.enregistrer();
	}

	public boolean loginUser(String email, String motDePasse) {
		// On vérifie si un utilisateur a cet 'email'
		if (!user.existe(email)) {
			return false;
		}

		String userPassword = user.getHashedPassword(email);

		// On compare les mots de passe
		if (!Password.verifierMotDePasse(motDePasse, userPassword)) {
			return false;
		}

		user = user.getUserByEmail(email);

		return true;
	}

	public HashMap<String, String> getLoggedUserInfos() {
		if (user.getIdUser() != 0) {
			HashMap<String, String> infos = new HashMap<String, String>();
			infos.put("id", "" + user.getIdUser());
			infos.put("nom", user.getNom());
			infos.put("prenom", user.getPrenom());
			return infos;
		}
		return null;
	}

	public boolean logoutUser() {
		if (user.getIdUser() > 0) {
			user = new User();

			return true;
		}
		return false;
	}
}
