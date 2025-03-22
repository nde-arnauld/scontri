package views.gui;

import controllers.CategorieController;
import controllers.EventController;
import controllers.LieuController;
import controllers.Org_EventController;
import dao.CategorieDAO;
import dao.EventDAO;
import dao.LieuDAO;
import dao.Org_EventDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.Categorie;
import models.Event;
import models.Lieu;

public class CreateUpdateEvent extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNom, txtCapacite, txtPrix;
    private JTextArea txtDescription;
    private JSpinner dateDebutSpinner, dateFinSpinner;
    private JButton btnValider, btnAnnuler;
    private List<Categorie> listOfCategories;
    private List<Lieu> listOfLieux;
    private Event theEvent; // L'événement sélectionné (si modification)
    private LieuDAO lieuDAO;
    private CategorieDAO categorieDAO;
    private LieuController lieuController;
    private CategorieController categorieController;
    private EventDAO eventDAO;
    private EventController eventController;
    private Org_EventDAO org_EventDAO;
    private Org_EventController org_EventController;
    private int idLoggedUser;
    /**
     * Constructeur pour la modification
     */
    public CreateUpdateEvent(Event event, Connection connection, int idUser) {
        this.theEvent = event;
        this.idLoggedUser = idUser;
        this.lieuDAO = new LieuDAO(connection) ;
        this.categorieDAO = new CategorieDAO(connection);
        this.lieuController = new LieuController(lieuDAO);
        this.categorieController = new CategorieController(categorieDAO);
        this.eventDAO = new EventDAO(connection);
        this.eventController = new EventController(eventDAO);
        this.org_EventDAO = new Org_EventDAO(connection);
        this.org_EventController = new Org_EventController(org_EventDAO);
        initialize();
    }

    /**
     * Constructeur pour la création
     * @wbp.parser.constructor
     */
    public CreateUpdateEvent(Connection connection,int idUser) {
        this(null,connection,idUser);
    }

    /**
     * Initialisation de l'interface
     */
    public void initialize() {
        setTitle(theEvent == null ? "Créer un événement" : "Modifier un événement");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 650);
        setModal(true); // Fenêtre modale
        setLocationRelativeTo(null); // Centrer la fenêtre
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Nom de l'événement
        JLabel label = new JLabel("Nom:");
        label.setBounds(10, 28, 178, 33);
        contentPane.add(label);
        txtNom = new JTextField();
        txtNom.setBounds(198, 28, 178, 33);
        contentPane.add(txtNom);

        // Description
        JLabel label_1 = new JLabel("Description:");
        label_1.setBounds(10, 100, 178, 60);
        contentPane.add(label_1);
        JScrollPane scrollDesc = new JScrollPane();
        scrollDesc.setBounds(198, 89, 178, 86);
        contentPane.add(scrollDesc);
        txtDescription = new JTextArea(3, 20);
        scrollDesc.setViewportView(txtDescription);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        // Capacité
        JLabel label_2 = new JLabel("Capacité:");
        label_2.setBounds(10, 203, 178, 33);
        contentPane.add(label_2);
        txtCapacite = new JTextField();
        txtCapacite.setBounds(198, 203, 178, 33);
        contentPane.add(txtCapacite);

        // Prix
        JLabel label_3 = new JLabel("Prix:");
        label_3.setBounds(10, 264, 178, 33);
        contentPane.add(label_3);
        txtPrix = new JTextField();
        txtPrix.setBounds(198, 264, 178, 33);
        contentPane.add(txtPrix);

        // Date de début
        JLabel label_4 = new JLabel("Date de début:");
        label_4.setBounds(10, 325, 178, 33);
        contentPane.add(label_4);
        dateDebutSpinner = new JSpinner(new SpinnerDateModel());
        dateDebutSpinner.setBounds(198, 325, 178, 33);
        contentPane.add(dateDebutSpinner);

        // Date de fin
        JLabel label_5 = new JLabel("Date de fin:");
        label_5.setBounds(10, 386, 178, 33);
        contentPane.add(label_5);
        dateFinSpinner = new JSpinner(new SpinnerDateModel());
        dateFinSpinner.setBounds(198, 386, 178, 33);
        contentPane.add(dateFinSpinner);

        // Lieu
        JLabel label_6 = new JLabel("Lieu:");
        label_6.setBounds(10, 447, 178, 33);
        contentPane.add(label_6);
        JComboBox<String> comboLieu = new JComboBox<>();
        comboLieu.setBounds(198, 447, 178, 33);
        LoadLieuComboBox(comboLieu); // Remplir la combo box avec les lieux
        contentPane.add(comboLieu);

        // Catégorie
        JLabel label_7 = new JLabel("Catégorie:");
        label_7.setBounds(10, 508, 178, 33);
        contentPane.add(label_7);
        JComboBox<String> comboCategorie = new JComboBox<>();
        comboCategorie.setBounds(198, 508, 178, 33);
        LoadCategorieComboBox(comboCategorie); // Remplir la combo box avec les catégories
        contentPane.add(comboCategorie);

        // Boutons
        btnValider = new JButton("Valider");
        btnValider.setBorderPainted(false);
        btnValider.setBackground(new Color(0, 0, 160));
        btnValider.setForeground(new Color(255, 255, 255));
        btnValider.setBounds(10, 570, 178, 33);
        btnValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
                String nom = txtNom.getText();
                String description = txtDescription.getText();
                int capacite = Integer.parseInt(txtCapacite.getText());
                double prix = Double.parseDouble(txtPrix.getText());
                java.util.Date dateDebutUtil = (java.util.Date) dateDebutSpinner.getValue();
                java.time.LocalDateTime dateDebut = dateDebutUtil.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                java.util.Date dateFinUtil = (java.util.Date) dateFinSpinner.getValue();
                java.time.LocalDateTime dateFin = dateFinUtil.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                int lieu = Integer.parseInt(((String) comboLieu.getSelectedItem()).split("-")[0]);
                int categorie = Integer.parseInt(((String) comboCategorie.getSelectedItem()).split("-")[0]);

                if (theEvent == null) {
                // Création d'un nouvel événement               
                int eventId = eventController.createEvent(nom, description, capacite, prix, dateDebut, dateFin, "actif", lieu, categorie);
                boolean isCreated = org_EventController.createOrgEvent( idLoggedUser, eventId);
                if (isCreated) {
                    JOptionPane.showMessageDialog(null, "Événement créer avec succès.");
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de la création de l'événement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                
                } else {
                // Modification de l'événement existant
                boolean isUpdated = eventController.updateEvent(theEvent.getIdEvent(), nom, description, capacite, prix, dateDebut, dateFin, "actif", lieu, categorie);
                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Événement mis à jour avec succès.");
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de la mise à jour de l'événement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                }
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CreateUpdateEvent.this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            }
        });
        contentPane.add(btnValider);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		txtNom.setText("");
                txtDescription.setText("");
                txtCapacite.setText("");
                txtPrix.setText("");
        	}
        });
        btnAnnuler.setBorderPainted(false);
        btnAnnuler.setForeground(Color.black);
        btnAnnuler.setBackground(new Color(255, 255, 255));
        btnAnnuler.setBounds(198, 569, 178, 33);
        contentPane.add(btnAnnuler);

        // Remplir les champs si modification
        if (theEvent != null) {
            txtNom.setText(theEvent.getNom());
            txtDescription.setText(theEvent.getDescription());
            txtCapacite.setText(String.valueOf(theEvent.getCapacite()));
            txtPrix.setText(String.valueOf(theEvent.getPrix()));
            dateDebutSpinner.setValue(java.sql.Timestamp.valueOf(theEvent.getDateDebut()));
            dateFinSpinner.setValue(java.sql.Timestamp.valueOf(theEvent.getDateFin()));
            comboLieu.setSelectedItem(theEvent.getIdLieu());
            comboCategorie.setSelectedItem(theEvent.getIdCat());
        }
    }

    private void LoadLieuComboBox(JComboBox<String> comboLieu) {
    	// Code pour charger les catégories dans la combo box
    	listOfLieux = lieuController.listLieux();
    	
        for (Lieu lieu : listOfLieux) {
            comboLieu.addItem(lieu.getIdLieu() + "-"+ lieu.getNom());
        }
    }

    private void LoadCategorieComboBox(JComboBox<String> comboCategorie) {
        // Code pour charger les catégories dans la combo box
    	listOfCategories = categorieController.listCategories();
    	
        for (Categorie categorie : listOfCategories) {
            comboCategorie.addItem(categorie.getIdCat() + "-"+categorie.getNom());
        }
    }

}
