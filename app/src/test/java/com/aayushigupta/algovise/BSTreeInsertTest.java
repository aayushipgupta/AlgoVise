package com.aayushigupta.algovise;

import static org.junit.Assert.assertEquals;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.algorithms.model.TreeNode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BSTreeInsertTest {

    Algorithm algorithm;
    private static int LENGTH = 6;



    @BeforeClass
    public static void defaultTree_isCorrect() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.BST_INSERT);
        Algorithm algo = AlgorithmFactory.getCurrentAlgorithm();
        TreeNode tree = (TreeNode) algo.getDataStrucure();
        assertEquals(3, tree.getValue());
        assertEquals(1, tree.getLeftChild().getValue());
        assertEquals(8, tree.getRightChild().getValue());
        assertEquals(4, tree.getRightChild().getLeftChild().getValue());
        assertEquals(2, tree.getLeftChild().getRightChild().getValue());
        assertEquals(9, tree.getRightChild().getRightChild().getValue());
    }

    @Before
    public void resetAlgorithm() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.BST_INSERT);
        algorithm = AlgorithmFactory.getCurrentAlgorithm();
    }

    @Test
    public void defaultInsertion_isCorrect() {
        algorithm.reset();
        assertEquals(LENGTH + 1, algorithm.getMaxStepCount());
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(LENGTH + 2, algorithm.getMaxStepCount());
        assertEquals(true, algorithm.isAlgoComplete());
        TreeNode tree = (TreeNode) algorithm.getDataStrucure();
        assertEquals(4, tree.getRightChild().getLeftChild().getLeftChild().getValue());
    }

    @Test
    public void negativeInsertion_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("-99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        assertEquals(LENGTH + 1, algorithm.getMaxStepCount());
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(LENGTH + 2, algorithm.getMaxStepCount());
        assertEquals(true, algorithm.isAlgoComplete());
        TreeNode tree = (TreeNode) algorithm.getDataStrucure();
        assertEquals(-99, tree.getLeftChild().getLeftChild().getValue());
    }

    @Test
    public void emptyInsertion_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("99");
        algorithm.setAlgoParams(params);
        algorithm.setDataStrucure(null);
        algorithm.reset();
        assertEquals(1, algorithm.getMaxStepCount());
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(2, algorithm.getMaxStepCount());
        assertEquals(true, algorithm.isAlgoComplete());
        TreeNode tree = (TreeNode) algorithm.getDataStrucure();
        assertEquals(99, tree.getValue());
    }

    @Test
    public void sameRepeatInsertion_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("99");
        algorithm.setAlgoParams(params);
        algorithm.setDataStrucure(null);
        // Insert 99 once
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        // Insert 99 second time
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        // Insert 99 third time
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        // Insert 99 fourth time
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(5, algorithm.getMaxStepCount());
        assertEquals(true, algorithm.isAlgoComplete());
        TreeNode tree = (TreeNode) algorithm.getDataStrucure();
        assertEquals(99, tree.getValue());
        assertEquals(99, tree.getLeftChild().getValue());
        assertEquals(99, tree.getLeftChild().getLeftChild().getValue());
        assertEquals(99, tree.getLeftChild().getLeftChild().getLeftChild().getValue());
    }

    @Test
    public void largeNumberInsertion_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("99999");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        assertEquals(LENGTH + 1, algorithm.getMaxStepCount());
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(LENGTH + 2, algorithm.getMaxStepCount());
        assertEquals(true, algorithm.isAlgoComplete());
        TreeNode tree = (TreeNode) algorithm.getDataStrucure();
        assertEquals(99999, tree.getRightChild().getRightChild().getRightChild().getValue());
    }
}
