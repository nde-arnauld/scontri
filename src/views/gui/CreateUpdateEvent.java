package views.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Event;

public class CreateUpdateEvent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Event theEvent ; 

	/**
	 * Create the frame.
	 */
	public CreateUpdateEvent(Event event) { //pour la modification
		this.theEvent = event;
		initialize();
	}
	public CreateUpdateEvent() { //pour la création 
		this(null); 
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		System.out.println(theEvent);
	}

}
