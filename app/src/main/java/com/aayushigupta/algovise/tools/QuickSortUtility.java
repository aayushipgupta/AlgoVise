package com.aayushigupta.algovise.tools;

public class QuickSortUtility {

    public static int iteration = 0;
    public static int run_till_iteration = 0;
    public static boolean isAlgoComplete = false;
    public static int last_low, last_high;
    public static int swap_index_1, swap_index_2;
    public static String last_log_line = "";

    /*
     * This function takes last element as pivot, places the pivot element at its
     * correct position in sorted array, and places all smaller (smaller than pivot)
     * to left of pivot and all greater elements to right of pivot
     */
    int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                if(i != j) {
                    swap_index_1 = i;
                    swap_index_2 = j;
                    last_log_line = "Pivot: " + pivot + ", Swapping with " + arr[i] + " and " + arr[j];
                    iteration++;
                    if(iteration >= run_till_iteration) return i + 1;
                }
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        swap_index_1 = i + 1;
        swap_index_2 = high;
        last_log_line = "Pivot: " + pivot + ", Swapping " + arr[i + 1] + " and " + arr[high];
        iteration++;
        if(iteration >= run_till_iteration) return i + 1;

        return i + 1;
    }

    /*
     * The main function that implements QuickSort() arr[] --> Array to be sorted,
     * low --> Starting index, high --> Ending index
     */
    void sort(int arr[], int low, int high) {
        if (low < high) {
            last_low = low;
            last_high = high;
            /*
             * pi is partitioning index, arr[pi] is now at right place
             */
            int pi = partition(arr, low, high);
            if(iteration >= run_till_iteration) return;

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            if(iteration >= run_till_iteration) return;
            sort(arr, pi + 1, high);
            if(iteration >= run_till_iteration) return;
        }
    }

    public static int[] getSortResultTillIteration(int[] orig_array, int iteration_limit) {
        iteration = 0;
        run_till_iteration = iteration_limit;
        last_log_line = "";
        isAlgoComplete = false;
        QuickSortUtility ob = new QuickSortUtility();
        ob.sort(orig_array, 0, orig_array.length - 1);
        if(iteration < run_till_iteration) isAlgoComplete = true;
        return orig_array;
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver program
    public static void main(String args[]) {

        int arr[] = { 10, 7, 8, 9, 1, 5 };
        int n = arr.length;

        System.out.println("Given Array");
        printArray(arr);

        run_till_iteration = 5;

        QuickSortUtility ob = new QuickSortUtility();
        ob.sort(arr, 0, n - 1);

        System.out.println("\nLast log line:\n" + last_log_line);

        System.out.println("\nSorted array");
        printArray(arr);
    }

}
