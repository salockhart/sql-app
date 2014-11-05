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
		System.out.println("test");
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

}