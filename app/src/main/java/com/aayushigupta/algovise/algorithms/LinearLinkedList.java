package com.aayushigupta.algovise.algorithms;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LinearLinkedList extends Algorithm {

    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Static Data
    private static String description = null;
    // Execution Variables
    private List<DataNode> list;
    public int pointer = 0;
    private enum OperationType {
        TRAVERSE,
        INSERT,
        DELETE
    }
    OperationType operationType = null;
    private int targetIndex = 0;
    private int data = 0;
    private boolean isAlgoComplete;
    private String lastOperation;

    public LinearLinkedList() {
        list = new Stack<DataNode>();
        list.add(new DataNode(3, Color.GREEN));
        list.add(new DataNode(1, Color.GREEN));
        list.add(new DataNode(8, Color.GREEN));
        list.add(new DataNode(4, Color.GREEN));
        list.add(new DataNode(2, Color.GREEN));
        list.add(new DataNode(9, Color.GREEN));
        this.operationType = OperationType.TRAVERSE;
        this.isAlgoComplete = false;
        this.lastOperation = "";
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.linear_linked_list_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.linear_linked_list_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.linear_linked_list_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.linear_linked_list_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.linear_linked_list_c);
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
        pointer = 0;
        isAlgoComplete = false;
        this.lastOperation = "";
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setColor(DATANODE_COLOR);
        }
        AlgorithmLogSingleton.clearLogs();
        String logLine = "Linked List - [ ";
        for (int i = 0; i < list.size(); i++) {
            logLine += list.get(i).getValue() + " ";
        }
        logLine += "] total items - " + list.size();
        switch(operationType) {
            case TRAVERSE:
                logLine += "\nTraversing the Linked List";
                break;
            case INSERT:
                logLine += "\nInserting " + data + " into the Linked List at " + targetIndex + "th position";
                break;
            case DELETE:
                logLine += "\nDeleting from the Linked List from " + targetIndex + "th position";
                break;
        }
        AlgorithmLogSingleton.addLogLine(logLine);
    }

    @Override
    public void executeNextStep() {
        if(!isAlgoComplete) {
            String logLine = "";
            if(!lastOperation.isEmpty()) {
                if(lastOperation.endsWith(":-1")) {
                    int deleteIndex = Integer.parseInt(lastOperation.replace(":-1", ""));
                    list.remove(deleteIndex);
                    logLine += "\nNode deleted successfully.";
                    isAlgoComplete = true;
                    AlgorithmLogSingleton.addLogLine(logLine);
                }
                lastOperation = "";
                return;
            }
            boolean completed = false;
            switch(operationType) {
                case TRAVERSE:
                    logLine += "Pointer at index: " + pointer;
                    if (pointer < list.size()) {
                        int current = list.get(pointer).getValue();
                        logLine += "\nCurrent value: " + current + "\nMoving Forward";
                        list.get(pointer).setColor(FAILURE_COLOR);
                        pointer++;
                    } else {
                        logLine += "\nList traversal finished.";
                        completed = true;
                    }
                    if (!isAlgoComplete) {
                        AlgorithmLogSingleton.addLogLine(logLine);
                    }
                    isAlgoComplete = completed;
                    break;
                case INSERT:
                    logLine += "Pointer at index: " + pointer;
                    if (pointer < list.size()) {
                        int current = list.get(pointer).getValue();
                        logLine += "\nCurrent value: " + current;
                        if(pointer == targetIndex) {
                            logLine += "\nSetting current node's next link to new node" +
                                    "\nNew node's next link to current node's next";
                            logLine += "\nNode inserted successfully.";
                            list.add(targetIndex, new DataNode(data, SUCCESS_COLOR));
                            completed = true;
                        } else {
                            logLine += "\nMoving Forward";
                            list.get(pointer).setColor(FAILURE_COLOR);
                            pointer++;
                        }
                    } else {
                        if(list.size() == targetIndex) {
                            logLine += "\nSetting last node's next link to new node" +
                                    "\nNew node's next link to null";
                            logLine += "\nNode inserted successfully.";
                            list.add(targetIndex, new DataNode(data, SUCCESS_COLOR));
                        } else {
                            logLine += "\nList finished before reaching " + targetIndex + "th index";
                        }
                        completed = true;
                    }
                    if (!isAlgoComplete) {
                        AlgorithmLogSingleton.addLogLine(logLine);
                    }
                    isAlgoComplete = completed;
                    break;
                case DELETE:
                    logLine += "Pointer at index: " + pointer;
                    if (pointer < list.size()) {
                        int current = list.get(pointer).getValue();
                        logLine += "\nCurrent value: " + current;
                        if(pointer == targetIndex) {
                            logLine += "\nSetting previous node's next link to current node's next node";
                            lastOperation = targetIndex + ":-1";
                        } else {
                            logLine += "\nMoving Forward";
                            list.get(pointer).setColor(FAILURE_COLOR);
                            pointer++;
                        }
                    } else {
                        logLine += "\nList finished before reaching " + targetIndex + "th index";
                        completed = true;
                    }
                    if (!isAlgoComplete) {
                        AlgorithmLogSingleton.addLogLine(logLine);
                    }
                    isAlgoComplete = completed;
                    break;
            }
        }
    }

    @Override
    public boolean isAlgoComplete() {
        return isAlgoComplete;
    }

    @Override
    public Object getDataStrucure() {
        DataNode[] array = new DataNode[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    @Override
    public void setDataStrucure(Object dataStrucure) {
        DataNode[] array = (DataNode[]) dataStrucure;
        list = new ArrayList<DataNode>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
    }

    @Override
    public void setAlgoParams(List<String> params) {
        String operationParam = (String) params.get(0);
        String[] operations = operationParam.split(" ");
        operationType = OperationType.valueOf(operations[0]);
        if(operations.length > 1)
        targetIndex = Integer.parseInt(operations[1]);
        if(operations.length > 2)
        data = Integer.parseInt(operations[2]);
    }

    @Override
    public String getName() {
        return "Linked List";
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
        return list.size() + 2;
    }

    @Override
    public String getFirstParamLabel() {
        return "Comma separated numbers";
    }

    @Override
    public String getSecondParamLabel() {
        return "Index of insert or delete";
    }

    @Override
    public String getSecondButtonLabel() {
        return "DEL";
    }

    @Override
    public String getThirdParamLabel() {
        return "Value to insert or delete";
    }

    @Override
    public String getThirdButtonLabel() {
        return "ADD";
    }

    @Override
    public String getOperations() {
        return operationType.name() + " " + targetIndex + " " + data;
    }

    @Override
    public boolean getShowArrow() {
        return true;
    }

}
