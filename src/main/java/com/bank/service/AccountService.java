package com.bank.service;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.dao.AccountDao;
import com.bank.dao.Dao;
import com.bank.models.Account;

public class AccountService {
	
	// Inject our DAO to the service
	private Dao aDao = new AccountDao();
	
	// Lets make a logger here
	Logger logger = Logger.getLogger(AccountService.class);
	
	//private static Scanner scan = new Scanner(System.in);
	
	public void viewAllAccounts() {
		
//		System.out.println("Fetching accounts...");
		
		logger.info("Fetching Accounts...");
		
		// Lets call upon our DAO to get all of our accounts
		
		List<Account> accList = aDao.getAll();
		
		for (Account a: accList) {
			System.out.println(a);
		}
		
	}

}