package com.example.demo.days;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5HydrothermalVenture {

  public record Point (int column, int row) { }

  public record Line (List<Point> points) { }

  public record Board (List<Line> lines) { }

  // part one
  public  int totalNumOFPointsAtlLeastTwoLinesOverlap(String input) {
    return countPointsThatAtlLeastTwoLinesOverlap(parseHorizontalAndVerticalLines(input));
  }

  // part one
  public int totalNumOFPointsAtlLeastTwoLinesOverlapPartTwo(String input) {
    return countPointsThatAtlLeastTwoLinesOverlap(parseAllLines(input));
  }

  private int countPointsThatAtlLeastTwoLinesOverlap(Board board) {
    Map<Point, Long> pointsFrequencies = board.lines()
                                              .stream()
                                              .map(Line::points)
                                              .flatMap(Collection::stream)
                                              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    long totalPoints = pointsFrequencies.values()
                                        .stream()
                                        .filter(val -> val >= 2)
                                        .count();

    return (int) totalPoints;
  }

  private Board parseHorizontalAndVerticalLines(String input) {
    String[] splitInput = input.split("\n");

    List<Line> lines = Stream.of(splitInput)
                             .map(item -> item.replaceAll("\\s|->|,", " "))
                             .filter(this::isLineHorizontalOrVertical)
                             .map(this::getHorizontalOrVerticalLine)
                             .toList();

    return new Board(lines);
  }

  private Board parseAllLines(String input) {
    String[] splitInput = input.split("\n");

    List<Line> lines = Stream.of(splitInput)
                             .map(item -> getLine(item.replaceAll("\\s|->|,", " ")))
                             .toList();

    return new Board(lines);
  }

  private boolean isLineHorizontalOrVertical(String charNum) {
    var split = charNum.split("\\s+");
    return Integer.parseInt(split[0]) == Integer.parseInt(split[2]) ||
           Integer.parseInt(split[1]) == Integer.parseInt(split[3]);
  }

  private Line getHorizontalOrVerticalLine(String charNum) {
    var split = charNum.split("\\s+");
    int column1 = Integer.parseInt(split[0]);
    int row1 = Integer.parseInt(split[1]);

    int column2 = Integer.parseInt(split[2]);
    int row2 = Integer.parseInt(split[3]);

    var points = new ArrayList<Point>();
    if (row1 == row2 && column1 > column2) {
      for (int column = column2; column <= column1; column++) {
        points.add(new Point (column, row1));
      }
    } else if (row1 == row2 && column1 < column2) {
      for (int column = column1; column <= column2; column++) {
        points.add(new Point (column, row1));
      }
    } else if (column1 == column2 && row1 > row2) {
      for (int row = row2; row <= row1; row++) {
        points.add(new Point (column1, row));
      }
    } else {
      for (int row = row1; row <= row2; row++) {
        points.add(new Point (column1, row));
      }
    }

    return new Line (points);
  }

  private Line getLine(String charNum) {
    if (isLineHorizontalOrVertical(charNum)) {
      return getHorizontalOrVerticalLine(charNum);
    }
    var split = charNum.split("\\s+");

    int column1 = Integer.parseInt(split[0]);
    int row1 = Integer.parseInt(split[1]);

    int column2 = Integer.parseInt(split[2]);
    int row2 = Integer.parseInt(split[3]);

    return new Line(getDiagonalPoints(column1, row1, column2, row2));
  }

  private ArrayList<Point> getDiagonalPoints(int column1, int row1, int column2, int row2) {
    var points = new ArrayList<Point>();
    if (column1 > column2 && row1 > row2) {
      int limit = column1 - column2;
      for (int i = 0; i <= limit; i++) {
        points.add(new Point (column1, row1));
        column1--;
        row1--;
      }
    } else if (column1 < column2 && row1 < row2) {
      int limit = column2 - column1;
      for (int i = 0; i <= limit; i++) {
        points.add(new Point (column1, row1));
        column1++;
        row1++;
      }
    } else if (column1 < column2 && row1 > row2) {
      int limit = column2 - column1;
      for (int i = 0; i <= limit; i++) {
        points.add(new Point (column1, row1));
        column1++;
        row1--;
      }
    } else if (column1 > column2 && row1 < row2) {
      int limit = column1 - column2;
      for (int i = 0; i <= limit; i++) {
        points.add(new Point (column1, row1));
        column1--;
        row1++;
      }
    }
    return points;
  }

}
