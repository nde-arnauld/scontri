package views.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.EventController;
import controllers.Org_EventController;
import controllers.Part_EventController;
import utils.enums.PartEventStatus;

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
    private List<HashMap<String, Object>> events; // Liste des événements récupérés de la BDD
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Org_EventController org_EventController;
    private Part_EventController part_EventController;

    EventController eventController;
    private int idLoggedUser;

    /**
     * Create the frame.
     */
    public MyEvent(int idLoggedUser) {
        this.org_EventController = new Org_EventController();
        this.part_EventController = new Part_EventController();
        this.eventController = new EventController();
        this.idLoggedUser = idLoggedUser;

        initialize();

    }

    private void initialize() {

        setTitle("Mes Evenements");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(UIConst.X, UIConst.Y, UIConst.WIDTH, UIConst.HEIGHT);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Liste des événements créés"));
        panel.setBounds(10, 11, 631, 530);
        panel.setLayout(new BorderLayout());

        // Création du tableau avec un modèle de données modifiable
        String[] columnNames = { "ID", "Nom", "Date Début", "Date Fin" };
        tableModel = new DefaultTableModel(columnNames, 0) {
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
              
                // Créer la boîte de dialogue modale
                JDialog dialog = new JDialog(MyEvent.this, "Gestion des participants", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(MyEvent.this);

                ManagePartEvent managePartEvent = new ManagePartEvent(idLoggedUser,
                        selectedEventId);
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
        tADescription.setLineWrap(true); // Active le retour à la ligne automatique
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
        lblNewLabel_1.setIcon(new ImageIcon(
                "C:\\Users\\Etudiant\\Documents\\COURS_L3_STI_UCPP\\SEMESTRE_2\\PROJET\\Scontri\\media\\laCarte.png"));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(10, 264, 329, 163);
        panelDetails.add(lblNewLabel_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(10, 552, 989, 50);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JButton btnCreateEvent = new JButton("Créer un évenement");
        btnCreateEvent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCreateEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Créer la boîte de dialogue modale
                JDialog dialog = new JDialog(MyEvent.this, "Créer un évenement", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(400, 650);
                dialog.setLocationRelativeTo(MyEvent.this);

                CreateUpdateEvent createUpdateEvent = new CreateUpdateEvent(idLoggedUser);
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
        btnUpdateEvent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpdateEvent.addActionListener(new ActionListener() {
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

                // Récupérer l'événement depuis la base de données
                HashMap<String, Object> selectedEvent = eventController.getEventById(selectedEventId);

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
                CreateUpdateEvent createUpdateEvent = new CreateUpdateEvent(selectedEvent, idLoggedUser);
                dialog.setContentPane(createUpdateEvent.getContentPane());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Recharger les données de la table après la fermeture du dialogue
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
        btnDeleteEvent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
                    boolean deleted = eventController.deleteEvent(eventId);

                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Événement supprimé avec succès.", "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        loadEventData(); // Rafraîchir la liste après suppression
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la suppression.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
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
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        tableModel.setRowCount(0); // effacer les donnee d'abord

        events = org_EventController.getEventCreatedByOrg(idLoggedUser);

        for (HashMap<String, Object> event : events) {
            LocalDateTime dateDebut = (LocalDateTime) event.get("dateDebut");
            LocalDateTime dateFin = (LocalDateTime) event.get("dateFin");
            Object[] rowData = {
                    Integer.parseInt(event.get("id").toString()),
                    event.get("nom"),
                    dateDebut.format(outputFormatter),
                    dateFin.format(outputFormatter)
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Affiche les détails d'un événement dans les labels.
     */
    private void showDetails(int selectedEventId) {
        List<Map<String, Object>> partsEvents = part_EventController.getParticipantsInfoForEvent(selectedEventId);
        int participation = 0;

        for (Map<String, Object> part : partsEvents) {
            if (part.get("status").toString().equals(PartEventStatus.VALIDEE.toString())) {
                participation++;
            }
        }

        for (HashMap<String, Object> event : events) {
            int idEvent = Integer.parseInt(event.get("id").toString());
            String nom = event.get("nom").toString();
            String description = event.get("description").toString();
            int capacite = Integer.parseInt(event.get("capacite").toString());
            double prix = Double.parseDouble(event.get("prix").toString());
            LocalDateTime dateDebut = (LocalDateTime) event.get("dateDebut");
            LocalDateTime dateFin = (LocalDateTime) event.get("dateFin");

            if (idEvent == selectedEventId) {
                lblNom.setText(nom);
                tADescription.setText(description);
                lblCapacite.setText("Nombre de place disponible: " + (capacite - participation) + "/"
                        + capacite);
                lblPrix.setText("Prix: " + prix + " €");
                lblDateDebut.setText(dateDebut.format(outputFormatter));
                lblDateFin.setText(dateFin.format(outputFormatter));
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
