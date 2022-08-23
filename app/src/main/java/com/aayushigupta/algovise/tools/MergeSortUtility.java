package com.aayushigupta.algovise.tools;

public class MergeSortUtility {

    public static int iteration = 0;
    public static int run_till_iteration = 0;
    public static boolean isAlgoComplete = false;
    public static int last_l, last_m, last_r;
    public static String last_log_line = "";

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
        last_l = l;
        last_m = m;
        last_r = r;
        last_log_line = "Merged array from " + l + ":" + m + " and " + (m+1) + ":" + r;
    }

    // Main function that sorts arr[l..r] using
    // merge()
    void sort(int arr[], int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m = l + (r-l)/2;

            // Sort first and second halves
            sort(arr, l, m);
            iteration++;
            if(iteration >= run_till_iteration) return;

            sort(arr, m + 1, r);
            iteration++;
            if(iteration >= run_till_iteration) return;

            // Merge the sorted halves
            merge(arr, l, m, r);
            iteration++;
            if(iteration >= run_till_iteration) return;
        }
        last_l = l;
        last_m = -1;
        last_r = r;
        last_log_line = "Sorted array from " + l + " to " + r;
    }

    public static int[] getSortResultTillIteration(int[] orig_array, int iteration_limit) {
        iteration = 0;
        run_till_iteration = iteration_limit;
        last_log_line = "";
        isAlgoComplete = false;
        MergeSortUtility ob = new MergeSortUtility();
        ob.sort(orig_array, 0, orig_array.length - 1);
        if(iteration < run_till_iteration) isAlgoComplete = true;
        return orig_array;
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver code
    public static void main(String args[])
    {
        int arr[] = { 12, 11, 13, 5, 6, 7 };

        System.out.println("Given Array");
        printArray(arr);

        run_till_iteration = 7;

        MergeSortUtility ob = new MergeSortUtility();
        ob.sort(arr, 0, arr.length - 1);

        System.out.println("\nLast log line:\n" + last_log_line);

        System.out.println("\nSorted array");
        printArray(arr);
    }
}
