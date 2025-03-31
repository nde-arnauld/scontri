package views.gui;

import controllers.CategorieController;
import controllers.EventController;
import controllers.LieuController;
import controllers.Org_EventController;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

public class CreateUpdateEvent extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNom, txtCapacite, txtPrix;
    private JTextArea txtDescription;
    private JSpinner dateDebutSpinner, dateFinSpinner;
    private JButton btnValider, btnAnnuler;
    private List<HashMap<String, String>> listOfCategories;
    private List<HashMap<String, String>> listOfLieux;
    private HashMap<String, Object> theEvent;
    private LieuController lieuController;
    private CategorieController categorieController;
    private EventController eventController;
    private Org_EventController org_EventController;
    private int idLoggedUser;

    /**
     * Constructeur pour la modification
     */
    public CreateUpdateEvent(HashMap<String, Object> event, int idUser) {
        this.theEvent = event;
        this.idLoggedUser = idUser;
        this.categorieController = new CategorieController();
        this.eventController = new EventController();
        this.org_EventController = new Org_EventController();
        this.lieuController = new LieuController();
        initialize();
    }

    /**
     * Constructeur pour la création
     */
    public CreateUpdateEvent(int idUser) {
        this(null, idUser);
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
        btnValider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
                    java.time.LocalDateTime dateDebut = dateDebutUtil.toInstant()
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                    java.util.Date dateFinUtil = (java.util.Date) dateFinSpinner.getValue();
                    java.time.LocalDateTime dateFin = dateFinUtil.toInstant().atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime();

                    int idLieu = 0;
                    int idCat = 0;

                    for (HashMap<String, String> lieu : listOfLieux) {
                        if (lieu.get("nom").equals(comboLieu.getSelectedItem())) {
                            idLieu = Integer.parseInt(lieu.get("id").toString());
                            ;
                        }
                    }

                    for (HashMap<String, String> category : listOfCategories) {
                        if (category.get("nom").equals(comboCategorie.getSelectedItem())) {
                            idCat = Integer.parseInt(category.get("id").toString());
                        }
                    }

                    if (theEvent == null) {
                        // Création d'un nouvel événement
                        int eventId = eventController.createEvent(nom, description, capacite, prix, dateDebut, dateFin,
                                "actif", idLieu, idCat);
                        boolean isCreated = org_EventController.createOrgEvent(idLoggedUser, eventId);
                        if (isCreated) {
                            JOptionPane.showMessageDialog(null, "Événement créer avec succès.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Échec de la création de l'événement.", "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        int idEvent = Integer.parseInt(theEvent.get("id").toString());
                        // Modification de l'événement existant
                        boolean isUpdated = eventController.updateEvent(idEvent, nom, description,
                                capacite, prix, dateDebut, dateFin, "actif", idLieu, idCat);
                        if (isUpdated) {
                            JOptionPane.showMessageDialog(null, "Événement mis à jour avec succès.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Échec de la mise à jour de l'événement.", "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateUpdateEvent.this, "Erreur: " + ex.getMessage(), "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(btnValider);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            String nom = theEvent.get("nom").toString();
            String description = theEvent.get("description").toString();
            String capacite = theEvent.get("capacite").toString();
            String prix = theEvent.get("prix").toString();

            // Correction des dates
            LocalDateTime dateDebut = LocalDateTime.parse(theEvent.get("dateDebut").toString());
            LocalDateTime dateFin = LocalDateTime.parse(theEvent.get("dateFin").toString());

            int idLieu = Integer.parseInt(theEvent.get("idLieu").toString());
            int idCat = Integer.parseInt(theEvent.get("idCat").toString());

            txtNom.setText(nom);
            txtDescription.setText(description);
            txtCapacite.setText(capacite);
            txtPrix.setText(prix);
            dateDebutSpinner.setValue(java.sql.Timestamp.valueOf(dateDebut));
            dateFinSpinner.setValue(java.sql.Timestamp.valueOf(dateFin));
            comboLieu.setSelectedItem(idLieu);
            comboCategorie.setSelectedItem(idCat);
        }
    }

    private void LoadLieuComboBox(JComboBox<String> comboLieu) {
        // Code pour charger les catégories dans la combo box
        listOfLieux = lieuController.listLieux();

        for (HashMap<String, String> lieu : listOfLieux) {
            comboLieu.addItem(lieu.get("nom"));
        }
    }

    private void LoadCategorieComboBox(JComboBox<String> comboCategorie) {
        // Code pour charger les catégories dans la combo box
        listOfCategories = categorieController.listCategories();

        for (HashMap<String, String> categorie : listOfCategories) {
            comboCategorie.addItem(categorie.get("nom"));
        }
    }
}
