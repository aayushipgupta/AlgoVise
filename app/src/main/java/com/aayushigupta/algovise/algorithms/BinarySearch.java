package com.aayushigupta.algovise.algorithms;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;
import com.aayushigupta.algovise.visualiser.SearchVisualiser;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearch extends Algorithm {

    // Static Data
    private static String description = null;
    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Execution Variables
    private DataNode[] array;
    private int pointer;
    private int beg, end;
    private int x = Integer.MIN_VALUE;
    private boolean isAlgoComplete;

    public BinarySearch() {
        this.array = new DataNode[6];
        this.array[0] = new DataNode(1, Color.GREEN);
        this.array[1] = new DataNode(2, Color.GREEN);
        this.array[2] = new DataNode(3, Color.GREEN);
        this.array[3] = new DataNode(4, Color.GREEN);
        this.array[4] = new DataNode(8, Color.GREEN);
        this.array[5] = new DataNode(9, Color.GREEN);
        this.x = 4;
        this.pointer = -1;
        this.beg = 0;
        this.end = 5;
        isAlgoComplete = false;
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.binary_search_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.binary_search_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.binary_search_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.binary_search_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.binary_search_c);
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
        pointer = -1;
        beg = 0;
        end = array.length - 1;
        this.isAlgoComplete = false;
        for (int i = 0; i < array.length; i++) {
            array[i].setColor(DATANODE_COLOR);
        }
        AlgorithmLogSingleton.clearLogs();
        String logLine = "Array - [ ";
        for (int i = 0; i < array.length; i++) {
            logLine += String.valueOf(array[i].getValue()) + " ";
        }
        logLine += "] total items - " + array.length;
        logLine += "\nSearching for " + x;
        AlgorithmLogSingleton.addLogLine(logLine);
    }

    @Override
    public void executeNextStep() {
        boolean completed = false;
        String logLine = "";
        logLine += "Searching at index: " + pointer;
        if (beg <= end) {
            pointer = (beg + end) / 2;
            int current = array[pointer].getValue();
            if (current == x) {
                logLine += "\n" +  x + " is found at position " + pointer;
                array[pointer].setColor(SUCCESS_COLOR);
                logLine += "\nBinary Search Completed.";
                completed = true;
            } else {
                if (current > x) {
                    logLine += "\nCurrent value: " + current + ", Going left";
                    end = pointer - 1;
                } else {
                    logLine += "\nCurrent value: " + current + ", Going right";
                    beg = pointer + 1;
                }
                array[pointer].setColor(FAILURE_COLOR);
            }
        } else {
            logLine += "\n" + x + " is not found in the Array.";
            logLine += "\nBinary Search Completed.";
            completed = true;
        }
        if(!isAlgoComplete) {
            AlgorithmLogSingleton.addLogLine(logLine);
        }
        isAlgoComplete = completed;
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
        // Get Numbers from Data
        DataNode[] unsorted = (DataNode[]) dataStrucure;
        int [] numbers = new int[unsorted.length];
        for(int i = 0; i < unsorted.length; i++) {
            numbers[i] = unsorted[i].getValue();
        }
        // Sort Numbers
        Arrays.sort(numbers);
        // Create datanodes array from sorted numbers
        this.array = new DataNode[unsorted.length];
        for(int i = 0; i < numbers.length; i++) {
            this.array[i] = new DataNode(numbers[i], Color.GREEN);
        }
    }

    @Override
    public void setAlgoParams(List<String> params) {
        if (params.size() > 0) x = (Integer) Integer.parseInt(params.get(0));
    }

    @Override
    public String getName() {
        return "Binary Search";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCode(String language) {
        return languageCodeMap.get(language);
    }

    @Override
    public int getMaxStepCount() {
        return array.length + 1;
    }

    @Override
    public String getFirstParamLabel() {
        return "Comma separated numbers";
    }

    @Override
    public String getSecondParamLabel() {
        return "Number to search";
    }
}
