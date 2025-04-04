package scontri;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.UserController;
import models.User;
import utils.Password;

public class TestUserController {

    private UserController userController;
    private String testEmail = "test@gmail.com";
    private String testPassword = "Test@123";
    private LocalDate dateNaissance = LocalDate.of(2000, 1, 1);
    private LocalDate dateInscription = LocalDate.now();

    @BeforeEach
    void config() {
        userController = new UserController();

//        // Assurer qu'un utilisateur de test existe dans la base
//        User testUser = new User("Test", "User", "0123456789", testEmail, 
//                                  "Test Adresse", Password.hasherMotDePasse(testPassword), 
//                                  dateNaissance, dateInscription, "USER");
//        testUser.enregistrer();
    }

//    @Test
//    void testCreateUser_Success() {
//        String emailUnique = "newuser@example.com";
//        boolean created = userController.createUser("New", "User", "0987654321", emailUnique, 
//                                                    "New Adresse", "NewPass@123", 
//                                                    dateNaissance, dateInscription, "ADMIN");
//        assertTrue(created, "L'utilisateur devrait être créé avec succès.");
//    }

    @Test
    void testCreateUser_Failure_ExistingEmail() {
        boolean created = userController.createUser("Duplicate", "User", "0123456789", testEmail, 
                                                    "Some Adresse", "AnotherPass@123", 
                                                    dateNaissance, dateInscription, "USER");
        assertFalse(created, "La création devrait échouer car l'email existe déjà.");
    }


    @Test
    void testLoginUser_Failure_WrongPassword() {
        boolean loginFailed = userController.loginUser(testEmail, "WrongPassword");
        assertFalse(loginFailed, "La connexion devrait échouer avec un mauvais mot de passe.");
    }

    @Test
    void testLoginUser_Failure_NonExistentUser() {
        boolean loginFailed = userController.loginUser("nonexistent@example.com", "SomePassword");
        assertFalse(loginFailed, "La connexion devrait échouer avec un email inexistant.");
    }

    @Test
    void testGetLoggedUserInfos() {
        userController.loginUser(testEmail, "Azerty@123");
        HashMap<String, String> userInfos = userController.getLoggedUserInfos();

        assertNotNull(userInfos, "Les infos de l'utilisateur ne doivent pas être null.");
        assertEquals("AHISSOU", userInfos.get("nom"), "Le nom doit être 'Test'.");
        assertEquals("Meldi", userInfos.get("prenom"), "Le prénom doit être 'User'.");
    }

    @Test
    void testLogoutUser_Success() {
        userController.loginUser(testEmail, "Azerty@123");
        boolean logoutSuccess = userController.logoutUser();

        assertTrue(logoutSuccess, "La déconnexion doit réussir.");
        assertNull(userController.getLoggedUserInfos(), "Après déconnexion, les infos utilisateur doivent être null.");
    }
    
    @Test
    void testLogoutUser_Failure() {
//        userController.loginUser(testEmail, "Azerty@123");
        boolean logoutSuccess = userController.logoutUser();

        assertFalse(logoutSuccess, "La déconnexion doit échouer.");
    }
}

