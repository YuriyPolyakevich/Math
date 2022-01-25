package com.company.yuriy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Double.isNaN;

/**
 * Ограничение времени 0.5 секунд
 * Ограничение памяти 64Mb
 * Ввод стандартный ввод или input.txt
 * Вывод стандартный вывод или output.txt
 * На плоскости даны п точек с координатами (x1, x1), (x2, y2),..., (xN, yN). Необходимо
 * накрыть по крайней мере k из них кругом с центром на оси Ох.
 * Найдите наименьший возможный радиус такого круга с точностью до 10^(-3)
 * Формат ввода
 * В первой строке входа даны числа п и k, 1 <= k <= n <= 10000. В следующих п строках -
 * два целых числа, координаты соотетствующей точки. Координаты не превосходят по модулю 1000.
 * Формат вывода
 * Выведите радиус круга ровно с 6 знаками после запятой. Ваш ответ должен отличаться от
 * правильного не более, чем на 10^(-3), вывести нужно при этом 6 знаков, чтобы избежать
 * ошибок из-за округления.
 * Пример 1
 * Ввод
 * 3 3
 * 0 5
 * 3 4
 * -4 -3
 * Вывод
 * 5.000000
 */
public class MinimumRadiusToOverlapK_Points {


    public static void main(String[] args) {
        final int[] x = new int[] {3, 0, 3, -4};
        final int[] y = new int[] {3, 6, 4, -3};
        final int k = 3;
        new MinimumRadiusToOverlapK_Points().getResult(x, y, k);
    }

    public double getResult(int[] x, int[] y, int k) {
        double minRadius = 0;
        final Integer[] indexes = IntStream.range(0, x.length).boxed().toArray(Integer[]::new);
        final Comparator<Integer> xComp = Comparator.comparingInt(a -> x[a]);
        Arrays.sort(indexes, 0, indexes.length, xComp);
        double leftRadius = 0;
        double rightRadius = 10_000;
        final double threshold = 0.0001;
        while (rightRadius - leftRadius > threshold) {
            minRadius = (leftRadius + rightRadius) / 2;
            final List<Point> points = new ArrayList<>();
            final double currentRadius = minRadius;
            Arrays.stream(indexes).map(i -> new Point(x[i], y[i], false)).forEach(point -> {
                final double intersection = Math.sqrt(currentRadius * currentRadius - (point.y * point.y));
                if (!isNaN(intersection)) {
                    points.add(new Point(point.x - intersection, 0, false));
                    points.add(new Point(point.x + intersection, 0, true));
                }
            });
            if (points.size() / 2 < k) {
                leftRadius = minRadius;
                continue;
            }
            int counter = 0;
            points.sort(Comparator.comparingDouble(Point::getX));
            for (final Point p : points) {
                counter = p.end ? counter - 1 : counter + 1;
                if (counter == k) {
                    break;
                }
            }

            if (counter == k) {
                rightRadius = minRadius;
            } else {
                leftRadius = minRadius;
            }

        }
        final DecimalFormat numberFormat = new DecimalFormat("#.000000");;
        System.out.println(numberFormat.format(minRadius));
        return minRadius;
    }

    final class Point {
        double x, y;
        boolean end;

        public double getX() {return x;}

        public Point(double x, double y, boolean end) {
            this.x = x;
            this.y = y;
            this.end = end;
        }
    }

}
