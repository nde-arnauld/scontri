package views.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.EventController;
import controllers.UserController;
import dao.Database;
import dao.EventDAO;
import dao.UserDAO;
import models.Event;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyEvent extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblNom, lblDescription, lblCapacite, lblPrix, lblDateDebut, lblDateFin;
    private List<Event> events; // Liste des événements récupérés de la BDD
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    private Connection connection;
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private EventController eventController;
    private UserController userController;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    MyEvent frame = new MyEvent();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public MyEvent() {
    	
		
		this.connection = Database.getConnection();
		this.eventDAO = new EventDAO(connection);
		this.userDAO = new UserDAO(connection);
		this.eventController = new EventController(eventDAO);
		this.userController = new UserController(userDAO);
		
		initialize();
    	        
    }

    private void initialize() {
    	
    	setTitle("Mes Evenements");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 50, 1024, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Liste des événements créés"));
        panel.setBounds(10, 11, 631, 530);
        panel.setLayout(new BorderLayout());

        // Création du tableau avec un modèle de données modifiable
        String[] columnNames = {"ID", "Nom", "Date Début", "Date Fin"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
        lblNom.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNom.setBounds(10, 63, 329, 23);
        panelDetails.add(lblNom);
        
        lblDescription = new JLabel("Description");
        lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblDescription.setBounds(10, 123, 329, 86);
        panelDetails.add(lblDescription);
        
        lblCapacite = new JLabel("Capacité");
        lblCapacite.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblCapacite.setHorizontalAlignment(SwingConstants.CENTER);
        lblCapacite.setBounds(10, 264, 329, 14);
        panelDetails.add(lblCapacite);
        
        lblPrix = new JLabel("Prix");
        lblPrix.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrix.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblPrix.setBounds(10, 310, 329, 14);
        panelDetails.add(lblPrix);
        
        lblDateDebut = new JLabel("Date de début");
        lblDateDebut.setHorizontalAlignment(SwingConstants.CENTER);
        lblDateDebut.setBounds(29, 346, 123, 14);
        panelDetails.add(lblDateDebut);
        
        lblDateFin = new JLabel("Date de fin");
        lblDateFin.setHorizontalAlignment(SwingConstants.CENTER);
        lblDateFin.setBounds(181, 346, 138, 14);
        panelDetails.add(lblDateFin);
        
        JLabel lblNewLabel = new JLabel("Du :");
        lblNewLabel.setBounds(10, 346, 49, 14);
        panelDetails.add(lblNewLabel);
        
        JButton btnListPart = new JButton("Liste des participants");
        btnListPart.setForeground(Color.WHITE);
        btnListPart.setBorderPainted(false);
        btnListPart.setBackground(new Color(0, 0, 128));
        btnListPart.setBounds(53, 448, 250, 28);
        panelDetails.add(btnListPart);

       
        JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 552, 989, 50);            
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnCreateEvent = new JButton("Créer un évenement");
		btnCreateEvent.setBorderPainted(false);
		btnCreateEvent.setForeground(Color.WHITE);
		btnCreateEvent.setBackground(new Color(0, 0, 128));
		btnCreateEvent.setBounds(61, 11, 170, 28);
		panel_2.add(btnCreateEvent);
		
		JButton btnUpdateEvent = new JButton("Modifier");
		btnUpdateEvent.setForeground(Color.WHITE);
		btnUpdateEvent.setBorderPainted(false);
		btnUpdateEvent.setBackground(new Color(0, 0, 128));
		btnUpdateEvent.setBounds(292, 11, 170, 28);
		panel_2.add(btnUpdateEvent);
		
		JButton btnDeleteEvent = new JButton("Suprimer");
		btnDeleteEvent.setForeground(Color.WHITE);
		btnDeleteEvent.setBorderPainted(false);
		btnDeleteEvent.setBackground(new Color(232, 0, 5));
		btnDeleteEvent.setBounds(523, 11, 170, 28);
		panel_2.add(btnDeleteEvent);
		
		JButton btnClose = new JButton("Fermer");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setForeground(Color.black);
		btnClose.setBackground(new Color(255, 255, 255));
		btnClose.setBounds(754, 11, 170, 28);
		panel_2.add(btnClose);
        
    }
    
    /**
     * Charge les événements depuis la base de données et remplit le tableau.
     */
    private void loadEventData() {
           events = eventController.listEvents();
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

        for (Event event : events) {
            if (event.getIdEvent() == selectedEventId) {
                lblNom.setText(event.getNom());
                lblDescription.setText(event.getDescription());
                lblCapacite.setText("Nombre de place disponible: " + event.getCapacite());
                lblPrix.setText("Prix: " + event.getPrix() + " €");
                lblDateDebut.setText(event.getDateDebut().format(outputFormatter));
                lblDateFin.setText(event.getDateFin().format(outputFormatter));
                return;
            }
        }

      // Si aucun événement trouvé, afficher un message par défaut
        lblNom.setText("Nom: N/A");
        lblDescription.setText("Description: N/A");
        lblCapacite.setText("Capacité: N/A");
        lblPrix.setText("Prix: N/A");
        lblDateDebut.setText("Début: N/A");
        lblDateFin.setText("Fin: N/A");
    }
 }
