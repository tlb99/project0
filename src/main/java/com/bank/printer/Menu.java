package com.bank.printer;

import java.util.List;
import java.util.Queue;
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
			case Admin:
				adminMenu();
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
				// List accounts
				case 1:
					listAccounts(currentUser);
					break;
				// Apply for account
				case 2:
					applyForAccount();
					break;
				// Deposit to account	
				case 3:
					System.out.println("Please enter your account id.");
					input = scan.nextInt();
					Account aDeposit = as.getAccountByID(input);
					// Account and user mismatch -- exit
					if(aDeposit.getAccOwner() != currentUser.getId()) {
						System.out.println("Cannot deposit as this account does not belong to you.");
						break;
					}
					System.out.println("Enter deposit amount.");
					double deposit = scan.nextDouble();
					depositToAccount(aDeposit, deposit);
					break;
				// Withdraw from account		
				case 4:
					System.out.println("Please enter your account id.");
					input = scan.nextInt();
					Account aWithdraw = as.getAccountByID(input);
					// Account and user mismatch -- exit
					if(aWithdraw.getAccOwner() != currentUser.getId()) {
						System.out.println("Cannot deposit as this account does not belong to you.");
						break;
					}
					System.out.println("Enter withdrawal amount.");
					double withdrawal = scan.nextDouble();
					withdrawFromAccount(aWithdraw, withdrawal);
					break;
				// Transfer balances between accounts		
				case 5:
					System.out.println("Please enter the id of the account to transfer from.");
					input = scan.nextInt();
					Account aFrom = as.getAccountByID(input);
					System.out.println("Please enter the id of the account to transfer to.");
					input = scan.nextInt();
					Account aTo = as.getAccountByID(input);
					// Account and user mismatch -- exit
					if(aFrom.getAccOwner() != currentUser.getId() || aTo.getAccOwner() != currentUser.getId()) {
						System.out.println("Cannot deposit as one or both of these accounts don't belong to you.");
						break;
					}
					System.out.println("Enter withdrawal amount.");
					double transfer = scan.nextDouble();
					transferBalanceBetweenAccounts(aFrom, aTo, transfer);
					break;
				// Log out
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
			System.out.println("Press 1 to list all users. \nPress 2 to list user info by id. \nPress 3 to start the application review queue. \nPress 4 to log out.");
			int input = scan.nextInt();
			
			switch(input) 
			{
				// List all users
				case 1:
					listAllUsers();
					break;
				// List info for a specific user
				case 2:
					viewUserInfo();
					break;
				// Start application review queue
				case 3:
					startApplicationQueue();
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		
		}
		
	}
	
	private void adminMenu() {
		System.out.println("Welcome to the admin menu!");
		
		while(true) {
			System.out.println("Press 1 to list all users. \nPress 2 to list user info by id. \nPress 3 to start the application review queue. \nPress 4 to deposit to an account. \nPress 5 to withdraw from an account. \nPress 6 to transfer balances between accounts. \nPress 7 to cancel an account. \nPress 8 to log out.");
			int input = scan.nextInt();
			
			switch(input) 
			{
				// List all users
				case 1:
					listAllUsers();
					break;
				// List info for a specific user
				case 2:
					viewUserInfo();
					break;
				// Start application review queue
				case 3:
					startApplicationQueue();
					break;
				// Deposit to account	
				case 4:
					System.out.println("Please enter the account id.");
					input = scan.nextInt();
					Account aDeposit = as.getAccountByID(input);
					System.out.println("Enter deposit amount.");
					double deposit = scan.nextDouble();
					depositToAccount(aDeposit, deposit);
					break;
				// Withdraw from account		
				case 5:
					System.out.println("Please enter the account id.");
					input = scan.nextInt();
					Account aWithdraw = as.getAccountByID(input);
					System.out.println("Enter withdrawal amount.");
					double withdrawal = scan.nextDouble();
					withdrawFromAccount(aWithdraw, withdrawal);
					break;
				// Transfer balances between accounts		
				case 6:
					System.out.println("Please enter the id of the account to transfer from.");
					input = scan.nextInt();
					Account aFrom = as.getAccountByID(input);
					System.out.println("Please enter the id of the account to transfer to.");
					input = scan.nextInt();
					Account aTo = as.getAccountByID(input);
					System.out.println("Enter withdrawal amount.");
					double transfer = scan.nextDouble();
					transferBalanceBetweenAccounts(aFrom, aTo, transfer);
					break;
				// Cancel account
				case 7:
					cancelAccount();
					break;
				// Log out
				case 8:
					return;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		
		}
		
	}
	
	private void cancelAccount() {
		System.out.println("Enter the id of the account to cancel.");
		int input = scan.nextInt();
		as.deleteAccount(as.getAccountByID(input));
	}
	
	private void depositToAccount(Account a, double amount) {
		double newBalance = a.getBalance() + amount;
		
		// Basic validation
		if(amount < 0) {
			System.out.println("Invalid deposit amount entered.");
			return;
		}
		if(!a.isActive()) {
			System.out.println("Account is inactive -- cannot deposit");
			return;
		}
		
		a.setBalance(newBalance);
		as.updateAccount(a);
	}
	
	private void withdrawFromAccount(Account a, double amount) {
		double newBalance = a.getBalance() - amount;
		
		// Basic validation
		if(amount < 0 || newBalance < 0) {
			System.out.println("Invalid withdrawal amount entered.");
			return;
		}
		if(!a.isActive()) {
			System.out.println("Account is inactive -- cannot withdraw");
			return;
		}
		
		a.setBalance(newBalance);
		as.updateAccount(a);
	}
	
	private void transferBalanceBetweenAccounts(Account from, Account to, double amount) {
		double fromBalance = from.getBalance() - amount;
		double toBalance = to.getBalance() + amount;
		
		// Basic validation
		if(amount < 0 || fromBalance < 0) {
			System.out.println("Invalid withdrawal amount entered.");
			return;
		}
		if(!from.isActive() || !to.isActive()) {
			System.out.println("One or more accounts are inactive -- cannot transfer");
			return;
		}

		from.setBalance(fromBalance);
		to.setBalance(toBalance);
		as.updateAccount(from);
		as.updateAccount(to);
	}
	
	private void applyForAccount() {
		Account a = new Account(0.0, currentUser.getId(), false);
		
		Account new_a = as.register(a);
		
		System.out.println("Applied for account with ID " + new_a.getId());
	}
	
	private void listAllUsers() {
		us.viewAllUsers();
	}
	
	private void viewUserInfo() {
		System.out.println("Please enter the user id.");
		int input = scan.nextInt();
		
		User u = us.viewUser(input);
		
		listAccounts(u);
	}
	
	
	private void listAccounts(User user) {
		int id = user.getId();
		
		List<Account> accList = as.returnAccountsByOwnerId(id);
		
		if(accList.size() == 0) {
			System.out.println("No accounts found.");
		}
		else {
			System.out.println("Found the following accounts:");
			
			for(Account acc : accList) {
				System.out.println(acc.toString());
			}
		}	
	}
	
	private void startApplicationQueue() {
		// Accounts with a false isActive field are considered to be applications
		Queue<Account> appQueue = as.returnApplications();
		
		// Return if there are no applications to view
		if(appQueue == null || appQueue.isEmpty()) {
			System.out.println("There are no applications in the queue.");
			return;
		}
		
		// Iterate through applications
		int input = 0;
		
		System.out.println("Displaying account applications... ");
		
		while(!appQueue.isEmpty()) {
			Account a = appQueue.poll();
			System.out.println("Approve the following account?");
			System.out.println(a);
			System.out.println("Press 1 to approve account application. \nPress 2 to deny account application. \nPress 3 to quit the application review queue.");
			
			input = scan.nextInt();
			
			switch(input) 
			{
				case 1:
					System.out.println("Approved application!");
					as.updateAccount(a);
					break;
				case 2:
					System.out.println("Denied application!");
					as.deleteAccount(a);
					break;
				default:
					System.out.println("Exiting application review queue...");
					return;
			}
			
		} 
		
		System.out.println("No other open applications found.");
		
	}
}
