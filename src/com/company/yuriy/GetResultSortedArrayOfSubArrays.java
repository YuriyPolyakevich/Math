package com.company.yuriy;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by yuriy_polyakevich at 1/30/22
 */

//Example
//Input
//2 5
//1 3 5 7 9
//2 4 6 8 10
//Output
//1 2 3 4 5 6 7 8 9 10
public class GetResultSortedArrayOfSubArrays implements Task {

    public static void main(String[] args) {
        final Integer a[] = new Integer[]{1, 3, 5, 7, 9};
        final Integer b[] = new Integer[]{2, 4, 6, 8, 10};
        new GetResultSortedArrayOfSubArrays().solveTask(a, b);
    }

    @Override
    public Object solveTask(Object... args) {
        final Queue<Integer> queue = new PriorityQueue<>();
        for (Object arg : args) {
            final Integer[] array = (Integer[]) arg;
            queue.addAll(Arrays.asList(array));
        }
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        return null;
    }
}
