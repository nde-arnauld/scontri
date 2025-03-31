package views.console;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import controllers.LieuController;
import models.Lieu;
import utils.Popup;

public class LieuView {
    private LieuController lieuController;
    private Scanner scanner;

    public LieuView(LieuController lieuController) {
        this.lieuController = lieuController;
        scanner = new Scanner(System.in);
    }

    public void ajouterUnLieu() {
        System.out.println("\nAJOUTER UN NOUVEAU LIEU:");
        System.out.println("------------------------------\n");
        System.out.print("Nom du lieu : ");
        String nom = scanner.nextLine();
        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();
        System.out.print("Ville : ");
        String ville = scanner.nextLine();
        System.out.print("Code Postal : ");
        String codePostal = scanner.nextLine();

        boolean result = lieuController.createLieu(nom, adresse, ville, codePostal);
        Popup.toPrint(result, "Lieu ajouté avec succès !", "Échec de l'ajout du lieu. Il existe peut-être déjà.");
    }

    public void modifierUnLieu() {
        System.out.println("\nMODIFIER UN LIEU:");
        System.out.println("------------------------------\n");
        System.out.print("ID du lieu à modifier : ");
        int idLieu = Integer.parseInt(scanner.nextLine());
        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();
        System.out.print("Nouvelle adresse : ");
        String adresse = scanner.nextLine();
        System.out.print("Nouvelle ville : ");
        String ville = scanner.nextLine();
        System.out.print("Nouveau code postal : ");
        String codePostal = scanner.nextLine();

        boolean result = lieuController.updateLieu(idLieu, nom, adresse, ville, codePostal);
        Popup.toPrint(result, "Lieu modifié avec succès !", "Échec de la modification du lieu.");
    }

    public void supprimerUnLieu() {
        System.out.println("\nSUPPRIMER UN LIEU:");
        System.out.println("------------------------------\n");
        System.out.print("ID du lieu à supprimer : ");
        int idLieu = Integer.parseInt(scanner.nextLine());

        boolean result = lieuController.deleteLieu(idLieu);
        Popup.toPrint(result, "Lieu supprimé avec succès !", "Échec de la suppression du lieu. Vérifiez l'ID.");
    }

    public boolean ListerLieuxParNom(String nom) {
        List<HashMap<String, String>> lieux = lieuController.getLieuxByName(nom);

        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu trouvé avec ce nom.Veuillez le créer");
            return false;
        } else {
            System.out.println("Lieux trouvés :");
            for (HashMap<String, String> lieu : lieux) {
                System.out.println(
                        "ID: " + lieu.get("id") + " | Nom: " + lieu.get("nom") + " | Adresse: " + lieu.get("adresse")
                                + " | Ville: " + lieu.get("ville") + " | Code Postal: " + lieu.get("code_postal"));
            }
            return true;
        }
    }

}
