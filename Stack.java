package graphs;

// implemented the ADT Stack with linked list of nodes
public class Stack<T> {
	public Node<T> head;
	
	
	public class Node<T> {
		public Node<T> next;
		public T data;
		
		public Node(T data) {
			this.data = data;
		}
	}
	
	
	//adds node to top of stack
	public void push(T data) {
		Node<T> newNode = new Node<T>(data);
		newNode.next = head;
		head = newNode;
	}
	
	
	//removes and returns the first node on the stack
	public Node<T> pop() {
		Node<T> popNode = head;
		head = head.next;
		popNode.next = null;
		return popNode;
	}
	
	
	//returns first node on the stack
	public Node<T> peek() {
		return head;
	}

	public static void main(String[] args) {
		Stack<String> books = new Stack<>();
		books.push("Cracking the Coding Interview");
		books.push("How to Kill a Mockingbird");
		books.push("Physics with Mike");
		
		
		System.out.println(books.peek().data);
		System.out.println(books.peek().data);

	}

}
