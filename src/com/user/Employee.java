package com.user;

import com.account.Account;

public class Employee extends User {

	public Employee(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}
	
	public void addAccount(Customer customer) {
		customer.addAccount(new Account());
	}

}
