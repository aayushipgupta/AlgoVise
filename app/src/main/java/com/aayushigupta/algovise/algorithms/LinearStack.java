package com.aayushigupta.algovise.algorithms;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LinearStack extends Algorithm {

    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Static Data
    private static String description = null;
    // Execution Variables
    private Stack<DataNode> stack;
    private int data = 0;
    private enum OperationType {
        PEEK,
        PUSH,
        POP
    }
    private OperationType operationType = OperationType.PEEK;
    private boolean lastOperationPop;
    private boolean isAlgoComplete;

    public LinearStack() {
        stack = new Stack<DataNode>();
        stack.push(new DataNode(3, Color.GREEN));
        stack.push(new DataNode(1, Color.GREEN));
        stack.push(new DataNode(8, Color.GREEN));
        stack.push(new DataNode(4, Color.GREEN));
        stack.push(new DataNode(2, Color.GREEN));
        stack.push(new DataNode(9, Color.GREEN));
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.linear_stack_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.linear_stack_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.linear_stack_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.linear_stack_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.linear_stack_c);
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
        this.isAlgoComplete = false;
        this.lastOperationPop = false;
        for (int i = 0; i < stack.size(); i++) {
            stack.get(i).setColor(DATANODE_COLOR);
        }
        AlgorithmLogSingleton.clearLogs();
        String logLine = "Stack - [ ";
        for (int i = 0; i < stack.size(); i++) {
            logLine += stack.get(i).getValue() + " ";
        }
        logLine += "] total items - " + stack.size();
        switch(operationType) {
            case PEEK:
                logLine += "\nPeeking into the stack";
                break;
            case PUSH:
                logLine += "\nPushing data:" + data + " into the stack";
                break;
            case POP:
                logLine += "\nPopping from the stack";
                break;
        }
        AlgorithmLogSingleton.addLogLine(logLine);
    }

    @Override
    public void executeNextStep() {
        String logLine = "";
        if(lastOperationPop) {
            if(!stack.isEmpty()) {
                stack.pop();
                operationType = OperationType.PEEK;
            }
            lastOperationPop = false;
        }
        switch(operationType) {
            case PEEK:
                logLine += "The top of stack has value: " + stack.peek().getValue();
                stack.peek().setColor(FAILURE_COLOR);
                AlgorithmLogSingleton.addLogLine(logLine);
                this.isAlgoComplete = true;
                break;
            case PUSH:
                logLine += "Pushed the data: " + data + " into the stack";
                stack.push(new DataNode(data, FAILURE_COLOR));
                operationType = OperationType.PEEK;
                AlgorithmLogSingleton.addLogLine(logLine);
                break;
            case POP:
                logLine += "Popping the data: " + stack.peek().getValue() + " from the stack";
                stack.peek().setColor(SUCCESS_COLOR);
                lastOperationPop = true;
                AlgorithmLogSingleton.addLogLine(logLine);
                break;
        }
    }

    @Override
    public boolean isAlgoComplete() {
        return isAlgoComplete;
    }

    @Override
    public Object getDataStrucure() {
        DataNode[] array = new DataNode[stack.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = stack.get(i);
        }
        return array;
    }

    @Override
    public void setDataStrucure(Object dataStrucure) {
        DataNode[] array = (DataNode[]) dataStrucure;
        stack = new Stack<DataNode>();
        for (int i = 0; i < array.length; i++) {
            stack.push(array[i]);
        }
    }

    @Override
    public void setAlgoParams(List<String> params) {
        String operationParam = (String) params.get(0);
        String[] operations = operationParam.split(" ");
        operationType = OperationType.valueOf(operations[0]);
        if(operations.length > 1)
            data = Integer.parseInt(operations[1]);
    }

    @Override
    public String getName() {
        return "Linear Stack";
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
        return 3;
    }

    @Override
    public String getFirstParamLabel() {
        return "Comma separated numbers";
    }

    @Override
    public String getSecondParamLabel() {
        return "Value to push onto stack";
    }

    @Override
    public String getSecondButtonLabel() {
        return "Push";
    }

    @Override
    public String getThirdParamLabel() {
        return null;
    }

    @Override
    public String getThirdButtonLabel() {
        return "Pop";
    }

    @Override
    public String getOperations() {
        return operationType.name() + " " + data;
    }

}
