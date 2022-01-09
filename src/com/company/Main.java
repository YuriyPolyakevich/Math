package com.company;

public class Main {


    public static void main(String[] args) {

//        {1, 2, 3, 4, 6, 7}
        int a[] = {1};
        int b[] = {2};
        BinarySearch search = new BinarySearch();
        final long before = System.currentTimeMillis();
        final double x = search.findMedian(a, b);
        System.out.println(x);
    }
}
