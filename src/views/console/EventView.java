package views.console;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import controllers.EventController;
import models.Event;
import utils.Popup;
import utils.DateTaker;

public class EventView {
    private EventController eventController;
    private Scanner scanner;

    public EventView(EventController eventController) {
        this.eventController = eventController;
        scanner = new Scanner(System.in);
    }

    public void ajouterUnEvenement() {
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

        System.out.print("Statut : ");
        String status = scanner.nextLine();
        System.out.print("ID Lieu : ");
        int idLieu = Integer.parseInt(scanner.nextLine());
        System.out.print("ID Catégorie : ");
        int idCat = Integer.parseInt(scanner.nextLine());

        boolean result = eventController.addEvent(nom, description, capacite, prix, dateDebut, dateFin, status, idLieu, idCat);
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

        boolean result = eventController.updateEvent(id, nom, description, capacite, prix, dateDebut, dateFin, status, idLieu, idCat);
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
        List<Event> events = eventController.listEvents();

        if (events.isEmpty()) {
            System.out.println("Aucun événement disponible.");
        } else {
            System.out.println("\nLISTE DES ÉVÉNEMENTS :");
            System.out.println("------------------------------");
            for (Event event : events) {
                System.out.println(event.getIdEvent() + " - " + event.getNom() + " (" + event.getDateDebut() + " → " + event.getDateFin() + ")");
            }
        }
    }


      
    
}
