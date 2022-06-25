package com.bank.dao;

import java.util.List;

import com.bank.models.User;

public interface UserDao {
	
	// List off our CRUD (Create, Read, Update and Delete) methods to be implemented
	// These will only operate on our User Objects

	
	// Insert or create a user object
	
	int insert(User u); // Let's return the new primary key of whatever user was inserted
	
	//Read: return 1 or return all
	User findById(int id); // Returns user object associated with this id in DB
	User findByUsername(String username); // Return the user object associated with this username
	List<User> findAll(); // Return a list of all user objects in the DB;
	
	// Update
	boolean update(User u); // Updates a user in the DB and return true if successful and false if not
	
	// Delete
	boolean delete(int id); // Delete the user associated with the ID;
}