/*
 Programmer:Cory Hershman
 Class: Data Structures and Algorithms
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/17/2017
 Last Update: 11/16/2017
 Related Files: RedBlackTree.java, Node.java, names.txt
 Description: This class is a standard lookup table that uses an input text file to bring in records to add to the lookup table.
 The user can input command to search for records. There are 3 commands. If the user enters a last name, the program will show all
 records with the same last name. If the user enters a last name with an "!" the program will show all comparisons that were made to 
 find those records. If the user enters a last name with an "?" the program will show all records with the same first letter of their
 last name. If the user enters nothing, the program will close.
 */

package edu.frostburg.cosc310.Hershman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class LookupTable {
	
	public static void main(String[] args) {
		
		try {
			Scanner scan = new Scanner(new File("names.txt"));	//Scanner created on text file of records
			RedBlackTree lookupTable = new RedBlackTree();		//Lookup table created to hold all the records
			
			//Add all records to the lookupTable
			while(scan.hasNextLine()) {
				String line = scan.nextLine();	//Next line brought in
				Node curNode = new Node();
				
				//Lines that start with # are ignored
				if(line.startsWith("#")) {
					continue;
				}
				
				Scanner scan2 = new Scanner(line);	//Scanner created on line
				
				curNode.setID(scan2.nextInt());		//First input becomes ID number
				curNode.setFirst(scan2.next());		//Second input becomes first name
				curNode.setLast(scan2.next());		//Third input becomes last name
				curNode.setAge(scan2.nextInt());	//Fourth input becomes age
				
				scan2.close();
				
				lookupTable.insert(curNode);		//Record added to lookup table
			}
			scan.close();
			
			Scanner input = new Scanner(System.in);	//input scanner used to read user input
			
			//Main loop of the program. Prompts user to enter a command
			while(true) {
				String line = "";
				String name = "";
				String command = "";
				long startTime = 0;
				long finalTime = 0;
				long executionTime = 0;
				ArrayList<Node> matchedRecords = new ArrayList<Node>();	//matchedRecords will contain the records that were found in search
				System.out.print("Enter command");
				line = input.nextLine();				//line is the input from user
				Scanner input2 = new Scanner(line);		//scanner created on the input line
				
				//The first input string is the name if input exists
				if(input2.hasNext())
					name = input2.next();
				else
					break;	//If no input was given, then exit the while loop and finish the program
				
				//The second input string is the command if one exists
				if(input2.hasNext()) {
					command = input2.next();
					//If the command is a "?", then print all records whose last names begin with the same letter as the input name
					if(command.equals("?")) {
						if(!input2.hasNext()) {		//If there are no other inputs after the second input, then the command is valid
							//Since command was "name ?" then call findMatchesLetter method and record start and end time in nanoseconds
							startTime = System.nanoTime();
							matchedRecords = lookupTable.findMatchesLetter(name);
							finalTime = System.nanoTime();
						}
						else {		//If there are more than 2 inputs, then the command is invalid
							System.out.println("This is not a valid command, try again");
						}
					}
					//If the command is a "!", then print all records that were compared to find the records with the same last name as the input
					else if(command.equals("!")) {
						if(!input2.hasNext()) {		//If there are no other inputs after the second input, then the command is valid
							//Since command was "name !" then call printComparisons method and record start and end time in nanoseconds
							startTime = System.nanoTime();
							matchedRecords = lookupTable.printComparisons(name);
							finalTime = System.nanoTime();
						}
						else {		//If there are more than 2 inputs, then the command is invalid
							System.out.println("This is not a valid command, try again");
						}
					}
					//If the second input is anything else, then the command is invalid
					else {
						System.out.println("This is not a valid command, try again");
					}
				}
				//if there is no second input string, then print all records that share the same last name as the input name
				else {
					//Since command was "name" then call findMatches method and record start and end time in nanoseconds
					startTime = System.nanoTime();
					matchedRecords = lookupTable.findMatches(name);
					finalTime = System.nanoTime();
				}
				input2.close();
				
					executionTime = finalTime - startTime;				//Record execution time in nanoseconds
					if(executionTime != 0) {							//If execution time is not equal to 0 (an operation actually happened)
						executionTime = executionTime/1000;				//Divide by 1000 to get execution time in microseconds
						if (executionTime == 0)							//If the execution time now equals 0 (execution time was less than a microsecond)
							executionTime = 1;							//Round up to 1 microsecond (necessary for later)
					}
					
				//Print the matched records
				if(matchedRecords != null) {
					for(int i = 0; i < matchedRecords.size(); i++) {
						System.out.println(matchedRecords.get(i));
					}
				}
				else	//If matchedRecords is empty, no matches were found
					System.out.println("No matches found");
				
				//Checks if execution time is 0
				//If execution time was 0, then the command was invalid and no operation happened
				if(executionTime != 0) {
					System.out.println("Time required to complete operation: " + executionTime + " microseconds");	//Print out execution time
				}
			}
			input.close();
			
			
		} catch(FileNotFoundException e) {
			System.out.println("File was not found");
			e.printStackTrace();
		}
	}
}