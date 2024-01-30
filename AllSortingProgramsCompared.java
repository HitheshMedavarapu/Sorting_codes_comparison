// Veera Venkata Naveen Kurakula
// N04320130


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.ClassNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;


public class AllSortingProgramsCompared {

    public static int comparisonsq = 0;
    public static int comparisonsh = 0;
    public static int comparisonsm = 0;
    
    public static void main(String[] args) throws ClassNotFoundException{
        int[] sizes = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        for (int size : sizes) {
            int[] array = generateRandomArray(size);

            System.out.println("Array size: " + size);

            int comparisonsInsertion = insertionSort(Arrays.copyOf(array, array.length));
            System.out.println("Insertion Sort comparisons: " + comparisonsInsertion);

            //int comparisonsQuick = quickSort(Arrays.copyOf(array, array.length),0,array.length-1,0);
            System.out.println("Quick Sort comparisons: ");

            int comparisonsShell = shellSort(Arrays.copyOf(array, array.length), "Shell");
            System.out.println("Shell Sort comparisons: " + comparisonsShell);

            int comparisonsHibbard = shellSort(Arrays.copyOf(array, array.length), "Hibbard");
            System.out.println("Shell Sort (Hibbard) comparisons: " + comparisonsHibbard);

            int comparisonsKnuth = shellSort(Arrays.copyOf(array, array.length), "Knuth");
            System.out.println("Shell Sort (Knuth) comparisons: " + comparisonsKnuth);

            int comparisonsGonnet = shellSort(Arrays.copyOf(array, array.length), "Gonnet");
            System.out.println("Shell Sort (Gonnet) comparisons: " + comparisonsGonnet);

            int comparisonsSedgewick = shellSort(Arrays.copyOf(array, array.length), "Shell");
            System.out.println("Shell Sort (Sedgewick) comparisons: " + comparisonsSedgewick);

            int comparisonsHeap = heapSort(Arrays.copyOf(array,array.length));
            System.out.println("Heap Sort comparisons: " + comparisonsHeap);

            int comparisonsMerge = mergeSort(Arrays.copyOf(array, array.length),0,array.length-1);
            System.out.println("Merge Sort comparisons: " + comparisonsMerge);

            double nlognvalue = calculateNLogN(size);
            System.out.println("Value of N log N : " + nlognvalue);

            System.out.println();
        }
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }

        return array;
    }

    private static int insertionSort(int[] array) {
        int comparisons = 0;

        int n = array.length;  
        for (int j = 1; j < n; j++) {  
            int key = array[j];  
            int i = j-1;  
            while ( (i > -1) && ( array [i] > key ) ) {  
                comparisons++;
                //comparisons++;
                array [i+1] = array [i];  
                i--;  
            }  
            array[i+1] = key;  
        }
        return comparisons;
    }
   
    /* 
    public static int quickSort(int[] ks,int start,int end,int comp){
       // int comparison =0;
        if(start>=end){
            return 0;
        }
        double p=ks[start];

        int i=start+1;
        int j=end;

        while(i<=j){
            //comparisonsq++;
            comp++;
            if(ks[i]>p && ks[j]<p){
                swap(i,j,ks);

            }
            else if(ks[i]<=p){
                i++;
            }
            else if(ks[j]>=p){
                j--;
            }
        }
        swap(j,start,ks);  // p is at start index

        quickSort(ks,start,j-1,++comp);
        quickSort(ks,j+1,end,++comp);

       // comparison = comparisonsq;
        //comparisonsq =0;
        return comp;
    }

    public static void swap(int s1,int s2,int[] ks){

        double temp=ks[s1];
        ks[s1]=ks[s2];
        ks[s2]=(int) temp;
    }
    */

    

    public static int shellSort(int[] arr, String sequenceType) {
        int comparisons = 0;
        int[] gaps = getGapSequence(arr.length, sequenceType);

        for (int gap : gaps) {
            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                    comparisons++;
                }
                arr[j] = temp;
                //if (j >= gap) comparisons++;
            }
        }
        return comparisons;
    }

    // Generates gap sequences based on the type
    private static int[] getGapSequence(int length, String type) {
        switch (type) {
            case "Shell":
                return shellSequence(length);
            case "Hibbard":
                return hibbardSequence(length);
            case "Knuth":
                return knuthSequence(length);
            case "Gonnet":
                return gonnetSequence(length);
            case "Sedgewick":
                return sedgewickSequence(length);
            default:
                return new int[]{1}; // Default to insertion sort
        }
    }

    // Gap sequences for different Shell Sort variations
    private static int[] shellSequence(int length) {
        int n = (int) (Math.log(length) / Math.log(2));
        int[] gaps = new int[n];
        for (int i = 0; i < n; i++) {
            gaps[i] = length / (int) Math.pow(2, i + 1);
        }
        return gaps;
    }


    private static int[] hibbardSequence(int length) {
        int k = 1;
        while ((Math.pow(2, k) - 1) < length) {
            k++;
        }
        int[] gaps = new int[k - 1];
        for (int i = 0; i < gaps.length; i++) {
            gaps[i] = (int) Math.pow(2, k - i - 1) - 1;
        }
        return gaps;
    }


    private static int[] knuthSequence(int length) {
        int k = 1;
        while ((Math.pow(3, k) - 1) / 2 < length) {
            k++;
        }
        int[] gaps = new int[k - 1];
        for (int i = 0; i < gaps.length; i++) {
            gaps[i] = (int) (Math.pow(3, k - i - 1) - 1) / 2;
        }
        return gaps;
    }


    private static int[] gonnetSequence(int length) {
        ArrayList<Integer> gapList = new ArrayList<>();
        for (double gap = length / 2.2; gap > 1; gap /= 2.2) {
            gapList.add((int) gap);
        }
        gapList.add(1);
        return gapList.stream().mapToInt(i -> i).toArray();
    }


    private static int[] sedgewickSequence(int length) {
        ArrayList<Integer> gapList = new ArrayList<>();
        for (int i = 0; gapList.isEmpty() || gapList.get(gapList.size() - 1) < length; i++) {
            int gap1 = (int) (Math.pow(4, i) + 3 * Math.pow(2, i - 1) + 1);
            int gap2 = (int) (9 * Math.pow(2, i) - 9 * Math.pow(2, i - 1) + 1);
            if (gap1 < length) gapList.add(gap1);
            if (gap2 < length) gapList.add(gap2);
        }
        Collections.sort(gapList);
        Collections.reverse(gapList);
        return gapList.stream().mapToInt(i -> i).toArray();
    }


    public static int heapSort(int[] arr) {
        int comparisons = 0;
        int n = arr.length;

        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            comparisons += heapify(arr, n, i);
        }

        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Call heapify on the reduced heap
            comparisons += heapify(arr, i, 0);
        }
        return comparisons;
    }

    // To heapify a subtree rooted with node i
    private static int heapify(int[] arr, int n, int i) {
        int comparisons = 0;
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        comparisons++;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        comparisons++;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            comparisons += heapify(arr, n, largest);
        }

        return comparisons;
    }

    public static int mergeSort(int[] arr, int l, int r) {
        int comparisons = 0;
        if (l < r) {
            int m = l + (r - l) / 2;

            comparisons += mergeSort(arr, l, m);
            comparisons += mergeSort(arr, m + 1, r);

            comparisons += merge(arr, l, m, r);
        }
        return comparisons;
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int comparisons = 0;

        int[] L = Arrays.copyOfRange(arr, l, m + 1);
        int[] R = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0;
        int k = l;
        while (i < L.length && j < R.length) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            comparisons++;
        }

        while (i < L.length) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < R.length) {
            arr[k] = R[j];
            j++;
            k++;
        }

        return comparisons;
    }
    
    private static int mergeSort(int[] array) {
        int comparisons = 0;

        int n = array.length;
        if (n > 1) {
            int mid = n / 2;

            // Create left and right subarrays
            int[] left = Arrays.copyOfRange(array, 0, mid);
            int[] right = Arrays.copyOfRange(array, mid, n);

            // Recursively sort the subarrays
            mergeSort(left);
            mergeSort(right);

            // Merge the sorted subarrays
            merge(array, left, right);
        
        
        }
        comparisons = comparisonsm;
        comparisonsm=0;
        return comparisons ;
    }
    private static void merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Compare elements of left and right subarrays and merge
        while (i < left.length && j < right.length) {
            comparisonsm++; // Increment the comparison count
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Copy remaining elements of left subarray, if any
        while (i < left.length) {
            array[k++] = left[i++];
        }

        // Copy remaining elements of right subarray, if any
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
    

    public static double calculateNLogN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0");
        }
        // Assuming logarithm base 2, you can change the base if needed
        return n * Math.log(n) / Math.log(2);
    }

}


    