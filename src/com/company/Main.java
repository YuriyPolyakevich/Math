package com.company;

import java.util.Random;

public class Main {


    public static void main(String[] args) {
//        runTask1();
//        runTask2();
        int x2[] = {46, 47, 48, 49, -1, 2};
        BinarySearch search = new BinarySearch();
        final int x = search.findMinValueInShiftedSortedList(x2);
        System.out.println("Min value: " + x);
    }

    private static void runTask2() {
        final Task task2 = new PointInSegmentAmount();
        Integer x1[] = {2, 4, -1, 3};
        Integer x2[] = {5, 8, 0, 5};
        double q[] = {5, 2, 4};
        task2.solveTask(x1, x2, q);
    }

    private static void runTask1() {
        final Task task1 = new ConvexHullSquare();
        int x1[] = {-1, -1, 1, 1, 0};
        int y1[] = {-1, 1, -1, 1, 0};
        Object x = task1.solveTask(x1, y1);
        System.out.println(x);
    }
}
