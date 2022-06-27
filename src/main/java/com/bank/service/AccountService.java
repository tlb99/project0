package com.bank.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDao;
import com.bank.dao.AccountDaoImpl;
import com.bank.models.Account;

public class AccountService {
	
	// Inject our DAO to the service
	private AccountDao aDao = new AccountDaoImpl();
	
	// Lets make a logger here
	Logger logger = Logger.getLogger(AccountService.class);
	
	public void viewAllAccounts() {
		logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = aDao.findAll();
		
		for (Account a: accList) {
			System.out.println(a);
		}
	}

}