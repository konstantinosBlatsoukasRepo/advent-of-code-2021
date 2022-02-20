package com.example.demo.days;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Day12PassagePathing {

  // part one
  public int totalPathsVisitingSmallCavesOnce(String input) {
    Map<String, List<String>> graph = parse(input);
    return generateAllPaths(graph, 1).size();
  }

  // part two
  public int totalPathsVisitingSmallCavesTwice(String input) {
    Map<String, List<String>> graph = parse(input);
    return generateAllPathsPartTwo(graph, 2).size();
  }

  private Map<String, List<String>> parse(String input) {
    String[] connections = input.split("\n");

    Map<String, List<String>> graph = new HashMap<>();
    for (String connection : connections)
    {
      String[] nodes = connection.split("-");

      String firstNode = nodes[0];
      String secondNode = nodes[1];

      if (firstNode.equals("start") && graph.containsKey("start")) {
        List<String> firstNodes = graph.get(firstNode);
        firstNodes.add(secondNode);
      } else if (firstNode.equals("start") && !graph.containsKey("start")) {
        graph.put(firstNode, new ArrayList<>());
        List<String> firstNodes = graph.get(firstNode);

        firstNodes.add(secondNode);
      } else if (secondNode.equals("start") && graph.containsKey("start")) {
        List<String> secondNodes = graph.get(secondNode);
        secondNodes.add(firstNode);
      } else if (secondNode.equals("start") && !graph.containsKey("start")) {
        graph.put(secondNode, new ArrayList<>());
        List<String> secondNodes = graph.get(secondNode);
        secondNodes.add(firstNode);
      } else if (secondNode.equals("end") && graph.containsKey(firstNode)) {
        List<String> firstNodes = graph.get(firstNode);
        firstNodes.add(secondNode);

      } else if (secondNode.equals("end") && !graph.containsKey(firstNode)) {
        graph.put(firstNode, new ArrayList<>());

        List<String> firstNodes = graph.get(firstNode);
        firstNodes.add(secondNode);
      } else if (graph.containsKey(firstNode) && graph.containsKey(secondNode)) {
        List<String> relFirstNodes = graph.get(firstNode);
        relFirstNodes.add(secondNode);

        List<String> relSecondNodes = graph.get(secondNode);
        relSecondNodes.add(firstNode);
      } else if (graph.containsKey(firstNode) && !graph.containsKey(secondNode)) {
        List<String> relFirstNodes = graph.get(firstNode);
        relFirstNodes.add(secondNode);

        graph.put(secondNode, new ArrayList<>());

        List<String> secondNodes = graph.get(secondNode);
        secondNodes.add(firstNode);
      }else if (!graph.containsKey(firstNode) && graph.containsKey(secondNode)) {
        List<String> relSecondNodes = graph.get(secondNode);
        relSecondNodes.add(firstNode);

        graph.put(firstNode, new ArrayList<>());

        List<String> firstNodes = graph.get(firstNode);
        firstNodes.add(secondNode);
      } else if (!graph.containsKey(firstNode) && !graph.containsKey(secondNode)) {
        graph.put(firstNode, new ArrayList<>());

        List<String> firstNodes = graph.get(firstNode);
        firstNodes.add(secondNode);

        graph.put(secondNode, new ArrayList<>());

        List<String> secondNodes = graph.get(secondNode);
        secondNodes.add(firstNode);
      }
    }
    return graph;
  }

  private List<List<String>> generateAllPaths(Map<String, List<String>> graph, int totalValidOccurrences) {
    List<List<String>> generatedPaths = new ArrayList<>();

    String goalNode = "end";
    String startNode = "start";

    // starting node initialization
    Queue<List<String>> paths = new LinkedList<>();
    paths.add(Arrays.asList(startNode));

    while (!paths.isEmpty()) {
      List<String> currentPath = paths.poll();

      String lastNode = currentPath.get(currentPath.size() - 1);

      Map<String, Long> lowCasesHistogram = currentPath.stream()
                                                       .filter(this::isLowCase)
                                                       .collect(groupingBy(identity(), counting()));

      if (lowCasesHistogram.containsKey(lastNode)
          && lowCasesHistogram.get(lastNode) > totalValidOccurrences) {
        continue;
      }

      if (lastNode.equals(goalNode)) {
        generatedPaths.add(currentPath);
      } else {
        List<String> neighbors = graph.get(lastNode);
        for (String neighbor : neighbors) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(neighbor);
            paths.add(newPath);
        }
      }
    }
    return generatedPaths;
  }

  private List<List<String>> generateAllPathsPartTwo(Map<String, List<String>> graph, int totalValidOccurrences) {
    List<List<String>> generatedPaths = new ArrayList<>();

    String goalNode = "end";
    String startNode = "start";

    // starting node initialization
    Queue<List<String>> paths = new LinkedList<>();
    paths.add(List.of(startNode));

    while (!paths.isEmpty()) {
      List<String> currentPath = paths.poll();

      String lastNode = currentPath.get(currentPath.size() - 1);

      if (isLowCase(lastNode)) {
        Map<String, Long> lowCasesHistogram = currentPath.stream()
                                                         .filter(this::isLowCase)
                                                         .collect(groupingBy(identity(), counting()));

        long totalTwices = lowCasesHistogram.entrySet()
                                                        .stream()
                                                        .filter(entry -> entry.getValue() > 1)
                                                        .count();

        if (lowCasesHistogram.containsKey(lastNode)
            && lowCasesHistogram.get(lastNode) > totalValidOccurrences || totalTwices > 1) {
          continue;
        }
      }

      if (lastNode.equals(goalNode)) {
        generatedPaths.add(currentPath);
      } else {
        List<String> neighbors = graph.get(lastNode);
        for (String neighbor : neighbors) {
          List<String> newPath = new ArrayList<>(currentPath);
          newPath.add(neighbor);
          paths.add(newPath);
        }
      }
    }
    return generatedPaths;
  }

  private boolean isLowCase(String node) {
    return node.toLowerCase().equals(node) && !node.equals("start") && !node.equals("end");
  }
}
