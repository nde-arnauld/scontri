package app;

import java.sql.Connection;

import controllers.CategorieController;
import controllers.UserController;
import dao.CategorieDAO;
import dao.Database;
import dao.UserDAO;
import views.console.CategorieView;
import views.console.UserView;

public class App {

	public static void main(String[] args) {
		Connection connection = Database.getConnection();

		// Test authentification
		UserDAO userDAO = new UserDAO(connection);
		UserController userController = new UserController(userDAO);
		UserView userView = new UserView(userController);

		userView.ajouterUtilisateur();
		userView.SeConnecter();
		userView.SeDeconnecter();

		//
		// CategorieDAO categorieDAO = new CategorieDAO(connection);
		// CategorieController categorieController = new
		// CategorieController(categorieDAO);
		// CategorieView categorieView = new CategorieView(categorieController);
		//
		// categorieView.listerCategories();
		// categorieView.ajouterUneCategorie();

		Database.closeConnection();
	}

}
