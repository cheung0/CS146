/**
 * Name: Michael Cheung
 * Description of solution: Using template code, code up an implementation of heap sort 
 * code two helper methods for heap sort
 * @author Michael
 *
 */
package a3;

import static org.junit.Assert.*;


/**
 * 
 *
 */
public class MaxHeap {

    private int[] HeapArray; 
    public int[] getHeapArray() {
		return HeapArray;
	}

	private int size; 
    private int maxsize; 
  
    private static final int FRONT = 1; 
  
    public MaxHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = maxsize;
        HeapArray = new int[maxsize]; 
        //initialize heap array to a set of numbers, rearranged a little
        for (int i = FRONT; i < HeapArray.length; i++) {
        	HeapArray[i] = maxsize-i;
        }
    } 
  
    // Return the index of the parent of the node currently at pos 
    private int parent(int pos) 
    { 
        return (pos / 2); 
    } 
  
    // Return the index of the leftchild of the node currently at pos 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    // Return the index of the rightchild of the node currently at pos 
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 


    //Function to heapify the node at position i, up to the position n 
    //Fixes the max heap property from the position i to the position n
    private void maxHeapify(int i, int n) 
    { 
    	int[] heap = getHeapArray();
    	int left = leftChild(i);
    	int right = rightChild(i);
    	int max = -1;
    	if(left > maxsize || right > maxsize) {
    		return;
    	}
    	if(heap[left] > heap[right]) {
    		max = left;
    	}
    	else {
    		max = right;
    	}
    	if(heap[max] <= heap[i]) {
    		max = i;
    	}
    	if(n >= max && i != max) {
    		swap(i, max);
    		maxHeapify(max, n);
    	}
    }

    //Makes entire heap into a max heap starting from the middle and working
    //its way up to the starting index 
    public void buildMaxHeap() {
    	int[] heap = getHeapArray();
        for(int i = (maxsize / 2) - 1; i >= 1; i --) {
        	maxHeapify(i, maxsize);
        }
        
    }

    //Sorts an unordered array into a sorted array
    public void sort() {
    	//Turn into a max heap first
    	buildMaxHeap();
    	for(int i = maxsize - 1; i > 2; i --) {
    		swap(i, 1);
    		maxHeapify(1, i - 1);
    	}
    }

    
    
    // Swap two nodes of the heap at index first second
    private void swap(int first, int seconds) 
    { 
        int tmp; 
        tmp = HeapArray[first]; 
        HeapArray[first] = HeapArray[seconds]; 
        HeapArray[seconds] = tmp; 
    } 
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int SIZE = 30;
		MaxHeap heap = new MaxHeap(SIZE);
		heap.sort();
		int[] heapArr = heap.getHeapArray();
		assertEquals(heapArr[0], 0);
		assertEquals(heapArr[1], 1);
		assertEquals(heapArr[2], 2);
		assertEquals(heapArr[SIZE-1], SIZE-1);
		assertEquals(heapArr.length, SIZE);
		
		System.out.println("The minimum sum is:");
		System.out.println("heapArr[0] + heapArr[1] = " + (heapArr[0] + heapArr[1]));
		
		
	}

}
