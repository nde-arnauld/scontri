package views.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import controllers.UserController;
import dao.UserDAO;
import utils.DateTaker;
import utils.Password;
import utils.Popup;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.security.PrivateKey;
import java.sql.Connection;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodEvent;
import javax.swing.JFormattedTextField;

public class Logup extends JFrame {

	private JTextField tf_nom;
	private JTextField tf_adresse;
	private JPasswordField tf_password;
	private JTextField tf_prenom;
	private JTextField tf_email;
	JFormattedTextField tf_telephone;
	JFormattedTextField tf_date_jour;
	JFormattedTextField tf_date_mois;
	JFormattedTextField tf_date_annee;
	
	private Connection connection;
	private UserController userController;
	private UserDAO userDAO;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logup window = new Logup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application.
	 */
	public Logup(Connection connection, UserDAO userDAO, UserController userController) {
		initialize();
		this.connection = connection;
		this.userDAO = userDAO;
		this.userController = userController;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.getContentPane().setBackground(UIConst.WHITE_COLOR);
		this.setBounds(100, 100, UIConst.WIDTH, UIConst.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel lbl_inscription = new JLabel("INSCRIPTION");
		lbl_inscription.setForeground(new Color(135, 206, 250));
		lbl_inscription.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 40));
		lbl_inscription.setBackground(Color.WHITE);
		lbl_inscription.setBounds(360, 11, 260, 85);
		this.getContentPane().add(lbl_inscription);
		
		JLabel lbl_email = new JLabel("Nom");
		lbl_email.setForeground(new Color(135, 206, 250));
		lbl_email.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_email.setBackground(Color.WHITE);
		lbl_email.setBounds(76, 129, 80, 40);
		this.getContentPane().add(lbl_email);
		
		tf_nom = new JTextField();
		tf_nom.setToolTipText("Entrez votre nom");
		tf_nom.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_nom.setColumns(10);
		tf_nom.setBounds(211, 130, 250, 40);
		this.getContentPane().add(tf_nom);
		
		JLabel lbl_password = new JLabel("Prénom");
		lbl_password.setForeground(new Color(135, 206, 250));
		lbl_password.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_password.setBackground(Color.WHITE);
		lbl_password.setBounds(75, 199, 159, 40);
		this.getContentPane().add(lbl_password);
		
		JButton btn_creer = new JButton("ENREGISTRER");
		btn_creer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (champsRemplis()) {
					char[] passwordChars = tf_password.getPassword();
					String password = new String(passwordChars);
					
			        password = Password.hasherMotDePasse(password);
			        
			        LocalDate dateNaissance = LocalDate.parse(""+ tf_date_annee.getText() +"-"+ tf_date_mois.getText() +"-"+ tf_date_jour.getText());

			        LocalDate dateInscription = LocalDate.now();

			        String roleSystem = "user";

			        boolean result = userController.createUser(tf_nom.getText(), tf_prenom.getText(), tf_telephone.getText(), tf_email.getText(), tf_adresse.getText(), password, dateNaissance,
			                dateInscription, roleSystem);
			        if (result) {
						JOptionPane.showConfirmDialog(null, "Votre compte a été créé avec succès !", "Création de compte", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
						viderChamps();
			        } else {
						JOptionPane.showConfirmDialog(null, "Quelque chose s'est mal passé !", "Erreur de création de compte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			        }
				}
			}
		});
		btn_creer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_creer.setForeground(Color.WHITE);
		btn_creer.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		btn_creer.setBackground(new Color(135, 206, 250));
		btn_creer.setBounds(400, 466, 220, 60);
		this.getContentPane().add(btn_creer);
		
		JLabel lbl_email_1 = new JLabel("Téléphone");
		lbl_email_1.setForeground(new Color(135, 206, 250));
		lbl_email_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_email_1.setBackground(Color.WHITE);
		lbl_email_1.setBounds(500, 129, 124, 40);
		this.getContentPane().add(lbl_email_1);
		
		JLabel lbl_password_1 = new JLabel("Email");
		lbl_password_1.setForeground(new Color(135, 206, 250));
		lbl_password_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_password_1.setBackground(Color.WHITE);
		lbl_password_1.setBounds(76, 349, 159, 40);
		this.getContentPane().add(lbl_password_1);
		
		tf_adresse = new JTextField();
		tf_adresse.setToolTipText("Entrez votre adresse");
		tf_adresse.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_adresse.setColumns(10);
		tf_adresse.setBounds(211, 270, 697, 40);
		this.getContentPane().add(tf_adresse);
		
		JLabel lbl_email_2 = new JLabel("Adresse");
		lbl_email_2.setForeground(new Color(135, 206, 250));
		lbl_email_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_email_2.setBackground(Color.WHITE);
		lbl_email_2.setBounds(76, 267, 105, 40);
		this.getContentPane().add(lbl_email_2);
		
		JLabel lbl_password_2 = new JLabel("Mot de passe");
		lbl_password_2.setForeground(new Color(135, 206, 250));
		lbl_password_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_password_2.setBackground(Color.WHITE);
		lbl_password_2.setBounds(500, 349, 159, 40);
		this.getContentPane().add(lbl_password_2);
		
		tf_password = new JPasswordField();
		tf_password.setToolTipText("Entrez votre mot de passe");
		tf_password.setBounds(657, 349, 250, 40);
		this.getContentPane().add(tf_password);
		
		tf_prenom = new JTextField();
		tf_prenom.setToolTipText("Entrez votre prénom");
		tf_prenom.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_prenom.setColumns(10);
		tf_prenom.setBounds(211, 199, 250, 40);
		this.getContentPane().add(tf_prenom);
		
		tf_email = new JTextField();
		tf_email.setToolTipText("Entrez votre email");
		tf_email.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_email.setColumns(10);
		tf_email.setBounds(211, 349, 250, 40);
		this.getContentPane().add(tf_email);
		
		JLabel lbl_email_1_1 = new JLabel("Date de naissance");
		lbl_email_1_1.setForeground(new Color(135, 206, 250));
		lbl_email_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 18));
		lbl_email_1_1.setBackground(Color.WHITE);
		lbl_email_1_1.setBounds(500, 199, 150, 40);
		this.getContentPane().add(lbl_email_1_1);
		
		// Format pour l'affichage de la saisie
		NumberFormat format = NumberFormat.getNumberInstance();
		NumberFormat phoneFormat = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		phoneFormat.setGroupingUsed(false);
		
		tf_telephone = new JFormattedTextField(phoneFormat);
		tf_telephone.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_telephone.setBounds(657, 130, 250, 40);
		this.getContentPane().add(tf_telephone);
		
		tf_date_jour = new JFormattedTextField(format);
		tf_date_jour.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_date_jour.setToolTipText("Entrez le jour");
		tf_date_jour.setHorizontalAlignment(SwingConstants.CENTER);
		tf_date_jour.setBounds(657, 200, 60, 40);
		tf_date_jour.setColumns(2);
		this.getContentPane().add(tf_date_jour);
		
		tf_date_mois = new JFormattedTextField(format);
		tf_date_mois.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_date_mois.setToolTipText("Entrez le mois");
		tf_date_mois.setHorizontalAlignment(SwingConstants.CENTER);
		tf_date_mois.setBounds(727, 200, 60, 40);
		this.getContentPane().add(tf_date_mois);
		
		tf_date_annee = new JFormattedTextField(format);
		tf_date_annee.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_date_annee.setToolTipText("Entrez l'année");
		tf_date_annee.setHorizontalAlignment(SwingConstants.CENTER);
		tf_date_annee.setBounds(797, 200, 110, 40);
		this.getContentPane().add(tf_date_annee);
	}
	
	private boolean champsRemplis() {
		boolean correct = true;
		if (tf_nom.getText().length() < 3) {
			JOptionPane.showConfirmDialog(null, "Le nom doit contenir 3 caractères minimum.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tf_prenom.getText().length() < 3) {
			JOptionPane.showConfirmDialog(null, "Le prénom doit contenir 3 caractères minimum.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (tf_telephone.getText().length() < 9) {
			JOptionPane.showConfirmDialog(null, "Le numéro de téléphone doit contenir 9 caractères minimum.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// Vérification de la date
		int jour = 0, mois = 0, annee = 0;
		
		try {
			jour = Integer.parseInt(tf_date_jour.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Le jour doit contenir 2 chiffres.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			mois = Integer.parseInt(tf_date_mois.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Le mois doit contenir 2 chiffres.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			annee = Integer.parseInt(tf_date_annee.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "L'année doit contenir 4 chiffres.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (annee > LocalDate.now().getYear()) {
			JOptionPane.showConfirmDialog(null, "L'année saisie est incorrecte.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		switch (mois) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:{
				if (jour < 1 || jour > 31) {
					JOptionPane.showConfirmDialog(null, "Numéro du jour incorrect par rapport au mois.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
			case 2:{
				if (jour < 1 || jour > 29) {
					JOptionPane.showConfirmDialog(null, "Numéro du jour incorrect par rapport au mois.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
			case 4:
			case 6:
			case 9:
			case 11:{
				if (jour < 1 || jour > 30) {
					JOptionPane.showConfirmDialog(null, "Numéro du jour incorrect par rapport au mois.", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
		}
		
		// Vérification de l'email
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		
		if (!tf_email.getText().matches(regex)) {
			JOptionPane.showConfirmDialog(null, "Veuillez saisir un email correct. (ex: nom@example.com)", "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// Vérification du mot de passe
		boolean motDePasseOk = true;
        String errors = "Le mot de passe doit avoir : \n";

		char[] passwordChars = tf_password.getPassword();
		String password = new String(passwordChars);

        if (password.length() <= 8) {
            errors += "- Minimum 8 caractères.\n";
            motDePasseOk = false;
        }
        if (!password.matches(".*[A-Z].*")) {
            errors += "- Minimum une lettre majuscule.\n";
            motDePasseOk = false;
        }
        if (!password.matches(".*[a-z].*")) {
            errors += "- Minimum une lettre minuscule.\n";
            motDePasseOk = false;
        }
        if (!password.matches(".*[0-9].*")) {
            errors += "- Minimum un chiffre.\n";
            motDePasseOk = false;
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-={}|:;<>,.?~].*")) {
            errors += "- Minimum un caractère spécial.\n";
            motDePasseOk = false;
        }
        
        if (!motDePasseOk) {
			JOptionPane.showConfirmDialog(null, "Le mot de passe doit contenir : "+ errors, "Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
        }
		
		return correct;
	}
	
	private void viderChamps() {
		tf_nom.setText("");
		tf_prenom.setText("");
		tf_telephone.setText("");
		tf_adresse.setText("");
		tf_date_jour.setText("");
		tf_date_mois.setText("");
		tf_date_annee.setText("");
		tf_email.setText("");
		tf_password.setText("");
	}
}
