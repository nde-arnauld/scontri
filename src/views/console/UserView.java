package views.console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import controllers.UserController;
import utils.Password;

public class UserView {
	private UserController userController;
	private Scanner scanner;
	
	public UserView(UserController userController) {
		this.userController = userController;
		scanner = new Scanner(System.in);
	}
	
	public void ajouterUtilisateur() {
        System.out.println("\nAJOUT D'UN NOUVEL UTILISATEUR:");
        System.out.println("------------------------------\n");

        System.out.print("Nom: ");
        String nom = scanner.nextLine();
 
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();

        System.out.print("Téléphone: ");
        String telephone = scanner.nextLine();

        String email = emailCorrect("Email: ");

        System.out.print("Adresse: ");
        String adresse = scanner.nextLine();

        String motDePasse = motDePasseCorrect("Mot de passe: ");
        motDePasse = Password.hasherMotDePasse(motDePasse);

        LocalDate dateNaissance = saisirDate("Date de naissance (YYYY-MM-DD) : ");

        LocalDate dateInscription = LocalDate.now();

        System.out.print("Rôle (admin/user): ");
        String roleSystem = scanner.nextLine();

        boolean result = userController.createUser(nom, prenom, telephone, email, adresse, motDePasse, dateNaissance, dateInscription, roleSystem);
        
        toPrint(result, "Utilisateur ajouté avec succès !", "L'utilisateur n'a pas été ajouté !");
    }
	
	private String emailCorrect(String message) {
		while (true) {
            System.out.print(message);
            String email = scanner.nextLine();
            
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            
            if (email.matches(regex)) {
                return email;
            } else {
                System.out.println("Email invalide !");
            }
        }
	}
	
	// Méthode pour s'assurer que le mot de passe est correct.
    private String motDePasseCorrect(String message) {
        while (true) {
            System.out.print(message);
            String motDePasse = scanner.nextLine();
            
            boolean motDePasseOk = true;
            String errors = "Le mot de passe doit avoir : \n";
            
            if (motDePasse.length() <= 8) {
            	errors += "- Minimum 8 caractères.\n";
            	motDePasseOk = false;
            }
            if (!motDePasse.matches(".*[A-Z].*")) {
            	errors += "- Minimum une lettre majuscule.\n";
            	motDePasseOk = false;
            }
            if (!motDePasse.matches(".*[a-z].*")) {
            	errors += "- Minimum une lettre minuscule.\n";
            	motDePasseOk = false;
            }
            if (!motDePasse.matches(".*[0-9].*")) {
            	errors += "- Minimum un chiffre.\n";
            	motDePasseOk = false;
            }
            if (!motDePasse.matches(".*[!@#$%^&*()_+\\-={}|:;<>,.?~].*")) {
            	errors += "- Minimum un caractère spécial.\n";
            	motDePasseOk = false;
            }

            if (motDePasseOk) {
                return motDePasse; // Mot de passe valide
            } else {
                System.out.println(errors);
            }
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
    
// ================================================================================================================== //
 
    public void SeConnecter() {
    	System.out.println("\nSE CONNECTER : ");
    	System.out.println("-----------\n");
    	
    	System.out.println("Email : ");
    	String email = scanner.nextLine();
    	
    	System.out.println("Mot de passe : ");
    	String motDePasse = scanner.nextLine();
    	
    	boolean result = userController.loginUser(email, motDePasse);
    	
    	toPrint(result, "Utilisateur connecté!", "L'email ou le mot de passe est incorrect!");
    }
    
// ================================================================================================================== //
    
    public void SeDeconnecter() {
    	System.out.println("\nDéconnexion en cours ...");
    	userController.logoutUser();
    	System.out.println("Vous vous êtes déconnecté !!!");
    }
    
    private void toPrint(boolean result, String if_ok, String if_bad) {
    	if (result) {
            System.out.println(if_ok);
        } else {
            System.out.println(if_bad);
        }
    }
}
