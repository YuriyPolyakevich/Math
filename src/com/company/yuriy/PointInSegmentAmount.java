package com.company.yuriy;

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
        int allPossible = 0;
        int goneValue = 0;
        boolean changed = false;
        for (int j = i; j <= max; j++) {
            changed = false;
            int comeValue = 0;
            while (currentIndex < x.length && x[indexes[currentIndex]] == j) {
                if (!indexesToCheck.contains(indexes[currentIndex])) {
                    indexesToCheck.add(indexes[currentIndex]);
                }
                allPossible++;
                currentIndex++;
                comeValue++;
                changed = true;
            }

            for (int k = 0; k < indexesToCheck.size(); k++) {
                Integer index = indexesToCheck.get(k);
                if (y[index] == j) {
                    changed = true;
                    goneValue++;
                    indexesToCheck.remove(index);
                    k--;
                }
            }
            if (changed) {
                updateList(smallSegments, allPossible - comeValue, allPossible, j);
            }
            allPossible -= goneValue;
            goneValue = 0;
        }
        smallSegments.forEach(System.out::println);
        search(q, result, smallSegments);
        for (int j = 0; j < result.length; j++) {
            System.out.printf("Point: {%s}, amount: {%s}%n", q[j], result[j]);
        }
        return result;
    }

    private void search(double[] q, int[] result, List<SmallSegment> smallSegments) {
        final Integer[] objects = smallSegments.stream().map(s -> s.from).toArray(Integer[]::new);
        for (int j = 0; j < q.length; j++) {
            int i1 = Arrays.binarySearch(objects, (int) q[j]);
            if (i1 < 0) {
                i1 = -i1 - 2;
            }
            final SmallSegment smallSegment = smallSegments.get(i1);
            if (smallSegment.from == q[j]) {
                result[j] = smallSegment.startAmount;
            } else if (smallSegment.from < q[j] && q[j] < smallSegment.to) {
                result[j] = smallSegment.betweenAmount;
            } else {
                result[j] = smallSegment.endAmount;
            }
        }
    }

    private void updateList(List<SmallSegment> smallSegments, int afterGone, int allPossible, int pointValue) {
        if (smallSegments.size() == 0) {
            final SmallSegment e = new SmallSegment(pointValue, null);
            e.startAmount = allPossible;
            smallSegments.add(e);
            return;
        }
        final SmallSegment smallSegment = smallSegments.get(smallSegments.size() - 1);
        smallSegment.to = pointValue;
        smallSegment.betweenAmount = afterGone;
        smallSegment.endAmount = allPossible;
        smallSegments.set(smallSegments.size() - 1, smallSegment);
        final SmallSegment e = new SmallSegment(pointValue, null);
        e.startAmount = allPossible;
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
