package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day15Chiton {

  private record PointWithCost(String rowColumn, int cost) {}

  private record Point(int row, int column) {}

  // part one
  public long computeLowestTotalRisk(String input) {
    List<List<Integer>> riskLevels = parseRiskLevels(input);
    int totalNodes = riskLevels.size() * riskLevels.get(0).size();

    // all distances set to infinity except the starter point (0, 0)
    int totalRows = riskLevels.size();
    int totalColumns = riskLevels.get(0).size();
    Map<String, Integer> distances = initializeDistances(totalRows, totalColumns);

    // priority queue that contains the points along the costs
    Comparator<PointWithCost> comparingCost = Comparator.comparing(pointWithCost -> pointWithCost.cost());
    PriorityQueue<PointWithCost> pointsWithCost = new PriorityQueue<>(totalNodes, comparingCost);
    pointsWithCost.add(new PointWithCost(getId(0, 0), 0));

    //keep track of the visited points
    Set<String> visited = new HashSet<>();

    while (!pointsWithCost.isEmpty()) {
      // remove from the queue the point with the lowest cost
      PointWithCost lowestCostPoint = pointsWithCost.remove();
      // add point to visited ones
      visited.add(lowestCostPoint.rowColumn());
      // if the already found cost is lower, than  continue with the next lowest cost point
      if (distances.get(lowestCostPoint.rowColumn()) < lowestCostPoint.cost()) { continue; }

      List<String> nonDiagonalAdjacentPoints = getNonDiagonalAdjacentPoints(lowestCostPoint.rowColumn(), riskLevels);
      for (String adjPoint : nonDiagonalAdjacentPoints) {
        // if it is not visited
        if (!visited.contains(adjPoint)) {
          int newDist = getNewDist(lowestCostPoint, adjPoint, riskLevels, distances);
          if (newDist < distances.get(adjPoint)) {
            distances.put(adjPoint, newDist);
            pointsWithCost.add(new PointWithCost(adjPoint, newDist));
          }
        }
      }
    }

    return distances.get(getId(totalRows - 1, totalColumns - 1));
  }

  // part two
  public long computeLowestTotalRiskToEntireCave(String input) {
    String cave = generateEntireCave(input);

    return computeLowestTotalRisk(cave);
  }

  private String generateEntireCave(String input) {
    String firstBlock = buildFiveTilesFrom(input);
    input = increaseRiskLevel(input);

    String secondBlock = buildFiveTilesFrom(input);
    input = increaseRiskLevel(input);

    String thirdBlock = buildFiveTilesFrom(input);
    input = increaseRiskLevel(input);

    String forthBlock = buildFiveTilesFrom(input);
    input = increaseRiskLevel(input);

    String fifthBlock = buildFiveTilesFrom(input);

    return  firstBlock +  secondBlock + thirdBlock  + forthBlock  + fifthBlock;
  }

  private String buildFiveTilesFrom(String input) {
    List<String> tiles = new ArrayList<>();
    tiles.add(input);
    for (int totalInc = 0; totalInc < 4; totalInc++) {
      input = increaseRiskLevel(input);
      tiles.add(input);
    }

    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < tiles.get(0).split("\n").length; row++) {
      StringBuilder sbRow = new StringBuilder();
      for (int tileIndex = 0; tileIndex < 5; tileIndex++) {
        sbRow.append(tiles.get(tileIndex).split("\n")[row]);
      }
      sb.append(sbRow + "\n");
    }

    return sb.toString();
  }



  private String increaseRiskLevel(String input) {
    return Arrays.stream(input.split("\n"))
                 .map(this::increaseLine)
                 .collect(Collectors.joining("\n"));
  }

  private String increaseLine(String line) {
    return Arrays.stream(line.split(""))
                 .map(risk -> String.valueOf(1 + ((Integer.parseInt(risk + 1) - 1) % 9)))
                 .collect(Collectors.joining(""));
  }



  private int getNewDist(PointWithCost lowestCostPoint,
                         String adjPoint,
                         List<List<Integer>> riskLevels,
                         Map<String, Integer> distances) {
    String[] rowColumnz = adjPoint.split("\\D");

    int adjRow = Integer.parseInt(rowColumnz[1]);
    int adjColumn = Integer.parseInt(rowColumnz[2]);

    return  distances.get(lowestCostPoint.rowColumn()) + riskLevels.get(adjRow).get(adjColumn);
  }

  private List<List<Integer>> parseRiskLevels(String input) {
    List<List<Integer>> riskLevels = new ArrayList<>();
    List<String> lines = Arrays.stream(input.split("\n"))
                               .toList();
    for (int row = 0; row < lines.size(); row++) {
      List<Integer> columnValues  = Arrays.stream(lines.get(row).split(""))
                                          .map(val -> Integer.parseInt(val))
                                          .toList();
      riskLevels.add(columnValues);
    }
    return riskLevels;
  }

  private Map<String, Integer> initializeDistances(int totalRows, int totalColumns) {
    Map<String, Integer> distances = new HashMap<>();
    for (int row = 0; row < totalRows; row++) {
      for (int column = 0; column < totalColumns; column++) {
        String key = getId(row, column);
        if (row == 0 && column == 0) {
          distances.put(key, 0);
        } else {
          distances.put(key, Integer.MAX_VALUE);
        }
      }
    }
    return distances;
  }

  private String getId(int row, int column) {
    return "(" + row + "," + column + ")";
  }

  private List<String> getNonDiagonalAdjacentPoints(String rowColumn, List<List<Integer>> riskLevels) {
    String[] rowColumnz = rowColumn.split("\\D");

    int row = Integer.parseInt(rowColumnz[1]);
    int column = Integer.parseInt(rowColumnz[2]);

    return  Arrays.asList(new Point(row + 1, column),
                          new Point(row - 1, column),
                          new Point(row, column + 1),
                          new Point(row, column - 1))
                  .stream()
                  .filter(pointz -> isPointValid(pointz, riskLevels))
                  .map(point -> getId(point.row(), point.column()))
                  .toList();
  }

  private boolean isPointValid(Point point, List<List<Integer>> riskLevels) {
    return point.row() >= 0 && point.row() < riskLevels.size() &&
           point.column() >= 0 && point.column() < riskLevels.get(0).size();
  }

}
