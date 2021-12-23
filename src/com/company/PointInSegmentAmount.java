package com.company;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * на оси ох заданы отрезки.
 * x1[] - левые концы, x2[] - правые концы - arrays of ints.
 * потом идут запросы - каждый запрос - одна точка на ох (double). нужно ответить во сколько отрезков она входит.
 * входной файл
 * N, M — кол-во отрезков, кол-во запросов
 * x1_0, ... x1_N
 * x2_0, ... x2_N
 * q_x_1
 * q_x_2
 * ...
 * q_x_M
 */
public class PointInSegmentAmount implements Task {
    @Override
    public Object solveTask(Object... args) {
        Integer x[] = (Integer[]) args[0];
        Integer y[] = (Integer[]) args[1];
        double q[] = (double[]) args[2];
        int result[] = new int[q.length];

        Integer[] indexes = IntStream.range(0, x.length).boxed().toArray(Integer[]::new);
        Comparator<Integer> xComp = Comparator.comparingInt(a -> x[a]);
        Arrays.sort(indexes, 0, indexes.length, xComp);

        int i = x[indexes[0]];
        int max = Collections.max(Arrays.asList(x), null);
        final List<Integer> indexesToCheck = new ArrayList<>();

        for (int j = i; j <= max; j++) {

        }

        for (int j = 0; j < result.length; j++) {
            System.out.println(String.format("Point: {%s}, amount: {%s}", q[j], result[j]));
        }
        return result;
    }

    public class TimeUnit {
        int fromIncluded;
        int toExcluded;
        int number;
    }
}
