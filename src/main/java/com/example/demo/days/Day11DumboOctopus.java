package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day11DumboOctopus {

  private record Point(int row, int column, int value) {}

  // part one
  public int computeTotalFlashesAfter(int steps, String input) {
    List<List<Point>> points = parse(input);
    int totalFlashes = 0;
    for (int step = 0; step < steps; step++) {
      totalFlashes += performStep(points);
    }
    return totalFlashes;
  }

  // part two
  public int computeStepThatAllFlashing(String input) {
    List<List<Point>> points = parse(input);
    int currentStep = 0;

    do {
      performStep(points);
      currentStep++;
    }
    while (!areAllFlashing(points));

    return currentStep;
  }

  private boolean areAllFlashing(List<List<Point>> points) {
    for (List<Point> point : points)
    {
      for (int column = 0; column < points.get(0).size(); column++) {
        if (point.get(column).value() != 0) {
          return false;
        }
      }
    }
    return true;
  }

  private int performStep(List<List<Point>> points) {
    Set<Point> stepFlashedPoints = increaseEnergy(points);

    Queue<Point> newFlashedPoints = new LinkedList<>(stepFlashedPoints);
    int totalStepFlashes = newFlashedPoints.size();

    while (newFlashedPoints.size() > 0) {
      Point flashedPoint = newFlashedPoints.remove();
      List<Point> adjacentPoints = getAdjacentPoints(flashedPoint, points);
      Set<Point> newAdjFlashed = increaseAdjPointsEnergy(points, stepFlashedPoints, adjacentPoints);
      stepFlashedPoints.addAll(newAdjFlashed);
      newFlashedPoints.addAll(newAdjFlashed);
      totalStepFlashes += newAdjFlashed.size();
    }
    return totalStepFlashes;
  }

  private Set<Point> increaseEnergy(List<List<Point>> points) {
    Set<Point> flashedPoints = new HashSet<>();
    for (int row = 0; row < points.size(); row++) {
      for (int column = 0; column < points.get(0).size(); column++) {
        Point point = points.get(row)
                            .get(column);
        Point newPoint;
        if (point.value() + 1 > 9) {
          newPoint = new Point(row, column, 0);
          flashedPoints.add(newPoint);
        } else {
          newPoint = new Point(row, column, point.value() + 1);
        }
        points.get(row).set(column, newPoint);
      }
    }
    return flashedPoints;
  }

  private Set<Point> increaseAdjPointsEnergy(List<List<Point>> points,
                                             Set<Point> flashedPoints,
                                             List<Point> adjacentPoints) {
    Set<Point> newFlashed = new HashSet<>();
    for (Point adjacentPoint : adjacentPoints) {
      if (!flashedPoints.contains(adjacentPoint)) {
        int oldValue = points.get(adjacentPoint.row()).get(adjacentPoint.column()).value();
        if (oldValue + 1 > 9) {
          Point newPoint = new Point(adjacentPoint.row(), adjacentPoint.column(), 0);
          newFlashed.add(newPoint);
          points.get(adjacentPoint.row()).set(adjacentPoint.column(), newPoint);
        } else if (oldValue >= 0 && !flashedPoints.contains(adjacentPoint)) {
          Point newPoint = new Point(adjacentPoint.row(), adjacentPoint.column(), oldValue + 1);
          points.get(adjacentPoint.row()).set(adjacentPoint.column(), newPoint);
        }
      }
    }
    return newFlashed;
  }

  private List<List<Point>> parse(String input) {
    List<String> lines = Arrays.stream(input.split("\n"))
                               .toList();

    List<List<Point>> points = new ArrayList<>();

    for (int row = 0; row < lines.size(); row++) {
      List<Point> rowPoints = new ArrayList<>();
      List<String> columnValues = Arrays.stream(lines.get(row).split(""))
                                   .toList();
      for (int column = 0; column < columnValues.size(); column++) {
        int value = Integer.parseInt(columnValues.get(column));
        rowPoints.add(new Point(row, column, value));

      }
      points.add(rowPoints);
    }
    return points;
  }

  private List<Point> getAdjacentPoints(Point point, List<List<Point>> points) {
    List<Point> adjcCandidates = List.of(new Point(point.row() + 1, point.column(), -1),
                                         new Point(point.row(), point.column() + 1, -1),
                                         new Point(point.row() - 1, point.column(), -1),
                                         new Point(point.row(), point.column() - 1, -1),
                                         new Point(point.row() + 1, point.column() - 1, -1),
                                         new Point(point.row() - 1, point.column() + 1, -1),
                                         new Point(point.row() + 1, point.column() + 1, -1),
                                         new Point(point.row() - 1, point.column() - 1, -1));

    List<Point> adjcPoints = new ArrayList<>();
    for (Point adjcCandidate : adjcCandidates) {
      if (isPointValid(adjcCandidate, points)) {
        adjcPoints.add(points.get(adjcCandidate.row()).get(adjcCandidate.column()));
      }
    }

    return adjcPoints;
  }

  private boolean isPointValid(Point point, List<List<Point>> points) {
    return point.row() >= 0 && point.row() < points.size()
           && point.column() >= 0 && point.column() < points.get(0).size();
  }

}
