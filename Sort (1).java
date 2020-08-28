package a4;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

/**
 * Name: Michael Cheung
 * Student ID: 014373619
 * Description of solution: Using template code, code up implementations of various sorting algorithms,
 * such as insertion sort, selection sort, heap sort, merge sort, quick sort, and bucket sort with various 
 * helper methods included. Run tests to compare these sorting algorithms' runtime.
 * @author Michael
 *
 */
public class Sort {

	/**
	 * Build a random array
	 * @param rand a Random object
	 * @param LENGTH The range of the integers in the array 
	 *             will be from 0 to LENGTH-1
	 * @return
	 */
	private static int[] build_random_array(Random rand, int LENGTH) {
		int[] array = new int[LENGTH];
		//set index 0 to 0 for consistency with CLRS, where sorting starts at index 1
		array[0] = 0; 
		for (int i = 1; i < LENGTH; i++) {
	        // Generate random integers in range 0 to 999 
	        int rand_int = rand.nextInt(LENGTH); 
	        array[i] = rand_int;
		}
		return array;
	}

	/**
	 * Assert the array is sorted correctly
	 * @param array_unsorted The original unsorted array
	 * @param array_sorted The sorted array
	 */
	public static void assert_array_sorted(int[] array_unsorted, int[] array_sorted) {
		int a_sum = array_unsorted[0];
		int b_sum = array_sorted[0];
		for (int i = 1; i < array_unsorted.length; i++) {
			a_sum += array_unsorted[i];
	    }
		for (int i = 1; i < array_sorted.length; i++) {
			b_sum += array_sorted[i];
			assertTrue(array_sorted[i-1] <= array_sorted[i]);
	    }
		assertEquals(a_sum, b_sum);
	}
	
	
	public static void insertionSort(int[] array) {
		// sorted part of array starts at 1st index, ends at last index
		for(int i = 1; i < array.length; i ++) {
			int key = array[i];
			int k = i - 1;
			
			// loop through unsorted part of array and move elements one by one
			// into the sorted part of array
			while(k >= 0 && array[k] > key) {
				array[k + 1] = array[k];
				k -= 1;
			}
			array[k + 1] = key;
		}
	}


	// finds smallest number from unsorted part of array at puts it at beginning
	public static void selectionSort(int[] array) {
		// loops through all elements in array except last element
		for(int i = 0; i < array.length - 1; i ++) {
			
			// find smallest number in unsorted part of array
			int minimumIndex = i;
			for(int k = i + 1; k < array.length; k ++) {
				if(array[minimumIndex] > array[k]) {
					minimumIndex = k;
				}
 			}
			
			// swap smallest number with element at first index
			int temp = array[minimumIndex];
			array[minimumIndex] = array[i];
			array[i] = temp;
		}
	}


	public static void heapSort(int[] array) {
		// initialize variables
		int size = array.length;
		
		// build max heap
		for(int i = size / 2 - 1; i >= 0; i --) {
			maxHeapify(array, size, i);
		}
		
		for(int i = size - 1; i > 0; i --) {
			// swap first and last elements
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			
			// maintain heap property 
			maxHeapify(array, i, 0);
		}
	}
	
	// helper method for heap sort
	public static void maxHeapify(int[] array, int size, int i) {
		// initialize variables
		int max = i;
		int left = (2 * i) + 1;
		int right = (2 * i) + 2;
		
		if(left < size && array[left] > array[max]) {
			max = left;
		}
		
		if(right < size && array[right] > array[max]) {
			max = right;
		}
		
		// if max is not the same, do the swap
		if(max != i) {
			int temp = array[i];
			array[i] = array[max];
			array[max] = temp;
			// recursive call to heapify downwards
			maxHeapify(array, size, max);
		}		
	}

	public static void mergeSort(int[] array, int left, int right) {
		if(left < right) {
			// initialize middle 
			int middle = (left + right) / 2;
			
			// split into two recursive portions then merge together left and right parts
			mergeSort(array, left, middle);
			mergeSort(array, middle + 1, right);
			merge(array, left, right, middle);
		}
		
	}
	
	// helper method for merge sort
	// merges two sorted arrays together
	public static void merge(int[] array, int left, int right, int middle) {
		// initialize left and right arrays with correct sizes 
		// and indices of the two arrays and index of merged array
		int leftSize = middle - left + 1;
		int rightSize = right - middle;
		int[] leftArray = new int[leftSize];
		int[] rightArray = new int[rightSize];
		int i = 0;
		int j = 0;
		int k = left;
		
		// put data from array into left and right arrays
		for(int i1 = 0; i1 < leftSize; i1 ++) {
			leftArray[i1] = array[left + i1];
		}
		for(int i2 = 0; i2 < rightSize; i2 ++) {
			rightArray[i2] = array[1 + i2 + middle];
		}
		
		// intitially place elements from left and right arrays into merged array
		while(i < leftSize && j < rightSize) {
			if(leftArray[i] <= rightArray[j]) {
				array[k] = leftArray[i];
				i ++;
				k ++;
			}
			else {
				array[k] = rightArray[j];
				j ++;
				k ++;
			}
		}
		
		// copy over leftovers
		while(i < leftSize) {
			array[k] = leftArray[i];
			i ++;
			k ++;
		}
		while(j < rightSize) {
			array[k] = rightArray[j];
			j ++;
			k ++;
		}
	}

	// make sure to set high to be the index of the last element in the array
	public static void quickSort(int[] array, int low, int high) {
		if(low < high) {
			int pivotIndex = partition(array, low, high);
			
			quickSort(array, low, pivotIndex - 1);
			quickSort(array, pivotIndex + 1, high);
		}
	}
	
	// sets last element to be pivot initially, then places it in 
	// the array such that all elements to the left of it is smaller
	// than itself, and all elements to the right of it is bigger than 
	// itself
	public static int partition(int[] array, int low, int high) {
		int pivot = array[high];
		int index = low - 1;
		
		for(int i = low; i <= high - 1; i ++) {
			// if element is smaller than pivot
			if(array[i] < pivot) {
				index ++;
				
				// swap
				int temp = array[index];
				array[index] = array[i];
				array[i] = temp;
			}
		}
		
		// swap 
		int temp = array[high];
		array[high] = array[index + 1];
		array[index + 1] = temp;
		
		return index + 1;
	}

	public static void bucketSort(int[] array) {
		int bucketCount = array.length/2;
		int minIntValue = 0;
		int maxIntValue = array.length - 1;
        // Create bucket array
        List<Integer>[] buckets = new List[bucketCount];
        // Associate a list with each index in the bucket array         
        for(int i = 0; i < bucketCount; i++){
            buckets[i] = new LinkedList<>();
        }
        
        
        
        // Assign integers from array to the proper bucket
        for(int i = 0; i < array.length; i ++) {
        	int bucketIndex = Math.abs(bucketCount * array[i] / (maxIntValue));
        	if(bucketCount <= bucketIndex) {
        		bucketIndex = bucketCount - 1;
        	}
        	
        	buckets[bucketIndex].add(array[i]);
        }
        
        // sort buckets
        for(List<Integer> bucket : buckets){
            Collections.sort(bucket);
        }
        int i = 0;
        // Merge buckets to get sorted array
        for(List<Integer> bucket : buckets){
            for(int num : bucket){
                array[i++] = num;
            }
        }
	}

	public static void main(String[] args) {
		int NUM_RUNS = 3;
        // create instance of Random class 
        Random rand = new Random(); 
        
        /////////////////////////////////////////
		int LENGTH=100;
		System.out.println("_____________INPUT "+LENGTH+"_____________");
		int[] array_100 = build_random_array(rand, LENGTH);

		//For runtime computations
		long startTime, endTime;
		
		double duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			insertionSort(array_100_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		
		
		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			selectionSort(array_100_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			heapSort(array_100_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			mergeSort(array_100_c, 0, array_100_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			quickSort(array_100_c, 0, array_100_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100_c = array_100.clone();
			startTime = System.currentTimeMillis();
			bucketSort(array_100_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
			assert_array_sorted(array_100, array_100_c);
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

        /////////////////////////////////////////
		LENGTH=1000;
		System.out.println("_____________INPUT "+LENGTH+"_____________");
		int[] array_1000 = build_random_array(rand, LENGTH);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			insertionSort(array_1000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		
		
		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			selectionSort(array_1000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			heapSort(array_1000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			mergeSort(array_1000_c, 0, array_1000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			quickSort(array_1000_c, 0, array_1000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_1000_c = array_1000.clone();
			startTime = System.currentTimeMillis();
			bucketSort(array_1000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		
		
        /////////////////////////////////////////
		LENGTH=10000;
		System.out.println("_____________INPUT "+LENGTH+"_____________");
		int[] array_10000 = build_random_array(rand, LENGTH);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			insertionSort(array_10000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		
		
		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			selectionSort(array_10000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			heapSort(array_10000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			mergeSort(array_10000_c, 0, array_10000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			quickSort(array_10000_c, 0, array_10000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_10000_c = array_10000.clone();
			startTime = System.currentTimeMillis();
			bucketSort(array_10000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		
        /////////////////////////////////////////
		LENGTH=100000;
		System.out.println("_____________INPUT "+LENGTH+"_____________");
		int[] array_100000 = build_random_array(rand, LENGTH);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			insertionSort(array_100000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("InsertionSort mean runtime over " + NUM_RUNS + " runs is " + duration);
		
		
		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			selectionSort(array_100000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("SelectionSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			heapSort(array_100000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("HeapSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			mergeSort(array_100000_c, 0, array_100000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("MergeSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			quickSort(array_100000_c, 0, array_100000_c.length - 1);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("QuickSort mean runtime over " + NUM_RUNS + " runs is " + duration);

		duration = 0;
		for (int t = 0 ; t < NUM_RUNS ; t++) {
			int[] array_100000_c = array_100000.clone();
			startTime = System.currentTimeMillis();
			bucketSort(array_100000_c);
			endTime = System.currentTimeMillis();
			duration += ((double) (endTime - startTime));
		}
		duration = duration / (double) NUM_RUNS;
		System.out.println("BucketSort mean runtime over " + NUM_RUNS + " runs is " + duration);

	}

}
