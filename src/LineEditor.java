

import java.util.*; // Used for Scanner
import java.io.*; // Used for File, FileWriter, BufferedWriter


class Line{	// Line stores the String of plain text for a single line of text and provides different operations to manipulate that String
	
	private String Line;	// Where the line of plain text is stored
	private int lineLen;	// Measures length of line
	private String stringBuff;	// stores substrings of a line when copying 						
	
	Line prev;	// Since Line objects will also be nodes in our Document, we will provide Prev and Next tools to link the list
	Line next;
	
	Scanner sc = new Scanner(System.in); // This Scanner will be used for all operations that require input in this file
	
	Line (String s){ // Constructor for a Line object. has implementation in the case the s == null
		if (s != null){
			Line = s;
			lineLen = Line.length();				
		}
		else{
			Line = ""; // A default Line will be a blank String with a line length: 0
			lineLen = 0;
		}
		
	}

	private void insert(String s, int index){ // Used to add more text into a String

		if (lineLen == 0){ // Special case if line is empty: change the line to the input String and return
			Line = s;
			lineLen = Line.length(); // Always change line length after insertions and deletions
			return;
		}
		Line = Line.substring(0,index) + s + Line.substring(index); // If String is not empty, insert new String at position of input index
		lineLen = Line.length();
	}
	
	void deleteSubstr(){
		
		// Prompt user to enter two indices for the starting and ending positions of the deletion
		System.out.println("Please enter initial position of substring you want to delete");
		int in1 = intInput(); // intInput validates user input for the indices. More documentation found in the intInput() method
		System.out.println("Please enter final position of substring you want to delete");
		int in2 = intInput();
		showScale(); // Displays the number scale, more documentation found in showScale() method.
		
		// The following block prints a series of carets below the substring which is to be deleted
		for(int i = 0; i < in1; i++){
			System.out.print(" ");
		}
		for(int i = in1; i <= in2; i++){
			System.out.print("^");
		}
		System.out.print("\n");
		
		// The following loop asks the user if they want to delete, and validates if the user input is 'y', 'n', or invalid
		while(true){
			System.out.print("Delete okay? (y/n): ");
			String yesNo = sc.nextLine(); // Save the user input
			
			if (yesNo.equals("y")){
				Line = Line.substring(0, in1) + Line.substring(in2+1); // Manipulate line to be from the beginning to the first input
				// ... then from after the second input to the end
				lineLen -= (in2-in1+1); // Reduce line length accordingly
				break;
			}
			else if (yesNo.equals("n")){ // Quit out of insert if user specifies
				break;
			}
			else{
				System.out.println("Please enter a valid input"); // If input is invalid, print a message and continue looping
			}
			
		}
	}
	
	String showLine(){ // Prints the String that is associated with the current Line object
		return Line;
	}
		
	void copySubstr(){ // Copies a substring to the string buffer
		
		// Obtain starting and ending positions of copy substring from user
		System.out.println("Please enter initial position of substring you want to copy");
		int in1 = intInput();
		System.out.println("Please enter final position of substring you want to copy");
		int in2 = intInput();
		
		showScale();
		
		// Print carets
		for(int i = 0; i < in1; i++){
			System.out.print(" ");
		}
		for(int i = in1; i <= in2; i++){
			System.out.print("^");
		}
		System.out.print("\n");
		
		// Print message to let user know copy was a success
		System.out.println("Copied!");
		
		
		// Assign the desired substring to the string buffer which is copied and pasted from 
		stringBuff = Line.substring(in1, in2);
		
		
	}
	
	void cutSubstr(){ // Copies a substring to the string buffer then deletes that substring from the Line

		// Obtain user input for starting and ending position of cut substring
		System.out.println("Please enter initial position of substring you want to cut");
		int in1 = intInput();
		System.out.println("Please enter final position of substring you want to cut");
		int in2 = intInput();
		
		showScale();
		
		// Print carets
		for(int i = 0; i < in1; i++){
			System.out.print(" ");
		}
		for(int i = in1; i <= in2; i++){
			System.out.print("^");
		}
		System.out.print("\n");
		
		
		// Validate if substring is correct and user wants to cut that substring
		while(true){
			System.out.print("Cut okay? (y/n): ");
			String yesNo = sc.nextLine();
			if (yesNo.equals("y")){
				// If yes, store that substring to the string buff, then reassign Line to a new String without the undesirable substring
				stringBuff = Line.substring(in1, in2+1);
				Line = Line.substring(0, in1) + Line.substring(in2+1);
				lineLen -= (in2-in1+1); // Reduce line length accordingly
				break;
			}
			else if (yesNo.equals("n")){ // Else, quit out of cut operation
				break;
			}
			else{ // Input validation
				System.out.println("Please enter a valid input");
			}
		}
		
		
	}
	
	void paste(){ // Pastes the substring in the string buffer to the Line
		
		showScale();
		
		// Obtain user input for index position to be pasted to
		System.out.println("Please enter position to paste to");
		int in1 = intInput();
		
		System.out.print("\n"); // UI formatting
		
		// Utilize insert function to push plain text from stringBuff into Line
		insert(stringBuff, in1);
		
		showScale();
	}

	void enterNewSubstr(){ // Obtains plain text from user and pushes it into the Line
		
		showScale();
		
		//Obtain user input for position to be entered to
		System.out.println("Please enter position to enter to");
		int in1 = intInput();
		// Prompt user to enter new substring and utilize insert() function to push that text into Line at the desired index, in1
		System.out.print("What would you like to add: ");
		insert(sc.nextLine(), in1);
		
		showScale();
		
	}
	
	private int intInput(){ // User interface that collects a valid integer input from the user that is within the range of the line length
		
		int returnInt = -1;
		
		while (returnInt < 0 || returnInt > lineLen-1){ // returnInt must be within 0 and the length of the line
			System.out.print("desired line position: ");
			
			while(!sc.hasNextInt()){ // Continue looping until user enters an integer input
				System.out.print("\nPlease enter a valid number: ");
				sc.next();
			}
			
			returnInt = sc.nextInt(); // Save the correct integer input after validating
			sc.nextLine(); // clear sc's buffer after taking in the integer input. This ensures that no unwanted
			if (returnInt < 0 || returnInt > lineLen-1){ // If the input value is not in range, a message will be printed and the loop will continue
				System.out.println("\nInput out of range...\n");
			}	
		}
		
		return returnInt;
	}

	void showScale(){ // Prints a 'scale' (a String associated with a specific line object with a stylized index counter above it)
		
		// This foor loop prints the index numbers, 5, 10, 15, ... until it reaches the length of the line
		for (int i=0; i<lineLen-1; i++){
			
			if (i==0 || i == 5){ // From 1-5, the spacing is different, so we have a different conditional
				System.out.print(i);
				continue;
			}
			if (i>0 && i<5){
				System.out.print(" ");
				continue;
			}
			
			if ((i+1)%5 == 0){ // After five, the spacing evens out and we get 2 digit numbers like 10, 15, 20.
				// We print the number to the corresponding index before that number (i.e: 10 is printed to the 9th position)
				// .. because the number is two digits long and needs extra space
				System.out.print(i+1);
			}
			else if (i%5 == 0){
				// We have to compensate for the last conditional by not adding a space on the 5th, 10th, 15th indices
				continue;
			}
			else{ // Every other index will have a space
				System.out.print(" ");
			}
		}
		System.out.print("\n"); // On the next line, we have a series of dashes, crosses, and bars for the scale
		for (int i=0; i<lineLen; i++){
			if (i%5 == 0){
				if (i%2 == 0){
					System.out.print("|"); // Every number divisible by 5 and 2 (i.e: 10, 20, ...) will have a bar
				}
				else{
					System.out.print("+"); // Every number divisible by just 5, not 2 (i.e: 5, 15, ...) will have a cross
				}
			}
			else{
				System.out.print("-"); // Every other number will have a dash
			}
		}
		System.out.print("\n"); // The next line displays the plaintext associated with the current Line object
		System.out.println(showLine());
	}

}

 class Document { // Document stores a Linked List of Line Objects to emulate a document and provides operations on the Linked List


	private int numLines;	// measures how many lines are in the document at any time
	private Line dummy;		// Dummy node which points to the linked list of Lines
	private Line dummy2;	// A second dummy node is placed at the end of the list. When traversing the list, we stop when we reach dummy2
	private Line lineBuff1;	// A dummy node which will point to a buffer which holds a range of lines to be moved around
	private Line lineBuff2;	// the buffer also has a dummy node at the end of the list
	
	Scanner sc = new Scanner(System.in); // Scanner used for user input
	
	Document() { // Constructor creates blank instances of Line for the dummy nodes, sets number of lines to 0, and connects the nodes to each other
		// .. using double links. dummy <---> dummy2 and lineBuff1 <---> lineBuff2
		
		dummy = new Line("");
		dummy2 = new Line("");
		
		dummy.next = dummy2;
		dummy2.prev = dummy;
		
		lineBuff1 = new Line("");
		lineBuff2 = new Line("");
		
		lineBuff1.next = lineBuff2;
		lineBuff2.prev = lineBuff1;
		
		numLines = 0;
																// TODO: Upload text into list if user loads a file
	}
	
	boolean isEmpty(){ // Returns true if the list is empty
		return dummy.next == dummy2;
	}
	
	int numLines(){ // Returns number of lines in linked list
		return numLines;
	}
	
	int intInput(int insertOrNot){ // Prompts user to enter a valid integer input when a line number position is needed
		
		int returnInt = -1; // Initialized to -1 in order to enter the while loop.
		
		while (returnInt < insertOrNot || returnInt > numLines){ // This loop validates user input for in1, makes sure it is in range and is a number
			System.out.print("enter line number: ");
			
			while(!sc.hasNextInt()){ // Inner while loop repeats until user enters a integer input
				System.out.print("\nPlease enter a valid number: ");
				sc.next();
			}
			returnInt = sc.nextInt(); // Save the correct input after validating
			sc.nextLine(); // clear sc's buffer after taking in the integer input. This ensures that no unwanted
			if (returnInt < insertOrNot || returnInt > numLines){ // Print a warning message if input is still out of range
				System.out.println("\nInput out of range...\n");
			}
		}
		
		return returnInt;
	}
	
	String loadFile(String s){ // Loads a file either from command line or from user input. After, all lines are imported into the line editor
			
		String readFileName; // Will be initialized to either the command line argument or the user's input
		
		while(true){
			
			if (s.equals("")){
				System.out.print("\nEnter file name or type 'q' to return to menu: ");
				readFileName = sc.nextLine();	// Name of file to be opened
				System.out.println();	// UI formatting
				if (readFileName.equals("q")){	// Allows for user to quit if they no longer want to load a file
					break;
				}
			}
			else{ // If a command line argument is provided, this block will run and the readFileName variable will be assigned to the passed-in value
				System.out.println("\nLoading document from file. Use 'sa' to view your transferred document.\n");
				readFileName = s;
			}
			try{ // Try used in case file cannot be found or filename is invalid
				File readFile = new File (readFileName);	// Create a File type with the above string
				Scanner fileSC = new Scanner (readFile);	// Scanner traverses the readfile
				
				dummy.next = dummy2;	// Re-initialize the linked list. If there are any Lines in the Document, they will be removed
				dummy2.prev = dummy;
				numLines = 0;
				
				while (fileSC.hasNextLine()){ // Continue looping as long as there are still lines in the file
					Line tempLine = new Line(fileSC.nextLine()); // Store text from file into Line objects
					
					Line temp = dummy; // Traverse to correct position in linked list. The first time the outer while loop runs, this inner loop is
					// .. ignored, but as the Document grows and more Lines are added, we need to traverse to the end of the Document every time we want
					// .. to add another line from the file.
					while (temp.next != dummy2){
						temp = temp.next;
					}
					
					// Link the new Line object accordingly
					tempLine.next = dummy2;
					tempLine.prev = temp;
					temp.next = tempLine;
					dummy2.prev = tempLine;
					numLines++; // Increment numLines
					
				}
				fileSC.close(); // Close Scanner after reading is complete
				break;
			}
			catch (FileNotFoundException e) { // If any error occurs, run this text and repeat the while loop
				s = "";
				System.out.println("That file does not exist... please enter a valid file name or enter 'q' to move to main menu.");
			}
		}
		return readFileName;
	}
	
	void writeToFile(String fileName){ // Writes to a file. The file to be written to is either a new file or the file that was previously loaded

		
		BufferedWriter bw = null; // 
		FileWriter fw = null;
		
		while(true){
			try{
			
				if (fileName == null){ // Acquire file name from user if necessary
					System.out.print("Please enter a file to write to (in this format: 'test' OR 'test.txt'): ");
					fileName = sc.nextLine();
				}
				
				
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
				
				// Traverse through list and write each line to the BufferedWriter and therefore the output file
				Line temp = dummy.next; 
				while(temp != dummy2 ){
					bw.write(temp.showLine());
					
					// This if block adds newlines excluding after the last line
					if (temp.next != dummy2){
						bw.write("\n");
					}
					
					temp = temp.next;
				}
				
				// Print confirmation message
				System.out.println("Document saved to: '" + fileName + "'");
			}
			catch (IOException e){ // Catch block ensures user input is a valid file name
				System.out.println("Please enter a valid file name...");
				fileName = null;
				continue;
			}
			finally {
				// Afterward close the writers and return
				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();
					return;

				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
		}
	}
	
	void showAll(){ // Shows all lines of text in the Document
		if (isEmpty()){ // Print message if document is empty
			System.out.println("This document is empty...");
			return;
		}
		
		// Traverse through list, use a counter to remember the line number for printing purposes, and use the Line method showLine() to
		// .. output the String to the print statement
		Line temp = dummy;
		int ctr = 0;
		while (temp.next != dummy2){
			temp = temp.next;
			ctr++;
			System.out.println(ctr + ": " + temp.showLine());
		}
	}

	void showRange(){
		
		if (isEmpty()){	// Print message if list is empty
			System.out.println("This document is empty...");
			return;
		}
		
		// Use intInput() function to get starting and ending indices of display range from user
		System.out.println("Please enter the line position number that you want to START at ( valid indices: [1," + numLines + "] )\n");
		int in1 = intInput(1);
		System.out.println("\nPlease enter the line position number that you want to END at ( valid indices: [1," + numLines + "] )\n");
		int in2 = intInput(1);	
		
		System.out.println(); 	// UI formatting
		
		Line temp = dummy;		// Use temp to traverse list
		int ctr = 0;			// Count position in list
		
		while (ctr < in1){		// Iterate until position 1
			temp = temp.next;
			ctr++;
		}
		
		while (ctr <= in2){		// Iterate from position 1 to position 2 and print each Line using showLine()
			System.out.println(ctr + ": " + temp.showLine());
			temp = temp.next;
			ctr++;
		}
		
	}
	
	void showLine(){
		
		if (isEmpty()){	// Print message and exit if list is empty
			System.out.println("This document is empty...");
			return;
		}
		
		// Use intInput() function to get index of display Line from user
		System.out.println("Please enter the line position number that you want to display ( valid indices: [1," + numLines + "] )\n");
		int in1 = intInput(1);
		
		System.out.println(); 	// UI formatting
		
		Line temp = dummy;		// Use temp to traverse list
		
		int ctr = 0;			// Count position in list
		while (ctr < in1){		// Iterate until position 1
			temp = temp.next;
			ctr++;
		}
		
		System.out.println(ctr + ": " + temp.showLine()); // After traversing to the correct position, print the desired line
		
	}
	
	void insertLine(int index, Line li){
		// insertLine() contains the full user interface and algorithm for taking in a String, converting it to a Line, and adding it
		// to the doubly linked list: "doc"		
		
		
		Boolean repeat = false; // This boolean records if a user has repeatedly looped through the interface. As a result, subsequent "yes" 
		// .. commands from the user will trigger subsequent lines to be written to. Yes the first time writes to the specified line, the line
		// .. after the second time, the line after that the third time, etc...
		
		int in2 = -1; // in2 is the index that will be printed to. Initialized to -1 as a placeholder value until it is assigned a proper number
		
		// Loops repeatedly until user wants to exit
		while(true){
			
			String in = "y"; // in is the input from the user that states whether or not the user wants to add another line. Initialized to "y"
			// .. so the conditional below runs the first time
			
				System.out.print("type line? (y/n): ");	// checks if user wants to add a line (or another line in loops)
				in = sc.nextLine();	// Prompt user for input, save entire line to clear the scanner's buffer

			
			if (in.equals("y")){	// 'y' will prompt the insert algorithm to begin
				
				if (dummy.next == dummy2){ // Runs if the Document is empty
					
					Line firstLine;
					
						System.out.print("\n1: "); // UI formatting
						firstLine = new Line(sc.nextLine()); // Acquire desired string from user and create new Line object
						System.out.println();	// Cleaner formatting for the user interface

					
					// These assignments link firstLine into the list between the two dummy nodes
					dummy.next = firstLine;	
					dummy2.prev = firstLine;
					firstLine.prev = dummy;
					firstLine.next = dummy2;
					
					// Set in2 to one, because the first line has just been written to. Subsequent "y" commands will write to line 2, 3, etc...
					in2 = 1;
					repeat = true; // repeat also set to true so the algorithm knows it should start automatically entering in line positions
					numLines++; // Increment total number of lines counter
					
					
					continue;
				}
				
				
				
				if (repeat != true){ // User will only be asked for a line position if they have not been looping through the document already
					System.out.println("\nPlease enter the line position number that you want to insert AFTER ( valid indices: [0," + numLines + "] )\n");
					in2 = intInput(0);	// this int is the position at which a new line will be inserted
				}
				

				
				Line temp = dummy;	// Create a temp node to traverse the List
				
				int ctr = 1;	// Start counter to measure which line we are at
				for (int i = 0; i < in2; i++){
					temp = temp.next;	// Repeatedly iterate temp to the next item in the list until we get to the specified index, in2
					ctr++;
				}
				
				Line inputLine;
				
				System.out.print("\n" + ctr + ": ");	// Displays line which user is writing to, using 'ctr'
				inputLine = new Line(sc.nextLine());	// Takes next line of user input and stores it to a new Line object
				System.out.println();	// Formatting for UI
				
				// The following assignments link inputLine into the the List
				inputLine.next = temp.next;
				inputLine.prev = temp;				
				temp.next.prev = inputLine;
				temp.next = inputLine;
					
				in2++;
				repeat = true;
				numLines++;	// Increment total number of lines counter
		
				continue;
				
			}
			else if (in.equals("n")){	// Leave loop if 'n'
				return;
			}
			else{	// Input validation
				System.out.println("\nInvalid user input, please enter 'y' or 'n'\n");
			}
		}
	}
	
	void deleteLine(int index){
		
		// Acquire user input for position if a index isn't supplied from elsewhere
		if (index == -1){
			System.out.println("Please enter the position of the line you want to delete ( valid indices: [1," + numLines + "] )");
			index = intInput(1);
		}
		
		// Traverse list to correct position
		Line temp = dummy;
		int ctr = 0;
		
		while (ctr < index){
			temp = temp.next;
			ctr++;
		}
		
		
		// The following block 'unlinks' the Line by setting the previous and next Line's .next and .prev values to each other
		temp.prev.next = temp.next;
		temp.next.prev = temp.prev;
		numLines--;
		
	}
	
	void deleteRange(int in1, int in2){
		
		// User inputs starting and ending indices of deletion, if indices are not already provided from outside the program 
		if (in1 == -1 && in2 == -1){
			System.out.println("Please enter the starting position of the range you want to delete ( valid indices: [1," + numLines + "] )");
			in1 = intInput(1);
			System.out.println("Please enter the ending position of the range you want to delete ( valid indices: [1," + numLines + "] )");
			in2 = intInput(1);
		}
		
		// numDeletes is the number of positions which will be deleted
		int numDeletes = in2 - in1;
		
		// Repeatedly delete the Line at the starting index until we've looped 'numDeletes' times
		for (int i = 0; i <= numDeletes; i++){
			deleteLine(in1);
		}
		
	}
	
	void copyRange(int in1, int in2){
		
		// Acquire starting and ending indices if not already supplied from outside the function
		if (in1 == -1 && in2 == -1){
			System.out.println("Please enter the starting position of the range you want to copy ( valid indices: [1," + numLines + "] )");
			in1 = intInput(1);
			System.out.println("Please enter the ending position of the range you want to copy ( valid indices: [1," + numLines + "] )");
			in2 = intInput(1);
		}
		
		// Create temp objects and assign them to the start of the Document and line buffer linked lists
		Line temp1 = dummy;
		Line temp2 = lineBuff1;
		int ctr = 0;
		
		// Traverse to start of index copying location
		while (ctr<in1){
			temp1 = temp1.next;
			ctr++;
		}
		
		
		// Continue traversing to ending location
		while (ctr <= in2){
			
			
			// Create and link a new Line object into the line buffer for every Line from in1 to in2 
			Line newLine = new Line(temp1.showLine());
			
			
			newLine.next = lineBuff2;
			newLine.prev = temp2;
			temp2.next = newLine;
			lineBuff2.prev = newLine;
			
			temp2 = temp2.next;
			temp1 = temp1.next;
			ctr++;
		}		
	}
	
	void pasteLines(int index){
		
		// Acquire desired paste index if one isn't already provided from outside function
		if (index == -1){
			System.out.println("Please enter the position which you want the buffer to be pasted after ( valid indices: [0," + numLines + "] )");
			index = intInput(0);
		}
		
		// Create temp node which points to first Line in line buffer
		Line temp = lineBuff1.next;
		
		// Traverse through line buffer
		while (temp != lineBuff2){
	
			Line temp2 = dummy;	// Create a temp node to traverse the Document's linked list
			
			for (int i = 0; i < index; i++){
				temp2 = temp2.next;	// Repeatedly iterate temp to the next item in the list until we get to the specified index
			}
			
			
			// Create and link a new Line object into the Document for every Line in the line buffer
			Line pasteLine = new Line(temp.showLine());
			
			pasteLine.next = temp2.next;
			pasteLine.prev = temp2;
			temp2.next.prev = pasteLine;
			temp2.next = pasteLine;
			numLines++;
			
			
			index++;
			temp = temp.next;
		}		
	}
 	
	void editLine(int index){
		
		// Acquire a line position that the user wants to edit if one isn't already provided from outside the function
		if (index == -1){
			System.out.println("Please enter the position which you want to edit ( valid indices: [1," + numLines + "] )");
			index = intInput(1);
		}
		
		// Traverse Document until we reach the specified line
		Line temp = dummy;
		
		for (int i=0; i<index; i++){
			temp = temp.next;
		} // At this point temp is storing the line that we want to edit
		
		temp.showScale();
		
		// Repeat until user 'quits'
		while(true){
		
			// The next block of code is formatting for the line menu's scale
			
			
			
			// Below are the menu options
			System.out.println("\tShow line:  s");
			System.out.println("\tCopy to string buffer:  c");
			System.out.println("\tCut:  t");
			System.out.println("\tPaste from string buffer:  p");
			System.out.println("\tEnter new substring:  e");
			System.out.println("\tDelete substring:  d");
			System.out.println("\tQuit line:  q");
		
			System.out.print("-> "); // Arrow indicates user should input something
			String in = sc.nextLine(); // Scans the next token of the input
			
			
			// Switch case determines which value the user enters and runs the corresponding method to perform the desired function
			switch(in){
				case "s":
					temp.showScale();
					continue;
				case "t":
					temp.cutSubstr();
					continue;
				case "c":
					temp.copySubstr();
					continue;
				case "p":
					temp.paste();
					continue;
				case "e":
					temp.enterNewSubstr();
					continue;
				case "d":
					temp.deleteSubstr();
					continue;
				case "q":
					System.out.println("\nReturning to main menu");
					return;
			}			
		}	
	}
 } // End of Document Class

//MainMenu class does the following: prints the menu, takes in and interprets user's menu choice, executes the desired function based on input
 public class LineEditor {

	private static void displayMenu(){ // Displays menu options
		System.out.println("   Menu: m           Delete line: dl");
		System.out.println("   Load file: l      Delete range: dr");
		System.out.println("   Show all: sa      Copy range: cr");
		System.out.println("   Show line:  sl    Paste lines:  pl");
		System.out.println("   Show range:  sr   Write to file:  w");
		System.out.println("   New line:  nl     Quit:  q");
		System.out.print("   Edit line:  el    Write and quit:  wq");
	}
	 
	public static void main(String[] args) {

		// Exit's if more than 1 command line arg is entered
		if (args.length > 1){
			System.out.println("Invalid number of command line arguments, exiting...\n");
			return;
		}
		
		// Creates the Document object that all our work will be done on
		Document doc = new Document();
		
		// Save fileName from either command line or user input and pass it into methods that need it
		String fileName = null;
		
		if (args.length == 1){
			doc.loadFile(args[0]);
			fileName = args[0];
		}
		
		displayMenu(); // Displays the menu
		
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in

		
		while(true){
			
			
			System.out.print("\n-> "); // Prompts user to enter something
			String in = reader.nextLine(); // Scans the next token of the input
			System.out.println(); // UI Formatting
			
			
			// The switch case below takes in the user's input and triggers the correct method operation depending on what the input is.
			// .. Most of the methods are located inside the Document class, so we call doc.someClass() to trigger them. Finally, any
			// .. method that takes in -1 or null for a parameter tells the method that user input is needed for key values. Elsewhere in this
			// .. program, method calls are made with important values in those parameter places and the method knows to use those values 
			// .. instead of user input.
			switch(in){
				case "m":
					displayMenu();
					continue;
				case "l":
					fileName = doc.loadFile("");
					continue;
				case "sa":
					doc.showAll();
					continue;
				case "sl":
					doc.showLine();
					continue;
				case "sr":
					doc.showRange();
					continue;
				case "nl":
					doc.insertLine(-1, null);
					continue;
				case "el":
					doc.editLine(-1);
					continue;
				case "dl":
					doc.deleteLine(-1);
					continue;
				case "dr":
					doc.deleteRange(-1,-1);
					continue;
				case "cr":
					doc.copyRange(-1, -1);
					continue;
				case "pl":
					doc.pasteLines(-1);
					continue;
				case "w":
					doc.writeToFile(fileName);
					continue;
				case "q":
					reader.close(); // Close the scanner and exit program
					System.out.println("Powering off...");
					return;
				case "wq":
					doc.writeToFile(fileName);
					System.out.println("Powering off...");
					reader.close();
					return;
					
			}
			
			// If no switch case runs, the following error message is displayed and the loop runs again
			System.out.println("\nInput not recognized, please enter one of the inputs below.");
			
		}
	}
}
