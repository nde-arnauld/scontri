package views.console;

import java.util.List;
import java.util.Scanner;

import controllers.Part_EventController;
import models.Event;
import utils.Popup;

public class Part_EventView {
    private Part_EventController partEventController;
    private Scanner scanner;

    public Part_EventView(Part_EventController partEventController) {
        this.partEventController = partEventController;
        scanner = new Scanner(System.in);
    }

    public void afficherEvenements(List<Event> evenements) {
        System.out.println("Liste des événements disponibles :");

        for (Event event : evenements) {
            System.out.print("- ");
            System.out.println(event);
        }
    }

    public void participerAEvenement(int idUser) {
        System.out.print("Entrez l'ID de l'événement auquel vous souhaitez vous inscrire : ");
        int idEvent = scanner.nextInt();

        boolean result = partEventController.addPartEvent(idUser, idEvent);
        Popup.toPrint(result, "Votre demande de participation a été prise en compte!",
                "Une erreur s'est produite.\n Veuillez recommencer svp!");
    }

    public void annulerParticipation(int idUser) {
        System.out.print("Entrez l'ID de l'événement dont vous souhaitez annuler votre participation : ");
        int idEvent = scanner.nextInt();

        boolean result = partEventController.cancelParticipation(idUser, idEvent);
        Popup.toPrint(result, "Votre participation a été annulée avec succès!",
                "Une erreur s'est produite.\n Veuillez recommencer svp!");
    }

}
