package com.example.demo.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day9SmokeBasin
{
  private record Point(int row, int column) {}

  private record PointPartTwo(int row, int column, int value) {}

  // part one
  public int calculateSumOfRiskLevels(String input)
  {
    List<List<Integer>> points = parseInput(input);
    List<Integer> lowPoints = getLowPoints(points);
    return lowPoints.stream()
                    .mapToInt(point -> point + 1)
                    .sum();
  }

  // part two
  public int calculateThreeLargestBasinsProduct(String input) {
    List<List<Integer>> points = parseInput(input);
    List<PointPartTwo> lowPoints = getLowPointsPartTwo(points);
    return computeBasinsSizes(lowPoints, points);
  }

  private List<PointPartTwo> getLowPointsPartTwo(List<List<Integer>> points) {
    List<PointPartTwo> lowPoints = new ArrayList<>();
    for (int row = 0; row < points.size(); row++) {
      for (int column = 0; column < points.get(0).size(); column++) {
        if (isLowPoint(row, column, points)) {
          lowPoints.add(new PointPartTwo(row, column, points.get(row).get(column)));
        }
      }
    }
    return lowPoints;
  }

  private int computeBasinsSizes(List<PointPartTwo> lowPoints, List<List<Integer>> points) {
    return  lowPoints.stream()
                     .map(lowPoint -> computeBasinSize(lowPoint, points))
                     .sorted(Collections.reverseOrder())
                     .limit(3)
                     .reduce(1, (a, b) -> a * b);
  }

  private int computeBasinSize(PointPartTwo lowPoint, List<List<Integer>> points) {
    Set<PointPartTwo> basinPoints = new HashSet<>();

    Queue<PointPartTwo> queue = new LinkedList<>();
    queue.add(lowPoint);
    while (queue.size() > 0) {
      PointPartTwo removedPoint = queue.remove();
      if (!basinPoints.contains(removedPoint)) {
        basinPoints.add(removedPoint);

        for (PointPartTwo adjPoint : getAdjacentPoints(removedPoint, points)) {
          if (!basinPoints.contains(adjPoint)) {
            queue.add(adjPoint);
          }
        }
      }
    }
    return basinPoints.size();
  }

  private List<List<Integer>> parseInput(String input)
  {
    List<List<Integer>> points = new ArrayList<>();

    String[] rows = input.split("\\n");
    for (int row = 0; row < rows.length; row++)
    {
      var newRow = new ArrayList<Integer>();
      points.add(newRow);
      var columns = rows[row].split("");
      for (int column = 0; column < columns.length; column++)
      {
        newRow.add(Integer.valueOf(columns[column]));
      }
    }
    return points;
  }

  private List<Integer> getLowPoints(List<List<Integer>> points)
  {
    List<Integer> lowPoints = new ArrayList<>();
    for (int row = 0; row < points.size(); row++) {
      for (int column = 0; column < points.get(0).size(); column++) {
        if (isLowPoint(row, column, points)) {
          lowPoints.add(points.get(row)
                              .get(column));
        }
      }
    }
    return lowPoints;
  }

  private boolean isLowPoint(Integer row, Integer  column, List<List<Integer>> points) {
    return getAdjacentNumbers(row, column, points).stream()
                                                  .mapToInt(Integer::intValue)
                                                  .allMatch(num -> points.get(row).get(column) < num);
  }

  private List<Integer> getAdjacentNumbers(Integer row, Integer  column, List<List<Integer>> points) {

    List<Point> possibleAdjPoints = List.of(new Point(row + 1, column),
                                            new Point(row - 1, column),
                                            new Point(row, column + 1),
                                            new Point(row, column -1));

    List<Integer> adjacentNumbers = new ArrayList<>();
    for (Point possibleAdjPoint : possibleAdjPoints) {
      if (isPointValid(possibleAdjPoint.row(), possibleAdjPoint.column(), points)) {
        adjacentNumbers.add(points.get(possibleAdjPoint.row()).get(possibleAdjPoint.column()));
      }
    }
    return adjacentNumbers;
  }

  private List<PointPartTwo> getAdjacentPoints(PointPartTwo pointPartTwo, List<List<Integer>> points) {
    int row = pointPartTwo.row();
    int column = pointPartTwo.column();

    List<Point> possibleAdjPoints = List.of(new Point(row + 1, column),
                                            new Point(row - 1, column),
                                            new Point(row, column + 1),
                                            new Point(row, column -1));

    List<PointPartTwo> adjacentPoints = new ArrayList<>();
    for (Point possibleAdjPoint : possibleAdjPoints) {

      if (isPointValid(possibleAdjPoint.row(), possibleAdjPoint.column(), points)) {
        int adjPointValue = points.get(possibleAdjPoint.row()).get(possibleAdjPoint.column());
        if (adjPointValue != 9) {
          adjacentPoints.add(new PointPartTwo(possibleAdjPoint.row(), possibleAdjPoint.column(), adjPointValue));
        }

      }
    }
    return adjacentPoints;
  }

  private boolean isPointValid(Integer row, Integer  column, List<List<Integer>> points) {
    boolean isValidRow = row >= 0 && row < points.size();
    boolean isValidColumn = column >= 0 && column < points.get(0).size();
    return isValidRow && isValidColumn;
  }

}
