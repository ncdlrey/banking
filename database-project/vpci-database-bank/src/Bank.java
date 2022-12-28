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

	static boolean error = false;


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

		int choice;

		CreditCard newCredit = new CreditCard(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
		SavingAccount newSavings = new SavingAccount(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
		ChequingAccount newChequing = new ChequingAccount(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);
		Account newActivity = new Account(lName, fName, userSin, birthY, birthM, birthD, savAcc, cheqAcc, credBal);

		newActivity.getSummary(lName, fName, userSin);

		while (!error) {
			int subAction = 10;



			System.out.println("\n--------------------------------------------------------\r\n"
					+ ">                     PROFILE MENU                     <\r\n"
					+ "--------------------------------------------------------\r\n"
					+ "\nPlease choose an action from the following: \r\n" 
					+ "	 1: View account activity\r\n"
					+ "	 2: Deposit\r\n"
					+ "	 3: Withdraw\r\n"
					+ "	 4: Process cheque\r\n" 
					+ "	 5: Process purchase\r\n"
					+ "	 6: Process payment for credit card\r\n" 
					+ "	 7: Transfer funds\r\n" 
					+ "	 8: Open account or issue card\r\n"
					+ "	 9: Cancel account or card\r\n"
					+ "	10: Return to main menu\r\n");



			try {
				System.out.print("SELECTION: ");
				subAction = scan.nextInt();


				while (subAction>10) {
					System.out.println("Please ONLY choose an action from within the menu: ");
					System.out.print("SELECTION: ");
					subAction = scan.nextInt();
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid sub action.");
				error = true;
				break;
			}


			if (subAction==10) {
				break;
			}

			else{

				switch(subAction) {
				case 1: // View activity
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">       You have chosen to VIEW ACCOUNT ACTIVITY       <");
					System.out.println("--------------------------------------------------------");
					newActivity.getActivity(lName, fName, userSin);
					break;

				case 2: // Deposit
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">              You have chosen to DEPOSIT              <");
					System.out.println("--------------------------------------------------------");

					System.out.println("\nWHICH ACCOUNT WOULD YOU LIKE TO DEPOSIT INTO?:\n");
					System.out.println("1. SAVINGS ACCOUNT\n2. CHEQUING ACCOUNT");
					System.out.print("\nSELECTION: ");
					try {
						choice = scan.nextInt();
						if (choice == 1) {
							newSavings.deposit(lName, fName, userSin);
						}
						else if (choice == 2) {
							newChequing.deposit(lName, fName, userSin);
						}
						else {
							System.out.println("Option does not exist. Back to PROFILE MENU...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid sub action.");
						error = true;
						break;
					}


					break;

				case 3: // Withdraw
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">              You have chosen to WITHDRAW             <");
					System.out.println("--------------------------------------------------------");

					System.out.println("\nWHICH ACCOUNT WOULD YOU LIKE TO WITHDRAW FROM?:\n");
					System.out.println("1. SAVINGS ACCOUNT\n2. CHEQUING ACCOUNT");
					System.out.print("\nSELECTION: ");

					try {
						choice = scan.nextInt();
						if (choice == 1) {
							newSavings.withdraw(lName, fName, userSin);
						}
						else if (choice == 2) {
							newChequing.withdraw(lName, fName, userSin);
						}

						else {
							System.out.println("Option does not exist. Back to PROFILE MENU...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid sub action.");
						error = true;
						break;
					}

					break;

				case 4: // Process cheque
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">           You have chosen to PROCESS CHEQUE          <");
					System.out.println("--------------------------------------------------------");
					newChequing.processCheque(lName, fName, userSin);
					break;

				case 5: //5: Process purchase 
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">          You have chosen to PROCESS PURCHASE         <");
					System.out.println("--------------------------------------------------------");
					newCredit.processPurchase(lName, fName, userSin);
					break;

				case 6: 
					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/           PROCESS PAYMENT FOR A CREDIT CARD          \\");
					System.out.println("--------------------------------------------------------\n");
					newCredit.processPayment(lName, fName, userSin);
					break;

				case 7:
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">            You have chosen to TRANSFER FUNDS         <");
					System.out.println("--------------------------------------------------------");
					System.out.println("1. SAVINGS to CHEQUING");
					System.out.println("2. CHEQUING to SAVINGS");
					try {
						choice = scan.nextInt();
						if (choice == 1) {
							newSavings.transfer(lName, fName, userSin);
						}
						else if (choice ==2) {
							newChequing.transfer(lName, fName, userSin);

						}
						else {
							System.out.println("Option does not exist. Back to PROFILE MENU...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid sub action.");
						error = true;
						break;
					}

					break;
				case 8: // Open Account or issue card
					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/           OPEN AN ACCOUNT/ISSUE A CREDIT CARD        \\");
					System.out.println("--------------------------------------------------------\n");

					System.out.println("ADD ACCOUNT or ISSUE CREDIT CARD?:\n");
					System.out.println("1. ADD ACCOUNT\n2. ISSUE A CREDIT CARD");
					System.out.print("\nSELECTION: ");

					try {
						choice = scan.nextInt();

						if (choice ==1) {
							System.out.println("\nWHAT KIND OF ACCOUNT WOULD YOU LIKE TO ADD?:\n");
							System.out.println("1. SAVINGS ACCOUNT\n2. CHEQUING ACCOUNT");
							System.out.print("\nSELECTION: ");
							try {
								int y = scan.nextInt();
								if (y==1) {
									newSavings.addAccount(lName, fName, userSin);
								}
								else if (y==2) {
									newChequing.addAccount(lName, fName, userSin);
								}
								else {
									System.out.println("Option does not exist. Back to PROFILE MENU...");
								}
							}
							catch (InputMismatchException e) {
								System.out.println("Invalid sub action.");
								error = true;
								break;
							}
						}

						else if(choice ==2) {
							newCredit.issueCreditCard(lName, fName, userSin);
							break;
						}
						else {
							System.out.println("Option does not exist. Back to PROFILE MENU...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid sub action.");
						error = true;
						break;
					}

					break;

				case 9: // Cancel card or account
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">         You have chosen to CANCEL CARD/ACCOUNT       <");
					System.out.println("--------------------------------------------------------");
					System.out.println("WOULD YOU LIKE TO CANCEL ACCOUNT or CREDIT CARD?:\n");
					System.out.println("1. CANCEL SAVINGS ACCOUNT\n2. CANCEL CHEQUING ACCOUNT\n3. CANCEL CREDIT CARD ");
					System.out.print("\nSELECTION: ");
					try {
						choice = scan.nextInt();
						if (choice == 1) {
							newSavings.cancel(lName, fName, userSin);
						}

						else if (choice == 2) {
							newChequing.cancel(lName, fName, userSin);
						}

						else if(choice == 3) {
							newCredit.cancel(lName, fName, userSin);
						}
						else {
							System.out.println("Option does not exist. Back to PROFILE MENU...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid sub action.");
						error = true;
						break;
					}
					break;
				}
			}

		}
	}

	public static void main(String[] args) {


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


		while (!error) {
			int action = 8;

			displayMainMenu();

			try {
				System.out.print("SELECTION: ");
				action = scan.nextInt();

				while (action>8) {
					System.out.println("Please ONLY choose an action from within the menu: ");
					System.out.print("SELECTION: ");
					action = scan.nextInt();
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid sub action.");
				error = true;
				break;
			}


			if (action==8) {
				break;
			}

			else{
				//displayMainMenu();


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
						while(String.valueOf(sin).length()>9 || String.valueOf(sin).length()<9) {
							System.out.print("Must be 9 digits long: ");
							sin = scan.nextInt();
						}

						System.out.print("BIRTH YEAR: ");
						birthYear = scan.nextInt();
						while (birthYear<=0||birthYear>2022) {
							System.out.print("Must be before 2022: ");
							birthYear = scan.nextInt();
						}

						System.out.print("BIRTH MONTH: ");
						birthMonth = scan.nextInt();
						while (birthMonth<=0 || birthMonth>12) {
							System.out.print("Must be between 1-12: ");
							birthMonth = scan.nextInt();
						}

						System.out.print("BIRTH DATE: ");
						birthDay = scan.nextInt();
						while (birthDay<=0 || birthDay>31) {
							System.out.print("Must be between 1-31: ");
							birthDay = scan.nextInt();
						}

						// Credit card, saving account, and chequing account balance are set to 0 as default
						Customer.addAccount(lastName, firstName, sin, birthYear, birthMonth, birthDay, savingAccountBalance, chequingAccountBalance, creditCardBalance);


					}

					// Terminate entry if wrong data type is entered
					catch (InputMismatchException e) {
						System.out.println("Invalid entry.");
						error=true;
					}


					break;

				case 2:

					System.out.println("\n--------------------------------------------------------");
					System.out.println(">          You have chosen to DELETE CUSTOMER          <");
					System.out.println("--------------------------------------------------------");
					System.out.println("     +-+ How would you like to delete the user? +-+\n");

					// Ask user if they'd like to find the user through name or SIN
					try {
						System.out.println("\t1. Delete by entering NAME" + "\n\t2. Delete by entering SIN");
						System.out.print("\nSELECTION: ");
						searchMethod = scan.nextInt();

						if (searchMethod ==1) {
							scan.nextLine();
							System.out.print("Enter FIRST NAME: ");
							firstName = scan.nextLine();
							System.out.print("Enter LAST NAME: ");
							lastName = scan.nextLine();

							Customer.deleteWithName(lastName, firstName);
						}
						else if (searchMethod==2) {
							System.out.println("Enter SIN: ");
							sin = scan.nextInt();
							Customer.deleteWithSin(sin);
						}
						else {
							System.out.println("Option does not exist. Going back to main menu...");
						}
					}
					catch (InputMismatchException e) {
						System.out.println("Invalid entry.");
						error=true;

					}
					break;

				case 3:

					System.out.println("\n--------------------------------------------------------");
					System.out.println("\\                  You have chosen to                  /");
					System.out.println("/              SORT with FIRST & LAST NAME             \\");
					System.out.println("--------------------------------------------------------\n");

					try {
						System.out.println("Would you like to sort by fist name or last name?");
						System.out.println("\t1. FIRST NAME \t2.LAST NAME");
						System.out.print("\nSELECTION: ");
						sortMethod = scan.nextInt();

						if (sortMethod==1) {
							Customer.sortByFirstName();
							System.out.println("Customers have been sorted in text file.");

						}
						else if (sortMethod==2) {
							Customer.sortByLastName();
							System.out.println("Customers have been sorted in text file.");


						}
						else {
							System.out.println("Option does not exist. Going back to main menu...");
						}
					}
					catch (InputMismatchException ex) {
						System.out.println("Invalid entry.");
						error = true;
					}
					break;

				case 4:
					System.out.println("\n--------------------------------------------------------");
					System.out.println(">             You have chosen to SORT with SIN         <");
					System.out.println("--------------------------------------------------------\n");
					Customer.sortBySin();
					System.out.println("Customers have been sorted.");
					break;

				case 5:
					try {
						System.out.println("\n--------------------------------------------------------");
						System.out.println("\\                  You have chosen to                  /");
						System.out.println("/                DISPLAY CUSTOMER SUMMARY              \\");
						System.out.println("--------------------------------------------------------\n");

						System.out.println("Would you like to display all accounts or only one?: ");
						System.out.println("\n1. View ALL (name, sin) \n2. View specific account");
						System.out.print("\nSELECTION: ");
						int choose = scan.nextInt();
						System.out.println("");

						if (choose==1) {
							Customer.displayAll();
						}
						else if(choose==2) {
							System.out.println("Please enter the following information to view summary:");
							scan.nextLine();
							System.out.print("FIRST NAME: ");
							firstName = scan.nextLine();
							System.out.print("LAST NAME: ");
							lastName = scan.nextLine();
							System.out.print("SIN: ");
							sin = scan.nextInt();
							Customer.displayCustomerSummary(firstName,lastName,sin);
						}
						else {
							System.out.println("Option does not exist. Going back to main menu...");
						}
					}
					catch (InputMismatchException ex) {
						System.out.println("Invalid entry.");
						error = true;
					}

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
						error=true;

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
					catch (InputMismatchException ex) {
						System.out.println("Invalid entry.");
						error = true;
						break;
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
		System.out.println("THANK YOU FOR USING VP BANK!");
	}
}

