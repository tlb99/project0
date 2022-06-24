package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.bank.models.User;
import com.bank.util.ConnectionUtil;
import com.bank.models.Role;

public class UserDao implements Dao<User> {

	public Optional<User> get(String username) {
		// Let's instantiate a user to hold our retrieved user
		
		User u = new User();
		
		// Try with Resources to connect and work with database
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM users WHERE username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				int id = rs.getInt("id");
				String returnedUsername = rs.getString("username");
				String password = rs.getString("pwd");
				Role role = Role.valueOf(rs.getString("user_role"));
				
				u.setId(id);
				u.setUsername(returnedUsername);
				u.setPassword(password);
				u.setRole(role);
				
			} 
		} catch (SQLException e) {
			System.out.println("SQL Exception Thrown - can't retrieve user from DB");
			e.printStackTrace();
		}
		
		
		return u;
	}

	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(User u) {
		// Let's use insert to practice creating a SQL operation
		
		// Step 1. Capture the DB connection by using the connection util
		
		Connection conn = ConnectionUtil.getConnection(); // Pulls in current connection from connection util
		
		// Step 2. Generate a sql statement like "Insert into ....."
		
		// Use ? to represent data we're looking to enter into our sql statment
		String sql = "INSERT INTO users (username, pwd, user_role) values (?, ?, ?) RETURNING users.id";
		
		// Step 2b. Use a prepared statement to avoid SQL injection
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Start process to set the values we're entering into the DB
			
			// Go through each ? and give it a values
			
			// Let's start with username
			stmt.setString(1, u.getUsername());
			
			// Let's do the password as well
			stmt.setString(2, u.getPassword());
			
			// User role will be handled slightly since it's a java enum
			stmt.setObject(3, u.getRole(), Types.OTHER);
			
			// Use a resultset to extract the primary key from the user object that was persisted
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// if we return data, we can iterate over it
				
				rs.next();
				
				// We need to capture the first column of data return (which is the id of the return user object)
				
				int id = rs.getInt(1); 
				
				System.out.println("We returned a user with id #" + id);
				
				return id;
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Unable to insert user - sql exception");
			e.printStackTrace();
		}
		
		// If our database fails to enter someone in we should return an index that we know our DB could never generate
		
	}

	public void update(User t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	public void delete(User t) {
		// TODO Auto-generated method stub
		
	}

	public Optional<User> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
