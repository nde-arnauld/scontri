package views.console;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import controllers.CategorieController;
import controllers.EventController;
import controllers.LieuController;
import controllers.Org_EventController;
import models.Event;
import utils.Popup;
import utils.DateTaker;

public class EventView {
    private EventController eventController;
    private CategorieView categorieView;
    private LieuView lieuView;
    private Org_EventView org_EventView;
    private Scanner scanner;

    public EventView(EventController eventController, CategorieController categorieController,
            LieuController lieuController, Org_EventController org_EventController) {
        this.eventController = eventController;
        this.categorieView = new CategorieView(categorieController);
        this.lieuView = new LieuView(lieuController);
        this.org_EventView = new Org_EventView(org_EventController);
        scanner = new Scanner(System.in);
    }

    public void ajouterUnEvenement(int idCurrentUser) {
        System.out.println("\nAJOUTER UN NOUVEL EVENEMENT:");
        System.out.println("------------------------------\n");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Capacité : ");
        int capacite = Integer.parseInt(scanner.nextLine());
        System.out.print("Prix : ");
        double prix = Double.parseDouble(scanner.nextLine());

        LocalDateTime dateDebut = DateTaker.saisirDateTime("Date de début (yyyy-MM-dd HH:mm) : ");
        LocalDateTime dateFin = DateTaker.saisirDateTime("Date de fin (yyyy-MM-dd HH:mm) : ");

        categorieView.listerCategories();
        System.out.print("Choisissez L'ID de la Catégorie de l'évenement : ");
        int idCat = Integer.parseInt(scanner.nextLine());

        System.out.print("\nEntrez le nom du lieu : ");
        String nomLieu = scanner.nextLine();
        int idLieu = 0;

        while (idLieu == 0) {
            boolean found = lieuView.ListerLieuxParNom(nomLieu);
            if (found) {
                System.out.print("\nChoisissez L'ID du lieu de l'évenement : ");
                idLieu = Integer.parseInt(scanner.nextLine());
            } else {
                lieuView.ajouterUnLieu();
            }
        }

        String status = "actif";

        int idNewevent = eventController.createEvent(nom, description, capacite, prix, dateDebut, dateFin, status,
                idLieu, idCat);
        boolean result = org_EventView.ajouterOrgEvenement(idCurrentUser, idNewevent);
        Popup.toPrint(result, "Événement ajouté avec succès !", "Échec de l'ajout de l'événement.");
    }

    public void modifierUnEvenement() {
        System.out.println("\nMODIFIER UN EVENEMENT:");
        System.out.println("------------------------------\n");

        listerEvenements();

        System.out.print("Entrez l'ID de l'événement à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();
        System.out.print("Nouvelle description : ");
        String description = scanner.nextLine();
        System.out.print("Nouvelle capacité : ");
        int capacite = Integer.parseInt(scanner.nextLine());
        System.out.print("Nouveau prix : ");
        double prix = Double.parseDouble(scanner.nextLine());

        LocalDateTime dateDebut = DateTaker.saisirDateTime("Nouvelle date de début (yyyy-MM-dd HH:mm) : ");
        LocalDateTime dateFin = DateTaker.saisirDateTime("Nouvelle date de fin (yyyy-MM-dd HH:mm) : ");

        System.out.print("Nouveau statut : ");
        String status = scanner.nextLine();
        System.out.print("Nouvel ID Lieu : ");
        int idLieu = Integer.parseInt(scanner.nextLine());
        System.out.print("Nouvel ID Catégorie : ");
        int idCat = Integer.parseInt(scanner.nextLine());

        boolean result = eventController.updateEvent(id, nom, description, capacite, prix, dateDebut, dateFin, status,
                idLieu, idCat);
        Popup.toPrint(result, "Événement modifié avec succès !", "Échec de la modification de l'événement.");
    }

    public void supprimerUnEvenement() {
        System.out.println("\nSUPPRIMER UN EVENEMENT:");
        System.out.println("------------------------------\n");

        listerEvenements();

        System.out.print("Entrez l'ID de l'événement à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean result = eventController.deleteEvent(id);
        Popup.toPrint(result, "Événement supprimé avec succès !", "Échec de la suppression de l'événement.");
    }

    public void listerEvenements() {
        List<HashMap<String, Object>> events = eventController.listEvents();

        if (events.isEmpty()) {
            System.out.println("Aucun événement disponible.");
        } else {
            System.out.println("\nLISTE DES ÉVÉNEMENTS :");
            System.out.println("------------------------------");
            for (HashMap<String, Object> event : events) {
                System.out.println("ID: " + event.get("id") + "|Nom: " + event.get("nom") + "|Période: ("
                        + event.get("dateDebut") + " → " + event.get("dateFin") + ")");
            }
        }
    }

}
