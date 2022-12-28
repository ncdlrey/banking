import java.io.*;
import java.util.*;

public class Account {
	public static String lastName;
	public static String firstName;
	public static int sin;
	private static int birthYear;
	public static int birthMonth;
	public static int birthDay;
	private static double savingAccountBalance;
	public static double chequingAccountBalance;
	private static double creditCardBalance;

	static boolean found = false;
	static BufferedReader buffer;
	static BufferedWriter output;

	private static String line;

	static ArrayList<ArrayList<String>> allBankActivity = new ArrayList<ArrayList<String>>();

	// Constructor
	public Account (String lName, String fName, int userSin, int birthY, int birthM, int birthD, double savAccBal, double cheqAccBal, double credCardBal) {
		lastName = lName;
		firstName = fName;
		sin = userSin;
		birthYear = birthY;
		birthMonth = birthM;
		birthDay = birthD;
		savingAccountBalance = savAccBal;
		chequingAccountBalance = cheqAccBal;
		creditCardBalance = credCardBal;
	}

	/*****************************************************
	 * GET A CUSTOMER'S INFORMATION ONLY USING THEIR SIN
	 * @param s
	 * @return ArrayList of all the customer's information
	 *****************************************************/
	public static ArrayList<String> getCustomerWithSin(int s) {
		ArrayList<String> customer = new ArrayList<String>();

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			ArrayList<String> textFileContents = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}	

			int index = textFileContents.indexOf(Integer.toString(s));

			// Add info before sin
			for (int i =index-2; i<index;i++) {
				customer.add(textFileContents.get(i));
			}
			// Add info after sin
			for (int i =index; i<index+7;i++) {
				customer.add(textFileContents.get(i));
			}
		}

		catch(IOException e) {
			System.out.println("Cannot open file.");
		}

		// set all variables based on array with text file contents
		lastName = customer.get(0);
		firstName = customer.get(1);
		sin = Integer.parseInt(customer.get(2));
		birthYear = Integer.parseInt(customer.get(3));
		birthMonth = Integer.parseInt(customer.get(4));
		birthDay = Integer.parseInt(customer.get(5));
		if (customer.get(6).equals("none"))
			savingAccountBalance = 0;
		else {
			savingAccountBalance = Double.parseDouble(customer.get(6));
		}
		if (customer.get(7).equals("none"))
			chequingAccountBalance = 0;
		else {
			chequingAccountBalance = Double.parseDouble(customer.get(7));
		}
		if (customer.get(8).equals("none"))
			creditCardBalance = 0;
		else {
			creditCardBalance = Double.parseDouble(customer.get(8));
		}
		return customer;
	}

	/************************************************
	 * GET CUSTOMER WITH FIRST AND LAST NAME
	 * @param lN
	 * @param fN
	 * @return ArrayList of all the customer's info
	 ************************************************/
	public static ArrayList<String> getCustomerWithName(String lN, String fN) {
		ArrayList<String> customer = new ArrayList<String>();

		int index = -1;
		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			ArrayList<String> textFileContents = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}

			for(int i=0; i<textFileContents.size();i++) {
				if (textFileContents.get(i).equalsIgnoreCase(lN) && textFileContents.get(i+1).equalsIgnoreCase(fN)) {
					index = i; // Save index if full name matches the input
				}
			}

			for (int i =index; i<index+10;i++) {
				customer.add(textFileContents.get(i));
			}

		}
		catch (IOException e) {
			System.out.println("Issue opening file.");
		}

		// set all variables based on array with text file contents
		lastName = customer.get(0);
		firstName = customer.get(1);
		sin = Integer.parseInt(customer.get(2));
		birthYear = Integer.parseInt(customer.get(3));
		birthMonth = Integer.parseInt(customer.get(4));
		birthDay = Integer.parseInt(customer.get(5));
		if (customer.get(6).equals("none"))
			savingAccountBalance = 0;
		else {
			savingAccountBalance = Double.parseDouble(customer.get(6));
		}
		if (customer.get(7).equals("none"))
			chequingAccountBalance = 0;
		else {
			chequingAccountBalance = Double.parseDouble(customer.get(7));
		}
		if (customer.get(8).equals("none"))
			creditCardBalance = 0;
		else {
			creditCardBalance = Double.parseDouble(customer.get(8));
		}
		return customer;
	}


	/**************************************************
	 * PRINT OUT EVERY ACCOUNT STORED ON THE TEXT FILE
	 **************************************************/
	public static ArrayList<String> getAllAccounts(){

		ArrayList<String> textFileContents = new ArrayList<String>();

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}
		}
		catch(IOException e) {
			System.out.println("Can't read file.");
		}
		return textFileContents;
	}

	// GET BIRTH YEAR
	public static int getBirthYear() {
		return birthYear;
	}

	// GET CUSTOMER'S FULL NAME
	public static String getName() {
		return firstName + " " + lastName;
	}

	// GET CREDIT CARD BALANCE
	public static double getCreditCardBalance() {	
		return creditCardBalance;
	}

	/***********************************************
	 * ADD IN ACTIVITY/HISTORY FOR ONE CUSTOMER
	 * @param lN
	 * @param fN
	 * @param activity
	 ***********************************************/
	public static void accountActivity(String lN, String fN, String activity) {

		boolean historyExists = false;

		for (int i = 0;i<allBankActivity.size();i++) {

			if (allBankActivity.get(i).get(0).equalsIgnoreCase(lN) && allBankActivity.get(i).get(1).equalsIgnoreCase(fN)) {
				historyExists = true;
				allBankActivity.get(i).add(activity);
			}
		}

		if (historyExists == false) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(lN);
			temp.add(fN);
			temp.add(activity);
			allBankActivity.add(temp);
		}
	}

	/*****************************************************
	 * RETRIEVE ACCOUNT'S RECENT HISTORY/ACTIVITY
	 * @param lN
	 * @param fN
	 * @param uS
	 */
	public void getActivity(String lN, String fN, int uS) {	
		// if user found with sin
		if (uS!=0) {
			lN = Account.getCustomerWithSin(uS).get(0);
			fN = Account.getCustomerWithSin(uS).get(1);
		}

		boolean historyExists = false;

		int accIndex = -1;
		for (int i = 0; i<allBankActivity.size();i++) {
			if (allBankActivity.get(i).get(0).equalsIgnoreCase(lN) && allBankActivity.get(i).get(1).equalsIgnoreCase(fN)) {
				// history exists
				historyExists = true;

				accIndex = i;
				System.out.println(allBankActivity.get(accIndex).get(1) + " " + allBankActivity.get(accIndex).get(0) + "'S ACCOUNT HISTORY: ");
				for (int j =2;j<allBankActivity.get(accIndex).size();j++) {
					System.out.println(allBankActivity.get(accIndex).get(j));
				}	
			}
		}

		if (!historyExists) {
			System.out.println("No recent activity has been recorded.");
		}
	}

	/****************************************************
	 * RETRIEVE A SUMMAR OF THE CUSTOMER'S INFORMATION
	 * @param l
	 * @param f
	 * @param s
	 ****************************************************/
	public void getSummary(String l, String f, int s) {

		ArrayList<String> customer = new ArrayList<String>();

		// Check if account was found by sin or name
		if (sin == 0) { // Found by names
			customer = Account.getCustomerWithName(l, f);
		}

		else { // Found by sin
			customer = Account.getCustomerWithSin(sin);
		}

		System.out.println("*******************************************************");
		System.out.println("CUSTOMER SUMMARY FOR: " + customer.get(1).toUpperCase() + " " + customer.get(0).toUpperCase());
		System.out.println("--------");
		System.out.println("LAST NAME: " + customer.get(0));
		System.out.println("FIRST NAME: " + customer.get(1));
		System.out.println("SIN: " + customer.get(2));
		System.out.println("BIRTHDAY (YYYY/MM/DD): " + customer.get(3) + "/" + customer.get(4) + "/" + customer.get(5));
		System.out.println("SAVING ACCOUNT BALANCE: " + customer.get(6));
		System.out.println("CHEQUING ACCOUNT BALANCE: " + customer.get(7));
		System.out.println("CREDIT CARD BALANCE: " + customer.get(8));
		System.out.println("*******************************************************");
	}
}
