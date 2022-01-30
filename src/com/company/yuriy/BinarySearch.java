package com.company.yuriy;

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
    //TODO: refactor
    public double findMedian(int[] a, int[] b) {
        int totalElementCount = a.length + b.length;
        boolean isEvenNumberOfElements = totalElementCount % 2 == 0;
        //condition if one of arrays is empty
        if (a.length == 0) {
            return isEvenNumberOfElements
                    ? (b.length == 1 ? b[0] : ((double) b[b.length / 2] + b[b.length / 2 - 1]) / 2)
                    : b[b.length / 2];
        } else if (b.length == 0) {
            return isEvenNumberOfElements
                    ? (a.length == 1 ? a[0] : ((double) a[a.length / 2] + a[a.length / 2 - 1]) / 2)
                    : a[a.length / 2];
        }
        int indexToFound = totalElementCount / 2;
        int additionalIdnexToFoundIfEvenTotalAmount = isEvenNumberOfElements ? (totalElementCount / 2) - 1 : Integer.MIN_VALUE;
        int rightA_Array = a.length - 1;
        int rightB_Array = b.length - 1;
        int leftB_Array = 0;
        int leftA_Array = 0;
        int currentIndex = 0;
        boolean checkA_Array = rightA_Array >= rightB_Array;

        //Final results
        int answer = Integer.MIN_VALUE;
        int answerIfElementHasEvenAmountOfNumbers = Integer.MIN_VALUE;

        while (currentIndex != indexToFound || currentIndex != additionalIdnexToFoundIfEvenTotalAmount) {
            currentIndex = checkA_Array ? (leftA_Array + rightA_Array) / 2 : (leftB_Array + rightB_Array) / 2;
            int elementInArrayOnCurrentIndex = checkA_Array ? a[currentIndex] : b[currentIndex];
            //get index of element if we put it in another array
            int indexOfElementInAnotherArray = checkA_Array ? simpleBinarySearch(b, elementInArrayOnCurrentIndex) : simpleBinarySearch(a, elementInArrayOnCurrentIndex);

            //vars to process duplicates
            int lastIndexOfElementInCurrentArray = -1;
            int firstIndexOfElementInCurrentArray = -1;

            int differenceWithSecondArrayLastIndexOfElement = 0;
            int differenceWithSecondArrayFirstIndexOfElement = 0;

            if (indexOfElementInAnotherArray < 0) {
                indexOfElementInAnotherArray = Math.abs(indexOfElementInAnotherArray) - 1;
            } else {
                //if index >=0 then it means that we have same values in another array, so we have to process it
                final int lastIndexOfElementInSecondArray = findBorderValue(checkA_Array ? b : a, elementInArrayOnCurrentIndex, true);
                final int firstIndexOfElementInSecondArray = findBorderValue(checkA_Array ? b : a, elementInArrayOnCurrentIndex, false);

                differenceWithSecondArrayLastIndexOfElement = (lastIndexOfElementInSecondArray + 1) - indexOfElementInAnotherArray;
                differenceWithSecondArrayFirstIndexOfElement = indexOfElementInAnotherArray - firstIndexOfElementInSecondArray;
            }

            //find duplicates in current array
            if (checkA_Array) {
                if (a.length > 1 && currentIndex < a.length - 1) {
                    if (currentIndex < a.length - 1  && a[currentIndex] == a[currentIndex + 1]) {
                        lastIndexOfElementInCurrentArray = findBorderValue(a, elementInArrayOnCurrentIndex, true);
                    }
                    if (currentIndex > 0 && a[currentIndex] == a[currentIndex - 1]) {
                        firstIndexOfElementInCurrentArray = findBorderValue(a, elementInArrayOnCurrentIndex, false);
                    }
                }
            } else {
                if (b.length > 1) {
                    if (currentIndex < b.length - 1 && b[currentIndex] == b[currentIndex + 1]) {
                        lastIndexOfElementInCurrentArray = findBorderValue(b, elementInArrayOnCurrentIndex, true);
                    }
                    if (currentIndex > 0 && b[currentIndex] == b[currentIndex - 1]) {
                        firstIndexOfElementInCurrentArray = findBorderValue(b, elementInArrayOnCurrentIndex, false);
                    }
                }
            }


            int differenceWithCurrentArrayFirstIndexOfElement = firstIndexOfElementInCurrentArray == -1 ? 0 : currentIndex - firstIndexOfElementInCurrentArray;
            int differenceWithCurrentArrayLastIndexOfElement = lastIndexOfElementInCurrentArray == -1 ? 0 : lastIndexOfElementInCurrentArray - currentIndex;

            int indexInCombinedArray = indexOfElementInAnotherArray + currentIndex;

            //getting borders to process values like range in array that considered to be sorted combination of 2
            //for example we have {1, 3, 5}; {3, 4, 6} => {1, 3, 3, 4, 5, 6} => leftBorder == 1, right border == 2,
            int leftBorder = indexInCombinedArray - differenceWithCurrentArrayFirstIndexOfElement - differenceWithSecondArrayFirstIndexOfElement;
            int rightBorder = indexInCombinedArray + differenceWithCurrentArrayLastIndexOfElement + differenceWithSecondArrayLastIndexOfElement;

            //check if we found an answer; if number of elements is even, check if it was already set, if so - finish searching
            //otherwise continue
            if ((leftBorder <= indexToFound && rightBorder >= indexToFound) && indexToFound != Integer.MIN_VALUE) {
                answer = elementInArrayOnCurrentIndex;
                //clearing indexToFoundValue to avoid cycle to finish if amount of items is even
                if (isEvenNumberOfElements) {
                    //finish searching, cause we've found both elements
                    if (additionalIdnexToFoundIfEvenTotalAmount == Integer.MIN_VALUE + 1) {
                        break;
                    }
                    indexToFound = Integer.MIN_VALUE;
                } else {
                    break;
                }
            }
            //check if we found second element if total amount of elements is even
            if (isEvenNumberOfElements && (leftBorder <= additionalIdnexToFoundIfEvenTotalAmount && rightBorder >= additionalIdnexToFoundIfEvenTotalAmount)) {
                answerIfElementHasEvenAmountOfNumbers = elementInArrayOnCurrentIndex;
                //finish searching, cause we've found both elements
                if (indexToFound == Integer.MIN_VALUE) {
                    break;
                }
                //+1 because first initialization goes with MIN_VALUE
                additionalIdnexToFoundIfEvenTotalAmount = Integer.MIN_VALUE + 1;
            }

            //switching arrays if we processed the whole one already
            if (checkA_Array && leftA_Array >= rightA_Array) {
                checkA_Array = false;
                continue;
            } else if (!checkA_Array && leftB_Array >= rightB_Array) {
                checkA_Array = true;
                continue;
            }


            //updating indexes according to which array we are processing and index state (less/more) than we need to find
            if (indexToFound != Integer.MIN_VALUE) {
                if (indexInCombinedArray < indexToFound) {
                    if (checkA_Array) {
                        leftA_Array = currentIndex + (differenceWithCurrentArrayLastIndexOfElement + 1);
                    } else {
                        leftB_Array = currentIndex + (differenceWithCurrentArrayLastIndexOfElement + 1);
                    }
                } else {
                    if (checkA_Array) {
                        rightA_Array = currentIndex - (differenceWithCurrentArrayFirstIndexOfElement + 1);
                    } else {
                        rightB_Array = currentIndex - (differenceWithCurrentArrayFirstIndexOfElement + 1);
                    }
                }
            } else if (isEvenNumberOfElements && (additionalIdnexToFoundIfEvenTotalAmount != Integer.MIN_VALUE + 1)) {
                if (indexInCombinedArray < additionalIdnexToFoundIfEvenTotalAmount) {
                    if (checkA_Array) {
                        leftA_Array = currentIndex + (differenceWithCurrentArrayLastIndexOfElement + 1);
                    } else {
                        leftB_Array = currentIndex + (differenceWithCurrentArrayLastIndexOfElement + 1);
                    }
                } else {
                    if (checkA_Array) {
                        rightA_Array = currentIndex - (differenceWithCurrentArrayFirstIndexOfElement + 1);
                    } else {
                        rightB_Array = currentIndex - (differenceWithCurrentArrayFirstIndexOfElement + 1);
                    }
                }
            }

        }

        return isEvenNumberOfElements ? ((double) answer + answerIfElementHasEvenAmountOfNumbers) / 2 : answer;
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
