import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
	private ArrayList<JComboBox> operationsB;
	private ArrayList<JTextField> inputs;
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
	private static String[] operands= {"==", "<=", "<", ">=", ">", "LIKE"};
	
	public static void main(String[] args) {
		String username, password;
		username = JOptionPane.showInputDialog("Username: ");
		password = JOptionPane.showInputDialog("Password: ");
		
		Client a = new Client(username, password);
	}
	
	/**
	 * Create the window, with labels and buttons
	 */
	public Client(String username, String password) {
		//initiate connection to database
		connection = new SQLConnection(username, password);
		
		
		selectP = new JPanel(new FlowLayout());
		fromP = new JPanel(new FlowLayout());
		whereP = new JPanel(new FlowLayout());
		
		SELECT= new JLabel("SELECT");
		selectB= new ArrayList<JComboBox>();
		selectPlus= new JButton("+");
		selectPlus.addActionListener(this);
		selectMinus= new JButton("-");
		selectMinus.addActionListener(this);
		selectP.add(SELECT);
		selectB.add(createBoxColumns(true));
 		selectP.add(selectB.get(0));
		selectP.add(selectPlus);
		selectP.add(selectMinus);
		
		FROM= new JLabel("FROM");
		fromB= new ArrayList<JComboBox>();
		fromPlus= new JButton("+");
		fromPlus.addActionListener(this);
		fromMinus= new JButton("-");
		fromMinus.addActionListener(this);
		fromP.add(FROM);
		selectB.add(createBoxTables(true));
		fromP.add(selectB.get(0));
		fromP.add(fromPlus);
		fromP.add(fromMinus);
		
		WHERE= new JCheckBox("WHERE");
		WHERE.addActionListener(this);
		wherePlus= new JButton("+");
		wherePlus.setEnabled(false);
		wherePlus.addActionListener(this);
		whereMinus= new JButton("-");
		whereMinus.setEnabled(false);
		whereMinus.addActionListener(this);
		whereP.add(WHERE);
		whereB= createBoxColumns(false);
		whereP.add(whereB);
		operationsB.add(createOperations(false));
		whereP.add(operationsB.get(0));
		inputs.add(new JTextField());
		whereP.add(inputs.get(0));
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
				
				for(int i=0; i<operationsB.size(); i++) {
					operationsB.get(i).setEnabled(true);
				}
				
				for(int i=0; i<inputs.size(); i++) {
					inputs.get(i).setEnabled(true);
				}
			}
			else {
				wherePlus.setEnabled(false);
				whereMinus.setEnabled(false); 
				
				for(int i=0; i<operationsB.size(); i++) {
					operationsB.get(i).setEnabled(false);
				}
				
				for(int i=0; i<inputs.size(); i++) {
					inputs.get(i).setEnabled(false);
				}
			}
		}
		
		if(e.getSource()== selectPlus) {
			selectP.remove(selectPlus);
			selectP.remove(selectMinus);
			selectB.add(createBoxColumns(true));
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
			fromB.add(createBoxTables(true));
			fromP.add(fromB.get(fromB.size()-1));
			fromP.add(fromPlus);
			fromP.add(fromMinus);
		}
		
		if(e.getSource()== fromMinus) {
			if(fromB.size()>1) {
				fromP.remove(fromB.remove(fromB.size()-1));
			}
		}
		
		if(e.getSource()== wherePlus) {
			boolean on= WHERE.isSelected();
			
			whereP.remove(wherePlus);
			whereP.remove(whereMinus);
			operationsB.add(createOperations(on));
			whereP.add(operationsB.get(operationsB.size()-1));
			inputs.add(createField(on));
			whereP.add(inputs.get(inputs.size()-1));
			whereP.add(wherePlus);
			whereP.add(whereMinus);
		}
		
		if(e.getSource()== whereMinus) {
			//only check operations because operations and inputs grow/shrink together
			if(operationsB.size()>1) {
				fromP.remove(operationsB.remove(operationsB.size()-1));
				fromP.remove(inputs.remove(inputs.size()-1));
			}
		}
		
	}
	
	public JComboBox createBoxTables(boolean enable) {
		String[] tables= connection.getTables();
		JComboBox temp= new JComboBox(tables);
		temp.setEnabled(enable);
		return temp;
	}
	
	public JComboBox createBoxColumns(boolean enable) {
//		String[] columns= 
		JComboBox temp= new JComboBox(columns);
		temp.setEnabled(enable);
		return temp;
	}
	
	public JComboBox createOperations(boolean enable) {
		JComboBox temp= new JComboBox(operands);
		temp.setEnabled(enable);
		return temp;
	}
	
	public JTextField createField(boolean enable) {
		JTextField temp= new JTextField();
		temp.setEnabled(enable);
		return temp;
	}
	
	public String[] calcTables() {
		ArrayList<String> temp= new ArrayList<String>();
		for(int i=0; i<fromB.size(); i++) {
			temp.add(fromB.get(i).getSelectedItem())
		}
	}
}
