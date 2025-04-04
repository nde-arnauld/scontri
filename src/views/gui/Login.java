package views.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame; // Changement de JDialog à JFrame
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllers.UserController;

import java.awt.Cursor;

public class Login extends JFrame { // Changement de JDialog à JFrame

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_email;
	private JPasswordField tf_password;
	private JButton btnValider, btnInscription;

	private UserController userController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		userController = new UserController();
		initialize();
	}

	/**
	 * Initialize the contents of the dialog.
	 */
	private void initialize() {
		setTitle("Connexion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Changement de JDialog.DISPOSE_ON_CLOSE à JFrame.EXIT_ON_CLOSE
		setBounds(100, 100, 400, 300);
		setLocationRelativeTo(null);
		setResizable(false); // Désactiver l'agrandissement de la fenêtre

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Titre
		JLabel lblTitre = new JLabel("Connexion", JLabel.CENTER);
		lblTitre.setFont(UIConst.TITLE_FONT);
		lblTitre.setForeground(UIConst.PRIMARY_COLOR);
		lblTitre.setBounds(10, 10, 360, 30);
		contentPane.add(lblTitre);

		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 70, 178, 33);
		contentPane.add(lblEmail);

		tf_email = new JTextField();
		tf_email.setBounds(198, 70, 178, 33);
		contentPane.add(tf_email);

		// Mot de passe
		JLabel lblPassword = new JLabel("Mot de passe:");
		lblPassword.setBounds(10, 120, 178, 33);
		contentPane.add(lblPassword);

		tf_password = new JPasswordField();
		tf_password.setBounds(198, 120, 178, 33);
		contentPane.add(tf_password);

		// Bouton Valider
		btnValider = new JButton("Se connecter");
		btnValider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnValider.setBorderPainted(false);
		btnValider.setBackground(new Color(0, 0, 160));
		btnValider.setForeground(Color.WHITE);
		btnValider.setBounds(10, 220, 178, 33);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = tf_email.getText();
				char[] passwordChars = tf_password.getPassword();
				String password = new String(passwordChars);

				boolean result = userController.loginUser(email, password);

				if (result) {
					HashMap<String, String> userInfos = userController.getLoggedUserInfos();

					Home home = new Home(userInfos);
					home.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect",
							"Erreur de connexion", JOptionPane.ERROR_MESSAGE);
				}
				Arrays.fill(passwordChars, '\0');
			}
		});
		contentPane.add(btnValider);
		getRootPane().setDefaultButton(btnValider);

		// Bouton Inscription
		btnInscription = new JButton("S'inscrire");
		btnInscription.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInscription.setBorderPainted(false);
		btnInscription.setForeground(Color.BLACK);
		btnInscription.setBackground(Color.WHITE);
		btnInscription.setBounds(198, 220, 178, 33);
		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logup logup = new Logup(userController);
				logup.setVisible(true);
			}
		});
		contentPane.add(btnInscription);
	}
}