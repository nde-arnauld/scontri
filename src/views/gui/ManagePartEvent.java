package views.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.Part_EventController;
import dao.Part_EventDAO;
import utils.enums.PartEventStatus;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.awt.Color;


public class ManagePartEvent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<Map<String, Object>> participantsList; 
	private JTable table;
	private DefaultTableModel tableModel;
	private Part_EventDAO part_EventDAO;
	private Part_EventController part_EventController;
	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	private int idEvent;

	/**
	 * Create the frame.
	 */
	public ManagePartEvent(List<Map<String, Object>> usersList, int idEvent ,Connection connection) {
		this.participantsList = usersList;
		this.idEvent = idEvent;
		this.part_EventDAO = new Part_EventDAO(connection);
		this.part_EventController = new Part_EventController(null, part_EventDAO);
		initialize();
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 570, 400);
		panel.setLayout(new BorderLayout());
		
		String[] columnNames = {"Sélectionner", "Nom", "Prénom", "Date de la demande", "Statut"};
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
		loadParticipantsData();
		contentPane.add(panel);

		// Ajouter les boutons de validation et de rejet
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 420, 570, 40);
		contentPane.add(buttonPanel);

		JButton btnValidate = new JButton("Valider");
		btnValidate.setForeground(new Color(255, 255, 255));
		btnValidate.setBorderPainted(false);
		btnValidate.setBackground(new Color(0, 0, 160));
		btnValidate.setBounds(74, 11, 173, 23);
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleValidation(true);
			}
		});
		buttonPanel.setLayout(null);
		buttonPanel.add(btnValidate);

		JButton btnReject = new JButton("Rejeter");
		btnReject.setBackground(new Color(255, 255, 255));
		btnReject.setBorderPainted(false);
		btnReject.setBounds(321, 11, 173, 23);
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleValidation(false);
			}
		});
		buttonPanel.add(btnReject);
	}
	
	/**
	 * Charger les données des participants dans le tableau
	 */
	private void loadParticipantsData() {
		tableModel.setRowCount(0); // Clear existing data
		for (Map<String, Object> participant : participantsList) {
			Object datePart = participant.get("date_part");
			String formattedDate = datePart != null ? outputFormatter.format((java.time.LocalDateTime) datePart) : "";
			Object[] rowData = {
				false, // Case à cocher initialisée à false
				participant.get("nom"),
				participant.get("prenom"),
				formattedDate,
				participant.get("status")
			};
			tableModel.addRow(rowData);
		}
	}
		
	private void handleValidation(boolean isValid) {
		int rowCount = tableModel.getRowCount();
		boolean hasSelection = false;
		for (int i = 0; i < rowCount; i++) {
			Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
			if (isSelected != null && isSelected) {
				hasSelection = true;
				int idPart = (Integer) participantsList.get(i).get("id_user");
				if (isValid) {
					part_EventController.updateParticipation(idPart, idEvent, PartEventStatus.VALIDEE.toString());
				} else {
					part_EventController.updateParticipation(idPart, idEvent, PartEventStatus.REJETEE.toString());
				}
			}
		}
		if (hasSelection) {
			javax.swing.JOptionPane.showMessageDialog(this, "Opération réussie.", "Succès", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			loadParticipantsData(); // Recharger les données du tableau
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un participant.", "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}