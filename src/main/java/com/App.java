package com;

import java.util.Scanner;

import com.bank.printer.Menu;
import com.bank.exceptions.UserNotFoundException;
import com.bank.models.Role;
import com.bank.models.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

public class App {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		run();
	}
	
	public static void run() {
		
		UserService us = new UserService();
		AccountService as = new AccountService();
		
		System.out.println("Welcome to our Bank!");
				
		while (true) {
			System.out.println("Press 1 if you're an existing member trying to log in. \nPress 2 if you'd like to register.\nPress 3 to quit.");
			
			int input = scan.nextInt();
			
			if (input == 1) {
				
				System.out.println("Please enter your username");
				
				String username = scan.next();
				
				System.out.println("Please enter your password");
				
				String password = scan.next();
				
				try {
					User loggedInUser = us.login(username, password);
					System.out.println("Welcome to your account: " + loggedInUser.getUsername());
					
					Menu menu = new Menu(loggedInUser, us, as, scan);
					menu.start();
				}
				catch(UserNotFoundException e) {
					System.out.println("Could not find user, please try again.");
					continue;
				}
				
				
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
						u = new User(username, password, Role.Customer);
						break;
					case 2:
						u = new User(username, password, Role.Employee);
						break;
					default:
						u = new User(username, password, Role.Admin);
				}
				
				us.register(u);
			}
			else if (input == 3) {
				System.out.println("Exiting...");
				return;
			}
		}
		
	}
}
