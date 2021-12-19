package com.company;

public class Main {


    public static void main(String[] args) {
        final Task task1 = new ConvexHullSquare();
        int x1[] = {-4, 0, 2, 2};
        int y1[] = {0, 4, 2, 0};
        System.out.println(task1.solveTask(x1, y1));
    }
}
