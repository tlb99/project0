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
//		ConnectionUtil.getConnection();
//		
//		ConnectionUtil.getConnection();
		
		
	}
	
	public static void run() {
		
		UserService us = new UserService();
		
		System.out.println("Welcome to our Bank!");
		
		System.out.println("Press 1 if you're an existing member trying to log in. \nPress 2 if you'd like to register.");
		
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
			
			User u = new User(username, password, Role.Customer, null);
			
			
			
			us.register(u);
		}
		
	}
}
