package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.exceptions.UserNotFoundException;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.service.UserService;
import com.bank.util.ConnectionUtil;

public class UserDaoImpl implements UserDao{
	
	Logger logger = Logger.getLogger(UserDaoImpl.class); 

	public int insert(User u) {
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
				
				logger.info("We returned a user with id #" + id);
				
				return id;
			}
			
			
			
			
		} catch (SQLException e) {
			logger.info("Unable to insert user - sql exception");
			e.printStackTrace();
		}
		
		// If our database fails to enter someone in we should return an index that we know our DB could never generate
		
		return -1;
	}

	public User findById(int id) {
		// Let's instantiate a user to hold our retrieved user
		
		User u = new User();
		
		// Try with Resources to connect and work with database
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM users WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				String returnedUsername = rs.getString("username");
				String password = rs.getString("pwd");
				Role role = Role.valueOf(rs.getString("user_role"));
				
				u.setId(id);
				u.setUsername(returnedUsername);
				u.setPassword(password);
				u.setRole(role);
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't retrieve user from DB");
			e.printStackTrace();
		}
		
		
		return u;
	}

	public User findByUsername(String username) {
		
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
			logger.info("SQL Exception Thrown - can't retrieve user from DB");
			throw new UserNotFoundException("User not found");
		}
		
		
		return u;
	}

	public List<User> findAll() {
		// Let's instantiate a list to hold our retrieved users
		
		List<User> users = new LinkedList<User>();
		
		// Try with Resources to connect and work with database
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM users";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Keep moving the cursor forward
				while(rs.next()) {
					User u = new User();
					
					int id = rs.getInt("id");
					String returnedUsername = rs.getString("username");
					String password = rs.getString("pwd");
					Role role = Role.valueOf(rs.getString("user_role"));
					
					u.setId(id);
					u.setUsername(returnedUsername);
					u.setPassword(password);
					u.setRole(role);
					
					users.add(u);
				}
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't retrieve users from DB");
			e.printStackTrace();
		}
		
		
		return users;
	}

	public boolean update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setObject(3, u.getRole(), Types.OTHER);
			stmt.setInt(4, u.getId());
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				return true;
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't update user in DB");
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "DELETE FROM users WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				return true;
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't delete user from DB");
			e.printStackTrace();
		}
		
		return false;
	}

}