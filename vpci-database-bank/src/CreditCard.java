import java.io.*;
import java.util.ArrayList;

public class CreditCard extends Account{
	
	static String activity;


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
		
		// Check if they already have a credit card
		if (customer.get(8).equals("0")) {
			System.out.println("Credit card already issued.");
		}

		else if(2022-super.getBirthYear()<=18) {
			System.out.println("You cannot own a credit card. Credit cards are available only to customers over 18.");
		}

		else {
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
	}
	

}
