package com.company.yuriy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by yuriy_polyakevich at 2/6/22
 */

//По массиву двигаются два указателя — L и R, которые изначально направлены на самый первый элемент массива.
//Есть две операции: L— сдвинуть указатель 1 на один элемент вправо и R —
//сдвинуть указатель г на один элемент вправо. После выполнения каждой из операций нужно вывести
//k -ев порядке возрастания число среди всех чисел от 1 до е включительно, либо -1, если всего чисел от I до г меньше, чем k.
//Формат ввода
//В первой строке входа — три целых числа п, т, k.
//Во второй строке п целых чисел, задающих массив чисел, по которому будут двигаться два указателя, 1 ит.
//В третьей строке входного файла — т символов R или L, без пробелов, в одну строку.
//Это порядок выполняемых операций. Гарантируется, что указатель L никогда не “обгоняет" указатель R.
//Гарантируется, что указатель к никогда не выйдет за пределы массива. При этом 1<n, k< 100000, 0 < m < 2n – 2.
//Все числа в массиве неотрицательные и не превосходят 10°.
//Формат вывода
//Выведите ровно т строк, в каждой — ровно по одному целому числу.
//После выполнения каждой из операций нужно вывести k-е в
//порядке возрастания число среди всех чисел от 1 до т включительно, либо -1, если всего чисел от I до т меньше, чем :
//Пример 1
//Ввод
//7 4 2
//4 2 1 3 6 5 7
//RRLL
//Вывод
//4 2 2 -1
public class K_AscendingElementInSubArray {

    public static void main(String[] args) {
        //        final int k = 2;
        //        final Integer[] arr = { 4, 2, 1, 3, 6, 5, 7 };
        //        final Character[] movings = { 'R', 'R', 'L', 'L' };
        final int k = 5_000;
        final Character[] movings = new Character[100_000];
        final Integer[] arr = new Integer[1_000_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Arrays.fill(movings, 'R');
        for (int i = movings.length / 2; i < movings.length; i++) {
            movings[i] = 'L';
        }
        final long startTime = System.currentTimeMillis();
        solveProblem(arr, k, movings);
        System.out.println("Time spent: " + (System.currentTimeMillis() - startTime));
    }

    private static void solveProblem(Integer[] arr, int k, Character[] moves) {
        int l = 0, r = 0;
        final StringBuilder result = new StringBuilder();
        final Queue<Value> mainQueue = new PriorityQueue<>((o1, o2) -> -Integer.compare(o1.value, o2.value));
        final Value initialValue = new Value(0, arr[0], true);
        mainQueue.add(initialValue);
        final Queue<Value> nonMainQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.value));
        final Map<Integer, Value> valuesMap = new HashMap<>();
        valuesMap.put(0, initialValue);
        for (final Character move : moves) {
            if (move == 'R') {
                r++;
            } else {
                l++;
            }
            final int index = r - l;
            final int newValueInt = arr[index];
            final Value newValue = new Value(index, newValueInt, true);
            if ('R' == move) {
                final boolean mainQueueNotFilled = mainQueue.size() < k;
                if (mainQueueNotFilled) {
                    mainQueue.add(newValue);
                    if (mainQueue.size() == k) {
                        result.append(mainQueue.peek().value).append(" ");
                    } else {
                        result.append("-1").append(" ");
                    }
                    valuesMap.put(index, newValue);
                    continue;
                } else {
                    final Value peek = mainQueue.peek();
                    if (peek.value >= newValue.value) {
                        final Value poll = mainQueue.poll();
                        poll.isInMainQueue = false;
                        nonMainQueue.add(poll);
                        mainQueue.add(newValue);
                    } else {
                        newValue.isInMainQueue = false;
                        nonMainQueue.add(newValue);
                    }
                }
                valuesMap.put(index, newValue);
            } else {
                final Value value = valuesMap.remove(l - 1);
                if (value.isInMainQueue) {
                    mainQueue.remove(value);
                    if (!nonMainQueue.isEmpty()) {
                        final Value poll = nonMainQueue.poll();
                        poll.isInMainQueue = true;
                        mainQueue.add(poll);
                    }
                    if (mainQueue.size() < k) {
                        result.append("-1").append(" ");
                        continue;
                    }
                } else {
                    nonMainQueue.remove(value);
                }
            }
            assert mainQueue.peek() != null;
            result.append(mainQueue.peek().value).append(" ");
        }
        System.out.println(result);
        System.out.println();
    }

    private static class Value {
        int index;
        int value;
        boolean isInMainQueue;

        public Value(int index, int value, boolean isInMainQueue) {
            this.index = index;
            this.value = value;
            this.isInMainQueue = isInMainQueue;
        }
    }
}
