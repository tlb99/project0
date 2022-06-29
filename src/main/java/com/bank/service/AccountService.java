package com.bank.service;

import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDao;
import com.bank.dao.AccountDaoImpl;
import com.bank.exceptions.RegisterAccountFailedException;
import com.bank.exceptions.RegisterUserFailedException;
import com.bank.models.Account;

public class AccountService {
	
	// Inject our DAO to the service
	public AccountDao aDao = new AccountDaoImpl();

	// Lets make a logger here
	Logger logger = Logger.getLogger(AccountService.class);
	
	public Account register(Account a) {
		
		logger.info("Registering account....");
		
		// Let's make sure the registering account has an id of 0 before trying to register
		// This is just an additional layer of data validation
		
		if (a.getId()!= 0) {
			throw new RegisterAccountFailedException("Could not register account because ID was not 0");
		}
		
		// If the id is 0, we can call the dao to create a new object
		
		int generatedId = aDao.insert(a);
		
		// Let's do some checking before finishing
		
		if (generatedId != -1 && generatedId != a.getId()) {
			a.setId(generatedId);
		} else {
			throw new RegisterAccountFailedException("Account Id was either -1 or did not change after insertion");
		}
		
		logger.info("Successfully registered account with an ID of " + a.getId());
		
		
		return a;
	}
	
	public Account getAccountByID(int accId) {
		return aDao.findById(accId);
	}
	
	public void viewAllAccounts() {
		logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = aDao.findAll();
		
		for (Account a: accList) {
			System.out.println(a);
		}
	}
	
	public List<Account> returnAccountsByOwnerId(int id) {
		logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = aDao.findByOwner(id);
		
		if(accList == null) {
			logger.info("Failed to retrieve accounts from DB!");
		}
		else {
			logger.info("Successfully retrieved accounts for user #" + id);
		}
		
		return accList;
	}
	
	public Queue<Account> returnApplications() {
		logger.info("Fetching Applications...");
		
		Queue<Account> appQueue = aDao.findByIsActive();
		
		if(appQueue == null) {
			logger.info("Failed to retrieve applications from DB!");
		}
		else {
			logger.info("Successfully retrieved applications");
		}
		
		return appQueue;
	}
	
	public boolean updateAccount(Account a) {
		logger.info("Updating account...");
		
		a.setActive(true);
		
		boolean b = aDao.update(a);
		
		if(b) {
			logger.info("Successfully updated application!");
		}
		else {
			logger.info("Failed to update application in DB!");
		}
		
		return b;
	}
	
	public boolean deleteAccount(Account a) {
		logger.info("Deleted account...");
		
		boolean b = aDao.delete(a);
		
		if(b) {
			logger.info("Successfully deleted application!");
		}
		else {
			logger.info("Failed to delete application from DB!");
		}
		
		return b;
	}

}