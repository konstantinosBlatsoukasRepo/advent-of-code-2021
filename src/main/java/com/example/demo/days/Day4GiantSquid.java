package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4GiantSquid {

  private enum RowOrColumn {
    ROW,
    COLUMN
  }
  public record NumbersAndTables(int[] randomNums, int[][][] tables) { }

  public record Winner(int tableId, int rowOrColumnId, RowOrColumn rowOrColumn, int lastNumDrawnIndex) { }

  public record WinnerWithNums(Winner winner, int[] randomNums, int sum) { }

  // part 1
  public int calculateWinningBoardFinalScore(String input) {
    NumbersAndTables numbersAndTables = parseNumbersAndTables(input);
    Winner winner = findWinner(numbersAndTables);

    int[][][] tables = numbersAndTables.tables();
    int[][] winningTable = tables[winner.tableId()];
    int[] currentRandomNums = Arrays.copyOfRange(numbersAndTables.randomNums(), 0, winner.lastNumDrawnIndex());

    var drawnNums = Arrays.stream(currentRandomNums)
                          .boxed()
                          .collect(Collectors.toCollection(HashSet::new));

    int sum = calculateSumOfUnmarked(winningTable, drawnNums);

    return sum * currentRandomNums[winner.lastNumDrawnIndex() - 1];
  }

  // part 2
  public int calculateLastWinningTableFinalScore(String input) {
    NumbersAndTables numbersAndTables = parseNumbersAndTables(input);

    WinnerWithNums lastWinner = findLastWinner(numbersAndTables);

    return lastWinner.sum() * lastWinner.randomNums()[lastWinner.winner().lastNumDrawnIndex() - 1];
  }

  private WinnerWithNums findLastWinner(NumbersAndTables numbersAndTables) {
    int[] randomNums = numbersAndTables.randomNums();
    int[][][] tables = numbersAndTables.tables();

    int lastNumDrawnIndex = 5;

    var winningTables = new HashSet<Integer>();
    var lastWinner = new WinnerWithNums(null, null, -1);
    while (true) {
      int[] currentRandomNums = Arrays.copyOfRange(randomNums, 0, lastNumDrawnIndex);

      WinnerWithNums winner = scanTablesPartTwo(tables, currentRandomNums, lastNumDrawnIndex, winningTables);

      if (winner.winner() != null &&
          winner.winner().tableId() != -1 &&
          !winningTables.contains(winner.winner().tableId())) {
        winningTables.add(winner.winner().tableId());
        lastWinner = winner;
      }

      if (lastNumDrawnIndex == randomNums.length || winningTables.size() == tables.length) {
        return lastWinner;
      }

      lastNumDrawnIndex++;
    }

  }

  private NumbersAndTables parseNumbersAndTables(String input) {
    String[] splitInput = input.split("\n");

    int[] randomNums = Stream.of(splitInput[0].split(","))
                             .mapToInt(Integer::parseInt)
                             .toArray();

    List<String> tableRows = Stream.of(Arrays.copyOfRange(splitInput, 1, splitInput.length))
                                      .filter(row -> !row.equals("\n")).toList();

    List<List<String>> stringTables = new ArrayList<>();
    int currentTableIndex = 0;
    for (String tableRow : tableRows) {
      if (tableRow.equals("")) {
        List<String> stringTable = new ArrayList<>();
        stringTables.add(stringTable);
        currentTableIndex = stringTables.size() - 1;
      } else {
        stringTables.get(currentTableIndex).add(tableRow);
      }
    }

    int [][][] tables = new int[stringTables.size()][][];

    for (int i = 0; i < stringTables.size(); i++) {
      List<String> currentStringTable = stringTables.get(i);
      int [][] newTable = new int[currentStringTable.size()][];
      tables[i] = newTable;

      for (int innerTableIndex = 0; innerTableIndex < currentStringTable.size(); innerTableIndex++) {
        List<String> tableArRows = Stream.of(currentStringTable.get(innerTableIndex).split(" ")).filter(num -> !num.isEmpty()).toList();
        newTable[innerTableIndex] = new int[tableArRows.size()];
        for (int rowIndex = 0; rowIndex < tableArRows.size(); rowIndex++) {
          newTable[innerTableIndex][rowIndex] = Integer.parseInt(tableArRows.get(rowIndex));
        }
      }
    }

    return new NumbersAndTables(randomNums, tables);
  }

  private Winner findWinner(NumbersAndTables numbersAndTables) {
    int[] randomNums = numbersAndTables.randomNums();
    int[][][] tables = numbersAndTables.tables();

    int lastNumDrawnIndex = 5;

    while (true) {
      int[] currentRandomNums = Arrays.copyOfRange(randomNums, 0, lastNumDrawnIndex);

      Winner winner = scanTables(tables, currentRandomNums, lastNumDrawnIndex);
      if (winner.tableId() != -1) {
        return winner;
      }

      lastNumDrawnIndex++;
    }
  }

  private Winner scanTables(int[][][] tables, int[] currentRandomNums, int lastNumDrawnIndex) {
    // iterate over tables
    for (int currentTableIndex = 0; currentTableIndex < tables.length; currentTableIndex++) {

      int[][] currentTable = tables[currentTableIndex];

      int rowIndexResult = scanRows(currentTable, currentRandomNums);
      if (rowIndexResult != -1) {
        return new Winner(currentTableIndex, rowIndexResult, RowOrColumn.ROW, lastNumDrawnIndex );
      }

      int columnIndexResult = scanColumn(currentTable, currentRandomNums);
      if (columnIndexResult != -1) {
        return new Winner(currentTableIndex, columnIndexResult, RowOrColumn.COLUMN, lastNumDrawnIndex );
      }

    }
    return new Winner(-1, -1, null, -1);
  }

  private WinnerWithNums scanTablesPartTwo(int[][][] tables, int[] currentRandomNums, int lastNumDrawnIndex, Set<Integer> winningTables) {
    // iterate over tables
    for (int currentTableId = 0; currentTableId < tables.length; currentTableId++) {
      if (!winningTables.contains(currentTableId)) {
        int[][] currentTable = tables[currentTableId];

        int rowIndexResult = scanRows(currentTable, currentRandomNums);
        if (rowIndexResult != -1 && !winningTables.contains(currentTableId)) {

          var lastWinner = new Winner(currentTableId, rowIndexResult, RowOrColumn.COLUMN, lastNumDrawnIndex);

          var drawnNums = Arrays.stream(currentRandomNums)
                                .boxed()
                                .collect(Collectors.toCollection(HashSet::new));

          int[][] lastWiningTable = tables[lastWinner.tableId()];

          int sum = calculateSumOfUnmarked(lastWiningTable, drawnNums);

          return new WinnerWithNums(lastWinner, currentRandomNums, sum);
        }

        int columnIndexResult = scanColumn(currentTable, currentRandomNums);
        if (columnIndexResult != -1 && !winningTables.contains(currentTableId)) {
          var lastWinner = new Winner(currentTableId, columnIndexResult, RowOrColumn.COLUMN, lastNumDrawnIndex);

          var drawnNums = Arrays.stream(currentRandomNums)
                                .boxed()
                                .collect(Collectors.toCollection(HashSet::new));

          int[][] lastWiningTable = tables[lastWinner.tableId()];

          int sum = calculateSumOfUnmarked(lastWiningTable, drawnNums);

          return new WinnerWithNums(lastWinner, currentRandomNums, sum);
        }
      }
    }
    return new WinnerWithNums(null, currentRandomNums, -1);
  }

  private int scanRows(int[][] currentTable, int[] randomNums) {
    var randomNumsSet = new HashSet<Integer>();
    for (int randomNum : randomNums) {
      randomNumsSet.add(randomNum);
    }

    for (int row = 0; row < currentTable.length; row++) {
      // collect all numbers from a row to a list
      var arrayRowNums = new ArrayList<Integer>();
      for (int column = 0; column < currentTable[0].length; column++) {
        arrayRowNums.add(currentTable[row][column]);
      }

      if(randomNumsSet.containsAll(arrayRowNums)) {
        return row;
      }
    }

    return -1;
  }

  private int scanColumn(int[][] currentTable, int[] randomNums) {
    var randomNumsSet = new HashSet<Integer>();
    for (int randomNum : randomNums) {
      randomNumsSet.add(randomNum);
    }

    for (int column = 0; column < currentTable.length; column++) {
      // collect all numbers from a column to a list
      var arrayColumnNums = new ArrayList<Integer>();
      for (int[] ints : currentTable) {
        arrayColumnNums.add(ints[column]);
      }

      if(randomNumsSet.containsAll(arrayColumnNums)) {
        return column;
      }
    }

    return -1;
  }

  private int calculateSumOfUnmarked(int[][] winningTable, Set<Integer> drawnNums) {
    // sum of unmarked numbers (not included in the random numbers) in the winning table
    int sum = 0;
    //noinspection ForLoopReplaceableByForEach
    for (int row = 0; row < winningTable.length; row++) {
      for (int column = 0; column < winningTable[0].length; column++) {
        if (!drawnNums.contains(winningTable[row][column])) {
          sum += winningTable[row][column];
        }
      }
    }
    return sum;
  }

}
