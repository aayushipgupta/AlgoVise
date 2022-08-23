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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertionSort extends Algorithm {

    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Static Data
    private static String description = null;
    // Execution Variables
    private DataNode[] array;
    private int pointer1;
    private int pointer2;
    private boolean isAlgoComplete;

    public InsertionSort() {
        this.array = new DataNode[6];
        this.array[0] = new DataNode(3, Color.GREEN);
        this.array[1] = new DataNode(1, Color.GREEN);
        this.array[2] = new DataNode(8, Color.GREEN);
        this.array[3] = new DataNode(4, Color.GREEN);
        this.array[4] = new DataNode(2, Color.GREEN);
        this.array[5] = new DataNode(9, Color.GREEN);
        this.pointer1 = 0;
        this.pointer2 = 1;
        this.isAlgoComplete = false;
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.insertion_sort_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.insertion_sort_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.insertion_sort_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.insertion_sort_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.insertion_sort_c);
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
        pointer1 = 0;
        pointer2 = 1;
        isAlgoComplete = false;
        for (int i = 0; i < array.length; i++) {
            array[i].setColor(DATANODE_COLOR);
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
        if(!isAlgoComplete && pointer1 < array.length) {
            String logLine = "";
            if(pointer1 == array.length - 1) {
                logLine += "Array has been sorted";
                AlgorithmLogSingleton.addLogLine(logLine);
                isAlgoComplete = true;
                return;
            }
            if(pointer1 == pointer2 - 1) {
                logLine += "Doing iteration - " + pointer1 + "\n";
            }
            for (int i = 0; i < array.length; i++) {
                array[i].setColor(DATANODE_COLOR);
            }
            if (pointer1 < array.length && pointer2 < array.length) {
                int current = array[pointer1].getValue();
                int next = array[pointer2].getValue();
                if(next >= current) {
                    logLine += "Not swapping " + current + " and " + next;
                    // Do Not Swap Elements
                    array[pointer1].setColor(FAILURE_COLOR);
                    array[pointer2].setColor(FAILURE_COLOR);
                } else {
                    logLine += "Swapping " + current + " and " + next;
                    // Swap Elements
                    int temp = array[pointer2].getValue();
                    array[pointer2].setValue(array[pointer1].getValue());
                    array[pointer1].setValue(temp);
                    array[pointer1].setColor(SUCCESS_COLOR);
                    array[pointer2].setColor(SUCCESS_COLOR);
                }
            }
            pointer2 = pointer2 + 1;
            if(pointer2 == array.length) {
                pointer1 = pointer1 + 1;
                pointer2 = pointer1 + 1;
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
        return "Insertion Sort";
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
