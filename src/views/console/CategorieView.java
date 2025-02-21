package views.console;

import java.util.Scanner;

import controllers.CategorieController;
import utils.Popup;

public class CategorieView {
    private CategorieController categorieController;
    private Scanner scanner;

    public CategorieView(CategorieController categorieController) {
        this.categorieController = categorieController;
        scanner = new Scanner(System.in);
    }

    public void ajouterUneCategorie() {
        System.out.print("Entrez le nom de la catégorie à ajouter : ");
        String nom = scanner.nextLine();

        boolean result = categorieController.createCategorie(nom);

        Popup.toPrint(result, "Catégorie ajoutée avec succès !",
                "Échec de l'ajout de la catégorie. Peut-être qu'elle existe déjà.");
    }

    public void modifierUneCategorie() {
        System.out.print("Entrez l'ID de la catégorie à modifier : ");
        int idCat = Integer.parseInt(scanner.nextLine());

        System.out.print("Entrez le nouveau nom de la catégorie : ");
        String nouveauNom = scanner.nextLine();

        boolean result = categorieController.updateCategorie(idCat, nouveauNom);
        Popup.toPrint(result, "Catégorie modifiée avec succès !",
                "Échec de la modification de la catégorie. Vérifiez l'ID.");
    }

    public void supprimerUneCategorie() {
        System.out.print("Entrez l'ID de la catégorie à supprimer : ");
        int idCat = Integer.parseInt(scanner.nextLine());

        boolean result = categorieController.deleteCategorie(idCat);
        Popup.toPrint(result, "Catégorie supprimée avec succès !",
                "Échec de la suppression de la catégorie. Vérifiez l'ID.");
    }
}
