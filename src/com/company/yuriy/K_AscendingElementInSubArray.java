package com.company.yuriy;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by yuriy_polyakevich at 2/6/22
 */

//По массиву двигаются два указателя — Тит, которые изначально направлены на самый первый элемент массива.
//Есть две операции: L— сдвинуть указатель 1 на один элемент вправо и R —
//сдвинуть указатель г на один элемент вправо. После выполнения каждой из операций нужно вывести
//k -ев порядке возрастания число среди всех чисел от 1 до е включительно, либо -1, если всего чисел от I до г меньше, чем k.
//Формат ввода
//В первой строке входа — три целых числа п, т, k.
//Во второй строке п целых чисел, задающих массив чисел, по которому будут двигаться два указателя, 1 ит.
//В третьей строке входного файла — т символов R или L, без пробелов, в одну строку.
//Это порядок выполняемых операций. Гарантируется, что указатель I никогда не “обгоняет" указатель т.
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
        final int k = 2;
        final Integer[] arr = { 4, 2, 1, 3, 6, 5, 7 };
        final Character[] movings = { 'R', 'R', 'L', 'L'};
        final long startTime = System.currentTimeMillis();
        solveProblem(arr, k, movings);
        System.out.println("Time spent: " + (System.currentTimeMillis() - startTime));
    }

    private static void solveProblem(Integer[] arr, int k, Character[] movings) {
        int l = 0, r = 0;
        final StringBuilder result = new StringBuilder();
        for (final Character move : movings) {
            if ('R' == move) {
                r++;
            } else {
                l++;
            }
            if (k > (r - l) + 1) {
                result.append(-1).append(" ");
                continue;
            }
            final Queue<Integer> prQ = new PriorityQueue<>();
            final Integer[] array = new Integer[r - l + 1];
                    System.arraycopy(arr, l, array, 0, r - l + 1);
            prQ.addAll(Arrays.asList(array));
            int counter = 0;
            while (counter < k - 1) {
                prQ.poll();
                counter++;
            }
            result.append(prQ.peek()).append(" ");
        }
        System.out.println(result);
        System.out.println();
    }
}
