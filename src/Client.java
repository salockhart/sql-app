import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Client.java
 * @author Matt Trask
 * B00639252
 * Nov 26, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */

/**
 * Client.java
 * @author Stanford Lockhart
 * B00646015
 * Nov 28, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */

/*
 * Implement the run query button
 */

public class Client extends JFrame implements ActionListener{
	
	//Instance variables
	private JPanel selectP, fromP, whereP;
	private ArrayList<JComboBox<String>> selectB;
	private ArrayList<JComboBox<String>> fromB;
	private ArrayList<JComboBox<String>> whereB;
	private ArrayList<JComboBox<String>> operationsB;
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
		
		//creates all arrayLists
		fromB= new ArrayList<JComboBox<String>>();
		selectB= new ArrayList<JComboBox<String>>();
		whereB= new ArrayList<JComboBox<String>>();
		operationsB = new ArrayList<JComboBox<String>>();
		inputs = new ArrayList<JTextField>();
				
		selectP = new JPanel(new FlowLayout());
		fromP = new JPanel(new FlowLayout());
		whereP = new JPanel(new FlowLayout());
		
		FROM= new JLabel("FROM");
		fromPlus= new JButton("+");
		fromPlus.addActionListener(this);
		fromMinus= new JButton("-");
		fromMinus.addActionListener(this);
		fromP.add(FROM);
		fromB.add(createBoxTables(true));
		fromP.add(fromB.get(0));
		fromP.add(fromPlus);
		fromP.add(fromMinus);
		
		SELECT= new JLabel("SELECT");
		selectPlus= new JButton("+");
		selectPlus.addActionListener(this);
		selectMinus= new JButton("-");
		selectMinus.addActionListener(this);
		selectP.add(SELECT);
		selectB.add(createBoxColumns(true));
 		selectP.add(selectB.get(0));
		selectP.add(selectPlus);
		selectP.add(selectMinus);
		
		WHERE= new JCheckBox("WHERE");
		WHERE.addActionListener(this);
		wherePlus= new JButton("+");
		wherePlus.setEnabled(false);
		wherePlus.addActionListener(this);
		whereMinus= new JButton("-");
		whereMinus.setEnabled(false);
		whereMinus.addActionListener(this);
		whereP.add(WHERE);
		whereB.add(createBoxColumns(false));
		whereP.add(whereB.get(0));
		operationsB.add(createOperations(false));
		whereP.add(operationsB.get(0));
		inputs.add(createField(false));
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
		setTitle(username);
		setSize(1200,800);
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
				
				for(int i=0; i<whereB.size(); i++) {
					whereB.get(i).setEnabled(true);
				}
				
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
				
				for(int i=0; i<whereB.size(); i++) {
					whereB.get(i).setEnabled(false);
				}
				
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
			updateBoxes(selectB);
			updateBoxes(whereB);
		}
		
		if(e.getSource()== wherePlus) {
			boolean on= WHERE.isSelected();
			
			whereP.remove(wherePlus);
			whereP.remove(whereMinus);
			operationsB.add(createOperations(on));
			whereP.add(operationsB.get(operationsB.size()-1));
			whereB.add(createBoxColumns(on));
			whereP.add(whereB.get(whereB.size()-1));
			operationsB.add(createOperations(on));
			whereP.add(operationsB.get(operationsB.size()-1));
			inputs.add(createField(on));
			whereP.add(inputs.get(inputs.size()-1));
			whereP.add(wherePlus);
			whereP.add(whereMinus);
		}
		
		if(e.getSource()== whereMinus) {
			if(operationsB.size()>1) {
				whereP.remove(operationsB.remove(operationsB.size()-1));
				whereP.remove(operationsB.remove(operationsB.size()-1));
			}
			
			if(inputs.size()>1) {
				whereP.remove(inputs.remove(inputs.size()-1));
			}
			
			if(whereB.size()>1) {
				whereP.remove(whereB.remove(whereB.size()-1));
			}
		}
		
		//when entries in FROM change change select Jcombobox values and where Jcombobox 
		for(int i=0; i< fromB.size(); i++) {
			if(e.getSource()==fromB.get(i)) {
				updateBoxes(selectB);
				updateBoxes(whereB);
			}
		}
		
		if(e.getSource()== run) {
			String fromstr,wherestr,selectstr;
			fromstr="FROM ";
			wherestr="";
			selectstr="SELECT ";
			
			//select element of query
			for(int i=0;i<selectB.size()-1;i++){
				if(i!=selectB.size()-1){
					selectstr+=selectB.get(i).getSelectedItem()+", ";
				}
				else{
					selectstr+=selectB.get(i).getSelectedItem()+ " ";
				}
			}
			
			//from element of query
			for(int i=0;i<fromB.size()-1;i++){
				if(i!=fromB.size()-1){
					fromstr+=fromB.get(i).getSelectedItem()+", ";
				}
				else{
					fromstr+=fromB.get(i).getSelectedItem() + " ";
				}
			}
			
			//where element of query
			if(WHERE.isSelected()){
				wherestr="WHERE ";
				wherestr+=whereB.get(0).getSelectedItem() + " ";
				wherestr+=operationsB.get(0).getSelectedItem() + " ";
				wherestr+=inputs.get(0).getSelectedText() + " ";
				int tableC=1;
				int relC=1;
				int opC=1;
				while((tableC<whereB.size()-1)&&(relC<inputs.size()-1)&&(opC<operationsB.size()-1)){
					wherestr+=operationsB.get(opC).getSelectedItem()+ " ";
					opC++;
					wherestr+=whereB.get(tableC).getSelectedItem() + " ";
					tableC++;
					wherestr+=operationsB.get(opC).getSelectedItem() + " ";
					opC++;
					wherestr+=inputs.get(relC).getSelectedText() + " ";
					relC++;
				}
			}
			
			String query=selectstr+fromstr+wherestr+ ";";
			
			JTable result=connection.getTable(connection.query(query));
			
			JFrame frame= new JFrame("Results");
			JLabel QueryL= new JLabel(query);
			frame.add(QueryL);
			frame.add(result);
			frame.setSize(400, 400);
			frame.setLocation(250, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
			
		}
		validate();
	}
	
	//creates comboBox for FROM
	public JComboBox<String> createBoxTables(boolean enable) {
		String[] tables= connection.getTables();
		JComboBox<String> temp= new JComboBox<String>(tables);
		temp.addActionListener(this);
		temp.setEnabled(enable);
		return temp;
	}
	
	//creates comboBox for SELECT WHERE
	public JComboBox<String> createBoxColumns(boolean enable) {
		String[] columns = calcTables();
		JComboBox<String> temp = new JComboBox<String>(columns);
		temp.setEnabled(enable);
		return temp;
	}
	
	//creates specific comboBox holding all boolean operations
	public JComboBox<String> createOperations(boolean enable) {
		String[] operands = {"==", "<=", "<", ">=", ">", "LIKE", "AND", "OR"};
		JComboBox<String> temp = new JComboBox<String>(operands);
		temp.setEnabled(enable);
		return temp;
	}
	
	//creates textFields for later use
	public JTextField createField(boolean enable) {
		JTextField temp= new JTextField();
		temp.setEnabled(enable);
		temp.setPreferredSize(new Dimension(100, 20));
		return temp;
	}
	
	//helper method to take values in FROM fields to create columns
	public String[] calcTables() {
		ArrayList<String> temp= new ArrayList<String>();
		for(int i=0; i<fromB.size(); i++) {
			temp.add((String)fromB.get(i).getSelectedItem());
		}
		//Remove duplicate tables
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(temp);
		temp.clear();
		temp.addAll(hs);
		String query = "SELECT * FROM ";
		for (int i = 0; i < temp.size(); i++)
			query += temp.get(i) + ", ";	//adds element to query
		query = query.substring(0, query.length() - 2) + ";";	//chops off last character ','
		return connection.getColumns(connection.query(query)).clone();
	}
	
	public void updateBoxes(ArrayList<JComboBox<String>> boxes) {
		for(int j=0; j<boxes.size(); j++) {
			boxes.get(j).removeAllItems();
			String[] columns= calcTables();
			for(int k=0; k<columns.length; k++) {
				boxes.get(j).addItem(columns[k]);
			}
		}
	}
}
