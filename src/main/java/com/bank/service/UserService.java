package com.bank.service;

import com.bank.dao.UserDaoImpl;
import com.bank.dao.UserDao;
import com.bank.exceptions.RegisterUserFailedException;
import com.bank.models.User;

import java.util.List;

import org.apache.log4j.Logger;


public class UserService {
	
	// Dependency Injection
	private UserDao udao = new UserDaoImpl();
	
	Logger logger = Logger.getLogger(UserService.class);
	
	public UserDao getUdao() {
		return udao;
	}


	public void setUdao(UserDao udao) {
		this.udao = udao;
	}


	public User register(User u) {
		
		logger.info("Registering user....");
		
		// Let's make sure the registering user has an id of 0 before trying to register
		// This is just an additional layer of data validation
		
		if (u.getId()!= 0) {
			throw new RegisterUserFailedException("User not valid to register because Id was not 0");
		}
		
		// If the id is 0, we can call the dao to create a new object
		
		int generatedId = udao.insert(u);
		
		// Let's do some checking before finishing
		
		if (generatedId != -1 && generatedId != u.getId()) {
			u.setId(generatedId);
		} else {
			throw new RegisterUserFailedException("User's Id was either -1 or did not change after insertion");
		}
		
		logger.info("Successfully registered user with the Id of " + u.getId());
		
		
		return u;
	}
	
	
	public User login(String username, String password) {
		
		// We now need to call upon our userDAO to get us some information about the user with this specific username
		
		User returnedUser = udao.findByUsername(username);
		
		
		
		
		// Check to see if returned password matches the entered password
		
		if (returnedUser.getPassword().equals(password)) {
			
			logger.info("Successfully Logged in!");
			
			logger.info("Reached the inside of the if statement");
			return returnedUser;
		}
		
		// Otherwise the password is not equal
		return null;
	}
	
	public void viewAllUsers() {
		logger.info("Fetching Users...");
		
		List<User> userList = udao.findAll();
		
		for (User u: userList) {
			System.out.println(u);
		}
	}

	public void viewUser(int id) {
		logger.info("Fetching User + " + id + "...");
		
		User u = udao.findById(id);
		
		System.out.println(u);
	}
	
}