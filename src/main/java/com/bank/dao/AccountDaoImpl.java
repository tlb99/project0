package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.models.Account;
import com.bank.util.ConnectionUtil;

public class AccountDaoImpl implements AccountDao{

	Logger logger = Logger.getLogger(AccountDaoImpl.class); 
	
	public int insert(Account a) {
		Connection conn = ConnectionUtil.getConnection(); // Pulls in current connection from connection util
		
		String sql = "INSERT INTO accounts (balance, acc_owner, active) values (?, ?, ?) RETURNING accounts.id";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// Balance
			stmt.setDouble(1, a.getBalance());
			
			// Password
			stmt.setInt(2, a.getAccOwner());
			
			// Is active
			stmt.setObject(3, a.isActive());
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// if we return data, we can iterate over it
				
				rs.next();
				
				// We need to capture the first column of data return (which is the id of the return user object)
				
				int id = rs.getInt(1); 
				
				logger.info("We returned an account with id #" + id);
				
				return id;
			}
			
			
		} catch (SQLException e) {
			logger.info("Unable to insert account due to SQL exception");
			e.printStackTrace();
		}
		
		// If our database fails to enter someone in we should return an index that we know our DB could never generate
		
		return -1;
	}

	public List<Account> findAll() {
		
		// Instantiate a linkedlist to store all of the objects that we retrieve
		List<Account> accList = new LinkedList<Account>();
		
		// Obtain a connection using try with resources
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			// Create a statement to execute against the DB
			Statement stmt = conn.createStatement();
			
			// Let's create our SQL query
			String sql = "SELECT * FROM accounts";
			
			// We'll return all of the data queried so we need a ResultSet obj to iterate over it
			ResultSet rs = stmt.executeQuery(sql);
			
			// Open a while loop to get all the info
			while (rs.next()) {
				
				// Gather the id of the accounts, balance, accOwnerId, and isActive
				int id = rs.getInt("id"); // Capture the value in the id column
				double balance = rs.getDouble("balance");
				int accOwnerId = rs.getInt("acc_owner");
				boolean isActive = rs.getBoolean("active");
				
				// Let's create an Account object to store all of this
				
				Account a = new Account(id, balance, accOwnerId, isActive);
				
				accList.add(a);
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
		
		
		return accList;
	}

	public Account findById(int id) {		
		Account a = new Account();
				
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				// Gather balance, accOwnerId, and isActive
				double balance = rs.getDouble("balance");
				int accOwnerId = rs.getInt("acc_owner");
				boolean isActive = rs.getBoolean("active");
				
				a.setId(id);
				a.setBalance(balance);
				a.setAccOwner(accOwnerId);
				a.setActive(isActive);
				
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't retrieve account by id from DB");
			e.printStackTrace();
		}
		
		
		return a;
	}

	public List<Account> findByOwner(int accOwnerId) {
		
		// Instantiate a linkedlist to store all of the objects that we retrieve
		List<Account> accList = new LinkedList<Account>();
		
		// Obtain a connection using try with resources
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			// Let's create our SQL query
			String sql = "SELECT * FROM accounts WHERE acc_owner = ?";
			
			// Create a statement to execute against the DB
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, accOwnerId);
			
			// We'll return all of the data queried so we need a ResultSet obj to iterate over it
			ResultSet rs = stmt.executeQuery();
			
			// Open a while loop to get all the info
			while (rs.next()) {
				
				// Gather the id of the accounts, balance, accOwnerId, and isActive
				int id = rs.getInt("id"); // Capture the value in the id column
				double balance = rs.getDouble("balance");
				boolean isActive = rs.getBoolean("active");
				
				// Let's create an Account object to store all of this
				
				Account a = new Account(id, balance, accOwnerId, isActive);
				
				accList.add(a);
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Unable to retrieve accounts due to SQL Exception");
			e.printStackTrace();
		}
		
		
		return accList;
	}

	public boolean update(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE accounts SET balance = ?, acc_owner = ?, active = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getAccOwner());
			stmt.setObject(3, a.isActive(), Types.BOOLEAN);
			stmt.setInt(4, a.getId());
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				return true;
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't update account in DB");
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean delete(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "DELETE FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getId());
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				
				// Move the cursor forward
				rs.next();
				
				return true;
				
			} 
		} catch (SQLException e) {
			logger.info("SQL Exception Thrown - can't delete account from DB");
			e.printStackTrace();
		}
		
		return false;
	}

}