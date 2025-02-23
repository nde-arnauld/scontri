package app;

import java.sql.Connection;
import java.util.Scanner;

import controllers.CategorieController;
import controllers.EventController;
import controllers.LieuController;
import controllers.Org_EventController;
import controllers.Part_EventController;
import controllers.UserController;
import dao.CategorieDAO;
import dao.Database;
import dao.EventDAO;
import dao.LieuDAO;
import dao.Org_EventDAO;
import dao.Part_EventDAO;
import dao.UserDAO;
import models.User;
import utils.Popup;
import views.console.EventView;
import views.console.Part_EventView;
import views.console.UserView;

public class App {

	public static void main(String[] args) {
		Connection connection = Database.getConnection();
		Scanner clavier = new Scanner(System.in);

		/**
		 * Fonctionnalités :
		 * 
		 * -> Connexion | Création de compte
		 * -> Créer un évènement
		 * -> Adhérer à un évènement
		 * -> Voir les évènements auxquels je participe
		 */

		UserDAO userDAO = new UserDAO(connection);
		UserController userController = new UserController(userDAO);
		UserView userView = new UserView(userController);

		do {
			int choix = 0;
			User user = null;

			do {
				// Menu de connexion
				do {
					clavier.reset();
					menuConnexion();
					choix = clavier.nextInt();
				} while (choix < 1 || choix > 3);

				switch (choix) {
					case 1:
						userView.SeConnecter();
						break;
					case 2:
						userView.ajouterUtilisateur();
						userView.SeConnecter();
						break;
					case 3:
						System.out.println("Vous avez quitté l'application.");
						System.out.println("Merci pour votre visite.\n");
						clavier.close();
						Database.closeConnection();
						return;
					default:
						System.out.println("Votre choix n'est pas correct, choisissez entre 1-3.\n");
						break;
				}
				user = userController.getLoggedUser();
				choix = 0;
			} while (user == null);

			CategorieDAO categorieDAO = new CategorieDAO(connection);
			CategorieController categorieController = new CategorieController(categorieDAO);

			LieuDAO lieuDAO = new LieuDAO(connection);
			LieuController lieuController = new LieuController(lieuDAO);

			Org_EventDAO orgEventDAO = new Org_EventDAO(connection);
			Org_EventController orgEventController = new Org_EventController(orgEventDAO);

			EventDAO eventDAO = new EventDAO(connection);
			EventController eventController = new EventController(eventDAO);
			EventView eventView = new EventView(eventController, categorieController, lieuController,
					orgEventController);

			Part_EventDAO partEventDAO = new Part_EventDAO(connection);
			Part_EventController partEventController = new Part_EventController(eventController, partEventDAO);
			Part_EventView partEventView = new Part_EventView(partEventController);

			do {
				do {
					clavier.reset();
					menuPrincipal();
					choix = clavier.nextInt();
				} while (choix < 1 || choix > 5);

				switch (choix) {
					case 1:
						// Affichage de la vue 'EventView' pour créer un évènement
						eventView.ajouterUnEvenement(user.getIdUser());
						break;
					case 2:
						// La liste des évènements
						partEventView.afficherEvenements(eventController.listEvents(),
								"Liste de tous les événements disponibles : ");
						// Affichage de la vue 'PartEventView' pour participer à un évènement
						partEventView.participerAEvenement(user.getIdUser());
						break;
					case 3:
						// La liste des évènements auxqels l'user participe
						partEventView.afficherEvenements(partEventController.getEventsForUser(user.getIdUser()),
								"Liste des évènements auxquels vous participez : ");
						break;
					case 4:
						// La liste des évènements
						partEventView.afficherEvenements(eventController.listEvents(),
								"Liste de tous les événements disponibles : ");
						break;
					case 5:
						boolean result = userController.logoutUser(user);
						Popup.toPrint(result, "Vous vous êtes déconnecté !", "Erreur de déconnexion.");
						break;
					default:
						break;
				}
			} while (choix != 5);
		} while (true);
	}

	public static void menuConnexion() {
		System.out.println("");
		System.out.println("BIENVENUE DANS ' SCONTRI ' : ");
		System.out.println("----------------------------\n");
		System.out.println("1- Se connecter");
		System.out.println("2- Créer un compte");
		System.out.println("3- Quitter");
		System.out.print("choix : ");
	}

	public static void menuPrincipal() {
		System.out.println("");
		System.out.println("MENU PRINCIPAL :");
		System.out.println("----------------\n");
		System.out.println("1- Créer un évènement.");
		System.out.println("2- Adhérer à un évènement.");
		System.out.println("3- Voir les évènements auxquels je participe.");
		System.out.println("4- Voir la liste de tous les évènements.");
		System.out.println("5- Se déconnecter");
		System.out.print("choix : ");
	}

}
