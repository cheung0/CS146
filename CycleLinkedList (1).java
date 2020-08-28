/**
 * 
 */
package a2;

import static org.junit.Assert.*;

/**
 * Name: Michael Cheung
 * Student ID: 014373619
 * Description of solution: I have two pointers, a slow one that moves one node at a time, and a fast one that moves two nodes at a time. 
 * When the fast and slow pointer meet at the same node, then i know that a cycle exists, otherwise there is no cycle
 * @author Michael
 *
 */


public class CycleLinkedList {

	/*This function returns true if given linked 
	list has a cycle, else returns false. */
	public static boolean hasCycle( Node head) {
		// slow pointer that moves by one
		Node slow = head;
		
		// fast pointer that moves by two
		Node fast = head;
		
		// moves both pointers
		while(slow != null && fast != null && fast.getNext() != null) {
			slow = slow.getNext();
			fast = fast.getNext().getNext();
			
			// if fast and slow pointers meet at same place, there is a cycle
			if(slow == fast) {
				return true;
			}
		}
		
		// when there's no cycle
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node head = new Node("start");
		Node prev = head;
		for (int i =0; i<4; i++) {
			Node temp = new Node(Integer.toString(i));
			prev.setNext(temp);
			prev=temp;
		}

		boolean b = hasCycle(head);
		System.out.println("Testing...");
		assertEquals(b, false);
		System.out.println("Success!");

		prev.setNext(head.getNext());

		b = hasCycle(head);
		System.out.println("Testing...");
		assertEquals(b, true);
		System.out.println("Success!");

	}
}
	


