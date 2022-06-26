package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


// We're going to make a Singleton Connection class
public class ConnectionUtil {
	
	// Private static instance
	
	private static Connection conn = null;
	
	// Private constructor
	
	private ConnectionUtil() {
	}
	
	// Public static getInstance() method
	
	public static Connection getConnection() {
		
		// Check to see if there is a connection instance
		// If there is then return it
		
		try {
			if (conn != null && !conn.isClosed()) {
				System.out.println("Using a previously made connection");
				return conn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Established Connection to Database!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot establish connection");
			e.printStackTrace();
		}
		
		return conn;
	}

}