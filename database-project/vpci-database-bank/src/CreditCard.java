import java.io.*;
import java.util.*;

public class CreditCard extends Account{

	static String activity;
	static Scanner scan = new Scanner(System.in);


	public CreditCard(String lName, String fName, int userSin, int birthY, int birthM, int birthD, double savAccBal,
			double cheqAccBal, double credCardBal) {
		super(lName, fName, userSin, birthY, birthM, birthD, savAccBal, cheqAccBal, credCardBal);
	}

	private static String line;

	/**
	 * ISSUE CREDIT CARED BASED ON NAME
	 * @param l
	 * @param f
	 */
	public void issueCreditCard(String l, String f, int sin) {

		ArrayList<String> customer = new ArrayList<String>();
		int index = -1;

		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check first if they are old enough to own a credit card
		if(2022-super.getBirthYear()<=18) {
			System.out.println("You cannot own a credit card. Credit cards are available only to customers over 18.");
		}

		// Check if they already have a credit card
		else if (customer.get(8).equals("none")) { // Customer doesn't have existing credit card
			try {

				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches the input
					}
				}

				for (int i =0;i<allAccounts.size();i++) {
					if (i == index+8) {
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

				activity = "Credit card issued";
				accountActivity(customer.get(0), customer.get(1), activity);
				System.out.println("A CREDIT CARD has been issued for " + super.getName());
			}

			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}

		else {
			System.out.println("Credit card already issued.");
		}
	}

	/*************************************
	 * PROCESS A PURCHASE ON CREDIT CARD
	 * @param l
	 * @param f
	 * @param sin
	 *************************************/
	public void processPurchase(String l, String f, int sin) {

		int index = -1;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they even have a credit card
		if (customer.get(8).equals("none")) { // If customer doesn't have card
			System.out.println("Credit card has not been issued.");
		}

		else { // If customer DOES have account 
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
					double purchaseTotal;
					System.out.print("Purchace total: $");

					purchaseTotal = scan.nextDouble();

					while(purchaseTotal<0) {
						System.out.print("Value must be positive. Re-enter: $");
						purchaseTotal = scan.nextDouble();
					}

					// Calculate new value to store as credit card balance after withdrawal
					double newCreditValue = Double.parseDouble(allAccounts.get(index+8)) - purchaseTotal;

					// Update the arrayList with new credit card balance
					allAccounts.set(index+8, Double.toString(newCreditValue));

					// In text file, update credit card balance
					output = new BufferedWriter(new FileWriter("allUsers.txt")); 
					for (int i = 0; i<allAccounts.size();i++) {
						output.write(allAccounts.get(i));
						output.newLine();

					}
					output.close();

					activity = "$" + purchaseTotal + " used from CREDIT CARD. New CREDIT CARD BALANCE: $" + allAccounts.get(index+8);
					accountActivity(customer.get(0), customer.get(1), activity);
					System.out.println("$" + purchaseTotal + " HAS BEEN USED FROM " + super.getName() + "'S CREDIT CARD");
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

	/**************************************************************************
	 * PROCESS A PAYMENT FOR CREDIT CARD THROUGH SAVINGS OR CHEQUING ACCOUNT
	 * @param l
	 * @param f
	 * @param sin
	 **************************************************************************/
	public void processPayment(String l, String f, int sin) {
		int index = -1;
		double newSavBal;
		double newCheqBal;

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = super.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = super.getCustomerWithSin(sin);
		}

		// Make an array to store all text file contents
		ArrayList<String> allAccounts = new ArrayList<String>();
		allAccounts = super.getAllAccounts();

		for(int i=0; i<allAccounts.size();i++) {
			if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
				index = i; // Save index if full name matches
			}
		}

		// Check if credit card is already payed for
		if (customer.get(8).equals("none")) { // owes nothing
			System.out.println("Credit does not exist.");
		}
		else if (Double.parseDouble(customer.get(8)) == 0) { // owes nothing
			System.out.println("Credit card already payed for.");
		}
		else {
			System.out.println("Would you like to pay from SAVINGS or CHEQUING account?: ");
			System.out.println("\n1.SAVINGS \n2.CHEQUING");
			try {
				int choose = scan.nextInt();
				if (choose==1) {
					if(customer.get(6).equals("0")) {
						System.out.println("Balance in SAVINGS ACCOUNT is $0.00. Customer has insufficient funds.");
					}
					else if(Double.parseDouble(customer.get(6))<Math.abs(Double.parseDouble(customer.get(8))))  {
						System.out.println("Customer has insufficient funds to process payment.");
					}
					else if(customer.get(6).equals("none")) {
						System.out.println("No savings account exists.");
					}
					else {
						// process payment
						newSavBal = Double.parseDouble(customer.get(6)) - (Math.abs(Double.parseDouble(customer.get(8))));

						// Update the arrayList with new savings balance and new credit balance (0)
						allAccounts.set(index+6, Double.toString(newSavBal));
						allAccounts.set(index+8, "0");

						// In text file, update credit card balance
						try {
							output = new BufferedWriter(new FileWriter("allUsers.txt")); 
							for (int i = 0; i<allAccounts.size();i++) {
								output.write(allAccounts.get(i));
								output.newLine();
							}
							output.close();
						}
						catch (IOException e) {
							System.out.println("Cannot write into file.");
						}
						activity = "$" + Math.abs(Double.parseDouble(customer.get(8))) + " used from SAVINGS ACCOUNT to pay off CREDIT CARD. New SAVINGS ACCOUNT BALANCE: $" + allAccounts.get(index+6);
						accountActivity(customer.get(0), customer.get(1), activity);
						System.out.println("$" +  Math.abs(Double.parseDouble(customer.get(8))) + " HAS BEEN USED FROM " + super.getName() + "'S SAVING ACCOUNT to pay off credit card.");
					}
				}
				else if (choose==2) {
					if(customer.get(7).equals("0")) {
						System.out.println("Balance in CHEQUING ACCOUNT is $0.00. Customer has insufficient funds.");
					}
					else if(Double.parseDouble(customer.get(7))<Math.abs(Double.parseDouble(customer.get(8))))  {
						System.out.println("Customer has insufficient funds to process payment.");
					}
					else if(customer.get(7).equals("none")) {
						System.out.println("No chequing account exists.");
					}
					else {
						// process payment
						newCheqBal = Double.parseDouble(customer.get(7)) - (Math.abs(Double.parseDouble(customer.get(8))));

						// Update the arrayList with new savings balance and new credit balance (0)
						allAccounts.set(index+7, Double.toString(newCheqBal));
						allAccounts.set(index+8, "0");

						// In text file, update credit card balance
						try {
							output = new BufferedWriter(new FileWriter("allUsers.txt")); 
							for (int i = 0; i<allAccounts.size();i++) {
								output.write(allAccounts.get(i));
								output.newLine();
							}
							output.close();
						}
						catch (IOException e) {
							System.out.println("Cannot write into file.");
						}
						activity = "$" + Math.abs(Double.parseDouble(customer.get(8))) + " used from CHEQUING ACCOUNT to pay off CREDIT CARD. New CHEQUING ACCOUNT BALANCE: $" + allAccounts.get(index+7);
						accountActivity(customer.get(0), customer.get(1), activity);
						System.out.println("$" + Math.abs(Double.parseDouble(customer.get(8)))  + " HAS BEEN USED FROM " + super.getName() + "'S CHEQUING ACCOUNT to pay off credit card.");
					}
				}
				else {
					System.out.println("Not an option. Returning to SUBMENU...");
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid entry.");
			}
		}
	}


	/*********************************************
	 * CANCEL CREDIT CARD IF BALANCE IS 0
	 * @param l
	 * @param f
	 * @param sin
	 *********************************************/
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

		// Check if they even have a credit card
		if (customer.get(8).equals("none")) { // If customer doesn't have account
			System.out.println("Chequing account does not exist.");
		}
		else if(Double.parseDouble(customer.get(8))<0) { // card with negative balance
			System.out.println("You cannot cancel a card that hasn't been payed off yet.");
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

				// Update the arrayList with new credit card deletion
				allAccounts.set(index+8, "none");


				// Update text file
				output = new BufferedWriter(new FileWriter("allUsers.txt")); 
				for (int i = 0; i<allAccounts.size();i++) {
					output.write(allAccounts.get(i));
					output.newLine();
				}

				output.close();

				activity = "CREDIT CARD CANCELLED";
				accountActivity(customer.get(0), customer.get(1), activity);
				System.out.println(super.getName() + "'S CREDIT CARD CANCELLED.");
			}
			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}
		}		
	}
}
