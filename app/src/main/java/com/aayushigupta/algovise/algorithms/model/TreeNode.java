package com.aayushigupta.algovise.algorithms.model;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    private int value;
    private int color;
    private Map<Integer, TreeNode> children;

    public TreeNode(int value, int color) {
        this.value = value;
        this.color = color;
        this.children = new HashMap<Integer, TreeNode>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public TreeNode getLeftChild() {
        if(children.containsKey(0)) {
            return children.get(0);
        }
        return null;
    }

    public void setLeftChild(TreeNode child) {
        children.put(0, child);
    }

    public TreeNode getRightChild() {
        if(children.containsKey(1)) {
            return children.get(1);
        }
        return null;
    }

    public void setRightChild(TreeNode child) {
        children.put(1, child);
    }

    public Map<Integer, TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Integer, TreeNode> children) {
        this.children = children;
    }
}
