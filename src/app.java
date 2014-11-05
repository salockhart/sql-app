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
		conn = getConnection("", "");
		
		//Query the DB
		Statement stmt = null;
		String query = "SELECT * FROM countries";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
	            String countryID = rs.getString("COUNTRY_ID");
	            String countryName = rs.getString("COUNTRY_NAME");
	            int regionID = rs.getInt("REGION_ID");
	            System.out.println(countryID + "\t" + countryName + "\t" + regionID);
	        }
		} catch (SQLException e) {
			
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}
	
	public static Connection getConnection(String username, String password) throws SQLException, ClassNotFoundException {
		
		Connection conn = null;
		Properties connProp = new Properties();
		connProp.put("user", username);
		connProp.put("password", password);
		
		String url = "";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + url, connProp);
		System.out.println("Connected.");
		return conn;
	}

}