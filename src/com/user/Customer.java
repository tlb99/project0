package com.user;

import java.util.List;

import com.account.Account;

import java.util.ArrayList;

public class Customer extends User {
	private List<Account> accounts;

	public Customer(String username, String password) {
		super(username, password);
		accounts = new ArrayList<Account>();
	}
	
	public void addAccount(Account account){
		this.accounts.add(account);
	}
	
	public List<Account> getAccounts(){
		return this.accounts;
	}

}
