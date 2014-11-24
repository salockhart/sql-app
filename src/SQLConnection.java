import java.sql.*;
import java.util.Properties;


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
	public SQLConnection(String username, String password) throws ClassNotFoundException, SQLException {
		//Create the connection
		Class.forName("com.mysql.jdbc.Driver");
		conn = getConnection(username, password);
	}
	
	/**
	 * Query the database with our connection
	 * @param query
	 * @throws SQLException
	 */
	public void query(String query) throws SQLException {
		//Query the DB
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
//			while (rs.next()) {
//	            int actorID = rs.getInt("actor_id");
//	            String firstName = rs.getString("first_name");
//	            String lastName = rs.getString("last_name");
//	            System.out.println(actorID + "\t" + firstName + "\t" + lastName);
//	        }
		} catch (SQLException e) {
			
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}
	
	/**
	 * Get the names of all the tables
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public String[] getTables() throws SQLException {
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
	}
	
	/**
	 * Get the names of all the columns in a given table
	 * @param table
	 * @return
	 * @throws SQLException 
	 */
	public String[] getColumns(String table) throws SQLException {
		ResultSetMetaData rsmd = conn.createStatement().executeQuery("SELECT * FROM " + table).getMetaData();
		int columnCount = rsmd.getColumnCount();
	
		//Create the array
		String[] columns = new String[columnCount];
		
		// The column count starts from 1
		for (int i = 1; i < columnCount + 1; i++ ) {
		  columns[i-1] = rsmd.getColumnName(i);
		}
		
		return columns;
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
}