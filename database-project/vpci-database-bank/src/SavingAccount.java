import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SavingAccount extends Account{


	static String activity;
	static Scanner scan = new Scanner (System.in);


	public SavingAccount(String lName, String fName, int userSin, int birthY, int birthM, int birthD, double savAccBal,
			double cheqAccBal, double credCardBal) {
		super(lName, fName, userSin, birthY, birthM, birthD, savAccBal, cheqAccBal, credCardBal);
	}

	/**
	 * ADD SAVINGS ACCOUNT BASED ON SIN OR NAME
	 * @param l
	 * @param f
	 * @param sin
	 */
	public void addAccount(String l, String f, int sin) {
		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they already have a savings account
		if (customer.get(6).equals("none")) { // customer doesn't have savings account
			try {

				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches
					}
				}

				for (int i =0;i<allAccounts.size();i++) {
					if (i == index+6) {
						allAccounts.set(i, "0");
					}
				}

				output = new BufferedWriter(new FileWriter("allUsers.txt")); 

				// In text file, update 'none' to 0.
				for (int i = 0; i<allAccounts.size();i++) {
					output.write(allAccounts.get(i));
					output.newLine();
				}
				output.close();

				activity = "Savings account opened";
				accountActivity(customer.get(0), customer.get(1), activity);
				System.out.println("A SAVINGS ACCOUNT has been OPENED for " + super.getName());
			}

			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}

		else {
			System.out.println("Savings account already exists.");
		}
	}

	/***********************************************
	 * DEPOSIT MONEY INTO SAVINGS ACCOUNT
	 * @param l
	 * @param f
	 * @param sin
	 **********************************************/
	public void deposit(String l, String f, int sin) {

		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they even have a savings account
		if (customer.get(6).equals("none")) { // If customer doesn't have account
			System.out.println("Savings account does not exist.");
		}

		else { // If customer DOES have account
			try {

				// Ask how much user would like to deposit
				try {
					double deposit;
					System.out.print("How much would you like to deposit: $");
					deposit = scan.nextDouble();

					while(deposit<0) {
						System.out.print("Value must be positive. Re-enter: $");
						deposit = scan.nextDouble();
					}

					ArrayList<String> allAccounts = new ArrayList<String>();
					allAccounts = super.getAllAccounts();

					for(int i=0; i<allAccounts.size();i++) {
						if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
							index = i; // Save index if full name matches
						}
					}

					double newSavingsValue = deposit + Double.parseDouble(allAccounts.get(index+6));

					allAccounts.set(index+6, Double.toString(newSavingsValue));

					output = new BufferedWriter(new FileWriter("allUsers.txt")); 

					// In text file, update savings balance
					for (int i = 0; i<allAccounts.size();i++) {
						output.write(allAccounts.get(i));
						output.newLine();
					}
					output.close();

					activity = "$" + deposit + " deposited into SAVINGS ACCOUNT. New SAVINGS BALANCE: $" + allAccounts.get(index+6);
					accountActivity(customer.get(0), customer.get(1), activity);
					System.out.println("$" + deposit + " HAS BEEN DEPOSITED INTO " + super.getName() + "'S SAVING ACCOUNT");
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid entry.");
				}
			}
			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}	
	}


	/*****************************************
	 * WITHDRAW MONEY FROM SAVINGS ACCOUNT
	 * @param l
	 * @param f
	 * @param sin
	 *****************************************/
	public void withdraw(String l, String f, int sin) {

		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they even have a savings account
		if (customer.get(6).equals("none")) { // If customer doesn't have account
			System.out.println("Savings account does not exist.");
		}

		else if (customer.get(6).equals("0")) { // Customer cannot withdraw without any current balance
			System.out.println("No money exists in savings account to withdraw.");
		}

		else { // If customer DOES have account and it's not empty
			try {

				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches
					}
				}

				// Ask how much user would like to withdraw
				try {
					double withdraw;
					System.out.print("How much would you like to withdraw: $");
					withdraw = scan.nextDouble();

					while(withdraw<0) {
						System.out.print("Value must be positive. Re-enter: $");
						withdraw = scan.nextDouble();
					}

					while (withdraw>Double.parseDouble(allAccounts.get(index+6))) {
						System.out.println("You cannot withdraw more than the current balance ($" + Double.parseDouble(allAccounts.get(index+6)) + ")");
						System.out.print("Re-enter a smaller value: ");
						withdraw = scan.nextDouble();
					}

					// Calculate new value to store as savings balance after withdrawal
					double newSavingsValue = Double.parseDouble(allAccounts.get(index+6)) - withdraw;
					// Update the arrayList with new savings balance
					allAccounts.set(index+6, Double.toString(newSavingsValue));

					// In text file, update savings balance
					output = new BufferedWriter(new FileWriter("allUsers.txt")); 
					for (int i = 0; i<allAccounts.size();i++) {
						output.write(allAccounts.get(i));
						output.newLine();

					}
					output.close();

					activity = "$" + withdraw + " withdrawn from SAVINGS ACCOUNT. New SAVINGS BALANCE: $" + allAccounts.get(index+6);
					accountActivity(customer.get(0), customer.get(1), activity);
					System.out.println("$" + withdraw + " HAS BEEN WITHDRAWN FROM " + super.getName() + "'S SAVING ACCOUNT");
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid entry.");
				}
			}

			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}	
	}

	/*********************************************
	 * TRANSFER MONEY FROM SAVINGS TO CHEQUING
	 * @param l
	 * @param f
	 * @param sin
	 **********************************************/
	public void transfer(String l, String f, int sin) {

		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they even have a savings account
		if (customer.get(6).equals("none")) { // If customer doesn't have account
			System.out.println("Savings account does not exist to transfer money from.");
		}
		// Check if they even have a chequing account
		if (customer.get(7).equals("none")) { // If customer doesn't have account
			System.out.println("Chequing account does not exist to transfer money into.");
		}
		else if (customer.get(6).equals("0")) { // If customer doesn't have money
			System.out.println("No money available to transfer.");
		}
		else {
			// transfer funds
			try {
				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches
					}
				}

				// Ask how much user would like to transfer
				try {
					double transfer;
					System.out.print("How much would you like to transfer: $");
					transfer = scan.nextDouble();

					while(transfer<0) {
						System.out.print("Value must be positive. Re-enter: $");
						transfer = scan.nextDouble();
					}

					while (transfer>Double.parseDouble(allAccounts.get(index+6))) {
						System.out.println("You cannot transfer more than the current balance ($" + Double.parseDouble(allAccounts.get(index+6)) + ")");
						System.out.print("Re-enter a smaller value: ");
						transfer = scan.nextDouble();
					}

					// Calculate new value to store as savings & chequing balance after transfer
					double newSavingsValue = Double.parseDouble(allAccounts.get(index+6)) - transfer;
					double newChequingValue = Double.parseDouble(allAccounts.get(index+7)) + transfer;
					// Update the arrayList with new savings & chequing balance
					allAccounts.set(index+6, Double.toString(newSavingsValue));
					allAccounts.set(index+7, Double.toString(newChequingValue));


					// In text file, update savings balance
					output = new BufferedWriter(new FileWriter("allUsers.txt")); 
					for (int i = 0; i<allAccounts.size();i++) {
						output.write(allAccounts.get(i));
						output.newLine();
					}
					output.close();

					activity = "$" + transfer + " TRANSFERED from SAVINGS ACCOUNT to CHEQUING ACCOUNT. New SAVINGS BALANCE: $" + allAccounts.get(index+6);
					accountActivity(customer.get(0), customer.get(1), activity);
					System.out.println("$" + transfer + " HAS BEEN transfered FROM " + super.getName() + "'S SAVING ACCOUNT to CHEQUING ACCOUNT.");
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid entry.");
				}
			}

			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}
	}

	/****************************************
	 * CANCEL A SAVINGS ACCOUNT
	 * @param l
	 * @param f
	 * @param sin
	 ****************************************/
	public void cancel(String l, String f, int sin) {

		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they even have a savings account
		if (customer.get(6).equals("none")) { // If customer doesn't have account
			System.out.println("Savings account does not exist.");
		}
		else {
			try {

				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches
					}
				}

				// Update the arrayList with new savings deletion
				allAccounts.set(index+6, "none");


				// In text file, update savings balance
				output = new BufferedWriter(new FileWriter("allUsers.txt")); 
				for (int i = 0; i<allAccounts.size();i++) {
					output.write(allAccounts.get(i));
					output.newLine();
				}

				output.close();

				activity = "SAVINGS ACCOUNT CANCELLED";
				accountActivity(customer.get(0), customer.get(1), activity);
				System.out.println(super.getName() + "'S SAVING ACCOUNT CANCELLED.");
			}
			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}
	}
}