package views.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import models.Event;
import models.User;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ManagePartEvent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private List<User> participantsList; 
    private JTable table;
    private DefaultTableModel tableModel;



	/**
	 * Create the frame.
	 */
	public ManagePartEvent(List<User> usersList) {
		
		this.participantsList = usersList;
		
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
		
        String[] columnNames = {"Nom", "Prénom", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
			 *v
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
        contentPane.add(panel);
	}
	
    /**
     */
    private void loadEventData() {
    		   
	    for (User user : participantsList) {
	
	        Object[] rowData = {
        		user.getNom(),
        		user.getPrenom(),
	        };
	        tableModel.addRow(rowData);
	    }
    }
    
}
