package com.company;

import java.util.*;
import java.util.stream.IntStream;

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
        if (x.length < 1 || y.length < 1) {
            return null;
        }
        double q[] = (double[]) args[2];
        int result[] = new int[q.length];

        Integer[] indexes = IntStream.range(0, x.length).boxed().toArray(Integer[]::new);
        Comparator<Integer> xComp = Comparator.comparingInt(a -> x[a]);
        Arrays.sort(indexes, 0, indexes.length, xComp);

        int i = x[indexes[0]];
        int currentIndex = 0;
        int max = Collections.max(Arrays.asList(y));
        final List<Integer> indexesToCheck = new ArrayList<>();
        final List<SmallSegment> smallSegments = new ArrayList<>();
        int currentState = 0;
        int previousState = currentState;
        boolean changed = false;
        for (int j = i; j <= max; j++) {
            changed = false;
            while (currentIndex < x.length && x[indexes[currentIndex]] == j) {
                if (!indexesToCheck.contains(indexes[currentIndex])) {
                    indexesToCheck.add(indexes[currentIndex]);
                }
                currentState++;
                currentIndex++;
                changed = true;
            }

            if (changed) {
                updateList(smallSegments, previousState, currentState, j);
            }
            changed = false;
            previousState = currentState;
            for (int k = 0; k < indexesToCheck.size(); k++) {
                Integer index = indexesToCheck.get(k);
                if (y[index] == j) {
                    changed = true;
                    currentState--;
                    indexesToCheck.remove(index);
                    k--;
                }
            }
            if (changed) {
                updateList(smallSegments, previousState, currentState, j);
            }
        }
        smallSegments.forEach(System.out::println);
        for (int j = 0; j < result.length; j++) {
            System.out.println(String.format("Point: {%s}, amount: {%s}", q[j], result[j]));
        }
        return result;
    }

    private void updateList(List<SmallSegment> smallSegments, int previousState, int currentState, int pointValue) {
        if (smallSegments.size() == 0) {
            final SmallSegment e = new SmallSegment(pointValue, null);
            e.startAmount = currentState;
            smallSegments.add(e);
            return;
        }
        final SmallSegment smallSegment = smallSegments.get(smallSegments.size() - 1);
        smallSegment.to = pointValue;
        smallSegment.betweenAmount = previousState;
        smallSegment.endAmount = currentState;
        smallSegments.set(smallSegments.size() - 1, smallSegment);
        final SmallSegment e = new SmallSegment(pointValue, null);
        e.startAmount = currentState;
        smallSegments.add(e);
    }

    public class SmallSegment {
        Integer from;
        Integer to;
        int betweenAmount = 0;
        int startAmount;
        int endAmount;

        public SmallSegment(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "SmallSegment{" +
                    "from=" + from +
                    ", to=" + to +
                    ", startAmount=" + startAmount +
                    ", betweenAmount=" + betweenAmount +
                    ", endAmount=" + endAmount +
                    '}';
        }
    }
}
