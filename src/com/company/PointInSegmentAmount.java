package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointInSegmentAmount implements Task {
    @Override
    public Object solveTask(Object... args) {
        int x[] = (int[]) args[0];
        int y[] = (int[]) args[1];
        double q[] = (double[]) args[2];
        int result[] = new int[q.length];
        int max = y[y.length - 1];
        int n = q.length > x.length ? q.length : x.length;
        for (int i = 0; i < n; i++) {
            if (i < x.length - 1) {
                for (int j = i + 1; j < x.length; j++) {
                    if (x[j] < x[i]) {
                        int t = x[j];
                        x[j] = x[i];
                        x[i] = t;
                        int t1 = y[j];
                        y[j] = y[i];
                        y[i] = t1;
                    }
                }
            }
            if (i < q.length) {
                for (int j = i + 1; j < q.length; j++) {
                    if (q[j] < q[i]) {
                        double t = q[j];
                        q[j] = q[i];
                        q[i] = t;
                    }
                }
            }
            if (max < y[i]) {
                max = y[i];
            }
        }
        int i = x[0];
        int exist[] = new int[x.length];
        int xIndex = 0;
        int qIndex = 0;
        while (q[qIndex] < x[0]) {
            result[qIndex] = 0;
            qIndex++;
        }
        if (qIndex == result.length) {
            return result;
        }
        final List<Integer> indexesToCheck = new ArrayList<>();
        while (i != max) {
            while (xIndex < x.length && x[xIndex] == i) {
                exist[xIndex] = 1;
                indexesToCheck.add(xIndex);
                xIndex++;
            }
            if (qIndex < q.length && q[qIndex] == i) {
                final long count = Arrays.stream(exist).filter(e -> e == 1).count();
                while (qIndex < q.length && q[qIndex] == i) {
                    result[qIndex] = (int) count;
                    qIndex++;
                }
            }


            for (int j = 0; j < indexesToCheck.size(); j++) {
                final Integer integer = indexesToCheck.get(j);
                if (y[integer] == i) {
                    exist[integer] = 0;
                    indexesToCheck.remove(integer);
                    j--;
                }
            }

            if (qIndex < q.length && (q[qIndex] >= i && q[qIndex] < (i + 1))) {
                final long count = Arrays.stream(exist).filter(e -> e == 1).count();
                while (qIndex < q.length && (q[qIndex] >= i && q[qIndex] < (i + 1))) {
                    result[qIndex] = (int) count;
                    qIndex++;
                }
            }
            i++;
        }
        if (qIndex != result.length - 1) {
            for (int j = qIndex; j < result.length; j++) {
                result[j] = 0;
            }
        }
        for (int j = 0; j < result.length; j++) {
            System.out.println(String.format("Point: {%s}, amount: {%s}", q[j], result[j]));
        }
        return result;
    }
}
