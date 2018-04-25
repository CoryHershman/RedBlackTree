RedBlackTree program
Last Updated: 11/17/2017
---------------
Source code:
	Node.java
	RedBlackTree.java
	LookupTable.java
---------------
"LookupTable.java" is the main class. This project creates a lookup table implemented with a 
red black tree. The nodes of the lookup table are organized by last name, first name keys. 
The main class will prompt the user to enter a command. There are 4 valid commands.

Commands:
	[last name] - searches for all records that share the last name entered
	[last name, ?] - searches for all records with the same first letter of their last name,
another valid input is [letter, ?] for the same result
	[last name, !] - searches for all records that share the last name entered and prints
out all comparisons that were made to find the matched records
		- If nothing is entered, the program finishes
---------------
Package Path:RedBlackTreeLookup\src\edu\frostburg\cosc310\Hershman
---------------
Test case files:
	names.txt
	names2.txt
	names3.txt
	names4.txt
	names5.txt

"names.txt" is the default test case. To test the other cases, go to line 28 of "LookupTable.java"
and change the input file. "names5.txt" is the full sample text file with nearly 3000 records.
In the input file, lines that begin with "#" will be ignored.
---------------
Author: Cory Hershman