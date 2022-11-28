/**
 * MAIN PROGRAM
 * @author Nicole D.
 * 
 **/

import java.util.*;
import java.io.*;


class Bank{

	static Scanner scan = new Scanner (System.in);
	static boolean subMenuOpen = false;



	public static void displayMainMenu() {
		System.out.println("\n--------------------------------------------------------\r\n"
				+ ">                     MAIN MENU                        <\r\n"
				+ "--------------------------------------------------------\r\n"
				+ "\nPlease choose an action from the following: \r\n" 
				+ "	1: Add a customer\r\n"
				+ "	2: Delete a customer\r\n"
				+ "	3: Sort customers by last name, first name\r\n"
				+ "	4: Sort customers by SIN\r\n"
				+ "	5: Display customer summary (name, SIN)\r\n"
				+ "	6: Find profile by last name, first name\r\n"
				+ "	7: Find profile by SIN\r\n"
				+ "	8: Quit\r\n"
				+ "");
	}

	public static void displaySubMenu(String lName, String fName, int userSin, int birthY, int birthM, int birthD, double savAcc, double cheqAcc, double credBal) {
		int subAction =10;


		do { // bro what is the difference bewtween chequing and saving account

			System.out.println("\n--------------------------------------------------------\r\n"
					+ ">                     PROFILE MENU                     <\r\n"
					+ "--------------------------------------------------------\r\n"
					+ "\nPlease choose an action from the following: \r\n" 
					+ "	 1: View account activity\r\n"
					+ "	 2: Deposit\r\n"
					+ "	 3: Withdraw\r\n"
					+ "	 4: Process cheque\r\n" // withdraw // Check if they even have an account, say proper prompt
					+ "	 5: Process purchase\r\n" // save money to chequing account using cheque //open, you can use either chequing or saving
					+ "	 6: Process payment for credit card\r\n" // pay credit card bill using saving or chequing
					+ "	 7: Transfer funds\r\n" // move funds from savings to chequing or vice versa
					+ "	 8: Open account or issue card\r\n" // age check validity
					+ "	 9: Cancel account or card\r\n" // overwrite as 'none' cancelled // MAKE SURE CREDIT CARD BALANCE IS ZERO!
					+ "	10: Return to main menu\r\n");

			// Only credit account can go negative 
			// If credit card is positive and wants to be cancelled for some reason, the bank owes them the credit card balance

			// If account wants to be cancelled with positive balances, just cancel it anyways


			try {
				System.out.print("SELECTION: ");
				subAction = scan.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid sub action.");
				break;
			}

			switch(subAction) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;

			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8: // Open Account or issue card

				System.out.println("\n--------------------------------------------------------");
				System.out.println("\\                  You have chosen to                  /");
				System.out.println("/           OPEN AN ACCOUNT/ISSUE A CREDIT CARD        \\");
				System.out.println("--------------------------------------------------------\n");

				System.out.println("ADD ACCOUNT or ISSUE CREDIT CARD?:\n");
				System.out.println("1. ADD ACCOUNT\n2. ISSUE A CREDIT CARD");
				System.out.print("\nSELECTION: ");
				int choice = scan.nextInt();

				switch (choice){
				case 1:
					System.out.println("\nWHAT KIND OF ACCOUNT WOULD YOU LIKE TO ADD?:\n");
					System.out.println("1. SAVINGS ACCOUNT\n2. CHEQUING ACCOUNT");
					System.out.print("\nSELECTION: ");
					int x = scan.nextInt();

					switch(x) {
					case 1:
						SavingAccount newSavings = new SavingAccount(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
						newSavings.addAccount(lName, fName, userSin);
						break;

					case 2: 
						ChequingAccount newChequing = new ChequingAccount(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
						newChequing.addAccount(lName, fName, userSin);
						break;
					}

					break;



				case 2:

					CreditCard newCredit = new CreditCard(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
					newCredit.issueCreditCard(lName, fName, userSin);
					break;
				}
			case 9:
				break;
			}
		}


		while(subAction<10);
		System.out.println("\nBACK TO MAIN MENU...");

	}

	public static void main(String[] args) {

		int action = 8;

		String lastName = null;
		String firstName = null;
		int sin = 0;
		int birthYear = 0;
		int birthMonth = 0;
		int birthDay = 0;
		double savingAccountBalance = 0;
		double chequingAccountBalance = 0;
		double creditCardBalance = 0;

		int searchMethod;
		int sortMethod;


		// MAIN MENU
		System.out.println("========================================================\r\n" 
				+"                Welcome to the VP Bank!\r\n" 
				+ "========================================================");
		
		
		while (true) {
			displayMainMenu();

			try {
				System.out.print("SELECTION: ");
				action = scan.nextInt();
			}
			
			catch (InputMismatchException e) {
				System.out.println("Invalid entry.");
			}
			
			while (action>8) {
				System.out.println("Please ONLY choose an action from within the menu: ");
				System.out.print("SELECTION: ");
				action = scan.nextInt();
			}
			
			if (action==8) {
				break;
			}
			
			else{
				displayMainMenu();


				switch(action) {
				case 1: //Add account
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">           You have chosen to ADD CUSTOMER            <");
					System.out.println("--------------------------------------------------------");
					System.out.println("   +-+-+ Please enter the following information +-+-+\n");

					try {

						scan.nextLine();
						System.out.print("LAST NAME: "); 
						lastName = scan.nextLine();

						System.out.print("FIRST NAME: ");
						firstName = scan.nextLine();

						System.out.print("SIN: ");
						sin = scan.nextInt();

						System.out.print("BIRTH YEAR: ");
						birthYear = scan.nextInt();

						System.out.print("BIRTH MONTH: ");
						birthMonth = scan.nextInt();

						System.out.print("BIRTH DATE: ");
						birthDay = scan.nextInt();

						// Credit card, saving account, and chequing account balance are set to 0 as default
						Customer.addAccount(lastName, firstName, sin, birthYear, birthMonth, birthDay, savingAccountBalance, chequingAccountBalance, creditCardBalance);
						Customer add = new Customer(lastName, firstName, sin, birthYear, birthMonth, birthDay, savingAccountBalance, chequingAccountBalance, creditCardBalance);
						System.out.println("\n       + ACCOUNT SUCESSFULLY ADDED! + \n");


					}

					// Terminate entry if wrong data type is entered
					catch (InputMismatchException e) {
						System.out.println("Invalid entry.");
					}


					break;

				case 2:

					System.out.println("\n--------------------------------------------------------");
					System.out.println(">          You have chosen to DELETE CUSTOMER          <");
					System.out.println("--------------------------------------------------------");
					System.out.println("     +-+ How would you like to delete the user? +-+\n");

					// Ask user if they'd like to find the user through name or SIN
					System.out.println("\t1. Delete by entering NAME" + "\n\t2. Delete by entering SIN");
					System.out.println("\nSELECTION: ");

					searchMethod = scan.nextInt();

					switch(searchMethod) {
					case 1:
						scan.nextLine();
						System.out.print("Enter FIRST NAME: ");
						firstName = scan.nextLine();
						System.out.print("Enter LAST NAME: ");
						lastName = scan.nextLine();

						Customer.deleteWithName(lastName, firstName);
						break;

					case 2: 
						System.out.println("Enter SIN: ");
						sin = scan.nextInt();
						Customer.deleteWithSin(sin);
						break;
					}

					break;

				case 3:

					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/              SORT with FIRST & LAST NAME             \\");
					System.out.println("--------------------------------------------------------\n");

					System.out.println("Would you like to sort by fist name or last name?");
					System.out.println("\t1. FIRST NAME \t2.LAST NAME");

					sortMethod = scan.nextInt();

					switch (sortMethod) {

					case 1:
						Customer.sortByFirstName();
						break;

					case 2:
						Customer.sortByLastName();
					}

					System.out.println("Customers have been sorted.");
					break;

				case 4:
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">             You have chosen to SORT with SIN         <");
					System.out.println("--------------------------------------------------------\n");
					Customer.sortBySin();
					System.out.println("Customers have been sorted.");
					break;

				case 5:
					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/                DISPLAY CUSTOMER SUMMARY              \\");
					System.out.println("--------------------------------------------------------\n");
					System.out.println("Please enter the following information to view summary:");
					scan.nextLine();
					System.out.print("FIRST NAME: ");
					firstName = scan.nextLine();
					System.out.print("LAST NAME: ");
					lastName = scan.nextLine();
					System.out.print("SIN: ");
					sin = scan.nextInt();

					Customer.displayCustomerSummary(firstName,lastName,sin);
					break;

				case 6:
					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/          FIND CUSTOMER with FIRST & LAST NAME        \\");
					System.out.println("--------------------------------------------------------\n");
					scan.nextLine();
					try {
						System.out.print("Enter FIRST NAME: ");
						firstName = scan.nextLine();
						System.out.print("Enter LAST NAME: ");
						lastName = scan.nextLine();
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid entry.");
					}


					if (Customer.findWithName(lastName, firstName)) {
						System.out.println("\nAccount found!");
						System.out.println("Opening profile menu...\n");
						subMenuOpen = true;
						displaySubMenu(lastName, firstName, 0, birthYear, birthMonth, birthDay, savingAccountBalance, chequingAccountBalance, creditCardBalance);
						// sin set to 0 to avoid issues when altering multiple accounts
					}
					else 
						System.out.println("\nAccount not found.\n");			
					break;

				case 7:
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">       You have chosen to FIND CUSTOMER with SIN      <");
					System.out.println("--------------------------------------------------------\n");
					System.out.print("Enter SIN: ");
					try {
						sin = scan.nextInt();
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid entry.");
					}


					if (Customer.findWithSin(sin)) {
						System.out.println("\nAccount found!");
						System.out.println("Opening profile menu...\n");
						subMenuOpen = true;
						displaySubMenu(null, null, sin, birthYear, birthMonth, birthDay, savingAccountBalance, chequingAccountBalance, creditCardBalance);
						// names set to null to avoid issues when altering multiple accounts

					}
					else 
						System.out.println("\nAccount not found.\n");
					break;
				} 
			}
		}
		System.out.println("\nEXITING PROGRAM");
	}
}

