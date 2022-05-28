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

	// Get all users
	public void getUsers() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select * from users");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) + ", " +
					rs.getString(2) + ", " +
					rs.getString(3));
		}
		rs.close();
	}

	// Query database
	public ResultSet queryDb(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}

	// Modify the database
	public void updateDb(String query, String[] values) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		for (int i = 0; i < values.length; i++) {
			stmt.setString(i + 1, values[i]);
		}
		stmt.execute();
	}

	// Close the db connection
	public void close() throws SQLException {
		connection.close();
	}

	// Connect to the DB
	public static void demo() throws Exception {
		DB db = new DB();
		db.connect();
		db.getUsers();
		db.updateDb("update users set username=? where password=?",
				new String[] {"daaaave", "password"});
		ResultSet rs = db.queryDb("select username,password,full_name from users");
		while (rs.next()) {
			System.out.println("user: " + rs.getString(1) + " | " +
					rs.getString(2) + " | " +
					rs.getString(3));
		}
		rs.close();
		db.close();
	}
}
