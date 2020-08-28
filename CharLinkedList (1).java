package linked;

import java.util.*;


public class CharLinkedList 
{
	private CharNode		head;	// Empty if head and
	private CharNode		tail;	// tail are null
	
	
	public CharLinkedList()		{ }
	
	
	public CharLinkedList(String s)
	{
		for (int i=s.length()-1; i>=0; i--)
			insertAtHead(s.charAt(i));
	}
	
	
	public void insertAtHead(char ch)
	{
		assert hasIntegrity();		// Precondition
		
		CharNode node = new CharNode(ch);
		node.setNext(head);
		head = node;
		if (tail == null)
			tail = node;			// Corner case: inserting into empty node
		
		assert hasIntegrity();		// Postcondition
	}
	
	
	public CharNode find(char ch) {
		CharNode current = head;
		
		while(current != null) {
			if(current.getData() == ch) {
				return current;
			}
			
			current = current.getNext();
		}
		
		return null;
	}
	
	
	public void duplicate(char ch) {
		
		
		CharNode beforeDuplicatedNode = find(ch);
		
		if(beforeDuplicatedNode == null) {
			throw new IllegalArgumentException("Cannot duplicate a node with " + ch + " data");
				
		}
		
		else {
			CharNode duplicatedNode = new CharNode(ch);
			duplicatedNode.setNext(beforeDuplicatedNode.getNext());
			beforeDuplicatedNode.setNext(duplicatedNode);
			
			if(beforeDuplicatedNode == tail) {
				tail = duplicatedNode;
			}
		}
	}
	
	
	public String toString()
	{
		String s = "";
		CharNode node = head;
		while (node != null)
		{
			s += node.getData();
			node = node.getNext();
		}
		return s;
	}
	
	
	//
	// Returns true if this list has emptiness integrity, has tail integrity, has no loops,  
	// and tail is reachable from head.
	//
	// Caution: this checks for most but not all common integrity problems. 
	//
	boolean hasIntegrity()
	{
		// Check emptiness. If either head or tail is null, the other must
		// also be null. Different logic from what you saw in lecture. Returns
		// immediately if this list is empty.
		if (head == null  ||  tail == null)
			return head == null  &&  tail == null;
		
		// Check tail integrity (tail.next must be null).
		if (tail.getNext() != null)
			return false;
		
		// Check for loops.
		Set<CharNode> visitedNodes = new HashSet<>();
		CharNode node = head;
		while (node != null)
		{
			if (visitedNodes.contains(node))
				return false;		// Current node has been visited before, we must have a loop
			visitedNodes.add(node); // First visit to this node
			node = node.getNext();
		}
		
		// Make sure tail is reachable from head.
		node = head;
		while (node != null && node != tail)
			node = node.getNext();
		return node == tail;
	}
	
	
	public static void main(String[] args) {
		CharLinkedList test = new CharLinkedList("i like macboks");

		System.out.println(test.head.getNext().getNext().getData());
		
	}
}