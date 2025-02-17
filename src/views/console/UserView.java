package views.console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import controllers.UserController;

public class UserView {
	private UserController userController;
	private Scanner scanner;
	
	public UserView(UserController userController) {
		this.userController = userController;
		scanner = new Scanner(System.in);
	}
	
	public void ajouterUtilisateur() {
        System.out.println("📝 Ajout d'un nouvel utilisateur");

        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();

        System.out.print("Téléphone: ");
        String telephone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Adresse: ");
        String adresse = scanner.nextLine();

        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        LocalDate dateNaissance = saisirDate("Date de naissance (YYYY-MM-DD) : ");

        LocalDate dateInscription = LocalDate.now();

        System.out.print("Rôle (utilisateur/admin): ");
        String roleSystem = scanner.nextLine();

        boolean result = userController.inscrireUtilisateur(nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription, roleSystem);
        
        if (result) {
            System.out.println("Utilisateur ajouté avec succès !");
        } else {
            System.out.println("L'utilisateur n'a pas été ajouté !");
        }
    }

    // Méthode pour saisir et valider une date
    private LocalDate saisirDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr); // Conversion String -> LocalDate
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide ! Veuillez entrer la date au format YYYY-MM-DD.");
            }
        }
    }
}
