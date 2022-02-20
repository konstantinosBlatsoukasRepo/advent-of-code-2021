package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13TransparentOrigami {

  private record Instruction(String upOrLeft, int value) {}

  private record TransparentPaper(List<List<String>> randomDots, List<Instruction> instructions) {}

  private record Point(int column, int row) {}

  // part one
  public int computeVisibleDots(String input) {
    TransparentPaper transparentPaper = parse(input);

    List<List<String>> foldedUp = foldPaper(transparentPaper.randomDots(), transparentPaper.instructions()
                                                                                        .get(0));

    long count = foldedUp.stream()
                         .flatMap(Collection::stream)
                         .filter(val -> val.equals("#"))
                         .count();
    return (int) count;
  }

  // part two
  public void displayCoe(String input) {
    TransparentPaper transparentPaper = parse(input);

    List<List<String>> dots = transparentPaper.randomDots();
    for (Instruction instruction : transparentPaper.instructions()) {
      dots = foldPaper(dots, instruction);
//      print(dots);
//      System.out.println("----------------------");
//      System.out.println("----------------------");
    }
    print(dots);

  }

  private List<List<String>> foldPaper(List<List<String>> randomDots, Instruction instruction) {
    String upOrLeft = instruction.upOrLeft();

    List<List<String>> foldedPaper;
    if (upOrLeft.equals("up")) {
      foldedPaper = foldUp(randomDots, instruction.value());
    } else {
      foldedPaper = foldLeft(randomDots, instruction.value());
    }

    return foldedPaper;
  }

  private List<List<String>> foldUp(List<List<String>> randomDots, int where) {
    List<List<String>> foldedPoints = new ArrayList<>();

    int lowRow = 0;
    int highRow = randomDots.size() - 1;
    for (int mergedRowIndex = 0; mergedRowIndex < where; mergedRowIndex++) {
      List<String> mergedRow = new ArrayList<>();
      for (int columnIndex = 0; columnIndex < randomDots.get(0).size(); columnIndex++) {
        if (randomDots.get(lowRow).get(columnIndex).equals("#") ||
            randomDots.get(highRow).get(columnIndex).equals("#")) {
          mergedRow.add("#");
        } else {
          mergedRow.add(" ");
        }
      }
      foldedPoints.add(mergedRow);
      lowRow++;
      highRow--;
    }
    return foldedPoints;
  }

  private List<List<String>> foldLeft(List<List<String>> randomDots, int where) {
    List<List<String>> foldedPoints = new ArrayList<>();
    for (List<String> randomDot : randomDots) {

      int lowColumn = 0;
      int highColumn = randomDots.get(0)
                                 .size() - 1;
      List<String> mergedRow = new ArrayList<>();
      for (int columnIndex = 0; columnIndex < where; columnIndex++)
      {
        if (randomDot.get(lowColumn)
                     .equals("#") || randomDot.get(highColumn)
                                              .equals("#")) {
          mergedRow.add("#");
        } else {
          mergedRow.add(" ");
        }
        ++lowColumn;
        --highColumn;
      }
      foldedPoints.add(mergedRow);

    }
    return foldedPoints;
  }

  private void print(List<List<String>> randomDots) {
    for (int row = 0; row < randomDots.size(); row++) {
      StringBuilder sb = new StringBuilder();
      for (int column = 0; column < randomDots.get(0).size(); column++) {
        sb.append(randomDots.get(row).get(column));
      }
      System.out.println(sb);
    }
  }

  private TransparentPaper parse(String input) {
    List<String> lines = Arrays.stream(input.split("\n"))
                                 .toList();

    Set<Point> points = new HashSet<>();
    List<Instruction> instructions = new ArrayList<>();
    for (String line : lines) {
      if (line.contains(",")) {
        String[] columnRow = line.split(",");
        int column = Integer.valueOf(columnRow[0]);
        int row = Integer.valueOf(columnRow[1]);
        points.add(new Point(column, row));
      } else if (line.contains("y")) {
        var instruction = new Instruction("up", Integer.parseInt(line.split("=")[1]));
        instructions.add(instruction);
      } else if (line.contains("x")) {
        var instruction = new Instruction("left", Integer.parseInt(line.split("=")[1]));
        instructions.add(instruction);
      }
    }

    int maxColumn = points.stream()
                          .mapToInt(Point::column)
                          .max()
                          .getAsInt();

    int maxRow = points.stream()
                          .mapToInt(Point::row)
                          .max()
                          .getAsInt();

    List<List<String>> randomDots = new ArrayList<>();
    for (int row = 0; row <= maxRow; row++) {
      List<String> rowValues = new ArrayList<>();
      for (int column = 0; column <= maxColumn; column++) {
        if (points.contains(new Point(column, row))) {
          rowValues.add("#");
        } else {
          rowValues.add(" ");
        }
      }
      randomDots.add(rowValues);
    }

    return new TransparentPaper(randomDots, instructions);
  }

}
