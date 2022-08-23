package com.aayushigupta.algovise;

import static org.junit.Assert.assertEquals;

import android.graphics.Color;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.LinearLinkedList;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InsertionSortTest {

    Algorithm algorithm;
    private static int[] DEFAULT_ARRAY = {3, 1, 8, 4, 2, 9};
    private static int[] REVERSE_ARRAY = {9, 8, 4, 3, 2, 1};
    private static int[] SORT_FIRST_STEP_ARRAY = {1, 3, 8, 4, 2, 9};
    private static int[] SORTED_ARRAY = {1, 2, 3, 4, 8, 9};
    private static int LENGTH = 6;

    @BeforeClass
    public static void defaultArray_isCorrect() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.INSERTION_SORT);
        Algorithm algo = AlgorithmFactory.getCurrentAlgorithm();
        DataNode[] array = (DataNode[]) algo.getDataStrucure();
        assertEquals(LENGTH, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], array[i].getValue());
        }
    }

    @Before
    public void resetAlgorithm() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.INSERTION_SORT);
        algorithm = AlgorithmFactory.getCurrentAlgorithm();
    }

    @Test
    public void defaultSorting_isCorrect() {
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(true, algorithm.isAlgoComplete());
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        for(int i = 0; i < array.length; i++) {
            assertEquals(SORTED_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void sortingFirstStep_isCorrect() {
        algorithm.reset();
        algorithm.executeNextStep();
        assertEquals(false, algorithm.isAlgoComplete());
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        for(int i = 0; i < array.length; i++) {
            assertEquals(SORT_FIRST_STEP_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void sortingReverse_isCorrect() {
        DataNode[] reverseArray = new DataNode[LENGTH];
        for(int i = 0; i < reverseArray.length; i++) {
            reverseArray[i] = new DataNode(REVERSE_ARRAY[i], Color.GREEN);
        }
        algorithm.setDataStrucure(reverseArray);
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(true, algorithm.isAlgoComplete());
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        for(int i = 0; i < array.length; i++) {
            assertEquals(SORTED_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void sortingEmpty_isCorrect() {
        DataNode[] emptyArray = new DataNode[0];
        algorithm.setDataStrucure(emptyArray);
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(true, algorithm.isAlgoComplete());
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(0, array.length);
    }

    @Test
    public void sortingSingle_isCorrect() {
        DataNode[] singleArray = new DataNode[1];
        singleArray[0] = new DataNode(1, Color.GREEN);
        algorithm.setDataStrucure(singleArray);
        algorithm.reset();
        for(int i = 0; i < algorithm.getMaxStepCount(); i++) {
            algorithm.executeNextStep();
        }
        assertEquals(true, algorithm.isAlgoComplete());
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(1, array.length);
        assertEquals(1, array[0].getValue());
    }

}
