package com.company.yuriy;

/**
 * Created by yuriy_polyakevich at 1/19/22
 */

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * ну и лёгкая.
 *
 * Правильной скобочной последовательностью называется строка, состоящая только из скобок, в которой все скобки можно разбить на пары таким образом, что:
 *
 * в каждой паре есть левая и правая скобка, причем левая скобка расположена левее правой;
 * для любых двух пар скобок либо одна из них полностью внутри другой пары, либо промежутки между скобками в парах не пересекаются
 * в паре с круглой скобкой может быть только круглая скобка, с квадратной — квадратная, с фигурной — фигурная
 * Примеры:
 *
 * Если разрешены только круглые скобки:
 * правильные последовательности: (), (()), ()(), ()()(), (())(), ((()))
 * неправильные последовательности: )(, )), ((, ())()(, ()), ))((
 * Если разрешены круглые и квадратные скобки:
 * правильные последовательности: [],(), [()], [[([])]()]
 * неправильные последовательности: [), ([)], (())()[]][
 * Если разрешены еще и фигурные скобки:
 * правильные последовательности: [{(())}({})], []{}(), {}, (), []
 * неправильные последовательности: [{(})], [(]())]{}
 *
 *
 * Требуется определить, является ли она правильной скобочной последовательностью. Если да, выведите слово CORRECT
 * Если нет, выведите длину максимального префикса ,
 * который либо сам является правильной скобочной последовательностью,
 * либо может быть продолжен до таковой.
 *
 * напремер
 * ([)]
 * 2
 */
public class BracketCorrectness implements Task{

    public static void main(String[] args) {
        final String task = "(())()[]][";
        Object answer = new BracketCorrectness().solveTask(task);
        System.out.println(answer);
    }


    @Override
    public Object solveTask(Object... args) {
        final List<Character> leftBrackets = Arrays.asList('{', '(', '[');
        final List<Character> rightBrackets = Arrays.asList('}', ')', ']');
        final char[] symbols = ((String) args[0]).toCharArray();
        final Stack<Character> leftStack = new Stack<>();
        final int size = symbols.length;
        for (int i = 0; i < size; i++) {
            char symbol = symbols[i];
            if (leftBrackets.contains(symbol)) {
                leftStack.push(symbol);
                continue;
            }
            if (rightBrackets.contains(symbol)) {
                if (leftStack.size() == 0) {
                    return i;
                }
                final Character pop = leftStack.pop();
                if (leftBrackets.indexOf(pop) != rightBrackets.indexOf(symbol)) {
                    //-1 cause we popped the symbol
                    return i;
                }
            }
        }
        return leftStack.size() == 0 ? "CORRECT" : leftStack.size();
    }
}
















