package com.company;

/**
 * Created by yuriy_polyakevich at 1/2/22
 */

//Написать функцию, которая для сортированного списка отвечает на вопрос, входит ли в него заданное значение.
//Список может быть пустым.
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

    public boolean isValueExist(int[] a, int searchValue) {
        return simpleBinarySearch(a, searchValue) >= 0;
    }
}
