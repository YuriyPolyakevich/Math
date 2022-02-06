package com.company.yuriy.memorymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by yuriy_polyakevich at 2/6/22
 */

//С. Менеджер памяти
//Ограничение времени
//Ограничение памяти
//0.5 секунд 64Mb стандартный ввод или input.txt стандартный вывод или output.txt
//Ввод Вывод
//Пeтe поручили написать менеджер памяти для новой стандартной библиотеки языка H++.
//В распоряжении у менеджера находится массив из N последовательных ячеек памяти,
//пронумерованных от 1 до N. Задача менеджера — обрабатывать запросы приложений на выделение и освобождение памяти.
//Запрос на выделение памяти имеет один параметр К.
//Такой запрос означает, что приложение просит выделить
//ему к последовательных ячеек памяти. Если в распоряжении
//менеджера есть хотя бы один свободный блок из к последовательных ячеек,
//то он обязан в ответ на запрос выделить такой блок.
//При этом наш менеджер выделяет память из самого длинного свободного блока,
//а если таких несколько, то из них он выбирает тот, у которого номер первой ячейки — наименьший.
//После этого выделенные ячейки становятся занятыми и не могут быть Использованы для выделения памяти,
//пока не будут освобождены. Если блока из К Последовательных свободных ячеек нет, то запрос отклоняется.
//Запрос на освобождение памяти имеет один параметр Т. Такой запрос означает,
//что менеджер должен освободить память, выделенную ранее при обработке запроса с порядковым номером Т.
//Запросы нумеруются, начиная с единицы. Гарантируется, что запрос с номером Т — запрос на выделение,
//причем к нему еще не применялось освобождение памяти.
//Освобожденные ячейки могут снова быть использованы для выделения памяти.
//Если запрос с номером т был отклонен, то текущий запрос на освобождение памяти игнорируется.
//Требуется написать симуляцию менеджера памяти, удовлетворяющую приведенным критериям.
//Формат ввода
//В первой строке входа два числа и М — количество ячеек памяти и запросов соответственно (1 < N < 25 - 1, 1< M < 10°).
//Каждая из следующих М строк содержит по одному числу. (і + 1)-я строка
//содержит положительное число K, если i-й запрос - запрос на выделение к
//ячеек памяти (1 < K< N), и отрицательное число -T, если i-й запрос — запрос на освобождение памяти,
//выделенной по запросу номер т(1 <т і).
//Формат вывода
//Для каждого запроса на выделение памяти выведите в выход одно число на
//отдельной строке с результатом выполнения этого запроса. Если память была выделена,
//выведите номер первой ячейки памяти в выделенном блоке, иначе выведите число -1.
public class MemoryManager {

    private static long timeSpent = 0;

    public static void main(String[] args) {
        final int n = (int) Math.pow(2, 32);
        final int m = 100_000;
        final int[] input = new int[m];
        int c = 1;
        for (int i = 0; i < m; i++) {
            int a = 15;
            if (i % a == 0 && i != 0) {
                final int upper = c * a;
                final int low = (c - 1) * a;
                final int i1 = -(new Random().nextInt(upper - low) + low);
                input[i] = i1;
                c++;
            } else
                input[i] = new Random().nextInt(29) + 1;
        }
//        final int n = 6;
//        final int m = 8;
//        final int[] input = {2, 3, -1, 3, 3, -5, 2, 2};
        long s = System.currentTimeMillis();
        final String solve = solve(n, m, input);
        System.out.println(solve);
        final long l = System.currentTimeMillis() - s;
        System.out.println("Total time spent: " + l);
        System.out.println("Time spent on removing: " + timeSpent);
    }

    private static String solve(int n, int m, int[] input) {
        final Map<Integer, Segment> busyParts = new HashMap<>();
        final StringBuilder result = new StringBuilder();
        final Comparator<Segment> segmentComparator = (o1, o2) -> (o1.to - o1.from) == (o2.to - o2.from) ?
                -Integer.compare(o1.from, o2.from) :
                -Integer.compare(o1.to - o1.from, o2.to - o2.from);
        Queue<Segment> freeParts = new PriorityQueue<>(segmentComparator);
        freeParts.add(new Segment(0, n));
        for (int i = 0, inputLength = input.length; i < inputLength; i++) {
            final int query = input[i];
            if (query > 0 && freeParts.peek() == null) {
                result.append(-1).append(" ");
                continue;
            }
            if (query > 0 && freeParts.peek() != null) {
                if (freeParts.peek().to - freeParts.peek().from >= query) {
                    final Segment polledSegment = freeParts.poll();
                    result.append(polledSegment.from + 1).append(" ");
                    final Segment value = new Segment(polledSegment.from, polledSegment.from + query);
                    if (polledSegment.prevSegment != null) {
                        value.prevSegment = polledSegment.prevSegment;
                        polledSegment.prevSegment.nextSegment = value;
                    } else {
                        final Segment prev = new Segment(polledSegment.from, value.from);
                        prev.nextSegment = value;
                        value.prevSegment = value.from == 0 ? null : prev;
                    }
                    if (polledSegment.to == value.to) {
                        value.nextSegment = polledSegment.nextSegment;
                        if (polledSegment.nextSegment != null) {
                            polledSegment.nextSegment.prevSegment = value;
                        }
                    } else {
                        final Segment next = new Segment(value.to, polledSegment.to);
                        next.prevSegment = value;
                        value.nextSegment = value.to == n ? null : next;
                    }
                    value.isBusy = true;
                    busyParts.put(i, value);
                    if (value.prevSegment != null && !value.prevSegment.isBusy) {
                        freeParts.add(value.prevSegment);
                    }
                    if (value.nextSegment != null && !value.nextSegment.isBusy) {
                        freeParts.add(value.nextSegment);
                    }
                } else {
                    result.append(-1).append(" ");
                }
                continue;
            }
            if (query < 0) {
                long s = System.currentTimeMillis();
                final int absoluteValue = Math.abs(query) - 1;
                if (busyParts.containsKey(absoluteValue)) {
                    final Segment segment = busyParts.get(absoluteValue);
                    busyParts.remove(absoluteValue);
                    if (busyParts.isEmpty()) {
                        freeParts.add(new Segment(0, n));
                    } else {
                        int from = 0, to = n;
                        Segment prev = null;
                        Segment next = null;
                        final Segment e = new Segment(from, to);
                        if (segment.prevSegment != null) {
                            prev = segment.prevSegment;
                            from = prev.isBusy ? segment.from : prev.to;
                            //TODO: replace removeIf
                            freeParts.removeIf(se -> se.from == segment.prevSegment.from && se.to == segment.prevSegment.to);
                            e.prevSegment = prev.prevSegment;
                            e.from = from;
                        }
                        if (segment.nextSegment != null) {
                            next = segment.nextSegment;
                            to = next.isBusy ? next.from : next.to;
                            freeParts.removeIf(se -> se.from == segment.nextSegment.from && se.to == segment.nextSegment.to);
                            e.nextSegment = next.nextSegment;
                            e.to = to;
                        }
                        freeParts.add(e);
                    }
                    timeSpent += (System.currentTimeMillis() - s);
                }
            }
        }
        return result.toString();
    }

    private static class Segment {
        int from, to;
        Segment nextSegment;
        Segment prevSegment;
        boolean isBusy = false;

        public Segment(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

}
