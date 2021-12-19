package com.company;

import com.sun.xml.internal.ws.spi.db.DatabindingException;

import java.util.ArrayList;
import java.util.List;

/**
 * есть набор точек, заданных координатами -1,000,000< x[i], y[i] < +1,000,000
 * нужно найти площадь выпуклой оболочки этих точек
 **/

public class ConvexHullSquare implements Task {

    @Override
    public Object solveTask(Object... args) {
        int x[] = (int[]) args[0];
        int y[] = (int[]) args[1];
        int numberOfPoints = x.length;
        if (numberOfPoints != y.length || numberOfPoints < 3) {
            return String.format("Wrong numberOfPoints x: %s, y: %s", numberOfPoints, y.length);
        }
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(x[0], y[0], 0));
        //finding most left point
        for (int i = 1; i < numberOfPoints; i++) {
            final Point e = new Point(x[i], y[i], i);
            if (points.get(0).x > x[i] || (points.get(0).x == x[i] && points.get(0).y < y[i])) {
                Point point = points.get(0);
                points.set(0, e);
                points.add(point);
            } else {
                points.add(e);
            }
        }
        Point currentPoint = points.get(0);
        points.remove(0);
        final List<Point> orderedPoints = new ArrayList<Point>();
        orderedPoints.add(currentPoint);
        do {
            Point nextPoint = points.get(0);
            for (int i = 1; i < points.size(); i++) {
                final Point checkPoint = points.get(i);
                int cross = crossProduct(checkPoint.x - currentPoint.x,
                        nextPoint.x - currentPoint.x,
                        checkPoint.y - currentPoint.y,
                        nextPoint.y - currentPoint.y);
                if (cross < 0) {
                    nextPoint = checkPoint;
                } else if (cross == 0) {
                    double dist = pointDistance(nextPoint, currentPoint);
                    double dist1 = pointDistance(checkPoint, currentPoint);
                    if (dist1 < dist) {
                        nextPoint = checkPoint;
                    }
                }
            }
            currentPoint = nextPoint;
            orderedPoints.add(currentPoint);
            points.remove(currentPoint);
        } while (orderedPoints.size() != numberOfPoints);
        return square(orderedPoints);
    }

    private double pointDistance(Point p1, Point p2) {
        int x1 = p1.x;
        int x2 = p2.x;
        int y1 = p1.y;
        int y2 = p2.y;
        return Math.sqrt(((x2 - x1) * (x2 - x1)) * ((y2 - y1) * (y2 - y1)));
    }

    //takes list of points ordered from left to right clockwise and calculates
    //square using triangle method
    private int square(List<Point> p) {
        int totalSquare = 0;
        for (int i = 1; i + 1 < p.size(); i++) {
            int x1 = p.get(i).x - p.get(0).x;
            int y1 = p.get(i).y - p.get(0).y;
            int x2 = p.get(i + 1).x - p.get(0).x;
            int y2 = p.get(i + 1).y - p.get(0).y;
            int cross = crossProduct(x1, x2, y1, y2);
            totalSquare += cross;
        }
        return Math.abs(totalSquare) / 2;
    }

    private int crossProduct(int x1, int x2, int y1, int y2) {
        return x1 * y2 - y1 * x2;
    }

    public static class Point {
        int x;
        int y;
        int index;

        Point(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
