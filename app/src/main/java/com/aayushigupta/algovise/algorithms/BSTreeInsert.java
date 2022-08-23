package com.aayushigupta.algovise.algorithms;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.algorithms.model.TreeNode;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BSTreeInsert extends Algorithm {

    private static final Map<String, String> languageCodeMap = new HashMap<String, String>();
    // Static Data
    private static String description = null;
    // Execution Variables
    private TreeNode rootNode;
    private int pointer;
    private int MAX_NODES = Integer.MAX_VALUE;
    private int x = Integer.MIN_VALUE;
    private int nodeCount = 0;
    private String treeNodeList;
    private boolean isAlgoComplete;

    public BSTreeInsert() {
        this.rootNode = new TreeNode(3, Color.GREEN);
        TreeNode leftNode = new TreeNode(1, Color.GREEN);
        TreeNode rightNode = new TreeNode(8, Color.GREEN);
        this.rootNode.setLeftChild(leftNode);
        this.rootNode.setRightChild(rightNode);
        rightNode.setLeftChild(new TreeNode(4, Color.GREEN));
        leftNode.setRightChild(new TreeNode(2, Color.GREEN));
        rightNode.setRightChild(new TreeNode(9, Color.GREEN));
        this.x = 4;
        this.isAlgoComplete = false;
        Resources res = AlgorithmFactory.getResources();
        if (res != null) {
            try {
                InputStream descriptionStream = res.openRawResource(R.raw.bst_insert_description);
                byte[] descriptionBytes = new byte[descriptionStream.available()];
                descriptionStream.read(descriptionBytes);
                description = new String(descriptionBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pseudocodeStream = res.openRawResource(R.raw.bst_insert_pseudocode);
                byte[] pseudocodeBytes = new byte[pseudocodeStream.available()];
                pseudocodeStream.read(pseudocodeBytes);
                languageCodeMap.put(languages[0], new String(pseudocodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream javaStream = res.openRawResource(R.raw.bst_insert_java);
                byte[] javacodeBytes = new byte[javaStream.available()];
                javaStream.read(javacodeBytes);
                languageCodeMap.put(languages[1], new String(javacodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream pythonStream = res.openRawResource(R.raw.bst_insert_python);
                byte[] pythoncodeBytes = new byte[pythonStream.available()];
                pythonStream.read(pythoncodeBytes);
                languageCodeMap.put(languages[2], new String(pythoncodeBytes));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
            try {
                InputStream cCodeStream = res.openRawResource(R.raw.bst_insert_c);
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
        MAX_NODES = 0;
        isAlgoComplete = false;
        resetColor(rootNode);
        AlgorithmLogSingleton.clearLogs();
        String logLine = "Array - [ ";
        treeNodeList = "";
        printNodes(rootNode);
        logLine += treeNodeList;
        nodeCount = 0;
        countNodes(rootNode);
        logLine += "] total items - " + nodeCount;
        logLine += "\nInserting value = " + x;
        AlgorithmLogSingleton.addLogLine(logLine);
    }

    private void resetColor(TreeNode treeNode) {
        treeNode.setColor(DATANODE_COLOR);
        for(TreeNode childNode : treeNode.getChildren().values()) {
            resetColor(childNode);
        }
    }

    private void printNodes(TreeNode treeNode) {
        treeNodeList += treeNode.getValue() + " ";
        for(TreeNode childNode : treeNode.getChildren().values()) {
            printNodes(childNode);
        }
    }

    private void countNodes(TreeNode treeNode) {
        nodeCount++;
        for(TreeNode childNode : treeNode.getChildren().values()) {
            countNodes(childNode);
        }
    }

    private boolean insertNode(TreeNode treeNode) {
        pointer++;
        if(pointer > MAX_NODES) return false;
        if(treeNode == null) {
            AlgorithmLogSingleton.addLogLine("Inserting as root node: " + x);
            this.rootNode = new TreeNode(x, SUCCESS_COLOR);
            return true;
        } else {
            treeNode.setColor(FAILURE_COLOR);
            if(x <= treeNode.getValue()) {
                if(treeNode.getLeftChild() == null) {
                    AlgorithmLogSingleton.addLogLine("Inserting to left of node: " + treeNode.getValue());
                    treeNode.setLeftChild(new TreeNode(x, SUCCESS_COLOR));
                    return true;
                } else {
                    if(pointer == MAX_NODES) {
                        AlgorithmLogSingleton.addLogLine("Going left of " + treeNode.getLeftChild().getValue());
                    }
                    return insertNode(treeNode.getLeftChild());
                }
            } else {
                if(treeNode.getRightChild() == null) {
                    AlgorithmLogSingleton.addLogLine("Inserting to right of node: " + treeNode.getValue());
                    treeNode.setRightChild(new TreeNode(x, SUCCESS_COLOR));
                    return true;
                } else {
                    if(pointer == MAX_NODES) {
                        AlgorithmLogSingleton.addLogLine("Going right of " + treeNode.getRightChild().getValue());
                    }
                    return insertNode(treeNode.getRightChild());
                }
            }
        }
    }

    @Override
    public void executeNextStep() {
        boolean completed = false;
        if (!isAlgoComplete) {
            pointer = 0;
            MAX_NODES++;
            completed = insertNode(rootNode);
            if(completed) {
                AlgorithmLogSingleton.addLogLine("Inserted " + x + " at depth = " + pointer);
                isAlgoComplete = true;
            }
        }
    }

    @Override
    public boolean isAlgoComplete() {
        return isAlgoComplete;
    }

    @Override
    public Object getDataStrucure() {
        return rootNode;
    }

    @Override
    public void setDataStrucure(Object dataStrucure) {
        this.rootNode = null;
        DataNode[] array = (DataNode[]) dataStrucure;
        for(DataNode node : array) {
            this.pointer = 0;
            this.x = node.getValue();
            insertNode(this.rootNode);
        }
        AlgorithmLogSingleton.clearLogs();
    }

    @Override
    public void setAlgoParams(List<String> params) {
        if (params.size() > 0) x = (Integer) Integer.parseInt(params.get(0));
    }

    @Override
    public String getName() {
        return "BST Insert";
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
        nodeCount = 0;
        countNodes(rootNode);
        return nodeCount + 1;
    }

    @Override
    public String getFirstParamLabel() {
        return "Comma separated numbers";
    }

    @Override
    public String getSecondParamLabel() {
        return "Number to insert";
    }

}
