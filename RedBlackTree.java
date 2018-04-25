/*
 Programmer:Cory Hershman
 Class: Data Structures and Algorithms
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/17/2017
 Last Update: 11/16/2017
 Related Files: Node.java, LookupTable.java, names.txt
 Description: This class is a red black tree that is ordered based on last name, first name keys. It can be
 searched based on this key
 */

package edu.frostburg.cosc310.Hershman;

import java.util.ArrayList;

public class RedBlackTree {
	
	Node rootNode;					//root node of the tree
	final int LEFT = 0;				//LEFT and RIGHT will be used as binary representations of direction
	final int RIGHT = 1;
	
	//Empty constructor
	public RedBlackTree() {
		this.rootNode = null;
	}
	
	//Constructor that initializes rootNode
	public RedBlackTree(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	//insert method
	//Requires: new node that will be inserted
	//Returns: Nothing
	//Finds leaf position that the new node should be inserted at 
	public void insert(Node newNode) {
		
		newNode.setColor(0);	//Set the new node's color to red by default
		//If there are no nodes, then the new node becomes the root node
		if(this.rootNode == null) {
			newNode.setColor(1);	//Root node comes in black
			this.rootNode = newNode;
			return;
		}
		
		//Starting from the root node, look for the position that newNode should be inserted at
		Node curNode = this.rootNode;
		while(true) {
			int i = newNode.compareTo(curNode);			//Compare the new node's last name, first name key to the current node
			
			//If the new node is greater than the current node, check the current node's right child.
			//If the right child is null, insert the new node there; otherwise, the right child becomes the new current node
			if(i > 0) {
				if(curNode.getRChild() != null)			//If it's not null
					curNode = curNode.getRChild();		//Right child becomes new current node
				else {									//If it is null
					curNode.setRChild(newNode);			//new node becomes the right child
					newNode.setParent(curNode);			//and current node becomes the new node's parent
					break;								//If the new node was inserted here, stop the while loop
				}
			}
			//If the new node is less than or equal to the current node, check the current node's left child.
			//If the left child is null, insert the new node there; otherwise the right child becomes the new current node
			else if(i <= 0) {
				if(curNode.getLChild() != null)			//If it's not null
					curNode = curNode.getLChild();		//Left child becomes the new current node
				else {									//If it is null
					curNode.setLChild(newNode);			//new node beecomes the left child
					newNode.setParent(curNode);			//and current node becomes the new node' parent
					break;								//If the new node was inserted here, stop the while loop
				}
			}
		}
		//If the new node's parent is red, then a double red has occurred
		if(newNode.getParent().getColor() == 0) {
			doubleRedHandler(newNode);					//Call doubleRedHandler to correct the double red
		}
	}		//End of insert method
	
	//doubleRedHandler method
	//Requires: Bottom node of a double red pattern
	//Returns: Nothing
	//This method corrects a double red occurrence
	private void doubleRedHandler(Node curNode) {
		//Check the uncle
		Node uncle = new Node();
		Node grandparent = curNode.getParent().getParent();
		
		int uncleDirection = LEFT;						//uncleDirection is the direction of the uncle relative to the current node
		
		int i = curNode.getParent().compareTo(grandparent);	//Compare the current node's last name, first name key to its grandparent
		
		//If the current node is greater than the grandparent, then the uncle is to the left
		if(i > 0) {
			uncle = grandparent.getLChild();			//Uncle is grandparent's left child
			uncleDirection = LEFT;
		}
		//If the current node is less than or greater than the grandparent, then the uncle is to the right
		else if(i <= 0) {
			uncle = grandparent.getRChild();			//Uncle is the grandparent's right child
			uncleDirection = RIGHT;
		}
		
		
		//If the uncle is red and not null, call recolor method
		if(uncle != null) {
			if(uncle.getColor() == 0) {
				recolor(curNode);
				return;
			}
		}
		//If the uncle is black or null, check if either the zigZig or zigZag method is needed
		if (uncleDirection == RIGHT){
			i = curNode.compareTo(curNode.getParent());	//Compare the current node's last name, first name key to its parent
			
			//If the current node is greater than the parent, with an uncle to the right, then the structure that needs corrected is a zig zag
			if(i > 0)
				zigZagRes(curNode, uncleDirection);		//Call zigZagRes to fix the double red
			//If the current node is less than or equal to the parent, with an uncle to the right, then the structure that needs corrected is a zig zig
			else
				zigZigRes(curNode, uncleDirection);		//Call zigZigRes to fix the double red
		}
		else if (uncleDirection == LEFT) {
			i = curNode.compareTo(curNode.getParent());	//Compare the current node's last name, first name key to its parent

			//If the current node is greater than the parent, with an uncle to the left, then the structure that needs corrected is a zig zig
			if(i > 0)
				zigZigRes(curNode, uncleDirection);	//Call zigZigRes to fix the double red
			//If the current node is less than or equal to the parent, with an uncle to the left, then the structure that needs corrected is a zig zag
			else
				zigZagRes(curNode, uncleDirection);
		}
	}
	
	//recolor method
	//Requires: Bottom node of a double red pattern
	//Returns: Nothing
	//This method recolors the parent and uncle of the target node to black, and recolors its grandparent to red
	private void recolor(Node curNode) {
		Node grandparent = curNode.getParent().getParent();
		grandparent.setColor(0);						//Set the grandparent's color to red (grandparent will always be black beforehand)
		if(grandparent.getLChild() != null)				//If the grandparent has a left child (it should)
			grandparent.getLChild().setColor(1);		//Set that left child's color to black
		if(grandparent.getRChild() != null)				//If the grandparent has a right child (it should)
			grandparent.getRChild().setColor(1);		//Set that right child's color to black
		if(this.rootNode.getColor() == 0)				//If, after recoloring, the root node is red
			this.rootNode.setColor(1);					//Set the root node's color to black
		if(grandparent != this.rootNode)				//If the grandparent is not the root node
			if(grandparent.getParent().getColor() == 0) {	//Check if it's parent is red
				//If it is red, then a new double red has occured that needs to be fixed
				doubleRedHandler(grandparent);			//Call doubleRedHandler again to fix the double red
			}
	}
	
	//zigZigRes method
	//Requires: Bottom node of a double red pattern, the direction of the uncle relative to the target node
	//Returns: Nothing
	//Restructures a double red in a zig zig pattern by doing a single rotation
	public void zigZigRes(Node curNode, int uncleDirection) {
		Node parent = curNode.getParent();					//parent is the parent of current node
		Node grandparent = curNode.getParent().getParent();	//grandparent is the grandparent of current node
		
		//If the grandparent is the root node, then the parent will become the new root node after single rotation
		if(this.rootNode == grandparent) {
			this.rootNode = parent;
		}
		
		if(parent != this.rootNode) {						//if the parent will not become the root node
			parent.setParent(grandparent.getParent());		//Then set the parent's parent to be the grandfather's parent
			
			//Check which direction the parent will be in relation to its new parent
			int i = parent.compareTo(parent.getParent());
			if(i > 0)
				parent.getParent().setRChild(parent);		//If it is right of its new parent then it becomes the right child
			else
				parent.getParent().setLChild(parent);		//If it is left of its new parent then it becomes the left child
		}
		else {												//If the parent will become the root node
			parent.setParent(null);							//Then the parent will not have a parent
		}
		
		grandparent.setParent(parent);						//Set the grandparent's new parent to be the current node's parent
		
		//If the uncle direction is to the right of current node
		if(uncleDirection == RIGHT) {
			grandparent.setLChild(parent.getRChild());			//Hand off the parent's right child to be the original grandparent's left child
			if(grandparent.getLChild() != null)					//If the grandparent's new left child is not null
				grandparent.getLChild().setParent(grandparent);	//Then update that left child's parent to be the original grandparent of current node
			parent.setRChild(grandparent);						//Set the parent's right child to be the original grandparent of current node
		}
		//If the uncle direction is to the left of the current node
		else if(uncleDirection == LEFT) {
			grandparent.setRChild(parent.getLChild());			//Hand off the parent's left child to be the original grandparent's right child
			if(grandparent.getRChild() != null)					//If the grandparent's new right child is not null
				grandparent.getRChild().setParent(grandparent);	//Then update that right child's parent to be the original grandparent of current node
			parent.setLChild(grandparent);						//Set the parent's left child to be the original grandparent of current node
		}
		parent.setColor(1);										//The parent of current node becomes black
		grandparent.setColor(0);								//The original grandparent (now sibling) of current node becomes red
		
	}
	
	//zigZagRes method
	//Requires: Bottom node of a double red pattern, the direction of the uncle relative to the target node
	//Returns: Nothing
	//Restructures a double red in a zig zag pattern by doing a double rotation
	public void zigZagRes(Node curNode, int uncleDirection) {
		Node parent = curNode.getParent();						//parent is the parent of current node
		Node grandparent = curNode.getParent().getParent();		//grandparent is the grandparent of current node
		
		//If the grandparent is the root node, then the current node will become the new root node after double rotation
		if(this.rootNode == grandparent)
			this.rootNode = curNode;
		
		//If the uncle direction is to the right of current node
		if(uncleDirection == RIGHT) {
			//First rotation
			curNode.setParent(grandparent);						//Grandparent will become the new parent of current node
			parent.setParent(curNode);							//Parent's new parent will be the current node
			parent.setRChild(curNode.getLChild());				//Hand off the current node's left child to be the parent's right child
			if(parent.getRChild() != null)						//If parent's right child is not null
				parent.getRChild().setParent(parent);			//Then update that right child's parent to be the original parent of current node
			curNode.setLChild(parent);							//Parent will become current node's left child
			//Second rotation
			if(curNode != this.rootNode) {						//If current node will not become the root node
				curNode.setParent(grandparent.getParent());		//Set current node's parent to be it's original grandparent's parent
				//Check which direction the current node will be in relation to its new parent
				int i = curNode.compareTo(curNode.getParent());
				if(i > 0)
					curNode.getParent().setRChild(curNode);		//If it is right of its new parent then it becomes the right child
				else
					curNode.getParent().setLChild(curNode);		//If it is left of its new parent then it becomes the left child
			}
			else {												//If current node will become the root node
				curNode.setParent(null);						//Then current node will not have a parent
			}
			grandparent.setParent(curNode);						//Current node will become grandparent's new parent
			grandparent.setLChild(curNode.getRChild());			//Hand off the current node's right child to be the original grandparent's left child
			if(grandparent.getLChild() != null)					//If the grandparent's new left child is not null
				grandparent.getLChild().setParent(grandparent);	//Then update that left child's parent to be the original grandparent of current node
			curNode.setRChild(grandparent);						//Set the right child of current node to be grandparent
		}
		//If the uncle direction is to the left of current node
		else if(uncleDirection == LEFT) {
			//First rotation
			curNode.setParent(grandparent);						//Grandparent will become the new parent of current node
			parent.setParent(curNode);							//Parent's new parent will be the current node
			parent.setLChild(curNode.getRChild());				//Hand off the current node's right child to be the parent's left child
			if(parent.getLChild() != null)						//If parent's left child is not null
				parent.getLChild().setParent(parent);			//Then update that left child's parent to be the original parent of current node
			curNode.setRChild(parent);							//Parent will become current node's right child
			//Second rotation
			if(curNode != this.rootNode) {						//If current node will not become the root node
				curNode.setParent(grandparent.getParent());		//Set current node's parent to be it's original grandparent's parent
				//Check which direction the current node will be in relation to its new parent
				int i = curNode.compareTo(curNode.getParent());
				if(i > 0)
					curNode.getParent().setRChild(curNode);		//If it is right of its new parent then it becomes the right child
				else if(i <= 0)
					curNode.getParent().setLChild(curNode);		//If it is left of its new parent then it becomes the left child
			}
			else {												//If the current node will become the root node
				curNode.setParent(null);						//Then current node will not have a parent
			}
			grandparent.setParent(curNode);						//Current node will become grandparent's new parent
			grandparent.setRChild(curNode.getLChild());			//Hand off the current node's left child to be the original grandparent's right child
			if(grandparent.getRChild() != null)					//If the grandparent's new right child is not null
				grandparent.getRChild().setParent(grandparent);	//Then update that right child's parent to be the original grandparent of current node
			curNode.setLChild(grandparent);						//Set the left child of current node to be grandparent
		}
		
		//recolor
		curNode.setColor(1);									//Current node becomes black
		grandparent.setColor(0);								//Grandparent becomes red
	}
	
	//findRecord method
	//Requires: The last name and first name to be searched for
	//Returns: The matching node if found. Returns null if not found
	//Searches for a node with the same last name and first name as the input
	public Node findRecord(String last, String first) {
		Node curNode = this.rootNode;							//Current node starts as the root node
		while(curNode != null) {								//While current node is not null (leaf not has not been reached)
			//Check which direction the last name key of the input is in relation to the current node
			int i = last.compareTo(curNode.getLast());
			if(i > 0)
				curNode = curNode.getRChild();					//If the record should be right of current node, current node's right child becomes the current node
			else if(i < 0)
				curNode = curNode.getLChild();					//If the record should be left of current node, current node's left child becomes the current node
			else {												//If the last names are the same
				//Check which direction the first name key of the input is in relation to the current node
				i = first.compareTo(curNode.getFirst());
				if(i > 0)
					curNode = curNode.getRChild();				//If the record should be right of current node, current node's right child becomes the current node
				else if(i < 0)
					curNode = curNode.getLChild();				//If the record should be left of current node, current node's left child becomes the current node
				else											//If the first names are also the same, match has been found
					return curNode;								//Return matched node
			}
		}
		return null;											//If no match was found, return null
	}
	
	//findMatches method
	//Requires: The last name to be searched for
	//Returns: An array list of nodes
	//Finds all records with the same last name and returns them as an array list of nodes
	public ArrayList<Node> findMatches(String last) {
		Node curNode = this.rootNode;
		ArrayList<Node> records = new ArrayList<Node>();
		
		//Search for a node with the same last name as the query
		//Loop ends if an external node of tree is reached
		while(curNode != null) {
			//Check which direction the last name key of the input is in relation to the current node
			int i = last.compareTo(curNode.getLast());
			if(i > 0)											//If the current node's last name is less than the query
				curNode = curNode.getRChild();					//Check it's right child
			else if(i < 0)										//If the current node's last name is greater than the query
				curNode = curNode.getLChild();					//Check it's left child
			else												//If the current node's last name is the same as the query, name has been found
				break;											//highest node with matching last name has been found
		}
		
		//If an external node is reached, then no matches have been found
		if(curNode == null) {
			return null;										//Return null
		}

		//curNode is currently the highest node in tree with matching last name
		subTreeSearch(curNode, last, records);					//Search sub tree for all occurrences of the same last name
		
		return records;											//After the sub tree has been searched, return array list of all matched nodes
	}

	//printComparisons method
	//Requires: The last name to be searched for
	//Returns: An array list of nodes
	//Finds all records with the same last name, and returns them as an array list of nodes, preceded by the nodes that were compared
	public ArrayList<Node> printComparisons(String last) {
		Node curNode = this.rootNode;
		ArrayList<Node> records = new ArrayList<Node>();

		//Search for a node with the same last name as the query
		//Loop ends if an external node of tree is reached
		while(curNode != null) {
			//Check which direction the last name key of the input is in relation to the current node
			int i = last.compareTo(curNode.getLast());
			if(i > 0) {											//If the current node's last name is greater than the query
				records.add(curNode);							//Add current node to records
				curNode = curNode.getRChild();					//And check its right child
			}
			else if(i < 0) {									//If the current node's last name is less than the query
				records.add(curNode);							//Add current node to records
				curNode = curNode.getLChild();					//And check its left child
			}
			else												//If the current node's last name is the same as the query, name has been found
				break;											//highest node with matching last name has been found
		}
		
		//If an external node is reached, then no matches have been found
		if(curNode == null) {
			return records;										//Return the comparisons that were made
		}
		
		//curNode is currently the highest node in tree with matching last name
		subTreeSearch(curNode, last, records);					//Search sub tree for all occurrences of the same last name
		
		return records;											//After sub tree has been searched, return array list of all matched nodes and all comparisons made
	}
	
	//subTreeSearch method
	//Requires: The root node of the sub tree, the last name to be searched for, and the list of nodes matched so far
	//Returns: An updated version of the array list of nodes
	//Recursive method that searches a sub tree for all occurrences of the same last name
	public ArrayList<Node> subTreeSearch(Node curNode, String name, ArrayList<Node> records) {
		//Check the current node's last name against the input name
		int i = curNode.getLast().compareTo(name);
		
		//If the current node's last name is the same as the query, check both children and add itself to the list of records
		if(i == 0) {
			if(curNode.getLChild() != null)										//If left child is not a null-leaf node
				records = subTreeSearch(curNode.getLChild(), name, records);	//Check left child, update record
			
			records.add(curNode);												//Add itself to records
			
			if(curNode.getRChild() != null)										//If right child is not a null-leaf node
				records = subTreeSearch(curNode.getRChild(), name, records);	//Check right child, update record
		}
		//If the current node's last name is less than the query, check it's right child
		else if(i < 0) {
			if(curNode.getRChild() != null)										//If the right child is not a null-leaf node
				records = subTreeSearch(curNode.getRChild(), name, records);	//check right child, update record
		}
		//If the current node's last name is greater than the query, check it's left child
		else if(i > 0) {
			if(curNode.getLChild() != null)										//If the left child is not a null-leaf node
				records = subTreeSearch(curNode.getLChild(), name, records);	//check left child, update record
		}
		return records;															//Return current version of matched records
	}
	
	//findMatchesLetter method
	//Requires: The last name to be searched for
	//Returns: An array list of nodes
	//Finds all records with the same first letter in their last name and returns them as an array list of nodes
	public ArrayList<Node> findMatchesLetter(String last) {
		Node curNode = this.rootNode;
		ArrayList<Node> records = new ArrayList<Node>();

		//Search for a node with the same last name as the query
		//Loop ends if an external node of tree is reached
		while(curNode != null) {

			if(last.charAt(0) > curNode.getLast().charAt(0))					//If the current node's first letter is less than the query
				curNode = curNode.getRChild();									//Then check it's right child
			else if(last.charAt(0) < curNode.getLast().charAt(0))				//If the current node's last name is greater than the query
				curNode = curNode.getLChild();									//Then check it's left child
			else																//If the current node's first letter is the same as the query
				break;															//Then highest node in tree with matching first letter has been found
		}

		if(curNode == null) {													//If no matches have been found
			return null;														//Return null
		}

		//curNode is currently the highest node in tree with matching first letter
		//Search sub tree for all occurrences of last names with the same first letter
		//Return an array list of all matching records
		return subTreeSearchLetter(curNode, last, records);
	}

	//subTreeSearchLetter method
	//Requires: The root node of the sub tree, the last name to be searched for, and the list of nodes matched so far
	//Returns: An updated version of the array list of nodes
	//Recursive method that searches a sub tree for all nodes with the same first letter of their last name as the input name
	public ArrayList<Node> subTreeSearchLetter(Node curNode, String name, ArrayList<Node> records) {
		//If the current node's first letter is the same as the query, check both children and add itself to the list of records
		if(name.charAt(0) == curNode.getLast().charAt(0)) {
			if(curNode.getLChild() != null)											//If left child is not a null-leaf node
				records = subTreeSearchLetter(curNode.getLChild(), name, records);	//Check left child; update record
			
			records.add(curNode);													//Add itself to records
			
			if(curNode.getRChild() != null)											//If right child is not a null-leaf node
				records = subTreeSearchLetter(curNode.getRChild(), name, records);	//check right child; update record
		}
		//If the current node's first letter is less than the query, check it's right child
		else if(curNode.getLast().charAt(0) < name.charAt(0)) {
			if(curNode.getRChild() != null)											//If right child is not a null-leaf node
				records = subTreeSearchLetter(curNode.getRChild(), name, records);	//Check right child, update record
		}
		//If the current node's first letter is greater than the query, check it's left child
		else if(curNode.getLast().charAt(0) > name.charAt(0)) {
			if(curNode.getLChild() != null)											//If left child is not a null-leaf node
				records = subTreeSearchLetter(curNode.getLChild(), name, records);	//Check left child, update record
		}
		return records;																//Return current version of matched records
	}
	
	//display method
	//Requires: The root node of the tree or sub tree
	//Returns: Printout of every node in the tree or sub tree as an inorder traversal
	//In the format of [last name, first name, color of node]
	//The method is recursively called to traverse the tree
	public void display(Node curNode) {
		if(curNode.getLChild() != null)												//If current node's left child is not null
			display(curNode.getLChild());											//Call display method on the current node's left child
		
		System.out.println(curNode.getLast() + ", " + curNode.getFirst() + ", " + curNode.getColor());	//Print out the current node's information
		
		if(curNode.getRChild() != null)												//If current node's right child is not null
			display(curNode.getRChild());											//Call display method on the current node's right child
	}
}