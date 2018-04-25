/*
 Programmer:Cory Hershman
 Class: Data Structures and Algorithms
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/17/2017
 Last Update: 11/16/2017
 Related Files: RedBlackTree.java, LookupTable.java, names.txt
 Description: This class is a node to be used in a red black tree lookup table.
 */

package edu.frostburg.cosc310.Hershman;

//Add a compareTo method
public class Node {
	
	private int id;
	private String first;	//first name
	private String last;	//last name
	private int age;
	private Node lChild;	//left child
	private Node rChild;	//right child
	private Node parent;
	private int color;		//0 is red, 1 is black
	
	final int RED = 0;
	final int BLACK = 1;
	
	//Empty constructor
	public Node() {
		id = 0;
		first = null;
		last = null;
		age = 0;
		lChild = null;
		rChild = null;
		parent = null;
		color = BLACK;	//color is black by default (like a null leaf)
	}
	
	//Constructor with data
	public Node(int id, String first, String last, int age) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.age = age;
		lChild = null;
		rChild = null;
		parent = null;
		color = BLACK;	//color is black by default
	}
	
	//compareTo method
	//Requires: Node to be compared to
	//Returns: Positive number if calling node should be right of target node, negative if left of target node, 0 if name pairs are the same
	//Compares 2 nodes based on their last name, first name pairs
	public int compareTo(Node targetNode) {
		int i = this.last.compareTo(targetNode.getLast());
		
		if(i > 0)
			return 1;
		else if(i < 0)
			return -1;
		else {
			i = this.first.compareTo(targetNode.getFirst());
			
			if(i > 0)
				return 1;
			else if(i < 0)
				return -1;
			else
				return 0;
		}
	}
	
	//GetID
	//Returns ID
	public int getID() {
		return id;
	}
	
	//getFirst
	//Returns first name
	public String getFirst() {
		return first;
	}
	
	//getLast
	//returns last name
	public String getLast() {
		return last;
	}
	
	//getAge
	//returns age
	public int getAge() {
		return age;
	}
	
	//getColor
	//returns color
	public int getColor() {
		return color;
	}
	
	//setID
	//Requires: new ID number
	//Returns: nothing
	public void setID(int id) {
		this.id = id;
	}
	
	//setFirst
	//Requires: new first name
	//Returns: nothing
	public void setFirst(String first) {
		this.first = first;
	}
	
	//setLast
	//Requires: new last name
	//Returns: nothing
	public void setLast(String last) {
		this.last = last;
	}
	
	//setAge
	//Requires: new age
	//Returns: nothing
	public void setAge(int age) {
		this.age = age;
	}
	
	//setColor
	//Requires: new color
	//Returns: nothing
	public void setColor(int color) {
		this.color = color;
	}
	
	//getLChild
	//Returns: Left child
	public Node getLChild() {
		return lChild;
	}
	
	//getRChild
	//Returns: Right child
	public Node getRChild() {
		return rChild;
	}
	
	//getParent
	//Returns: Parent
	public Node getParent() {
		return parent;
	}
	
	//setLChild
	//Requires: new Left child
	//Returns: nothing
	public void setLChild(Node lChild) {
		this.lChild = lChild;
	}
	
	//setRChild
	//Requires: new Right child
	//Returns: nothing
	public void setRChild(Node rChild) {
		this.rChild = rChild;
	}
	
	//setParent
	//Requires: new Parent
	//Returns: nothing
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	//toString
	//Requires: nothing
	//Returns: Printout of the node's information in the format of [ID, Last name, First name, Age]
	public String toString() {
		String s = "";
		s = s + getID();
		s  = s + ", " + getLast();
		s = s + ", " + getFirst();
		s = s + ", " + getAge();
		return s;
	}
}