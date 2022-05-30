package edu.au.cc.gallery.tools;

import java.util.Scanner;

public class UserAdmin {
	public static void main(String[] args) throws Exception {
		String input = "";
		Scanner sc = new Scanner(System.in);

		String menu = "";
		menu += "\n1) List users";
		menu +=	"\n2) Add user";
		menu +=	"\n3) Edit user";
		menu +=	"\n4) Delete user";
		menu +=	"\n5) Quit";
		menu +=	"\nEnter command> ";

		do {
			System.out.print(menu);
			input = sc.nextLine();
			int selection = Integer.parseInt(input);

			String user = "";
			String username = "";
			String password = "";
			String fullName = "";
			
			switch (selection) {
				case 1:
					// listUsers();
					break;
				case 2:
					System.out.print("\nUsername> ");
					username = sc.nextLine();

					System.out.print("Password> ");
					password = sc.nextLine();

					System.out.print("Full name> ");
					fullName = sc.nextLine();

					// user = getUser(username);
					// if (user == null) {
					//	addUser(username, password, fullName);
					// } else {
					//	System.out.println("\nError: user with username " + username + " already exists");
					// }
					break;
				case 3:
					System.out.print("\nUsername to edit> ");
					username = sc.nextLine();
					// user = getUser(username);

					// If user exists in the database
					// Get more information from the user
					// if (user != null) {
					//	String[] userArray = user.split(",");
					//	System.out.println("New Password (press enter to keep current)> ");
					//	password = sc.nextLine().trim();
					//	if (password == "") {
					//		password = userArray[1];
					//	}

					//	System.out.println("New full name (press enter to keep current)> ");
					//	fullName = sc.nextLine().trim();
					//	if (fullName == "") {
					//		fullName = userArray[2];
					//	}
					// }
					// Update the user
					// updateUser(username, password, fullName);

					// Else
					System.out.println("\nNo such user.");
					break;
				case 4:
					System.out.print("\nUsername to delete> ");
					username = sc.nextLine();
					
					// user = getUser(username);
					// If user does not exist - print error
					// if (user == null) {
					//	System.out.println("\nNo such user.");
					// Delete user
					// } else {
						// First confirm
					//	System.out.print("Are you sure you want to delete " + username + "? [Y / n] ");
					//	String response = sc.nextLine().toLowerCase();
					//	if (response.equals("y")) {
					//		deleteUser(username);
					//	}
					// }
					break;
				case 5:
					break;
				default:
					System.out.println("\nPlease select a number between 1 and 5");
			}
		} while (!input.equals("5"));
	}
}
