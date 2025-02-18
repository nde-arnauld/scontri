package app;

import java.sql.Connection;

import controllers.UserController;
import dao.Database;
import dao.UserDAO;
import views.console.UserView;

public class App {

	public static void main(String[] args) {
		Connection connection = Database.getConnection();
		
		UserDAO userDAO = new UserDAO(connection);
		UserController userController = new UserController(userDAO);
		UserView userView = new UserView(userController);
		
		// userView.ajouterUtilisateur();
		userView.SeConnecter();
		userView.SeDeconnecter();
		
		Database.closeConnection();
	}

}
