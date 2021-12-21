package com.company;

import javafx.collections.transformation.SortedList;

import java.util.*;

public class PointInSegmentAmount implements Task {
    @Override
    public Object solveTask(Object... args) {
        int x[] = (int[]) args[0];
        int y[] = (int[]) args[1];
        int q[] = (int[]) args[2];
        final Map<Integer, List<Integer>> tree = new TreeMap<>();
        for (int i = 0; i < x.length; i++) {
            if (!tree.containsKey(x[i])) {
                tree.put(x[i], new ArrayList<>());
            }
            tree.get(x[i]).add(y[i]);
            Collections.sort(tree.get(x[i]));
        }
        for (int i = 0; i < q.length; i++) {
            int count = 0;

            System.out.println(String.format("q[%s] - %s times", i, count));
        }
        return null;
    }
}
