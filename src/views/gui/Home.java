package views.gui;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Home extends JFrame {
	private int idUser;

	/**
	 * Create the application.
	 */
	public Home(int idUser) {
		this.idUser = idUser;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(UIConst.X, UIConst.Y, UIConst.WIDTH, UIConst.HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Création du menu "Fichier"
		JMenu menuMesEvents = new JMenu("Evènements");
		menuBar.add(menuMesEvents);

		JMenuItem mesEvents = new JMenuItem("Mes évènements");
		JMenuItem mesRequests = new JMenuItem("Mes Participations");

		menuMesEvents.add(mesEvents);
		menuMesEvents.add(mesRequests);

		// Ajouter une action lorsqu'on clique
		mesEvents.addActionListener(e -> {
			MyEvent myEvent = new MyEvent(idUser);
			myEvent.setVisible(true);
		});

		mesRequests.addActionListener(e -> {

		});

	}

}
