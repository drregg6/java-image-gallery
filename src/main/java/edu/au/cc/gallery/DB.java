package edu.au.cc.gallery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
	// Constants
	private static final String dbUrl = "jdbc:postgresql://demo-database-1.cscij4pvmhz5.us-east-1.rds.amazonaws.com/image_gallery";
	private Connection connection;

	// Privately get Password - ensures it will not be public on Github
	private String getPassword() {
		try (BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/.sql-password"))) {
			String result = br.readLine();
			return result;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	// Connect to database - store in instance var
	public void connect() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbUrl, "image_gallery", getPassword());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	
	// CRUD METHODS
	// Get all users
	public String listUsers() throws SQLException {
		String res = "username     password     full_name";
		res += "\n------------------------------------------";

		// Get users from the database
		PreparedStatement ps = connection.prepareStatement("select * from users");
		ResultSet rs = ps.executeQuery();	

		// Format the output
		while (rs.next()) {
			res += "\n" + rs.getString(1);
			res += "   |   " + rs.getString(2);
			res += "   |   " + rs.getString(3);
		}
		rs.close();

		// Return to app
		return res;
	}

	// Get a single user from the database
	public String getUser(String username) throws SQLException {
		String res = "";
		// If database can find user - return user
		PreparedStatement ps = connection.prepareStatement("select * from users where username=?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			res += rs.getString(1) + ",";
			res += rs.getString(2) + ",";
			res += rs.getString(3);
		}
		rs.close();

		if (res.equals("")) {
			return null;
		}
		return res;
	}

	// Add a user to the database
	public void addUser(String username, String password, String fullName) throws SQLException {
		// Prepare statement
		PreparedStatement ps = connection.prepareStatement("insert into users (username, password, full_name) values (?, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, fullName);

		// Execute query
		ps.execute();
	}

	// Update a user
	public void updateUser(String username, String password, String fullName) throws SQLException {
		// Prepare the statement
		PreparedStatement ps = connection.prepareStatement("update users set password=?, full_name=? where username=?");
		ps.setString(1, password);
		ps.setString(2, fullName);
		ps.setString(3, username);

		// Execute the query
		ps.execute();
	}

	// Delete a user
	public void deleteUser(String username) throws SQLException {
		// If deletion is successful - return true
		PreparedStatement ps = connection.prepareStatement("delete from users where username=?");
		ps.setString(1, username);
		
		// Execute query
		ps.execute();
	}


	// Close the db connection
	public void close() throws SQLException {
		connection.close();
	}

	// Connect to the DB
	public static void demo() throws Exception {
		DB db = new DB();
		db.connect();
		
		// Test methods for CRUD
		System.out.println(db.listUsers());
		db.addUser("dave2", "password", "Dave Regg");
		System.out.println("\n" + db.listUsers());
		db.updateUser("dave2", "password1", "Dave Regg");
		System.out.println("\nGetUser dave2: " + db.getUser("dave2"));
		System.out.println("GetUser dave3: " + db.getUser("dave3"));
		db.deleteUser("dave2");
		System.out.println("\n" + db.listUsers());

		db.close();
	}
}
