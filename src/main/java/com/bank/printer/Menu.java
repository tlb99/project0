package com.bank.printer;

import java.util.List;
import java.util.Scanner;

import com.bank.models.Account;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

public class Menu {
	private User currentUser;
	private UserService us;
	private AccountService as;
	private Scanner scan;
	
	public Menu(User user, UserService us, AccountService as, Scanner scan) {
		this.currentUser = user;
		this.us = us;
		this.scan = scan;
		this.as = as;
	}
	
	public void start() {
		Role role = currentUser.getRole();
		
		switch(role) 
		{
			case Customer:
				customerMenu();
				break;
			case Employee:
				employeeMenu();
				break;
		}
	}
	
	private void customerMenu() {
		System.out.println("Welcome to the customer menu!");
		
		while(true) {
			System.out.println("Press 1 to list your accounts and balances. \nPress 2 to apply for an account. \nPress 3 to deposit to an account. \nPress 4 to withdraw from an account. \nPress 5 to transfer balances between accounts. \nPress 6 to log out.");
			int input = scan.nextInt();
			
			switch(input) 
			{
				case 1:
					listAccounts(currentUser);
					break;
				case 2:
					applyForAccount();
					break;
				case 6:
					return;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		
		}
		
	}
	
	private void employeeMenu() {
		System.out.println("Welcome to the employee menu!");
		
		while(true) {
			System.out.println("Press 1 to list all customers. \nPress 2 to list customer info by id. \nPress 3 to list account applications. \nPress 4 to log out.");
			int input = scan.nextInt();
			
			switch(input) 
			{
				case 1:
					listAllCustomers();
					break;
				case 2:
					applyForAccount();
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		
		}
		
	}
	
	private void applyForAccount() {
		Account a = new Account(0.0, currentUser.getId(), false);
		
		Account new_a = as.register(a);
		
		System.out.println("Applied for account with ID " + new_a.getId());
	}
	
	private void listAllCustomers() {
		us.viewAllUsers();
	}
	
	private void listAccounts(User user) {
		int id = user.getId();
		
		List<Account> accList = as.returnAccountsByOwnerId(id);
		
		if(accList.size() == 0) {
			System.out.println("You currently have no accounts.");
		}
		else {
			System.out.println("You currently have these accounts:");
			
			for(Account acc : accList) {
				System.out.println(acc.toString());
			}
		}	
	}
}
