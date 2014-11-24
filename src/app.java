import java.sql.*;
import java.util.Properties;


/**
 * app.java
 * @author Stanford Lockhart
 * B00646015
 * Nov 4, 2014
 * Dalhousie University
 * Faculty of Computer Science
 */
public class app {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		//Create the connection
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
//		System.out.println("test");
		conn = getConnection("root", "");
		
		//Query the DB
		Statement stmt = null;
		String query = "SELECT * FROM actor";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
	            int actorID = rs.getInt("actor_id");
	            String firstName = rs.getString("first_name");
	            String lastName = rs.getString("last_name");
	            System.out.println(actorID + "\t" + firstName + "\t" + lastName);
	        }
		} catch (SQLException e) {
			
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		
		conn.close();
	}
	
	public static Connection getConnection(String username, String password) throws SQLException, ClassNotFoundException {
		
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
	
	public static String[] getTables(Connection conn) throws SQLException {
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
	
	private static int sizeOfResultSet(ResultSet rs) throws SQLException {
		int rowCount = 0;
		if (rs.last()) {
			  rowCount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because using rs.next() later will move over the first element
		}
		return rowCount;
	}
		
//	/**
//	 * Code Snippet to get the names of all the tables in the requested database
//	 */
//	DatabaseMetaData md = conn.getMetaData();
//	ResultSet rs = md.getTables(null, null, "%", null);
//	while (rs.next()) {
//	  System.out.println(rs.getString(3));
//	}
	
//	/**
//	 * Code Snippet to get the names of all the columns in a result from a query
//	 */
//	ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM TABLE2");
//	ResultSetMetaData rsmd = rs.getMetaData();
//	int columnCount = rsmd.getColumnCount();
//
//	// The column count starts from 1
//	for (int i = 1; i < columnCount + 1; i++ ) {
//	  String name = rsmd.getColumnName(i);
//	  // Do stuff with name
//	}

}