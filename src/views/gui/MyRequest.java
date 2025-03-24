package views.gui;

import controllers.Part_EventController;
import dao.Database;
import dao.Part_EventDAO;
import models.Part_Event;
import utils.enums.PartEventStatus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MyRequest extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblNom, lblCapacite, lblPrix, lblDateDebut, lblDateFin;
    private JTextArea tADescription;
    private List<Map<String, Object>> events; // Liste des événements récupérés de la BDD
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Connection connection;
    private Part_EventDAO part_EventDAO;
    private Part_EventController part_EventController;
    private int idLoggedUser;

    /**
     * Create the frame.
     */
    public MyRequest(int idLoggedUser) {
        this.connection = Database.getConnection();
        this.part_EventDAO = new Part_EventDAO(connection);
        this.part_EventController = new Part_EventController(null, part_EventDAO);
        this.idLoggedUser = idLoggedUser;
        initialize();
    }

    public void initialize() {

        setTitle("Mes Demandes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(UIConst.X, UIConst.Y, UIConst.WIDTH, UIConst.HEIGHT);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Liste des événements auxquels je participe"));
        panel.setBounds(10, 11, 631, 530);
        panel.setLayout(new BorderLayout());
        // Création du tableau avec un modèle de données modifiable
        String[] columnNames = { " ", "ID", "Nom", "Date Début", "Date Fin", "status de l'adhesion" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Rendre seulement la colonne de sélection éditable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // La première colonne contient des cases à cocher
                }
                return super.getColumnClass(columnIndex);
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
                    int eventId = (int) table.getValueAt(selectedRow, 1);
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
                "D:\\UNIVERSITE_DE_CORSE\\SECOND SEMESTER\\PROJET-IHM\\SONCTRI\\scontri\\media\\laCarte.png"));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(10, 264, 329, 163);
        panelDetails.add(lblNewLabel_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(10, 552, 989, 50);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JButton btnAnnulerParticipation = new JButton("Annuler ma participation");
        btnAnnulerParticipation.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAnnulerParticipation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // Récupérer la ligne sélectionnée

                if (selectedRow != -1) {
                    // Vérifier si la case à cocher est sélectionnée
                    Boolean isSelected = (Boolean) table.getValueAt(selectedRow, 0);
                    if (!isSelected) {
                        JOptionPane.showMessageDialog(null,
                                "Veuillez cocher la case de sélection pour annuler votre participation.",
                                "Case non cochée", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Veuillez cocher la case de sélection pour annuler votre participation.",
                            "Case non cochée", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Récupérer l'ID de l'événement
                int eventId = (int) table.getValueAt(selectedRow, 1);

                // Demander confirmation
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Voulez-vous vraiment annuler votre participation à cet événement ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Annuler la participation de l'utilisateur à l'événement
                    boolean cancelled = part_EventController.cancelParticipation(idLoggedUser, eventId);

                    if (cancelled) {
                        JOptionPane.showMessageDialog(null, "Participation annulée avec succès.", "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        loadEventData(); // Rafraîchir la liste après annulation
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'annulation de la participation.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnAnnulerParticipation.setForeground(Color.WHITE);
        btnAnnulerParticipation.setBorderPainted(false);
        btnAnnulerParticipation.setBackground(new Color(232, 0, 5));
        btnAnnulerParticipation.setBounds(61, 11, 200, 28);
        panel_2.add(btnAnnulerParticipation);

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
        tableModel.setRowCount(0); // Effacer les données d'abord

        events = part_EventController.getEventsInfoForUser(idLoggedUser);

        for (Map<String, Object> eventInfo : events) {
            Object dateDebut = eventInfo.get("date_debut");
            Object dateFin = eventInfo.get("date_fin");

            String formattedDateDebut = dateDebut != null ? outputFormatter.format((java.time.LocalDateTime) dateDebut)
                    : "";
            String formattedDateFin = dateFin != null ? outputFormatter.format((java.time.LocalDateTime) dateFin) : "";

            Object[] rowData = {
                    false,
                    eventInfo.get("id_event"),
                    eventInfo.get("nom"),
                    formattedDateDebut,
                    formattedDateFin,
                    eventInfo.get("part_status")
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

        for (Map<String, Object> eventInfo : events) {
            if ((int) eventInfo.get("id_event") == selectedEventId) {
                lblNom.setText((String) eventInfo.get("nom"));
                tADescription.setText((String) eventInfo.get("description"));
                lblCapacite.setText("Nombre de place disponible: " + ((int) eventInfo.get("capacite") - participation) +"/"+ (int) eventInfo.get("capacite"));
                lblPrix.setText("Prix: " + eventInfo.get("prix") + " €");
                lblDateDebut.setText(outputFormatter.format((java.time.LocalDateTime) eventInfo.get("date_debut")));
                lblDateFin.setText(outputFormatter.format((java.time.LocalDateTime) eventInfo.get("date_fin")));
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
