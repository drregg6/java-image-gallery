package edu.au.cc.gallery.tools;

import edu.au.cc.gallery.DB;
import java.util.Scanner;

public class UserAdmin {
	public static void main(String[] args) throws Exception {
		DB db = new DB();
		db.connect();

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
					System.out.println("\n" + db.listUsers());
					break;
				case 2:
					System.out.print("\nUsername> ");
					username = sc.nextLine().toLowerCase();

					System.out.print("Password> ");
					password = sc.nextLine();

					System.out.print("Full name> ");
					fullName = sc.nextLine();

					user = db.getUser(username);
					if (user == null) {
						db.addUser(username, password, fullName);
					} else {
						System.out.println("\nError: user with username " + username + " already exists");
					}
					break;
				case 3:
					System.out.print("\nUsername to edit> ");
					username = sc.nextLine();
					user = db.getUser(username);

					// If user exists in the database
					// Get more information from the user
					if (user != null) {
						String[] userArray = user.split(",");
						
						System.out.println("New Password (press enter to keep current)> ");
						password = sc.nextLine();
						if (password.equals("")) {
							password = userArray[1];
						}

						System.out.println("New full name (press enter to keep current)> ");
						fullName = sc.nextLine();
						if (fullName.equals("")) {
							fullName = userArray[2];
						}

						// Update the user
						db.updateUser(username, password, fullName);
					} else {
						// Else
						System.out.println("\nNo such user.");
					}
					break;
				case 4:
					System.out.print("\nUsername to delete> ");
					username = sc.nextLine();
					
					user = db.getUser(username);
					// If user does not exist - print error
					if (user == null) {
						System.out.println("\nNo such user.");
					// Delete user
					} else {
						// First confirm
						System.out.print("Are you sure you want to delete " + username + "? [Y / n] ");
						String response = sc.nextLine().toLowerCase();
						if (response.equals("y")) {
							db.deleteUser(username);
						}
					}
					break;
				case 5:
					System.out.println("Goodbye");
					break;
				default:
					System.out.println("\nPlease select a number between 1 and 5");
			}
		} while (!input.equals("5"));

		db.close();
	}
}
