import java.io.*;
import java.util.*;

public class Account {

	private static String lastName;
	private static String firstName;
	private static int sin;
	private static int birthYear;
	private static int birthMonth;
	private static int birthDay;
	private static double savingAccountBalance;
	private static double chequingAccountBalance;
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

			// print
			for(int i = 0;i<customer.size();i++) {
				System.out.println(customer.get(i));
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
			savingAccountBalance = Integer.parseInt(customer.get(6));
		}
		if (customer.get(7).equals("none"))
			chequingAccountBalance = 0;
		else {
			chequingAccountBalance = Integer.parseInt(customer.get(7));
		}
		if (customer.get(8).equals("none"))
			creditCardBalance = 0;
		else {
			creditCardBalance = Integer.parseInt(customer.get(8));
		}

		return customer;

	}

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
			savingAccountBalance = Integer.parseInt(customer.get(6));
		}
		if (customer.get(7).equals("none"))
			chequingAccountBalance = 0;
		else {
			chequingAccountBalance = Integer.parseInt(customer.get(7));
		}
		if (customer.get(8).equals("none"))
			creditCardBalance = 0;
		else {
			creditCardBalance = Integer.parseInt(customer.get(8));
		}
		return customer;
	}

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

	public static int getBirthYear() {
		return birthYear;
	}

	public static String getName() {
		return firstName + " " + lastName;
	}

	public static double getCreditCardBalance() {	
		return creditCardBalance;
	}

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

		for (int i =0;i<allBankActivity.size();i++) {
			System.out.println(allBankActivity.get(i));
		}	
	}






}
