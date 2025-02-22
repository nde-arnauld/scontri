package app;

import java.sql.Connection;

import controllers.CategorieController;
import controllers.EventController;
import controllers.LieuController;
import controllers.Part_EventController;
import controllers.UserController;
import dao.CategorieDAO;
import dao.Database;
import dao.EventDAO;
import dao.LieuDAO;
import dao.Part_EventDAO;
import dao.UserDAO;
import models.User;
import views.console.CategorieView;
import views.console.EventView;
import views.console.Part_EventView;
import views.console.UserView;

public class App {

	public static void main(String[] args) {
		Connection connection = Database.getConnection();

		// Test authentification
		UserDAO userDAO = new UserDAO(connection);
		UserController userController = new UserController(userDAO);
		UserView userView = new UserView(userController);
		//
		//
		// userView.ajouterUtilisateur();
		userView.SeConnecter();
		// userView.SeDeconnecter();

		//
		CategorieDAO categorieDAO = new CategorieDAO(connection);
		CategorieController categorieController = new CategorieController(categorieDAO);
		// CategorieView categorieView = new CategorieView(categorieController);
		//
		// categorieView.listerCategories();
		// categorieView.ajouterUneCategorie();

		LieuDAO lieuDAO = new LieuDAO(connection);
		LieuController lieuController = new LieuController(lieuDAO);

		EventDAO eventDAO = new EventDAO(connection);
		EventController eventController = new EventController(eventDAO);
		EventView eventView = new EventView(eventController, categorieController, lieuController);

		// eventView.ajouterUnEvenement();

		User user = userController.getLoggedUser();

		Part_EventDAO partEventDAO = new Part_EventDAO(connection);
		Part_EventController partEventController = new Part_EventController(eventController, partEventDAO);
		Part_EventView partEventView = new Part_EventView(partEventController);

		// La liste des évènements
		partEventView.afficherEvenements(eventController.listEvents(), "Liste des événements disponibles : ");

		partEventView.participerAEvenement(user.getIdUser());

		partEventView.afficherEvenements(partEventController.getEventsForUser(user.getIdUser()),
				"Liste des évènements auxquels vous participez : ");
		partEventView.annulerParticipation(user.getIdUser());

		Database.closeConnection();

	}

}
