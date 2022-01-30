package com.company.yuriy;

import java.util.*;
import java.util.stream.Collectors;

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
        final Integer a[] = new Integer[]{1, 3, 5, 7, 9};
        final Integer b[] = new Integer[]{2, 4, 6, 8, 10};
        new GetResultSortedArrayOfSubArrays().solveTask(a, b);
    }

    @Override
    public Object solveTask(Object... args) {
        final List<Queue<Integer>> queues = Arrays.stream(args)
                .map(a -> new ArrayDeque<>(Arrays.asList((Integer[]) a)))
                .collect(Collectors.toList());
        final int totalCount = queues.stream().map(Collection::size).reduce(Integer::sum).orElse(0);
        int count = 0;
        while (count < totalCount) {
            final Queue<Integer> queue = queues.stream()
                    .filter(q -> q.size() > 0)
                    .min(Comparator.comparing(Queue::peek))
                    .orElseThrow(RuntimeException::new);
            System.out.print(queue.poll() + " ");
            count++;
        }
        return null;
    }
}
