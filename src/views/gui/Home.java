package views.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Home {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
        frame.setBounds(100, 50, 1024, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// Création du menu "Fichier"
		JMenu menuMesEvents = new JMenu("Evènements");
		menuBar.add(menuMesEvents);

		JMenuItem mesEvents = new JMenuItem("Mes évènements");
		JMenuItem mesRequests = new JMenuItem("Mes Participations");

		menuMesEvents.add(mesEvents);
		menuMesEvents.add(mesRequests);
		

		// Ajouter une action lorsqu'on clique 
		mesEvents.addActionListener(e -> {
			MyEvent myEvent = new MyEvent(1);
			myEvent.setVisible(true);
		});
		mesRequests.addActionListener(e -> {
			
		});

		
	}

}
