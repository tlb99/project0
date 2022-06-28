package com.bank.dao;

import java.util.List;
import java.util.Queue;

import com.bank.models.Account;

public interface AccountDao {
	
	// Let's define our crud methods
	
	//Create
	int insert(Account a);
	
	// A few methods for read
	
	List<Account> findAll();
	
	Account findById(int id); // Returns account based off Id
	
	List<Account> findByOwner(int accOwnerId); // Since a user can have many accounts we want to be able to return all
	
	// Update
	boolean update(Account a);
	
	// Delete
	
	boolean delete(Account a);
	
	Queue<Account> findByIsActive();

}