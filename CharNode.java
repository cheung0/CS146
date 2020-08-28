package linked;

class CharNode
{
	private char		ch;
	private CharNode	next;
	
	
	CharNode(char ch)
	{
		this.ch = ch; 
	}
	
	
	CharNode getNext()
	{
		return next;
	}
	
	
	void setNext(CharNode next)
	{
		this.next = next;
	}
	
	
	char getData()
	{
		return ch;
	}
}