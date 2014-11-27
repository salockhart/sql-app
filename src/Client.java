import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Client.java
 * @author Matt Trask
 * B00
 * Nov 26, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */

public class Client extends JFrame implements ActionListener{
	
	//Instance variables
	private JPanel selectP, fromP, whereP;
	private ArrayList<JComboBox> select;
	private ArrayList<JComboBox> from;
	private JButton run;
	private JLabel SELECT;
	private JLabel FROM;
	private JCheckBox WHERE; 
	private JButton selectPlus;
	private JButton selectMinus;
	private JButton fromPlus;
	private JButton fromMinus;
	private JButton wherePlus;
	private JButton whereMinus;
	
	public static void main(String[] args) {
		Client a = new Client();
	}
	
	/**
	 * Create the window, with labels and buttons
	 */
	public Client() {
		selectP = new JPanel(new FlowLayout());
		fromP = new JPanel(new FlowLayout());
		whereP = new JPanel(new FlowLayout());
		
		SELECT= new JLabel("SELECT");
		select= new ArrayList<JComboBox>();
		selectPlus= new JButton("+");
		selectMinus= new JButton("-");
		selectP.add(SELECT);
//		selectP.add(/*Combo Boxes*/);
		selectP.add(selectPlus);
		selectP.add(selectMinus);
		
		FROM= new JLabel("FROM");
		from= new ArrayList<JComboBox>();
		fromPlus= new JButton("+");
		fromMinus= new JButton("-");
		fromP.add(FROM);
//		fromP.add(/*Combo Boxes*/);
		fromP.add(fromPlus);
		fromP.add(fromMinus);
		
		WHERE= new JCheckBox("WHERE");
		wherePlus= new JButton("+");
		whereMinus= new JButton("-");
		whereP.add(WHERE);
//		whereP.add(/*Text Boxes*/);
		whereP.add(wherePlus);
		whereP.add(whereMinus);
		
		run= new JButton("Run Query");
		
		//add elements into frame
		setLayout(new GridLayout(4, 1));
		add(selectP);
		add(fromP);
		add(whereP);
		add(run);
		
		//Set window properties
		setTitle("SQL");
		setSize(400,400);
		setLocation(200,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Retrieve actions that occur in the window and make actions happen
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== WHERE) {
			//code to make rest of where query visible;
		}
		
		//code for all the buttons + or -
		
	}
}
