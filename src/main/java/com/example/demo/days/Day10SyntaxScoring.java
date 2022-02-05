package com.example.demo.days;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

public class Day10SyntaxScoring {

  private static final Set<String> LEFT_BRACES = Set.of("[", "{", "(", "<");

  private static final Set<String> RIGHT_BRACES = Set.of("]", "}", ")", ">");

  private static final Map<String, String> BRACES_PAIRS =
  Map.of("[", "]", "{", "}", "(", ")", "<", ">");

  private static final Map<String, Long> BRACES_POINTS =
  Map.of("]", 57L, "}", 1197L, ")", 3L,  ">", 25137L);

  private static final Map<String, Long> BRACES_POINTS_PART_TWO =
  Map.of(")", 1L, "]", 2L, "}", 3L, ">", 4L );

  public record Line(List<String> braces) {}

  // part one
  public long computeTotalSyntaxErrorScore(String input) {
    List<Line> lines = parseInput(input);
    List<String> illegalChars = new ArrayList<>();
    for (Line line : lines) {
      Optional<String> illegalChar = processCorrupted(line);
      illegalChar.ifPresent(illegalChars::add);
    }

    Map<String, Long> illegalsHistogram = illegalChars.stream()
                                                      .collect(groupingBy(identity(), counting()));

    long result = 0;
    for (Entry<String, Long> entry : illegalsHistogram.entrySet()) {
      String illegalChar = entry.getKey();
      long illegalOccurrences = entry.getValue();
      long totalPoints = BRACES_POINTS.get(illegalChar) * illegalOccurrences;
      result += totalPoints;
    }

    return result;
  }

  // part two
  public long computeMiddleScore(String input) {
    List<Line> lines = parseInput(input);
    List<String> missingBraces = new ArrayList<>();
    for (Line line : lines) {
      Optional<String> result = processIncomplete(line);
      result.ifPresent(missingBraces::add);
    }

    List<Long> missingBracesScores = computeMissingBracesScores(missingBraces);

    return missingBracesScores.get(missingBracesScores.size()/2);
  }

  private List<Line> parseInput(String input) {
    List<Line> lines = new ArrayList<>();
    for (String line : input.split("\n")) {

      List<String> characters = new ArrayList<>(Arrays.asList(line.split("")));

      lines.add(new Line(characters));
    }

    return lines;
  }

  private Optional<String> processCorrupted(Line line) {
    Stack<String> leftBraces = new Stack<>();
    for (String bracket : line.braces()) {
      if (LEFT_BRACES.contains(bracket)) {
        leftBraces.add(bracket);
      } else if (isIllegalChar(leftBraces, bracket)) {
        return Optional.of(bracket);
      }
    }
    return Optional.empty();
  }

  private Optional<String> processIncomplete(Line line) {
    Stack<String> leftBraces = new Stack<>();
    for (String bracket : line.braces()) {
      if (LEFT_BRACES.contains(bracket)) {
        leftBraces.add(bracket);
      } else if (isIllegalChar(leftBraces, bracket)) {
        return Optional.empty();
      }
    }

    List<String> missingBraces = leftBraces.stream()
                                           .map(BRACES_PAIRS::get)
                                           .toList();

    String missingBracesComplete = String.join("", missingBraces);

    String result = new StringBuilder(missingBracesComplete).reverse().toString();

    return Optional.of(result);
  }


  private List<Long> computeMissingBracesScores(List<String> missingBraces) {
    List<Long> scores = new ArrayList<>();
    for (String missingBrace : missingBraces) {
      long currentScore = 0L;
      for (String brace : missingBrace.split("")) {
        currentScore = (currentScore * 5) + BRACES_POINTS_PART_TWO.get(brace);
      }
      scores.add(currentScore);
    }

    return scores.stream().sorted().toList();
  }

  private boolean isIllegalChar(Stack<String> leftBraces, String bracket) {
    return RIGHT_BRACES.contains(bracket)
    && !BRACES_PAIRS.get(leftBraces.pop()).equals(bracket);
  }


}
