import java.io.*;
import java.util.*;

public class ChequingAccount extends Account{

	String activity;


	public ChequingAccount(String lName, String fName, int userSin, int birthY, int birthM, int birthD,
			double savAccBal, double cheqAccBal, double credCardBal) {
		super(lName, fName, userSin, birthY, birthM, birthD, savAccBal, cheqAccBal, credCardBal);
	}


	public void addAccount(String l, String f, int sin) {

		int index = -1;


		ArrayList <String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if(sin==0) { // account found with names
			customer = super.getCustomerWithName(l, f);
		}
		else {
			customer = super.getCustomerWithSin(sin);
		}

		// Check if they already have a chequing account
		if (customer.get(7).equals("0")) {
			System.out.println("Savings account already exists.");
		}

		// check if they meet minimum age requirement 
		else if(2022-super.getBirthYear()<=18) {
			System.out.println("You cannot own a chequing account. Chequing accounts are available only to customers over 18.");
		}

		else { // if they don't have an existing chequing account
			try {

				ArrayList<String> allAccounts = new ArrayList<String>();
				allAccounts = super.getAllAccounts();

				for(int i=0; i<allAccounts.size();i++) {
					if (allAccounts.get(i).equalsIgnoreCase(customer.get(0)) && allAccounts.get(i+1).equalsIgnoreCase(customer.get(1))) {
						index = i; // Save index if full name matches
					}
				}

				for (int i =0;i<allAccounts.size();i++) {
					if (i == index+7) {
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

				// Update account history
				activity = "Chequing account opened";
				accountActivity(customer.get(0), customer.get(1), activity);
				System.out.println("A SAVINGS ACCOUNT has been OPENED for " + super.getName());

			}

			catch (IOException e) {
				System.out.println("Issue writing in file.");
			}

		}



	}
}
