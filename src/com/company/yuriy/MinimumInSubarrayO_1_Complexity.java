package com.company.yuriy;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by yuriy_polyakevich at 1/30/22
 */

//Пусть задан массив из п целых чисел. По этому массиву будут ходить два указателя 1 иr ( 1<l, r < n).
//Изначально оба они указывают на первый элемент массива (1 = r = 1).
//Оба указателя могут двигаться только вправо, на одну позицию за раз.
//При этом указатель 1 никогда не оказывается правее указателя т, и ни один из них не выходит за пределы массива.
//Вам нужно после каждого перемещения указателя определить максимум всех элементов
//от указателя І вправо до указателят (включая позиции, на которые указывают I и г).
//Указание. Учетная стоимость обработки каждого запроса на перемещение и подсчет максимума должна оказаться O(1).
//Формат ввода
//В первой строке входного потока задано число п (1 кпк 100 000) — размер массива.
//Во Второй строке п целых чисел от -1 000 000 000 до 1 000 000 000 — сам массив.
//В третьей строке указано число м (0 х т к 2n — 2) — количество перемещений.
//В четвертой строке — т символов L или R, разделенных пробелами.
//L означает, что нужно сдвинуть l вправо, R — что нужно сдвинуть г вправо.
//Формат вывода
//Выведите в одну строку ровно т чисел,
//где і-е число — максимальное значение на отрезке от 1 до 7 после выполнения i-й операции.
//Пример
//Ввод
//10
//1 4 2 3 5 8 6 7 9 10
//12
//R R L R R R L L L R L L
//Вывод
//4 4 4 4 5 8 8 8 8 8 8 б
public class MinimumInSubarrayO_1_Complexity implements Task {

    public static void main(String[] args) {
        final int[] arr = new int[]{1, 4, 2, 3, 5, 8, 6, 7, 9, 10};
        final char[] movements = new char[]{'R', 'R', 'L', 'R', 'R', 'R', 'L', 'L', 'L', 'R', 'L', 'L'};
        new MinimumInSubarrayO_1_Complexity().solveTask(arr, movements);
    }


    @Override
    public Object solveTask(Object... args) {
        final int[] arr = (int[]) args[0];
        final char[] movements = (char[]) args[1];
        final Deque<Pair> maximums = new ArrayDeque<>();
        maximums.push(new Pair(arr[0], 0));
        int l = 0, r = 0;
        for (final char movement : movements) {
            if ('L' == movement) {
                popFront(maximums, new Pair(arr[l], l));
                l++;
            } else {
                r++;
                pushBack(maximums, new Pair(arr[r], r));
            }
            System.out.print(maximums.getLast().value + " ");
        }
        return null;
    }

    private void popFront(Deque<Pair> maximums, Pair pair) {
        if (maximums.getLast().index == pair.index) {
            maximums.removeLast();
        }
    }

    private void pushBack(Deque<Pair> maximums, Pair pair) {
        while (maximums.size() > 0 && maximums.getFirst().value <= pair.value) {
            maximums.removeFirst();
        }
        maximums.push(pair);
    }

    static final class Pair {
        int value, index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}
