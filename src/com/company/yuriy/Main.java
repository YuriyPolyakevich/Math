package com.company.yuriy;

public class Main {


    public static void main(String[] args) {

        final PointInSegmentAmount p = new PointInSegmentAmount();
        final BinarySearch binarySearch = new BinarySearch();
        int a[] = new int[]{3};
        int b[] = new int[]{1, 4};
        final double median = binarySearch.findMedian(a, b);
        System.out.println(median);
    }
}
