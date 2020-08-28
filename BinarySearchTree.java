package a5;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This is an implementation of a Binary Search Tree (BST).
 * @author Michael
Author: Michael Cheung
Description: Implement a binary search tree with and fill in the various functions that it contains. 
Implement the search method and insertion method recursively.
Run the assert equals statements to make sure that the code is working correctly.
Observe the runtimes for search and insertion.
Compare the results with hashtable.
 */
public class BinarySearchTree {

	/**
	 * Class containing key (name) for a node and right and left children
	 */
	class Node {
		
		private String key;
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}


		private Node left;
		
		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		
		private Node right;
		
		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		Node(String key) {
			this.key = key;
			right = null;
			left = null;
		}
	}
	
	/**
	 * The root of the binary search tree (BST)
	 */
	Node root;

	// Constructor 
	BinarySearchTree() {  
		root = null;  
	} 

	// Search for a given key in BST
	public Node search(String key) 
	{ 
		Node node = null;
		node = searchRecursive(root, key);
		return node;
	}

	// Implement the search recursively in this helper function
	public Node searchRecursive(Node root, String key) 
	{ 
		// if the root is null return null
		if(root == null) {
			return null;
		}
		
		//if the root's key is equals to the key that we want to find, return the root node
		if(root.getKey().equals(key)) {
			return root;
		}
		
		// if the key we want to find is less than the root's key, search the left subtree recursively
		if(root.key.compareTo(key) > 0) {
			return searchRecursive(root.left, key);
		}
		
		// search right subtree
		return searchRecursive(root.right, key);
	}

	// Insert a new Node with a key and value in BST
	public void insert(String key) {
		root = insertRecursive(root, key);
	}
	

	

	// Implement the insert recursively in this helper function
	public Node insertRecursive(Node root, String key) { 
		// if tree is empty, initialize the root with the key
		if(root == null) {
			root = new Node(key);
			return root;
		}
		
		// go to the left down the tree
		if(key.compareTo(root.key) < 0) {
			root.left = insertRecursive(root.left, key);
		}
		
		// go to the right down the tree
		else if(key.compareTo(root.key) > 0){
			root.right = insertRecursive(root.right, key);
		}
		
		return root;
	} 


	// A recursive inorder traversal of the BST 
	public void inorder(Node root, ArrayList<String> strList) { 
		if (root != null) { 
			inorder(root.getLeft(), strList); 
			System.out.println(root.getKey()); 
			strList.add(root.getKey());
			inorder(root.getRight(), strList); 
		}
	}



	public static void main(String[] args) { 

		//For runtime computations
		long startTime, endTime;
		double duration = 0;


		startTime = System.currentTimeMillis();
		BinarySearchTree bst = new BinarySearchTree(); 
		/**
		 * TODO:
		 * Read in the names from the names.txt file, and
		 * Insert all the names in the BST by calling:
		 *  insert(name)
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader("names.txt"));
			while (br.ready()) {
				//turns each line of input into a string
				String in = br.readLine();
				bst.insert(in);
			}
		} catch (Exception e) {
			//prints out useful exception message
			System.out.println(e.getMessage());
		}
		
		endTime = System.currentTimeMillis();
		duration += ((double) (endTime - startTime));
		System.out.println("BST insertion runtime is " + duration);

		/**
		 * So an inorder traversal of the tree, ensure the result is 
		 * order lexicographically
		 */
		ArrayList<String> strList = new ArrayList<String>();
		bst.inorder(bst.root, strList);
		//Ensure the inorder traversal gives a 
		//lexicographic ordering of the names
		for (int i = 1; i < strList.size(); i++) {
			assertTrue(strList.get(i-1).compareTo(strList.get(i)) <= 0  );
		}

		/**
		 * Verify that search returns the correct result
		 */
		startTime = System.currentTimeMillis();
		Node n = bst.search("aaaa");
		assertEquals(n, null);
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("BST search runtime for aaaa is " + duration);


		startTime = System.currentTimeMillis();
		n = bst.search("alice");

		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("BST search runtime for alice is " + duration);
		
		
		startTime = System.currentTimeMillis();
		n = bst.search("zoe");
		assertEquals(n.getKey(), "zoe");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("BST search runtime for zoe is " + duration);

	}
}
