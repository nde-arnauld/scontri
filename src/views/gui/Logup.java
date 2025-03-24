package views.gui;

import controllers.UserController;
import dao.UserDAO;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.text.NumberFormat;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import utils.Password;

public class Logup extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_nom, tf_prenom, tf_email, tf_adresse;
	private JPasswordField tf_password;
	private JFormattedTextField tf_telephone, tf_date_jour, tf_date_mois, tf_date_annee;
	private JButton btnValider, btnAnnuler;
	private Connection connection;
	private UserController userController;
	private UserDAO userDAO;

	/**
	 * Create the application.
	 */
	public Logup(Connection connection, UserDAO userDAO, UserController userController) {
		this.connection = connection;
		this.userDAO = userDAO;
		this.userController = userController;
		initialize();
	}

	/**
	 * Initialize the contents of the dialog.
	 */
	private void initialize() {
		setTitle("Inscription");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 650);
		setModal(true);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Titre
		JLabel lblTitre = new JLabel("Inscription", SwingConstants.CENTER);
		lblTitre.setFont(UIConst.TITLE_FONT);
		lblTitre.setForeground(UIConst.PRIMARY_COLOR);
		lblTitre.setBounds(10, 10, 360, 30);
		contentPane.add(lblTitre);

		// Nom
		JLabel lblNom = new JLabel("Nom:");
		lblNom.setBounds(10, 50, 178, 33);
		contentPane.add(lblNom);
		tf_nom = new JTextField();
		tf_nom.setBounds(198, 50, 178, 33);
		contentPane.add(tf_nom);

		// Prénom
		JLabel lblPrenom = new JLabel("Prénom:");
		lblPrenom.setBounds(10, 100, 178, 33);
		contentPane.add(lblPrenom);
		tf_prenom = new JTextField();
		tf_prenom.setBounds(198, 100, 178, 33);
		contentPane.add(tf_prenom);

		// Téléphone
		JLabel lblTelephone = new JLabel("Téléphone:");
		lblTelephone.setBounds(10, 150, 178, 33);
		contentPane.add(lblTelephone);
		tf_telephone = new JFormattedTextField();
		tf_telephone.setBounds(198, 150, 178, 33);
		contentPane.add(tf_telephone);

		// Date de naissance
		JLabel lblDateNaissance = new JLabel("Date de naissance:");
		lblDateNaissance.setBounds(10, 200, 178, 33);
		contentPane.add(lblDateNaissance);

		JPanel datePanel = new JPanel();
		datePanel.setBounds(198, 200, 178, 33);
		datePanel.setLayout(new GridLayout(1, 3, 5, 0));

		// Format pour l'affichage de la saisie
		NumberFormat format = NumberFormat.getNumberInstance();
		NumberFormat phoneFormat = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		phoneFormat.setGroupingUsed(false);

		tf_date_jour = new JFormattedTextField(format);
		tf_date_jour.setToolTipText("Jour de naissance (JJ)");
		tf_date_mois = new JFormattedTextField(format);
		tf_date_mois.setToolTipText("Mois de naissance (MM)");
		tf_date_annee = new JFormattedTextField(format);
		tf_date_annee.setToolTipText("Année de naissance (AAAA)");

		datePanel.add(tf_date_jour);
		datePanel.add(tf_date_mois);
		datePanel.add(tf_date_annee);
		contentPane.add(datePanel);

		// Adresse
		JLabel lblAdresse = new JLabel("Adresse:");
		lblAdresse.setBounds(10, 260, 178, 33);
		contentPane.add(lblAdresse);
		tf_adresse = new JTextField();
		tf_adresse.setBounds(198, 260, 178, 33);
		contentPane.add(tf_adresse);

		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 320, 178, 33);
		contentPane.add(lblEmail);
		tf_email = new JTextField();
		tf_email.setBounds(198, 320, 178, 33);
		contentPane.add(tf_email);

		// Mot de passe
		JLabel lblPassword = new JLabel("Mot de passe:");
		lblPassword.setBounds(10, 380, 178, 33);
		contentPane.add(lblPassword);
		tf_password = new JPasswordField();
		tf_password.setBounds(198, 380, 178, 33);
		contentPane.add(tf_password);

		// Boutons
		btnValider = new JButton("Valider");
		btnValider.setBorderPainted(false);
		btnValider.setBackground(new Color(0, 0, 160));
		btnValider.setForeground(Color.WHITE);
		btnValider.setBounds(10, 570, 178, 33);
		btnValider.addActionListener(e -> handleInscription());
		contentPane.add(btnValider);

		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBorderPainted(false);
		btnAnnuler.setForeground(Color.BLACK);
		btnAnnuler.setBackground(Color.WHITE);
		btnAnnuler.setBounds(198, 570, 178, 33);
		btnAnnuler.addActionListener(e -> viderChamps());
		contentPane.add(btnAnnuler);
	}

	private void handleInscription() {
		if (champsRemplis()) {
			char[] passwordChars = tf_password.getPassword();
			String password = new String(passwordChars);

			password = Password.hasherMotDePasse(password);

			LocalDate dateNaissance = LocalDate.parse(
					String.format("%s-%02d-%02d", tf_date_annee.getText(), Integer.parseInt(tf_date_mois.getText()), Integer.parseInt(tf_date_jour.getText())));

			LocalDate dateInscription = LocalDate.now();

			String roleSystem = "user";

			boolean result = userController.createUser(tf_nom.getText(), tf_prenom.getText(),
					tf_telephone.getText(), tf_email.getText(), tf_adresse.getText(), password, dateNaissance,
					dateInscription, roleSystem);
			if (result) {
				JOptionPane.showConfirmDialog(null, "Votre compte a été créé avec succès !",
						"Création de compte", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				viderChamps();
			} else {
				JOptionPane.showConfirmDialog(null, "Quelque chose s'est mal passé !",
						"Erreur de création de compte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean champsRemplis() {
		boolean correct = true;
		if (tf_nom.getText().length() < 3) {
			JOptionPane.showConfirmDialog(this, "Le nom doit contenir 3 caractères minimum.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (tf_prenom.getText().length() < 3) {
			JOptionPane.showConfirmDialog(this, "Le prénom doit contenir 3 caractères minimum.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (tf_telephone.getText().length() < 9) {
			JOptionPane.showConfirmDialog(this, "Le numéro de téléphone doit contenir 9 caractères minimum.",
					"Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérification de la date
		int jour = 0, mois = 0, annee = 0;

		try {
			jour = Integer.parseInt(tf_date_jour.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "Le jour doit contenir 2 chiffres.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			mois = Integer.parseInt(tf_date_mois.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "Le mois doit contenir 2 chiffres.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			annee = Integer.parseInt(tf_date_annee.getText());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "L'année doit contenir 4 chiffres.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (annee > LocalDate.now().getYear()) {
			JOptionPane.showConfirmDialog(this, "L'année saisie est incorrecte.", "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		switch (mois) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12: {
				if (jour < 1 || jour > 31) {
					JOptionPane.showConfirmDialog(this, "Numéro du jour incorrect par rapport au mois.",
							"Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
			case 2: {
				if (jour < 1 || jour > 29) {
					JOptionPane.showConfirmDialog(this, "Numéro du jour incorrect par rapport au mois.",
							"Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
			case 4:
			case 6:
			case 9:
			case 11: {
				if (jour < 1 || jour > 30) {
					JOptionPane.showConfirmDialog(this, "Numéro du jour incorrect par rapport au mois.",
							"Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					return false;
				}
				break;
			}
		}

		// Vérification de l'email
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		if (!tf_email.getText().matches(regex)) {
			JOptionPane.showConfirmDialog(this, "Veuillez saisir un email correct. (ex: nom@example.com)",
					"Saisie incorrecte", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showConfirmDialog(this, "Le mot de passe doit contenir : " + errors, "Saisie incorrecte",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
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
