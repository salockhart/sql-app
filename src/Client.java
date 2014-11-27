import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client.java
 * @author Matt Trask
 * B00639252
 * Nov 26, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */

public class Client extends JFrame implements ActionListener{
	
	//Instance variables
	private JPanel selectP, fromP, whereP;
	private ArrayList<JComboBox> selectB;
	private ArrayList<JComboBox> fromB;
	private JComboBox whereB;
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
	private SQLConnection connection;
	
	public static void main(String[] args) {
		Scanner keyboard= new Scanner(System.in);
		String username, password;
		System.out.print("Username: ");
		username= keyboard.next();
		System.out.print("Password: ");
		password= keyboard.next();
		
		Client a = new Client(username, password);
	}
	
	/**
	 * Create the window, with labels and buttons
	 */
	public Client(String username, String password) {
		//initiate connection to database
		connection= new SQLConnection(username, password);
		
		
		selectP = new JPanel(new FlowLayout());
		fromP = new JPanel(new FlowLayout());
		whereP = new JPanel(new FlowLayout());
		
		SELECT= new JLabel("SELECT");
		selectB= new ArrayList<JComboBox>();
		selectPlus= new JButton("+");
		selectMinus= new JButton("-");
		selectP.add(SELECT);
		selectB.add(createBox(true));
 		selectP.add(selectB.get(0));
		selectP.add(selectPlus);
		selectP.add(selectMinus);
		
		FROM= new JLabel("FROM");
		fromB= new ArrayList<JComboBox>();
		fromPlus= new JButton("+");
		fromMinus= new JButton("-");
		fromP.add(FROM);
		selectB.add(createBox(true));
		fromP.add(selectB.get(0));
		fromP.add(fromPlus);
		fromP.add(fromMinus);
		
		WHERE= new JCheckBox("WHERE");
		wherePlus= new JButton("+");
		wherePlus.setEnabled(false);
		whereMinus= new JButton("-");
		whereMinus.setEnabled(false);
		whereP.add(WHERE);
		whereB= createBox(false);
		whereP.add(whereB);
//		whereP.add(/* elements */);
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
			if(WHERE.isSelected()) {
				wherePlus.setEnabled(true);
				whereMinus.setEnabled(true);
				
				//loop for enabling list of other "boxes"
			}
			else {
				wherePlus.setEnabled(false);
				whereMinus.setEnabled(false); 
			}
		}
		
		if(e.getSource()== selectPlus) {
			selectP.remove(selectPlus);
			selectP.remove(selectMinus);
			selectB.add(createBox(true));
			selectP.add(selectB.get(selectB.size()-1));
			selectP.add(selectPlus);
			selectP.add(selectMinus);
		}
		
		if(e.getSource()== selectMinus) {
			if(selectB.size()>1) {
				selectP.remove(selectB.remove(selectB.size()-1));
			}
		}
		
		if(e.getSource()== fromPlus) {
			fromP.remove(fromPlus);
			fromP.remove(fromMinus);
			fromB.add(createBox(true));
			fromP.add(fromB.get(fromB.size()-1));
			fromP.add(fromPlus);
			fromP.add(fromMinus);
		}
		
		if(e.getSource()== fromMinus) {
			if(fromB.size()>1) {
				fromP.remove(fromB.remove(fromB.size()-1));
			}
		}
		
	}
	
	public JComboBox createBox(boolean enable) {
		String[] tables= connection.getTables();
		JComboBox temp= new JComboBox(tables);
		temp.setEnabled(enable);
		return temp;
	}
}
