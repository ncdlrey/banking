import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SavingAccount extends Account{


	static String activity;

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
		if (customer.get(6).equals("0")) {
			System.out.println("Savings account already exists.");
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



	}
}