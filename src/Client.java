import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Client extends JFrame implements ActionListener{
	private JPanel panel;
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
	
	
	public Client() {
		panel= new JPanel();
		select= new ArrayList<JComboBox>();
		from= new ArrayList<JComboBox>();
		run= new JButton("Run query");
		SELECT= new JLabel("SELECT");
		FROM= new JLabel("FROM");
		WHERE= new JCheckBox("WHERE");
		selectPlus= new JButton("+");
		selectMinus= new JButton("-");
		fromPlus= new JButton("+");
		fromMinus= new JButton("-");
		wherePlus= new JButton("+");
		whereMinus= new JButton("-");
		
		//add elements into frame
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== WHERE) {
			//code to make rest of where query visible;
		}
		
		//code for all the buttons + or -
		
	}
}
