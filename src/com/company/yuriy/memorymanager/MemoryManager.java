package com.company.yuriy.memorymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
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

    public static void main(String[] args) {
        final int n = 6;
        final int m = 8;
        final int[] input = { 2, 3, -1, 3, 3, -5, 2, 2 };
        System.out.println(solve(n, m, input));
    }

    private static String solve(int n, int m, int[] input) {
        final Segment[] busyParts = new Segment[m];
        final StringBuilder result = new StringBuilder();
        //TODO: fix comparator
        final Comparator<Segment> segmentComparator = (o1, o2) -> {
            if ((o1.to - o1.from) > (o2.to - o2.from)) {
                return 1;
            } else if ((o1.to - o1.from) < (o2.to - o2.from)) {
                return -1;
            } else {
                return o1.from <= o2.from ? -1 : 1;
            }
        };
        Queue<Segment> freeParts = new PriorityQueue<>(segmentComparator);
        freeParts.add(new Segment(0, n));
        for (int i = 0, inputLength = input.length; i < inputLength; i++) {
            final int query = input[i];
            if (query > 0 && freeParts.peek() == null) {
                result.append(-1).append("\n");
                continue;
            }
            if (query > 0 && freeParts.peek() != null) {
                if (freeParts.peek().to - freeParts.peek().from >= query) {
                    final Segment polledSegment = freeParts.poll();
                    result.append(polledSegment.from + 1).append("\n");
                    busyParts[i] = new Segment(polledSegment.from, polledSegment.from + query);
                    polledSegment.from = polledSegment.from + query;
                    freeParts.add(polledSegment);
                    continue;
                } else {
                    result.append(-1).append("\n");
                    continue;
                }
            }
            if (query < 0) {
                final int absoluteValue = Math.abs(query) - 1;
                if (busyParts[absoluteValue] != null) {
                    busyParts[absoluteValue] = null;
                    final List<Segment> segmemts = Arrays.stream(busyParts).filter(Objects::nonNull).collect(Collectors.toList());
                    freeParts = new PriorityQueue<>(segmentComparator);
                    if (segmemts.isEmpty()){
                        freeParts.add(new Segment(0, n));
                    } else {
                        int start = 0;
                        for (Segment segment: segmemts) {
                            if (start == segment.from) {
                                start = segment.to;
                                continue;
                            }
                            freeParts.add(new Segment(start, segment.from));
                            start = segment.to;
                        }
                        if (start != n) {
                            freeParts.add(new Segment(start, n));
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    private static class Segment {
        int from, to;

        public Segment(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Segment{" + "from=" + from + ", to=" + to + '}';
        }
    }

}
