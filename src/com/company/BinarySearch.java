package com.company;

/**
 * Created by yuriy_polyakevich at 1/2/22
 */


public class BinarySearch {

    public int simpleBinarySearch(int[] a, int searchValue) {
        int leftIndex = 0;
        int rightIndex = a.length - 1;
        int foundIndex = -1;
        while (leftIndex <= rightIndex) {
            foundIndex = (leftIndex + rightIndex) / 2;
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

    //Написать функцию, которая для списка целых чисел возвращает индекс найденного пика
    //(пиков может быть несколько, можно вернуть любой из них).
    //Значение является пиком, если оно больше или равно соседним значениям.
    //TODO
    public int findPeek(int[] a) {
        if (a.length == 1) {
            return 0;
        }
        int leftIndex = 0;
        int rightIndex = a.length - 1;
        int foundIndex = -1;
        while (leftIndex <= rightIndex) {
            foundIndex = (leftIndex + rightIndex) / 2;

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
            foundIndex = (leftIndex + rightIndex) / 2;
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
