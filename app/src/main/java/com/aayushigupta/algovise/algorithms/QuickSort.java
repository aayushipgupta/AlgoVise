package com.aayushigupta.algovise.algorithms;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.tools.MergeSortUtility;
import com.aayushigupta.algovise.tools.QuickSortUtility;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickSort extends Algorithm {

    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Static Data
    private static String description = null;
    // Execution Variables
    private DataNode[] array;
    private int[] original_array;
    private int iteration;
    private boolean isAlgoComplete;

    public QuickSort() {
        this.array = new DataNode[6];
        this.array[0] = new DataNode(3, Color.GREEN);
        this.array[1] = new DataNode(1, Color.GREEN);
        this.array[2] = new DataNode(8, Color.GREEN);
        this.array[3] = new DataNode(4, Color.GREEN);
        this.array[4] = new DataNode(2, Color.GREEN);
        this.array[5] = new DataNode(9, Color.GREEN);
        this.iteration = 0;
        this.isAlgoComplete = false;
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.quick_sort_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.quick_sort_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.quick_sort_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.quick_sort_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.quick_sort_c);
                byte[] cCodeBytes = new byte[cCodeStream.available()];
                cCodeStream.read(cCodeBytes);
                languageCodeMap.put(languages[3], new String(cCodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
        }
        reset();
    }

    @Override
    public void reset() {
        iteration = 0;
        isAlgoComplete = false;
        original_array = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            array[i].setColor(DATANODE_COLOR);
            original_array[i] = array[i].getValue();
        }
        AlgorithmLogSingleton.clearLogs();
        String logLine = "Array - [ ";
        for (int i = 0; i < array.length; i++) {
            logLine += array[i].getValue() + " ";
        }
        logLine += "] total items - " + array.length;
        if(array.length == 1) {
            logLine += "\nArray has been sorted";
            isAlgoComplete = true;
        }
        AlgorithmLogSingleton.addLogLine(logLine);
    }

    @Override
    public void executeNextStep() {
        if(!isAlgoComplete) {
            iteration++;
            // Set Values
            int[] sorted_array = QuickSortUtility.getSortResultTillIteration(original_array, iteration);
            for (int i = 0; i < array.length; i++) {
                array[i].setValue(sorted_array[i]);
            }
            // Set Colors
            if(QuickSortUtility.swap_index_1 == -1 || QuickSortUtility.swap_index_2 == -1) {
                for (int i = 0; i < array.length; i++) {
                    boolean arr1 = (i >= QuickSortUtility.last_low && i<= QuickSortUtility.last_high);
                    if(arr1) {
                        array[i].setColor(FAILURE_COLOR);
                    } else {
                        array[i].setColor(DATANODE_COLOR);
                    }
                }
            } else {
                for (int i = 0; i < array.length; i++) {
                    if(i == QuickSortUtility.swap_index_1 || i == QuickSortUtility.swap_index_2) {
                        array[i].setColor(SUCCESS_COLOR);
                    } else {
                        array[i].setColor(DATANODE_COLOR);
                    }
                }
            }
            String logLine = "Sorting array from " + QuickSortUtility.last_low + " to " + QuickSortUtility.last_high;
            logLine += "\n" + QuickSortUtility.last_log_line;
            isAlgoComplete = QuickSortUtility.isAlgoComplete;
            if(isAlgoComplete) {
                for (int i = 0; i < array.length; i++) {
                    array[i].setColor(DATANODE_COLOR);
                }
                logLine += "\nArray has been sorted";
            }
            AlgorithmLogSingleton.addLogLine(logLine);
        }
    }

    @Override
    public boolean isAlgoComplete() {
        return isAlgoComplete;
    }

    @Override
    public Object getDataStrucure() {
        return array;
    }

    @Override
    public void setDataStrucure(Object dataStrucure) {
        this.array = (DataNode[]) dataStrucure;
    }

    @Override
    public void setAlgoParams(List<String> params) {

    }

    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getMaxStepCount() {
        return array.length * array.length;
    }

    @Override
    public String getCode(String language) {
        return languageCodeMap.get(language);
    }

    @Override
    public String getFirstParamLabel() {
        return "Comma separated numbers";
    }

    @Override
    public String getSecondParamLabel() {
        return null;
    }
}
