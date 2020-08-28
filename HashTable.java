package a5;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;


/**
This is an implementation of a hash table.
@author Michael
Author: Michael Cheung
Implement the nametokey method and search method and insertion method.
Run the assert equals statements to make sure that the code is working correctly.
Observe the runtimes for search and insertion.
Compare with the results obtained from binary search tree.
 */
public class HashTable {


	/* Class containing next node and key and name value*/
	class Node { 
		private long key;

		public long getKey() {
			return key;
		}

		public void setKey(long key) {
			this.key = key;
		}

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		Node next; 

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		Node(long key, String value) {
			this.key = key;
			this.value = value;
			next = null;
		}
	} 

	//The hash table array
	Node[] array = null;
	//hash table size. We typically choose m to be a power of 2
	int m = 0;

	// Constructor 
	HashTable(int size) {  
		m = size;
		array = new Node[size];  
	}

	HashTable() {  
		this(100);
	} 

	// Search for a given key in the hashtable
	// Note: you need to use hashFunction(long key) to determine the slot (array index) you will search in
	public Node search(long key) 
	{ 
		/**
		 * TODO
		 */
		Node head = array[hashFunction(key)];
		while(head != null) {
			if(head.key == key) {
				return head;
			}
			head = head.next;
		}
		return head;
	}

	// Insert a new Node with a key (nameToKey(name)) and value (name) in the hashtable
	// Note: you need to use hashFunction(long key) to determine the slot (array index) you will insert into
	public void insert(long key, String value) {
		// find pointer to head of the bucket 
		int bucket = hashFunction(key);
		Node head = array[bucket];
		
		// if pointer is null
		if(head == null) {
			array[bucket] = new Node(key, value);
		}
		
		// traverse the linked list for appropriate bucket
		else {
			Node node = new Node(key, value);
			node.next = head;
			array[bucket] = node;
		}
	}


	/**
	 * This is the multiplication method as our hash function.
	 * @param key  It computes the hash function to find the slot (bucket) for key
	 * @return  the hash
	 */
	private int hashFunction(long key) {
		double k = (Math.sqrt(5) - 1.0) / 2.0;
		int hash = (int) (((double)m) * ((k*(double)key) % 1.0));
		return hash;
	}

	//Convert a name to a key using radix-26:
	//Use all of the letters of the alphabet as your digits.
	//Assume we only deal with lower-case letters for simplicity.
	//Then you can create the radix(base)-26 alphabet number system.
	//For example:
	// "alice" in radix-26 is 26^4*1 + 26^3*12 + 26^2*9 + 26^1*3 + 26^0*5 = 674055
	// "zoe" in radix-26 is 26^2*26 + 26^1*15 + 26^0*5 = 17971 
	public static long nameToKey(String name) {
		long k = 0;
		
		// do the converstion 
		for (int i = 0; i < name.length(); i ++) {
		    k = (k * 26) + name.charAt(i) - 'a' + 1;
		}
		
		return k;
	}


	public static void main(String[] args) {

		//For runtime computations
		long startTime, endTime;
		double duration = 0;

		//Assert nameToKey works right
		// "alice" in radix-26 is 26^4*1 + 26^3*12 + 26^2*9 + 26^1*3 + 26^0*5 = 674055
		// "zoe" in radix-26 is 26^2*26 + 26^1*15 + 26^0*5 = 17971 
		assertEquals(nameToKey("alice"), 674055);
		assertEquals(nameToKey("zoe"), 17971);

		/**
		 * _______HashTable - BIG arraysize of 2^10=1024
		 */
		System.out.println("_______HashTable - BIG arraysize of 2^10=1024");
		startTime = System.currentTimeMillis();
		HashTable ht = new HashTable((int)Math.pow(2, 10)); 
		//Assert hashFunction works right for m=2^10
		assertEquals(ht.hashFunction(1000), 34);
		/**
		 * TODO:
		 * Read in the names from the names.txt file, and
		 * Insert all the keys and names in the hashtable by calling:
		 *  insert(nameToKey(name), name)
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader("names.txt"));
			while (br.ready()) {
				//turns each line of input into a string
				String in = br.readLine();
				ht.insert(nameToKey(in), in);
			}
		} catch (Exception e) {
			//prints out useful exception message
			System.out.println(e.getMessage());
		}
		
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable - BIG arraysize of 2^10=1024 - insertion runtime is " + duration);

		/**
		 * Verify that search returns the correct result
		 */
		startTime = System.currentTimeMillis();
		Node n = ht.search(nameToKey("aaaa"));
		assertEquals(n, null);
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for aaaa is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("alice"));
		
		
		assertEquals(n.getKey(), 674055);
		assertEquals(n.getValue(), "alice");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for alice is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("zoe"));
		assertEquals(n.getKey(), 17971);
		assertEquals(n.getValue(), "zoe");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for zoe is " + duration);

		
		/**
		 * _______HashTable - MEDIUM arraysize of 2^7=128
		 */
		System.out.println("_______HashTable - MEDIUM arraysize of 2^7=128");
		startTime = System.currentTimeMillis();
		ht = new HashTable((int)Math.pow(2, 7)); 
		//Assert hashFunction works right for m=2^7
		assertEquals(ht.hashFunction(1000), 4);
		/**
		 * TODO:
		 * Read in the names from the names.txt file, and
		 * Insert all the names in the hashtable
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader("names.txt"));
			while (br.ready()) {
				//turns each line of input into a string
				String in = br.readLine();
				ht.insert(nameToKey(in), in);
			}
		} catch (Exception e) {
			//prints out useful exception message
			System.out.println(e.getMessage());
		}
		
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable - MEDIUM arraysize of 2^7=128 - insertion runtime is " + duration);


		/**
		 * Verify that search returns the correct result
		 */
		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("aaaa"));
		assertEquals(n, null);
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for aaaa is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("alice"));
		assertEquals(n.getKey(), 674055);
		assertEquals(n.getValue(), "alice");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for alice is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("zoe"));
		assertEquals(n.getKey(), 17971);
		assertEquals(n.getValue(), "zoe");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for zoe is " + duration);

		
		/**
		 * _______HashTable - SMALL arraysize of 2^4=16
		 */
		System.out.println("_______HashTable - SMALL arraysize of 2^4=16");
		startTime = System.currentTimeMillis();
		ht = new HashTable((int)Math.pow(2, 4)); 
		//Assert hashFunction works right for m=2^4
		assertEquals(ht.hashFunction(1000), 0);
		/**
		 * TODO:
		 * Read in the names from the names.txt file, and
		 * Insert all the names in the hashtable
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader("names.txt"));
			while (br.ready()) {
				//turns each line of input into a string
				String in = br.readLine();
				ht.insert(nameToKey(in), in);
			}
		} catch (Exception e) {
			//prints out useful exception message
			System.out.println(e.getMessage());
		}
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable - SMALL arraysize of 2^4=16 - insertion runtime is " + duration);


		/**
		 * Verify that search returns the correct result
		 */
		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("aaaa"));
		assertEquals(n, null);
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for aaaa is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("alice"));
		assertEquals(n.getKey(), 674055);
		assertEquals(n.getValue(), "alice");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for alice is " + duration);

		startTime = System.currentTimeMillis();
		n = ht.search(nameToKey("zoe"));
		assertEquals(n.getKey(), 17971);
		assertEquals(n.getValue(), "zoe");
		endTime = System.currentTimeMillis();
		duration = ((double) (endTime - startTime));
		System.out.println("HashTable search runtime for zoe is " + duration);

	}
}
