package com.company.yuriy;

import javax.crypto.spec.PSource;
import javax.swing.*;
import java.lang.invoke.CallSite;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yuriy_polyakevich at 1/30/22
 */


//Вам дано m упорядоченных по возрастанию последовательностей целых чисел,
//каждая из которых имеет длину n.
//Необходимо слить их в одну упорядоченную по возрастанию последовательность целых чисел длины тп.
//Сложность алгоритма не должна превышать о(mn*log(n)), затраты памяти — O(mn)).
//Формат ввода
//В первой строке входа два целых числа п, т
//(1 <= m, n < 1000). В следующих и строках по т целых чисел в каждой,
//числа в каждой строке упорядочены по возрастанию.
//Формат вывода
//Выведите все числа, упорядоченных по возрастанию.
//Пример 1
//Ввод
//2 5
//1 3 5 7 9
//2 4 6 8 10
//Вывод
//1 2 3 4 5 6 7 8 9 10
public class GetResultSortedArrayOfSubArrays implements Task {

    public static void main(String[] args) {
        List<Integer[]> arrays = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Integer ar[] = new Integer[1000];
            for (int j = 0; j < 1000; j++) {
                ar[j] = new Random().nextInt();
            }
            Arrays.sort(ar);
            arrays.add(ar);
        }
        new GetResultSortedArrayOfSubArrays().solveTask(arrays);
    }

    @Override
    public Object solveTask(Object... args) {
        final List<Integer[]> arg = (List<Integer[]>) args[0];
        final List<Integer> answer = new LinkedList<>();
        final List<Queue<Integer>> queues = arg.stream()
                .map(a -> new ArrayDeque<>(Arrays.asList(a)))
                .collect(Collectors.toList());
        final Queue<Queue<Integer>> prQ = new PriorityQueue<>(Comparator.comparing(Queue::peek));
        prQ.addAll(queues);
        while (!prQ.isEmpty()) {
            final Queue<Integer> poll = prQ.poll();
            if (poll != null) {
                answer.add(poll.poll());
                if (poll.size() != 0) {
                    prQ.add(poll);
                }
            }
        }
        answer.forEach(a -> System.out.print(a + " "));
        return null;
    }
}
