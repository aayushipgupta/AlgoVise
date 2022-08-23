package com.aayushigupta.algovise;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.LinearLinkedList;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LinkedListUnitTest {

    Algorithm algorithm;
    private static int[] DEFAULT_ARRAY = {3, 1, 8, 4, 2, 9};
    private static int LENGTH = 6;
    private static int[] INSERT_AT_HEAD_ARRAY = {99, 3, 1, 8, 4, 2, 9};
    private static int[] DELETE_AT_HEAD_ARRAY = {1, 8, 4, 2, 9};
    private static int[] INSERT_AT_MID_ARRAY = {3, 1, 8, 99, 4, 2, 9};
    private static int[] DELETE_AT_MID_ARRAY = {3, 1, 8, 2, 9};
    private static int[] INSERT_AT_END_ARRAY = {3, 1, 8, 4, 2, 9, 99};
    private static int[] DELETE_AT_END_ARRAY = {3, 1, 8, 4, 2};

    @BeforeClass
    public static void defaultArray_isCorrect() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.LINKED_LIST);
        Algorithm algo = AlgorithmFactory.getCurrentAlgorithm();
        DataNode[] array = (DataNode[]) algo.getDataStrucure();
        assertEquals(LENGTH, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], array[i].getValue());
        }
    }

    @Before
    public void resetAlgorithm() {
        AlgorithmFactory.setCurrentAlgorithm(AlgorithmFactory.LINKED_LIST);
        algorithm = AlgorithmFactory.getCurrentAlgorithm();
    }

    @Test
    public void traverse_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("TRAVERSE");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        LinearLinkedList linkedListAlgo = (LinearLinkedList) algorithm;
        assertEquals(LENGTH, linkedListAlgo.pointer);
    }

    @Test
    public void insertAtHead_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("INSERT 0 99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH + 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(INSERT_AT_HEAD_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void deleteAtHead_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 0");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH - 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DELETE_AT_HEAD_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void insertAtMid_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("INSERT 3 99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH + 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(INSERT_AT_MID_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void deleteAtMid_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 3");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH - 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DELETE_AT_MID_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void insertAtEnd_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("INSERT 6 99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH + 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(INSERT_AT_END_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void deleteAtEnd_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 5");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH - 1, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DELETE_AT_END_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void insertBeyondEnd_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("INSERT 7 99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void deleteBeyondEnd_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 6");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(LENGTH, array.length);
        for(int i = 0; i < array.length; i++) {
            assertEquals(DEFAULT_ARRAY[i], array[i].getValue());
        }
    }

    @Test
    public void deleteAll_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 0");
        algorithm.setAlgoParams(params);
        for(int i = 0; i<LENGTH + 1; i++) {
            algorithm.reset();
            while(!algorithm.isAlgoComplete()) {
                algorithm.executeNextStep();
            }
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(0, array.length);
    }

    @Test
    public void insertToEmptyList_isCorrect() {
        List<String> params = new ArrayList<String>();
        params.add("DELETE 0");
        algorithm.setAlgoParams(params);
        for(int i = 0; i<LENGTH + 1; i++) {
            algorithm.reset();
            while(!algorithm.isAlgoComplete()) {
                algorithm.executeNextStep();
            }
        }
        params = new ArrayList<String>();
        params.add("INSERT 0 99");
        algorithm.setAlgoParams(params);
        algorithm.reset();
        while(!algorithm.isAlgoComplete()) {
            algorithm.executeNextStep();
        }
        DataNode[] array = (DataNode[]) algorithm.getDataStrucure();
        assertEquals(1, array.length);
        assertEquals(99, array[0].getValue());
    }

}