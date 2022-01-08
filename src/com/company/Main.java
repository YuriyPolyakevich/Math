package com.company;

public class Main {


    public static void main(String[] args) {

//        {11, 2, 3, 4, 6, 7}
        int a[] = {1, 4, 6};
        int b[] = {2, 3, 7};
        BinarySearch search = new BinarySearch();
        final double x = search.findMedian(a, b);
        System.out.println(x);
    }
}
