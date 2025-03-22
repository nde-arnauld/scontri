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

import controllers.Org_EventController;
import controllers.Part_EventController;
import dao.Database;
import dao.EventDAO;
import dao.Org_EventDAO;
import dao.Part_EventDAO;
import models.Event;
import models.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyEvent extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblNom, lblCapacite, lblPrix, lblDateDebut, lblDateFin;
    private JTextArea tADescription;
    private List<Event> events; // Liste des événements récupérés de la BDD
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    private Connection connection;
    private Org_EventDAO org_EventDAO;
    private Part_EventDAO part_EventDAO;
    private Org_EventController org_EventController;
    private Part_EventController part_EventController;
    private int idLoggedUser;

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
    public MyEvent(int idLoggedUser) {
    	
		
		this.connection = Database.getConnection();
		this.org_EventDAO = new Org_EventDAO(connection);
		this.org_EventController = new Org_EventController(org_EventDAO);
		this.idLoggedUser = idLoggedUser;
		
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
        lblNom.setBounds(10, 23, 329, 23);
        panelDetails.add(lblNom);
        
//        lblDescription = new JLabel("Description");
//        lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
//        lblDescription.set
//        lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
//        lblDescription.setBounds(10, 123, 329, 86);
//        panelDetails.add(lblDescription);
        
        lblCapacite = new JLabel("Capacité");
        lblCapacite.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblCapacite.setHorizontalAlignment(SwingConstants.CENTER);
        lblCapacite.setBounds(18, 130, 329, 14);
        panelDetails.add(lblCapacite);
        
        lblPrix = new JLabel("Prix");
        lblPrix.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrix.setFont(new Font("Tahoma", Font.BOLD, 11));
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
        
        JButton btnListPart = new JButton("Liste des participants");
        btnListPart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Vérifier si une ligne est sélectionnée
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un événement.");
                    return;
                }

                // Récupérer l'ID de l'événement sélectionné
                int selectedEventId = (int) table.getValueAt(selectedRow, 0); // Supposons que l'ID est en première colonne

                // Récupérer la liste des participants 
                part_EventDAO = new Part_EventDAO(connection);
                part_EventController = new Part_EventController(null, part_EventDAO);
                List<User> usersList = part_EventController.getUsersForEvent(selectedEventId);
                
             // Créer la boîte de dialogue modale
                JDialog dialog = new JDialog(MyEvent.this, "Gestion des participants", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(MyEvent.this);
                
                ManagePartEvent managePartEvent = new ManagePartEvent(usersList);
                dialog.setContentPane(managePartEvent.getContentPane());
                dialog.setVisible(true);
            }
        });

        btnListPart.setForeground(Color.WHITE);
        btnListPart.setBorderPainted(false);
        btnListPart.setBackground(new Color(0, 0, 128));
        btnListPart.setBounds(53, 451, 250, 28);
        panelDetails.add(btnListPart);
        
        tADescription = new JTextArea();
        tADescription.setBounds(10, 57, 329, 64);
        tADescription.setLineWrap(true);  // Active le retour à la ligne automatique
        tADescription.setWrapStyleWord(true); // Coupe proprement aux mots
        tADescription.setEditable(false); // Rend le texte non éditable
        tADescription.setOpaque(false); // Fond transparent (comme JLabel)
        tADescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
        // Centrer le texte manuellement
        tADescription.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        tADescription.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        panelDetails.add(tADescription);
        
        JLabel lblAu = new JLabel("au");
        lblAu.setHorizontalAlignment(SwingConstants.CENTER);
        lblAu.setBounds(174, 202, 37, 14);
        panelDetails.add(lblAu);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("D:\\UNIVERSITE_DE_CORSE\\SECOND SEMESTER\\PROJET-IHM\\SONCTRI\\scontri\\media\\laCarte.png"));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(10, 264, 329, 163);
        panelDetails.add(lblNewLabel_1);

       
        JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 552, 989, 50);            
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnCreateEvent = new JButton("Créer un évenement");
        btnCreateEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 // Créer la boîte de dialogue modale
                JDialog dialog = new JDialog(MyEvent.this, "Créer un évenement", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(400, 650);
                dialog.setLocationRelativeTo(MyEvent.this);
                
                CreateUpdateEvent createUpdateEvent  = new CreateUpdateEvent(connection,idLoggedUser);
                dialog.setContentPane(createUpdateEvent.getContentPane());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Recharger les données de la table après la fermeture du dialogue
                        tableModel.setRowCount(0);
                        loadEventData();
                    }
                });
                dialog.setVisible(true);
            }
        });
		btnCreateEvent.setBorderPainted(false);
		btnCreateEvent.setForeground(Color.WHITE);
		btnCreateEvent.setBackground(new Color(0, 0, 128));
		btnCreateEvent.setBounds(61, 11, 170, 28);
		panel_2.add(btnCreateEvent);
		
		JButton btnUpdateEvent = new JButton("Modifier");
        btnUpdateEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Vérifier si une ligne est sélectionnée
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un événement.");
                    return;
                }

                // Récupérer l'ID de l'événement sélectionné
                int selectedEventId = (int) table.getValueAt(selectedRow, 0); // Supposons que l'ID est en première colonne

                // Récupérer l'événement depuis la base de données
                EventDAO eventDAO = new EventDAO(connection);
                Event selectedEvent = eventDAO.getEventById(selectedEventId);

                if (selectedEvent == null) {
                    JOptionPane.showMessageDialog(null, "Erreur : Impossible de trouver l'événement sélectionné.");
                    return;
                }

                // Créer la boîte de dialogue modale
                JDialog dialog = new JDialog(MyEvent.this, "Modifier un événement", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(400, 650);
                dialog.setLocationRelativeTo(MyEvent.this);

                // Passer l'événement sélectionné à la fenêtre de modification
                CreateUpdateEvent createUpdateEvent = new CreateUpdateEvent(selectedEvent, connection,idLoggedUser);
                dialog.setContentPane(createUpdateEvent.getContentPane());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Recharger les données de la table après la fermeture du dialogue
                        tableModel.setRowCount(0);
                        loadEventData();
                    }
                });
                dialog.setVisible(true);
            }
        });

		btnUpdateEvent.setForeground(Color.WHITE);
		btnUpdateEvent.setBorderPainted(false);
		btnUpdateEvent.setBackground(new Color(0, 0, 128));
		btnUpdateEvent.setBounds(292, 11, 170, 28);
		panel_2.add(btnUpdateEvent);
		
		JButton btnDeleteEvent = new JButton("Suprimer");

		btnDeleteEvent.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow(); // Récupérer la ligne sélectionnée
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un événement à supprimer.", 
		                                          "Aucune sélection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        // Récupérer l'ID de l'événement à supprimer
		        int eventId = (int) table.getValueAt(selectedRow, 0);

		        // Demander confirmation
		        int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet événement ?", 
		                                                    "Confirmation", JOptionPane.YES_NO_OPTION);
		        if (confirm == JOptionPane.YES_OPTION) {
		            // Supprimer l'événement de la base de données
		            EventDAO eventDAO = new EventDAO(connection);
		            boolean deleted = eventDAO.deleteEvent(eventId);

		            if (deleted) {
		                JOptionPane.showMessageDialog(null, "Événement supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
		                tableModel.setRowCount(0);
		                loadEventData(); // Rafraîchir la liste après suppression
		            } else {
		                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});

		
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
    	
	   events = org_EventController.getEventCreatedByOrg(idLoggedUser);          
	   
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
                tADescription.setText(event.getDescription());
                lblCapacite.setText("Nombre de place disponible: " + event.getCapacite());
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
