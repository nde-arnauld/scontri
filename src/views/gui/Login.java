package views.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.UserController;
import dao.Database;
import dao.UserDAO;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField tf_email;
	private JPasswordField tf_password;
	private Connection connection;
	private UserController userController;
	private UserDAO userDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = Database.getConnection();
		userDAO = new UserDAO(connection);
		userController = new UserController(userDAO);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, UIConst.WIDTH, UIConst.HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelGauche = new JPanel();
		panelGauche.setBackground(UIConst.PRIMARY_COLOR);
		panelGauche.setBounds(0, 0, UIConst.WIDTH/2, 563);
		frame.getContentPane().add(panelGauche);
		panelGauche.setLayout(null);
		
		JLabel lbl_greatingMessage = new JLabel("Bienvenue ");
		lbl_greatingMessage.setForeground(new Color(255, 255, 255));
		lbl_greatingMessage.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 50));
		lbl_greatingMessage.setBackground(new Color(0, 0, 0));
		lbl_greatingMessage.setBounds(130, 96, 260, 85);
		panelGauche.add(lbl_greatingMessage);
		
		JLabel lbl_besoinNewAct = new JLabel("Besoin de faire une nouvelle activité ?");
		lbl_besoinNewAct.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_besoinNewAct.setForeground(new Color(255, 255, 255));
		lbl_besoinNewAct.setBounds(46, 257, 430, 60);
		panelGauche.add(lbl_besoinNewAct);
		
		JLabel lbl_bonEndroit = new JLabel("Vous êtes au bon endroit!");
		lbl_bonEndroit.setForeground(Color.WHITE);
		lbl_bonEndroit.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_bonEndroit.setBounds(111, 299, 300, 50);
		panelGauche.add(lbl_bonEndroit);
		
		JLabel lbl_connexion = new JLabel("CONNEXION");
		lbl_connexion.setForeground(UIConst.PRIMARY_COLOR);
		lbl_connexion.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 40));
		lbl_connexion.setBackground(new Color(255, 255, 255));
		lbl_connexion.setBounds(612, 80, 260, 85);
		frame.getContentPane().add(lbl_connexion);
		
		tf_email = new JTextField();
		tf_email.setToolTipText("Entrez votre email");
		tf_email.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		tf_email.setBounds(690, 195, 250, 40);
		frame.getContentPane().add(tf_email);
		tf_email.setColumns(10);
		
		JLabel lbl_email = new JLabel("Email");
		lbl_email.setLabelFor(tf_email);
		lbl_email.setBackground(new Color(255, 255, 255));
		lbl_email.setForeground(new Color(135, 206, 250));
		lbl_email.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_email.setBounds(521, 205, 80, 40);
		frame.getContentPane().add(lbl_email);
		
		JLabel lbl_password = new JLabel("Mot de passe");
		lbl_password.setForeground(new Color(135, 206, 250));
		lbl_password.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		lbl_password.setBackground(Color.WHITE);
		lbl_password.setBounds(520, 275, 159, 40);
		frame.getContentPane().add(lbl_password);
		
		JButton btn_seConnecter = new JButton("SE CONNECTER");
		btn_seConnecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = tf_email.getText();
				char[] passwordChars = tf_password.getPassword();
				String password = new String(passwordChars);
				
				boolean result = userController.loginUser(email, password);
				
				if (result) {
					JOptionPane.showMessageDialog(null, "Vous êtes connecté avec succès !", "Connexion réussie", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Email ou mot de passe incorrect", "Erreur de connexion", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				Arrays.fill(passwordChars, '\0');
			}
		});
		btn_seConnecter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_seConnecter.setForeground(new Color(255, 255, 255));
		btn_seConnecter.setBackground(new Color(135, 206, 250));
		btn_seConnecter.setFont(new Font("Yu Gothic UI", Font.BOLD, 24));
		btn_seConnecter.setBounds(652, 372, 220, 60);
		frame.getContentPane().add(btn_seConnecter);
		
		tf_password = new JPasswordField();
		lbl_password.setLabelFor(tf_password);
		tf_password.setBounds(690, 264, 250, 40);
		frame.getContentPane().add(tf_password);
		
		JLabel lbl_bonEndroit_1 = new JLabel("Vous n'avez pas de compte ?");
		lbl_bonEndroit_1.setBackground(new Color(255, 255, 255));
		lbl_bonEndroit_1.setBounds(521, 484, 276, 30);
		frame.getContentPane().add(lbl_bonEndroit_1);
		lbl_bonEndroit_1.setForeground(new Color(176, 224, 230));
		lbl_bonEndroit_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		
		JLabel lbl_inscrire = new JLabel("<html><u>S'inscrire</u></html>");
		lbl_inscrire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Logup logup = new Logup(connection, userDAO, userController);
				logup.setVisible(true);
			}
		});
		lbl_inscrire.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_inscrire.setForeground(new Color(135, 206, 250));
		lbl_inscrire.setFont(new Font("Yu Gothic UI", Font.BOLD | Font.ITALIC, 22));
		lbl_inscrire.setBackground(Color.WHITE);
		lbl_inscrire.setBounds(798, 484, 100, 30);
		frame.getContentPane().add(lbl_inscrire);
	}
}
