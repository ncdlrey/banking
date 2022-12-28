import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Customer {


	private String lastName;
	private String firstName;
	private int sin;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private double savingAccountBalance;
	private double chequingAccountBalance;
	private double creditCardBalance;

	static boolean found = false;



	static BufferedReader buffer;
	static BufferedWriter output;

	private static String line;

	// Constructor
	public Customer (String lName, String fName, int userSin, int birthY, int birthM, int birthD, double savAccBal, double cheqAccBal, double credCardBal) {
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


	/*********************************
	 * ADD AN ACCOUNT TO THE DATABASE
	 * 
	 * @param lastName
	 * @param firstName
	 * @param sin
	 * @param birthYear
	 * @param birthMonth
	 * @param birthDay
	 * @param savingAccountBalance
	 * @param chequingAccountBalance
	 * @param creditCardBalance
	 *********************************/
	public static void addAccount(String lastName, String firstName, int sin, int birthYear, int birthMonth, int birthDay, double savingAccountBalance, double chequingAccountBalance, double creditCardBalance) {

		boolean exists = false;

		try {
			// Verify that sin doesn't already exist
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			while ((line = buffer.readLine())!=null){
				if (line.equals(String.valueOf(sin))) {
					exists = true;
				}
			}

			if (exists){
				System.out.println("\nAn account with the same SIN exists already.");
			}

			else {
				output = new BufferedWriter(new FileWriter("allUsers.txt", true)); 
				output.write(lastName.toUpperCase());									 			 
				output.newLine();
				output.write(firstName.toUpperCase());									 			 
				output.newLine();
				output.write(Integer.toString(sin));									 			
				output.newLine();
				output.write(Integer.toString(birthYear));									 		
				output.newLine();
				output.write(Integer.toString(birthMonth));									 		
				output.newLine();
				output.write(Integer.toString(birthDay));									 		
				output.newLine();


				// Default savings, chequing, and credit balance is zero (none/no account)
				output.write("none");									 		
				output.newLine();
				output.write("none");									 		
				output.newLine();
				output.write("none");		
				output.newLine();
				output.newLine(); // line break 
				output.close();

				System.out.println("\n       + ACCOUNT SUCESSFULLY ADDED! + \n");

			}
		}
		catch (IOException e) {
			System.out.println("Issue opening file.");
		}
	}


	/**********************************       
	 * DELETE ACCOUNT WITH NAME
	 * 
	 *  @param lastName
	 *  @param firstName
	 *     
	 * **********************************/
	public static void deleteWithName(String lastName, String firstName) {

		int index = 0;

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			ArrayList<String> list = new ArrayList<String>();

			// Put all contents of text file into one array list
			while ((line = buffer.readLine())!=null){
				list.add(line);
			}


			for(int i=0; i<list.size();i++) {
				if (list.get(i).equalsIgnoreCase(lastName) && list.get(i+1).equalsIgnoreCase(firstName)) {
					index = i; // Save index if full name matches the input
				}
			}

			// Remove entire account in one go
			for (int i = index+9; i>=index;i--) {
				list.remove(i);
			}

			// Overwrite the text file with modified array list
			output = new BufferedWriter(new FileWriter("allUsers.txt")); 
			for (int i = 0; i<list.size();i++) {
				output.write(list.get(i));
				output.newLine();

			}

			output.close();

		}
		catch(IOException e) {
			System.out.println("Cannot read file.");
		}
	}

	/**********************************       
	 * DELETE ACCOUNT WITH SIN
	 * 
	 *  @param sin
	 *     
	 * **********************************/
	public static void deleteWithSin (int sin) {

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			// Put all contents of text file into one array list
			ArrayList<String> list = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				list.add(line);
			}

			int index = list.indexOf(Integer.toString(sin));

			// Remove year, birth month, birth day, saving, chequing, credit + extra line break
			for (int i = index+7; i>index;i--) {
				list.remove(i);
			}

			// Remove sin, first name, and last name
			for (int i = index; i>index-3;i--) {
				list.remove(i);
			}

			// Overwrite text file
			output = new BufferedWriter(new FileWriter("allUsers.txt")); 
			for (int i = 0; i<list.size();i++) {
				output.write(list.get(i));
				output.newLine();
			}
			output.close();
		}
		catch(IOException e) {
			System.out.println("Cannot read file.");
		} 
	}


	/***************************************************************************************************************
	 * FIND AN ACCOUNT WITH LAST NAME, FIRST NAME
	 * 
	 * @TODO: ask if she wants the input in the format: "last name, first name" or if we can prompt them separately
	 * 
	 * @param lastName
	 * @param firstName
	 * @return 'found' as true if account exists within the database
	 ****************************************************************************************************************/
	public static boolean findWithName(String lastName, String firstName) {
		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			ArrayList <String> list = new ArrayList<String>();

			while ((line = buffer.readLine())!=null){
				list.add(line);
			}
			for(int i=0; i<list.size();i++) {
				if (list.get(i).equalsIgnoreCase(lastName) && list.get(i+1).equalsIgnoreCase(firstName)) {
					found = true;
				}
			}
		}
		catch(IOException e) {
			System.out.println("Cannot read file.");} 
		return found;
	}


	/****************************************************************
	 * FIND AN ACCOUNT WITH SIN	
	 * 
	 * @param sin
	 * @return 'found' as true if account exists within the database
	 * 
	 ****************************************************************/
	public static boolean findWithSin(int sin) {

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));


			ArrayList<String> list = new ArrayList<String>();


			// Put all contents of text file into one array list
			while ((line = buffer.readLine())!=null){
				list.add(line);
			}

			int index = list.indexOf(Integer.toString(sin));

			if (index!=-1)
				found = true;
		}
		catch(IOException e) {
			System.out.println("Cannot read file.");
		}
		return found; 
	}


	/********************************************
	 * SORT THE ACCOUNTS BASED ON LAST NAME
	 * @note Function will begin to sort by first name if the last names are the same.
	 * 
	 ********************************************/
	public static void sortByLastName() {

		int i, j;
		ArrayList<String> temp;

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			ArrayList<String> textFileContents = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}

			// Put all contents of first arrayList into an arrayList of arrayLists
			int customers = textFileContents.size()/10;
			ArrayList<ArrayList<String>> arraytOfAllArrays = new ArrayList<ArrayList<String>>();
			int m = 0; // Helps to keep spot in textFileCOntents arrayList
			for (i = 0; i<customers;i++) {
				arraytOfAllArrays.add(new ArrayList<String>()); //Add new arrayList for every new customer
				for (j=0;j<10;j++) {
					arraytOfAllArrays.get(i).add(textFileContents.get(j+m));}
				m+=10; // Makes sure to add information from different account each time
			}

			// Bubble sort
			for (i = 0; i<arraytOfAllArrays.size()-1;i++) {
				for (j = 0; j<arraytOfAllArrays.size()-1-i;j++) {

					// Swaps based on last name
					if (arraytOfAllArrays.get(j).get(0).compareToIgnoreCase(arraytOfAllArrays.get(j+1).get(0))>0) {
						//Swap
						temp = arraytOfAllArrays.get(j);
						arraytOfAllArrays.set(j, arraytOfAllArrays.get(j+1));
						arraytOfAllArrays.set(j+1, temp);
					}
					// If multiple people have the same last name, it will sort based on the first name
					else if (arraytOfAllArrays.get(j).get(0).compareToIgnoreCase(arraytOfAllArrays.get(j+1).get(0))==0
							&& arraytOfAllArrays.get(j).get(1).compareToIgnoreCase(arraytOfAllArrays.get(j+1).get(1))>0) {
						//Swap
						temp = arraytOfAllArrays.get(j);
						arraytOfAllArrays.set(j, arraytOfAllArrays.get(j+1));
						arraytOfAllArrays.set(j+1, temp);
					}
				}
			}
			// Overwrite the text file
			output = new BufferedWriter(new FileWriter("allUsers.txt")); 
			for (i = 0; i<arraytOfAllArrays.size();i++) {
				for(j = 0 ;j<10;j++) {
					output.write(arraytOfAllArrays.get(i).get(j));
					output.newLine();
				}
			}
			output.close();
		}
		catch (IOException e) {
			System.out.println("Cannot read file.");
		}
	}


	/********************************************
	 * SORT THE ACCOUNTS BASED ON FIRST NAME
	 ********************************************/
	public static void sortByFirstName() {
		int i, j;
		ArrayList<String> temp;

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));
			ArrayList<String> textFileContents = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}

			// Put all contents of first arrayList into an arrayList of arrayLists
			int customers = textFileContents.size()/10;
			ArrayList<ArrayList<String>> arraytOfAllArrays = new ArrayList<ArrayList<String>>();
			int m = 0; // Helps to keep spot in textFileCOntents arrayList
			for (i = 0; i<customers;i++) {
				arraytOfAllArrays.add(new ArrayList<String>()); //Add new arrayList for every new customer
				for (j=0;j<10;j++) {
					arraytOfAllArrays.get(i).add(textFileContents.get(j+m));}
				m+=10; // Makes sure to add information from different account each time
			}

			// Bubble sort
			for (i = 0; i<arraytOfAllArrays.size()-1;i++) {
				for (j = 0; j<arraytOfAllArrays.size()-1-i;j++) {

					// Swaps based on first name
					if (arraytOfAllArrays.get(j).get(1).compareToIgnoreCase(arraytOfAllArrays.get(j+1).get(1))>0) {
						//Swap
						temp = arraytOfAllArrays.get(j);
						arraytOfAllArrays.set(j, arraytOfAllArrays.get(j+1));
						arraytOfAllArrays.set(j+1, temp);
					}
				}
			}

			// Overwrite the text file
			output = new BufferedWriter(new FileWriter("allUsers.txt")); 
			for (i = 0; i<arraytOfAllArrays.size();i++) {
				for(j = 0 ;j<10;j++) {
					output.write(arraytOfAllArrays.get(i).get(j));
					output.newLine();
				}
			}
			output.close();
		}

		catch (IOException e) {
			System.out.println("Cannot read file.");}
	}


	/********************************************
	 * SORT THE ACCOUNTS BASED ON SIN 
	 ********************************************/
	public static void sortBySin() {
		int i, j;
		ArrayList<String> temp;

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			ArrayList<String> textFileContents = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				textFileContents.add(line);
			}

			// Put all contents of first arrayList into an arrayList of arrayLists
			int customers = textFileContents.size()/10;
			ArrayList<ArrayList<String>> arraytOfAllArrays = new ArrayList<ArrayList<String>>();
			int m = 0; // Helps to keep spot in textFileCOntents arrayList
			for (i = 0; i<customers;i++) {
				arraytOfAllArrays.add(new ArrayList<String>()); //Add new arrayList for every new customer
				for (j=0;j<10;j++) {
					arraytOfAllArrays.get(i).add(textFileContents.get(j+m));}
				m+=10; // Makes sure to add information from different account each time
			}

			// Bubble sort
			for (i = 0; i<arraytOfAllArrays.size()-1;i++) {
				for (j = 0; j<arraytOfAllArrays.size()-1-i;j++) {

					// Swaps based on SIN
					if (arraytOfAllArrays.get(j).get(2).compareToIgnoreCase(arraytOfAllArrays.get(j+1).get(2))>0) {
						//Swap
						temp = arraytOfAllArrays.get(j);
						arraytOfAllArrays.set(j, arraytOfAllArrays.get(j+1));
						arraytOfAllArrays.set(j+1, temp);
					}
				}
			}

			// Overwrite the text file
			output = new BufferedWriter(new FileWriter("allUsers.txt")); 
			for (i = 0; i<arraytOfAllArrays.size();i++) {
				for(j = 0 ;j<10;j++) {
					output.write(arraytOfAllArrays.get(i).get(j));
					output.newLine();
				}
			}
			output.close();
		}

		catch (IOException e) {
			System.out.println("Cannot read file.");
		}
	}


	/********************************************
	 * DISPLAY CUSTOMER SUMMARY
	 * @param firstName
	 * @param lastName
	 * @param sin
	 ********************************************/
	public static void displayCustomerSummary (String f, String l, int s) {

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			// Put all contents of text file into one array list
			ArrayList<String> list = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				list.add(line);
			}

			int indexOfSin = list.indexOf(Integer.toString(s));
			int indexOfCustomerStart = indexOfSin-2;

			// Check if credentials exist 
			if (indexOfSin != -1 && (list.indexOf(f.toUpperCase())!=-1) && (list.indexOf(l.toUpperCase())!=-1)) {
				if (list.get(indexOfSin-1).equalsIgnoreCase(f)&&list.get(indexOfSin-2).equalsIgnoreCase(l)) {

					System.out.println("\nCUSTOMER SUMMARY FOR: " + f.toUpperCase() + " " + l.toUpperCase());
					System.out.println("--------");

					System.out.println("LAST NAME: " + list.get(indexOfCustomerStart+0));
					System.out.println("FIRST NAME: " + list.get(indexOfCustomerStart+1));
					System.out.println("SIN: " + list.get(indexOfCustomerStart+2));
					System.out.println("BIRTHDAY (YYYY/MM/DD): " + list.get(indexOfCustomerStart+3) + "/" + list.get(indexOfCustomerStart+4) + "/" + list.get(indexOfCustomerStart+5));
					System.out.println("SAVING ACCOUNT BALANCE: " + list.get(indexOfCustomerStart+6));
					System.out.println("CHEQUING ACCOUNT BALANCE: " + list.get(indexOfCustomerStart+7));
					System.out.println("CREDIT CARD BALANCE: " + list.get(indexOfCustomerStart+8));


				}
			}

			else {
				System.out.println("Account could not be found.");
			}
		}
		catch(IOException e) {
			System.out.println("Cannot read file.");
		}
	}


	public static void displayAll() {
		System.out.println("ALL CUSTOMERS REGISTERED: ");

		try {
			buffer = new BufferedReader(new FileReader("allUsers.txt"));

			// Put all contents of text file into one array list
			ArrayList<String> list = new ArrayList<String>();
			while ((line = buffer.readLine())!=null){
				list.add(line);

			}

			int m=0;
			for (int i=0;i<list.size()/10;i++) {
				System.out.println("NAME: " + list.get(m+1) + " " + list.get(m) + ", SIN: " + list.get(m+2));
				m+=10;
			}

		}
		catch(IOException e) {
			System.out.println("Cannot read file.");
		}
	}





}

