package views.console;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import utils.Popup;

import controllers.Org_EventController;
import models.User;

public class Org_EventView {
    private Org_EventController orgEventController;
    private Scanner scanner;

    public Org_EventView(Org_EventController orgEventController) {
        this.orgEventController = orgEventController;
        this.scanner = new Scanner(System.in);
    }

    public void ajouterOrgEvenement() {
        System.out.println("\nAJOUTER UN ORGANISATEUR À UN ÉVÉNEMENT:");
        System.out.println("----------------------------------------\n");
        System.out.print("ID de l'utilisateur (organisateur) : ");
        int idUser = Integer.parseInt(scanner.nextLine());
        System.out.print("ID de l'événement : ");
        int idEvent = Integer.parseInt(scanner.nextLine());

        boolean result = orgEventController.createOrgEvent(idUser, idEvent);
        Popup.toPrint(result, "Organisateur ajouté avec succès !", "Échec de l'ajout de l'organisateur.");
    }

    public boolean ajouterOrgEvenement(int idUser, int idEvent) {
        return orgEventController.createOrgEvent(idUser, idEvent);
    }

    // Lister les organisateurs d'un événement
    public void listerOrgsEvenement() {
        System.out.println("\nLISTER LES ORGANISATEURS D'UN ÉVÉNEMENT:");
        System.out.println("-----------------------------------------\n");
        System.out.print("ID de l'événement : ");
        int idEvent = Integer.parseInt(scanner.nextLine());

        List<HashMap<String, Object>> organisateurs = orgEventController.listOrgsEvent(idEvent);

        if (organisateurs.isEmpty()) {
            System.out.println("Aucun organisateur trouvé pour cet événement.");
        } else {
            System.out.println("Liste des organisateurs :");
            for (HashMap<String, Object> user : organisateurs) {
                System.out.println("ID: " + user.get("id") + " | Nom: " + user.get("prenom") + " " + user.get("nom") +
                        " | Email: " + user.get("email") + " | Téléphone: " + user.get("telephone"));
            }
        }
    }

}
