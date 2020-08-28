package graphs;


//like a line of people
// implemented the Abstract Data Type with a linked list
public class Queue<T> {
	public Node<T> head;
	public Node<T> tail;
	
	
	public class Node<T> {
		public Node<T> next;
		public T data;
		
		public Node(T data) {
			this.data = data;
		}
	}
	
	
	//can only add to end of tail
	public void add(T data) {
		Node<T> addNode = new Node<T>(data);
		
		//empty queue
		if(head == null) {
			head = addNode;
			tail = addNode;
		}
		
		//non empty queue
		else {
			this.tail.next = addNode;
			tail = addNode;
		}
	}
	
	
	//removes a node from the head
	public Node<T> remove() {
		//empty queue
		if(head == null) {
			return null;
		}
		
		//non empty queue
		else {
			Node<T> removedNode = head;
			head = head.next;
			removedNode.next = null;
			return removedNode;
		}
	}
	
	
	//prints out the entire queue's data
	public void print() {
		Node<T> current = head;
		
		System.out.println("Queue:");
		
		while(current != null) {
			System.out.print(current.data + " --> ");
			current = current.next;
		}
		
		System.out.print("null");
	}
	
	
	public static void main(String[] args) {
		Queue<String> hotDogLine = new Queue<>();
		hotDogLine.add("Big Mike");
		hotDogLine.add("Phil");
		hotDogLine.add("Joseph");
		hotDogLine.print();
		
		System.out.println();
		
		Queue<Integer> numberLine = new Queue<>();
		for(int i = 1; i < 11; i ++) {
			numberLine.add(i);
		}
		numberLine.print();
		
				

	}

}
