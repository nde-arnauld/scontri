package views.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.EventController;
import controllers.Org_EventController;
import controllers.Part_EventController;
import dao.Database;
import dao.EventDAO;
import dao.Org_EventDAO;
import dao.Part_EventDAO;
import models.Event;
import models.Part_Event;
import models.User;
import utils.enums.PartEventStatus;

public class Home extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private JLabel lblNom, lblCapacite, lblPrix, lblDateDebut, lblDateFin;
	private JTextArea tADescription;
	private List<Event> events;
	private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	private Org_EventController org_EventController;
	private Part_EventController part_EventController;
	private EventController eventController;
	private int idLoggedUser;

	/**
	 * Create the application.
	 */
	public Home(User user) {
		Connection connection = Database.getConnection();

		this.idLoggedUser = user.getIdUser();
		setTitle("Accueil    Utilisateur connecté --> " + user.getNom() + " " + user.getPrenom());

		eventController = new EventController(new EventDAO(connection));
		org_EventController = new Org_EventController(new Org_EventDAO(connection));
		part_EventController = new Part_EventController(eventController, new Part_EventDAO(connection));

		events = new ArrayList<Event>();

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(UIConst.X, UIConst.Y, UIConst.WIDTH, UIConst.HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Création du menu "Evènement"
		JMenu menuHome = new JMenu("Accueil");
		JMenu menuMesEvents = new JMenu("Evènements");
		menuBar.add(menuHome);
		menuBar.add(menuMesEvents);

		JMenuItem mesEvents = new JMenuItem("Mes évènements");
		JMenuItem mesRequests = new JMenuItem("Mes Participations");

		menuMesEvents.add(mesEvents);
		menuMesEvents.add(mesRequests);

		// Ajouter une action lorsqu'on clique
		mesEvents.addActionListener(e -> {
			MyEvent myEvent = new MyEvent(idLoggedUser);
			myEvent.setVisible(true);
		});

		mesRequests.addActionListener(e -> {
			MyRequest myRequest = new MyRequest(idLoggedUser);
			myRequest.setVisible(true);
		});

		menuHome.addActionListener(e -> {
			this.setVisible(true);
		});

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Liste des événements en cours"));
		panel.setBounds(10, 11, 631, 530);
		panel.setLayout(new BorderLayout());

		// Création du tableau avec un modèle de données modifiable
		String[] columnNames = { "ID", "Nom", "Date Début", "Date Fin" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Rendre les cellules non éditables
			}
		};
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true); // Activer le tri
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);
		// Charger les événements depuis la BDD
		loadEventData();

		// Gérer le clic sur le tableau
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int eventId = (int) table.getValueAt(selectedRow, 0);
					showDetails(eventId);
				}
			}
		});
		contentPane.add(panel);

		// Panel pour afficher les détails
		JPanel panelDetails = new JPanel();
		panelDetails.setBounds(650, 11, 349, 530);
		contentPane.add(panelDetails);
		panelDetails.setBorder(BorderFactory.createTitledBorder("Détails de l'événement"));
		panelDetails.setLayout(null);

		lblNom = new JLabel("Nom de l'évenement");
		lblNom.setHorizontalAlignment(SwingConstants.CENTER);
		lblNom.setFont(UIConst.DEFAULT_FONT_BOLD);
		lblNom.setBounds(10, 23, 329, 23);
		panelDetails.add(lblNom);

		lblCapacite = new JLabel("Capacité");
		lblCapacite.setFont(UIConst.DEFAULT_FONT_BOLD);
		lblCapacite.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapacite.setBounds(18, 130, 329, 14);
		panelDetails.add(lblCapacite);

		lblPrix = new JLabel("Prix");
		lblPrix.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrix.setFont(UIConst.DEFAULT_FONT_BOLD);
		lblPrix.setBounds(18, 163, 329, 14);
		panelDetails.add(lblPrix);

		lblDateDebut = new JLabel("Date de début");
		lblDateDebut.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateDebut.setBounds(49, 202, 123, 14);
		panelDetails.add(lblDateDebut);

		lblDateFin = new JLabel("Date de fin");
		lblDateFin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateFin.setBounds(213, 202, 138, 14);
		panelDetails.add(lblDateFin);

		JLabel lblNewLabel = new JLabel("Du :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 202, 37, 14);
		panelDetails.add(lblNewLabel);

		// bouton "Liste des participants"

		JButton btnListPart = new JButton("Liste des participants");
		btnListPart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnListPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Vérifier si une ligne est sélectionnée
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un événement.");
					return;
				}

				// Récupérer l'ID de l'événement sélectionné
				int selectedEventId = (int) table.getValueAt(selectedRow, 0); // Supposons que l'ID est en première
																				// colonne

				// Récupérer la liste des participants
				part_EventController = new Part_EventController(eventController,
						new Part_EventDAO(Database.getConnection()));
				List<Map<String, Object>> participantsInfo = part_EventController
						.getParticipantsInfoForEvent(selectedEventId);

				// Créer la boîte de dialogue modale
				JDialog dialog = new JDialog(Home.this, "Gestion des participants", true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setSize(600, 500);
				dialog.setLocationRelativeTo(Home.this);

				ManagePartEvent managePartEvent = new ManagePartEvent(participantsInfo, Database.getConnection(),
						idLoggedUser, selectedEventId);
				dialog.setContentPane(managePartEvent.getContentPane());
				dialog.setVisible(true);
			}
		});

		btnListPart.setForeground(UIConst.WHITE_COLOR);
		btnListPart.setBorderPainted(false);
		btnListPart.setBackground(UIConst.PRIMARY_COLOR);
		btnListPart.setBounds(53, 451, 250, 28);
		panelDetails.add(btnListPart);

		tADescription = new JTextArea();
		tADescription.setBounds(10, 57, 329, 64);
		tADescription.setLineWrap(true); // Active le retour à la ligne automatique
		tADescription.setWrapStyleWord(true); // Coupe proprement aux mots
		tADescription.setEditable(false); // Rend le texte non éditable
		tADescription.setOpaque(false); // Fond transparent (comme JLabel)
		tADescription.setFont(UIConst.DEFAULT_FONT_PLAIN);
		// Centrer le texte manuellement
		tADescription.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		tADescription.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
		panelDetails.add(tADescription);

		JLabel lblAu = new JLabel("au");
		lblAu.setHorizontalAlignment(SwingConstants.CENTER);
		lblAu.setBounds(174, 202, 37, 14);
		panelDetails.add(lblAu);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(
				"C:\\Users\\Etudiant\\Documents\\COURS_L3_STI_UCPP\\SEMESTRE_2\\PROJET\\Scontri\\media\\laCarte.png"));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 264, 329, 163);
		panelDetails.add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 541, 989, 50);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		// bouton "Ajouter un commentaire"

		JButton btnParticiper = new JButton("Participer à l'événement");
		btnParticiper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnParticiper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Vérifier si une ligne est sélectionnée
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un événement.");
					return;
				}

				// Récupérer l'ID de l'événement sélectionné
				int selectedEventId = (int) table.getValueAt(selectedRow, 0); // Supposons que l'ID est en première
				boolean success = part_EventController.addPartEvent(idLoggedUser, selectedEventId);
				if (success) {
					JOptionPane.showMessageDialog(null, "Participation ajoutée avec succès !");
				} else {
					JOptionPane.showMessageDialog(null, "Échec de l'ajout de la participation. Veuillez réessayer.");
				}
			}
		});
		btnParticiper.setBorderPainted(false);
		btnParticiper.setForeground(UIConst.WHITE_COLOR);
		btnParticiper.setBackground(UIConst.PRIMARY_COLOR);
		btnParticiper.setBounds(196, 11, 200, 28);
		panel_2.add(btnParticiper);

	}

	/**
	 * Charge les événements depuis la base de données et remplit le tableau.
	 */
	private void loadEventData() {

		List<Event> allEvent = eventController.listEvents();

		for (Event event : allEvent) {
			List<User> orgs = org_EventController.listOrgsEvent(event.getIdEvent());
			boolean isOrg = false;

			for (User org : orgs) {
				if (org.getIdUser() == idLoggedUser) {
					isOrg = true;
				}
			}

			if (event.getStatus().compareTo("actif") == 0 && !isOrg) {
				events.add(event);
			}
		}

		for (Event event : events) {

			Object[] rowData = {
					event.getIdEvent(),
					event.getNom(),
					event.getDateDebut().format(outputFormatter),
					event.getDateFin().format(outputFormatter)
			};
			tableModel.addRow(rowData);
		}
	}

	/**
	 * Affiche les détails d'un événement dans les labels.
	 */
	private void showDetails(int selectedEventId) {
		List<Part_Event> partsEvents = part_EventController.getPartsEvent(selectedEventId);
		int participation = 0;

		for (Part_Event part : partsEvents) {
			if (part.getStatus() == PartEventStatus.VALIDEE) {
				participation++;
			}
		}

		for (Event event : events) {
			if (event.getIdEvent() == selectedEventId) {
				lblNom.setText(event.getNom());
				tADescription.setText(event.getDescription());
				lblCapacite.setText("Nombre de place disponible: " + (event.getCapacite() - participation) + "/"
						+ event.getCapacite());
				lblPrix.setText("Prix: " + event.getPrix() + " €");
				lblDateDebut.setText(event.getDateDebut().format(outputFormatter));
				lblDateFin.setText(event.getDateFin().format(outputFormatter));
				return;
			}
		}

		// Si aucun événement trouvé, afficher un message par défaut
		lblNom.setText("Nom: N/A");
		tADescription.setText("Description: N/A");
		lblCapacite.setText("Capacité: N/A");
		lblPrix.setText("Prix: N/A");
		lblDateDebut.setText("Début: N/A");
		lblDateFin.setText("Fin: N/A");
	}

}
