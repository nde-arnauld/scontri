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
		JMenu menuFichier = new JMenu("Menu");
		menuBar.add(menuFichier);

		// Création de l'item "Exporter"
		JMenuItem itemExporter = new JMenuItem("Mes évenements");
		menuFichier.add(itemExporter);

		// Ajouter une action lorsqu'on clique sur "Exporter"
		itemExporter.addActionListener(e -> {
			MyEvent myEvent = new MyEvent(1);
			myEvent.setVisible(true);
		});

		
	}

}
