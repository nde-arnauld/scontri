package views.console;

import java.util.List;
import java.util.Scanner;

import controllers.CategorieController;
import models.Categorie;

public class CategorieView {
	
	private CategorieController categorieController ;
	private Scanner scanner;
	
	public CategorieView(CategorieController catCon) {
		this.categorieController = catCon;
		scanner = new Scanner(System.in);
	}
	
	 public void ajouterCategorie() {
		 	System.out.println("\nAJOUT D'UNE NOUVELLE CATEGORIE:");
	        System.out.println("------------------------------\n");
		 
	        System.out.print("Entrez le nom de la catégorie : ");
	        String nomCategorie = scanner.nextLine();

	        boolean success = categorieController.createCategorie(nomCategorie);
	        if (success) {
	            System.out.println("Catégorie ajoutée avec succès !");
	        } else {
	            System.out.println("La catégorie existe déjà ou une erreur est survenue.");
	        }
	 }
	 
	 
	public void listerCategories() {
	        List<Categorie> categories = categorieController.listCategories();
	        
	        if (categories.isEmpty()) {
	            System.out.println("Aucune catégorie disponible.");
	        } else {
	            System.out.println("Liste des catégories :");
	            for (Categorie categorie : categories) {
	                System.out.println("- ID: " + categorie.getIdCat() + ", Nom: " + categorie.getNom());
	            }
	        }
	 }
	 	
	public void supprimerCategorie() {
		System.out.println("\nSUPPRESSION D'UNE CATEGORIE:");
        System.out.println("------------------------------\n");
        System.out.println("Entrez l'ID de la catégorie à supprimer :");
        int idCategorie = scanner.nextInt();
        scanner.nextLine();

        if (categorieController.supprimerCategorie(idCategorie)) {
            System.out.println("La catégorie " + idCategorie + " a été supprimée avec succès.");
        } else {
            System.out.println("La suppression a échoué. Vérifiez que l'ID est correct.");
        }
    }
	

}
