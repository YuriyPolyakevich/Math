package com.company.codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Поликарп получил в подарок массив целых чисел a[1…n]. Он хочет произвести некоторое количество операций (возможно, ноль), чтобы все элементы массива стали одинаковыми (то есть, чтобы стало a1=a2=⋯=an).
 * <p>
 * За одну операцию он может взять какие-то индексы в массиве и увеличить числа под этими индексами на 1.
 * Например, пусть a=[4,2,1,6,2]. Он может произвести следующую операцию: выбрать индексы 1, 2 и 4 и увеличить их всех на 1. В итоге за одну операцию он может получить новое состояние массива a=[5,3,1,7,2].
 * <p>
 * За какое минимальное количество операций он может сделать так, что все элементы массива стали равны между собой (то есть, чтобы стало a1=a2=⋯=an)?
 * <p>
 * Входные данные
 * Первая строка входных данных содержит единственное целое число t (1≤t≤104) — количество наборов входных данных в тесте.
 * <p>
 * Далее следуют описания наборов входных данных.
 * <p>
 * Первая строка описания каждого набора входных данных содержит одно целое число n (1≤n≤50) — длину массива a.
 * <p>
 * Вторая строка описания каждого набора входных данных содержит n целых чисел a1,a2,…,an (1≤ai≤109) — элементы массива a.
 * <p>
 * Выходные данные
 * Для каждого набора входных данных выведите одно целое число — минимальное необходимое количество операций, чтобы сделать все элементы массива a одинаковыми.
 */


public class Problem1624_A {

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        String line = br.readLine();
        do {
            int n = Integer.parseInt(line);
            line = br.readLine();
            final Integer[] a = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList())
                    .toArray(new Integer []{});
            System.out.println(Stream.of(a).max(Integer::compareTo).orElse(0) - Stream.of(a).min(Integer::compareTo).orElse(0));
            line = br.readLine();
        } while (line != null);
        br.close();
    }

}
