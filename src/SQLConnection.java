import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdesktop.swingx.JXTable;

/**
 * SQLConnection.java
 * @author Stanford Lockhart
 * B00646015
 * Nov 4, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */
public class SQLConnection {

	//Instance variables
	Connection conn = null;
	
	/**
	 * Create the database connection
	 * @param username
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public SQLConnection(String username, String password) {
		//Create the connection
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = getConnection(username, password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e + "SQL Driver not found - please install from"
					+ "http://dev.mysql.com/downloads/connector/j/ and try again",
					"ClassNotFound Exception", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Query the database with our connection
	 * @param query
	 * @throws SQLException
	 */
	public ResultSet query(String query) {
		//Query the DB
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
		} finally {
//			try {
//				if (stmt != null) {
//					stmt.close();
//				}
//			} catch (SQLException e) {
//				JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
//			}
		}
		return rs;
	}
	
	/**
	 * Get the names of all the tables
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public String[] getTables() {
		try {
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			
			//Get the number of tables
			int rowCount = sizeOfResultSet(rs);
			
			//Create the array
			String[] tables = new String[rowCount];
			
			//Add to the array
			int i = 0;
			while (rs.next())
			  tables[i++] = rs.getString(3);
			
			return tables;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Get the names of all the columns in a given table
	 * @param table
	 * @return
	 * @throws SQLException 
	 */
	public String[] getColumns(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			//Create the array
			String[] columns = new String[columnCount+1];

			//Add "All" option
			columns[0] = "*";
			
			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++) {
				columns[i] = rsmd.getColumnName(i);
			}

			return columns;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Get the names of all the columns in a given table minus * for all
	 * @param table
	 * @return
	 * @throws SQLException 
	 */
	public String[] getColumnsMinusAll(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			//Create the array
			String[] columns = new String[columnCount];
			
			// The column count starts from 1
			for (int i = 1; i < columnCount + 1; i++) {
				columns[i-1] = rsmd.getColumnName(i);
			}

			return columns;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Return a JTable representation of a ResultSet
	 * @param rs
	 * @return
	 */
	public JXTable getTable(ResultSet rs) {
		JXTable table = null;
		try {
			table = new JXTable(buildTableModel(rs));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "SQL Exception", JOptionPane.ERROR_MESSAGE);
		}
		return table;
	}
	
	/**
	 * Get a connection using our username and password
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private Connection getConnection(String username, String password) throws SQLException, ClassNotFoundException {
		
		Connection conn = null;
		Properties connProp = new Properties();
		connProp.put("user", username);
		connProp.put("password", password);
		
		String url = "localhost:3306/sakila";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + url, connProp);
		System.out.println("Connected.");
		return conn;
	}
	
	/**
	 * Gets the size of an input ResultSet
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private int sizeOfResultSet(ResultSet rs) throws SQLException {
		int rowCount = 0;
		if (rs.last()) {
			  rowCount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because using rs.next() later will move over the first element
		}
		return rowCount;
	}
	
	/**
	 * Builds and returns a table made from a result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//Get column names
		Vector<Object> columns = new Vector<Object>();
		String[] columnNames = getColumnsMinusAll(rs);
		for (String s : columnNames)
			columns.add(s);
		data.add(columns);
		
		//Get table data
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
		}
		
		return new DefaultTableModel(data, columns);
	}
}