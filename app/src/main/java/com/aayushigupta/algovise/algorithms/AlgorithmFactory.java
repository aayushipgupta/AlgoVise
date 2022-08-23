package com.aayushigupta.algovise.algorithms;

import android.content.res.Resources;

import com.aayushigupta.algovise.algorithms.model.Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmFactory {

    // Algorithm Types
    public static final String SEARCH_ALGORITHM = "Search";
    public static final String SORT_ALGORITHM = "Sort";
    public static final String LINEAR_ALGORITHM = "Linear";
    public static final String NON_LINEAR_ALGORITHM = "Non Linear";
    // Algorithm Icons
    public static final String SEARCH_ALGO_ICON = "ic_search_algo";
    public static final String SORT_ALGO_ICON = "ic_sort_algo";
    public static final String LINEAR_ALGO_ICON = "ic_linear_algo";
    public static final String NON_LINEAR_ALGO_ICON = "ic_non_linear_algo";
    // Algorithm Names
    public static final String LINEAR_SEARCH = "Linear Search";
    public static final String BINARY_SEARCH = "Binary Search";
    public static final String BUBBLE_SORT = "Bubble Sort";
    public static final String INSERTION_SORT = "Insertion Sort";
    public static final String MERGE_SORT = "Merge Sort";
    public static final String QUICK_SORT = "Quick Sort";
    public static final String LINEAR_STACK = "Linear Stack";
    public static final String LINKED_LIST = "Linked List";
    public static final String BST_INSERT = "BST Insert";
    public static final String BST_SEARCH = "BST Search";
    public static final String QUEUE = "queue";
    public static final String BFS = "bfs";
    public static final String DFS = "dfs";
    public static final String DIJKSTRA = "dijkstra";
    public static final String BELLMAN_FORD = "bellman_ford";
    public static final String N_QUEENS = "n_queens";
    private static final Map<String, List<AlgoModel>> algorithmMapByType;
    private static final List<String> algorithmTypes;
    private static Resources resources;
    private static Algorithm currentAlgorithm;

    static {
        // Algorithm Map
        algorithmMapByType = new HashMap<String, List<AlgoModel>>();
        List<AlgoModel> searchList = new ArrayList<AlgoModel>();
        searchList.add(new AlgoModel(LINEAR_SEARCH, SEARCH_ALGO_ICON));
        searchList.add(new AlgoModel(BINARY_SEARCH, SEARCH_ALGO_ICON));
        algorithmMapByType.put(SEARCH_ALGORITHM, searchList);
        List<AlgoModel> sortList = new ArrayList<AlgoModel>();
        sortList.add(new AlgoModel(BUBBLE_SORT, SORT_ALGO_ICON));
        sortList.add(new AlgoModel(INSERTION_SORT, SORT_ALGO_ICON));
        sortList.add(new AlgoModel(MERGE_SORT, SORT_ALGO_ICON));
        sortList.add(new AlgoModel(QUICK_SORT, SORT_ALGO_ICON));
        algorithmMapByType.put(SORT_ALGORITHM, sortList);
        List<AlgoModel> linearList = new ArrayList<AlgoModel>();
        linearList.add(new AlgoModel(LINEAR_STACK, LINEAR_ALGO_ICON));
        linearList.add(new AlgoModel(LINKED_LIST, LINEAR_ALGO_ICON));
        algorithmMapByType.put(LINEAR_ALGORITHM, linearList);
        List<AlgoModel> nonLinearList = new ArrayList<AlgoModel>();
        nonLinearList.add(new AlgoModel(BST_INSERT, NON_LINEAR_ALGO_ICON));
        nonLinearList.add(new AlgoModel(BST_SEARCH, NON_LINEAR_ALGO_ICON));
        algorithmMapByType.put(NON_LINEAR_ALGORITHM, nonLinearList);

        // Algorithm Types List
        algorithmTypes = new ArrayList<String>();
        algorithmTypes.add(SEARCH_ALGORITHM);
        algorithmTypes.add(SORT_ALGORITHM);
        algorithmTypes.add(LINEAR_ALGORITHM);
        algorithmTypes.add(NON_LINEAR_ALGORITHM);
    }

    public static void setCurrentAlgorithm(String algorithmTitle) {
        switch (algorithmTitle) {
            case LINEAR_SEARCH:
                currentAlgorithm = new LinearSearch();
                break;
            case BINARY_SEARCH:
                currentAlgorithm = new BinarySearch();
                break;
            case BUBBLE_SORT:
                currentAlgorithm = new BubbleSort();
                break;
            case INSERTION_SORT:
                currentAlgorithm = new InsertionSort();
                break;
            case MERGE_SORT:
                currentAlgorithm = new MergeSort();
                break;
            case QUICK_SORT:
                currentAlgorithm = new QuickSort();
                break;
            case LINEAR_STACK:
                currentAlgorithm = new LinearStack();
                break;
            case LINKED_LIST:
                currentAlgorithm = new LinearLinkedList();
                break;
            case BST_INSERT:
                currentAlgorithm = new BSTreeInsert();
                break;
            case BST_SEARCH:
                currentAlgorithm = new BSTreeSearch();
                break;
        }
    }

    public static void setCurrentAlgorithm(String algorithmType, int algoIndex) {
        switch (algorithmType) {
            case SEARCH_ALGORITHM:
                switch (algoIndex) {
                    case 0:
                        currentAlgorithm = new LinearSearch();
                        break;
                    case 1:
                        currentAlgorithm = new BinarySearch();
                        break;
                }
                break;
            case SORT_ALGORITHM:
                switch (algoIndex) {
                    case 0:
                        currentAlgorithm = new BubbleSort();
                        break;
                    case 1:
                        currentAlgorithm = new InsertionSort();
                        break;
                    case 2:
                        currentAlgorithm = new MergeSort();
                        break;
                    case 3:
                        currentAlgorithm = new QuickSort();
                        break;
                }
                break;
            case LINEAR_ALGORITHM:
                switch (algoIndex) {
                    case 0:
                        currentAlgorithm = new LinearStack();
                        break;
                    case 1:
                        currentAlgorithm = new LinearLinkedList();
                        break;
                }
                break;
            case NON_LINEAR_ALGORITHM:
                switch (algoIndex) {
                    case 0:
                        currentAlgorithm = new BSTreeInsert();
                        break;
                    case 1:
                        currentAlgorithm = new BSTreeSearch();
                        break;
                }
                break;
        }
    }

    public static Algorithm getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    public static Resources getResources() {
        return resources;
    }

    public static void setResources(Resources res) {
        resources = res;
    }

    public List<AlgoModel> getAlgorithmsByType(String type) {
        return algorithmMapByType.get(type);
    }

    public String getAlgorithmType(int typeIndex) {
        return algorithmTypes.get(typeIndex);
    }

    public static boolean isAlgorithmType(String algorithmType) {
        return algorithmType.equals(SEARCH_ALGORITHM) || algorithmType.equals(SORT_ALGORITHM) || algorithmType.equals(LINEAR_ALGORITHM) || algorithmType.equals(NON_LINEAR_ALGORITHM);
    }

    public static class AlgoModel {
        public String algorithmName;
        public String algorithmIcon;

        public AlgoModel(String algorithmName, String algorithmIcon) {
            this.algorithmName = algorithmName;
            this.algorithmIcon = algorithmIcon;
        }
    }

}
