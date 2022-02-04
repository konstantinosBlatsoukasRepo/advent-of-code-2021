package com.example.demo.days;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

public class Day7TheTreacheryOfWhales {

  public long calculateCheapestOutcomePartOne(String input) {
    BiFunction<Long, List<Long>, Long> fuelCalculator =
    (alignPosition, horizontalPositions) -> calculateFuelFor(alignPosition, horizontalPositions);
    return  calculateCheapestOutcome(input, fuelCalculator);
  }

  public long calculateCheapestOutcomePartTwo(String input) {
    BiFunction<Long, List<Long>, Long> fuelCalculator =
    (alignPosition, horizontalPositions) -> calculatePartTwoFuelFor(alignPosition, horizontalPositions);
    return  calculateCheapestOutcome(input, fuelCalculator);
  }

  private long calculateCheapestOutcome(String input, BiFunction<Long, List<Long>, Long> fuelCalculator) {

    List<Long> horizontalPositions = Arrays.stream(input.split(","))
                                           .map(Long::parseLong)
                                           .toList();

    long[] alignPositions = LongStream.rangeClosed(1, horizontalPositions.size()).toArray();

    long minFuel = Long.MAX_VALUE;
    for (long alignPosition : alignPositions) {
      long currentFuel = fuelCalculator.apply(alignPosition, horizontalPositions);
      if (minFuel > currentFuel) {
        minFuel = currentFuel;
      }
    }

    return minFuel;
  }

  private long calculateFuelFor(long alignPosition, List<Long> horizontalPositions) {
    long fuel = 0;
    for (long horizontalPosition : horizontalPositions) {
      fuel += Math.abs(horizontalPosition - alignPosition);
    }
    return fuel;
  }

  private long calculatePartTwoFuelFor(long alignPosition, List<Long> horizontalPositions) {
    long fuel = 0;
    for (long horizontalPosition : horizontalPositions) {
      long diff = Math.abs(horizontalPosition - alignPosition);
      fuel += ((diff * (diff + 1)) / 2);
    }
    return fuel;
  }

}
