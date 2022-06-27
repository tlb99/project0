package com;

import java.util.Scanner;

import com.bank.printer.MenuPrinter;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

public class App {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		//Let's just fetch our accounts to test
		
		AccountService as = new AccountService();
		as.viewAllAccounts();
		
		run();
		
		
	}
	
	public static void run() {
		
		UserService us = new UserService();
		
		System.out.println("Welcome to our Bank!");
				
		while (true) {
			System.out.println("Press 1 if you're an existing member trying to log in. \nPress 2 if you'd like to register.\nPress 3 to quit.");
			
			int input = scan.nextInt();
			
			if (input == 1) {
				
				System.out.println("Please enter your username");
				
				String username = scan.next();
				
				System.out.println("Please enter your password");
				
				String password = scan.next();
				
				User loggedInUser = us.login(username, password);
				
				System.out.println("Welcome to your account: " + loggedInUser.getUsername());
				
				// Maybe some data validation
				
				// Maybe creating a basic menu for your accounts
				
				// Giving option for accounts etc.
				
				
			} else if (input == 2) {
				
				System.out.println("Please enter a username");
				String username = scan.next();
				
				System.out.println("Please enter a secure password");
				String password = scan.next();
				
				System.out.println("Press 1 to register as a customer, 2 to register as an employee, or 3 to register as an admin.");
				input = scan.nextInt();
				User u;
				
				switch(input) 
				{
					case 1:
						u = new User(username, password, Role.Customer, null);
						break;
					case 2:
						u = new User(username, password, Role.Employee, null);
						break;
					default:
						u = new User(username, password, Role.Admin, null);
				}
				
				us.register(u);
			}
			else if (input == 3) {
				return;
			}
		}
		
	}
}
