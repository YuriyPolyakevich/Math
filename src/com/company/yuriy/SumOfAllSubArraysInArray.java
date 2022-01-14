package com.company.yuriy;

import java.util.stream.IntStream;

/**
 * Created by yuriy_polyakevich at 1/12/22
 */
public class SumOfAllSubArraysInArray {


    public int sum(int[] a) {
        final int n = a.length;
        return IntStream.range(0, n).map(i -> ((n - i) * (i + 1)) * a[i]).sum();
    }

}
