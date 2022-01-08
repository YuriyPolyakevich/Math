package com.company;

public class Main {


    public static void main(String[] args) {

//        {1, 3, 4, 5, 6, 6, 6, 6}
        int a[] = {5, 6, 6, 6};
        int b[] = {1, 3, 4, 6};
        BinarySearch search = new BinarySearch();
        final double x = search.findMedian(a, b);
        System.out.println(x);
    }
}
