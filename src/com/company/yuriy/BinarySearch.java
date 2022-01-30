package com.company.yuriy;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by yuriy_polyakevich at 1/2/22
 */


public class BinarySearch {

    public int simpleBinarySearch(int[] a, int searchValue) {
        int leftIndex = 0;
        int rightIndex = a.length - 1;
        int foundIndex = -1;
        while (leftIndex <= rightIndex) {
            foundIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (a[foundIndex] == searchValue) {
                return foundIndex;
            } else if (searchValue > a[foundIndex]) {
                leftIndex = foundIndex + 1;
            } else {
                rightIndex = foundIndex - 1;
            }
        }
        return -rightIndex - 2;
    }

    //Найти медиану двух отсортированных списков. В списках могут быть дубликаты.
    //Если количество элементов четное, то нужно взять среднее арифметическое двух серединных значений.
    //Гарантируется, что хоты бы один список не пустой.
    public double findMedian(int[] a, int[] b) {
        if (b.length > a.length) {
            final int[] t = Arrays.copyOf(a, a.length);
            a = Arrays.copyOf(b, b.length);
            b = t;
        }
        final int totalElementCount = a.length + b.length;
        final boolean isEvenNumberOfElements = totalElementCount % 2 == 0;
        final int n1_len = a.length;
        final int n2_len = b.length;
        int low = 0;
        int high = n1_len;
        while (low <= high) {
            final int i = low + (high - low) / 2;
            final int j = (n1_len + n2_len + 1) / 2 - i;

            final float left1 = i > 0 ? a[i - 1] : Float.MIN_VALUE;
            final float left2 = j > 0 ? b[j - 1] : Float.MIN_VALUE;
            final float right1 = i != n2_len ? a[i] : Float.MAX_VALUE;
            final float right2 = j != n2_len ? b[j] : Float.MAX_VALUE;

            if (left1 <= right2 && right1 >= left2) {
                if (isEvenNumberOfElements) {
                    return (max(left1, left2) + min(right1, right2)) / 2.0;
                } else {
                    return max(left1, left2);
                }
            } else if (left1 <= right2) {
                low = i + 1;
            } else {
                high = i - 1;
            }
        }
        return 1;
    }

    private int findBorderValue(int[] ints, int searchElement, boolean findLast) {
        int left = 0;
        int right = ints.length - 1;
        int currentIndex = -1;
        while (left <= right) {
            currentIndex = left + (right - left) / 2;
            int elem = ints[currentIndex];
            if (elem == searchElement) {
                if (findLast) {
                    left = currentIndex + 1;
                } else {
                    right = currentIndex - 1;
                }
            } else if (elem > searchElement) {
                right = currentIndex - 1;
            } else {
                left = currentIndex + 1;
            }
        }
        return currentIndex;
    }

    //Написать функцию, которая для списка целых чисел возвращает индекс найденного пика
    //(пиков может быть несколько, можно вернуть любой из них).
    //Значение является пиком, если оно больше или равно соседним значениям.
    public int findPeek(int[] a) {
        if (a.length == 1) {
            return 0;
        }
        int leftIndex = 0;
        int rightIndex = a.length - 1;
        int foundIndex = -1;
        while (leftIndex <= rightIndex) {
            foundIndex = leftIndex + (rightIndex - leftIndex) / 2;

            //processing border values
            if (foundIndex == 0) {
                if (a[foundIndex + 1] <= a[foundIndex]) {
                    return foundIndex;
                } else {
                    leftIndex += 1;
                    continue;
                }
            } else if (foundIndex == a.length - 1) {
                if (a[foundIndex - 1] <= a[foundIndex]) {
                    return foundIndex;
                } else {
                    rightIndex -= 1;
                    continue;
                }
            }

            if (a[foundIndex - 1] <= a[foundIndex] && a[foundIndex + 1] <= a[foundIndex]) {
                return foundIndex;
            }
            if (a[foundIndex + 1] >= a[foundIndex]) {
                leftIndex = foundIndex + 1;
            } else {
                rightIndex = foundIndex - 1;
            }
        }

        return foundIndex;
    }

    //Найти минимум в отсортированном по возрастанию, а затем циклически сдвинутом массиве из целых чисел.
    //Гарантируется, что массив не пустой, и что в нем нет дубликатов.
    public int findMinValueInShiftedSortedList(int[] a) {
        int leftIndex = 0;
        int rightIndex = a.length - 1;
        int foundIndex = -1;
        int minValue = Integer.MAX_VALUE;
        while (leftIndex <= rightIndex) {
            foundIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (a[rightIndex] >= a[foundIndex]) {
                rightIndex = foundIndex - 1;
            } else {
                leftIndex = foundIndex + 1;
            }
            if (a[foundIndex] < minValue) {
                minValue = a[foundIndex];
            }
        }
        return minValue;
    }

    //Написать функцию, которая для сортированного списка отвечает на вопрос, входит ли в него заданное значение.
    //Список может быть пустым.
    public boolean isValueExist(int[] a, int searchValue) {
        return simpleBinarySearch(a, searchValue) >= 0;
    }
}
