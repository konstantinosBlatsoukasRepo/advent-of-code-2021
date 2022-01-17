package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6Lanternfish {

  public record SimulationResult(List<Long> internalTimers, int totalFishProduced) { }

  public long countLanternfishesAfter(int days, String input) {

    List<Long> initialInternalTimers = Stream.of(input.split(","))
                                                .map(Long::parseLong)
                                                .toList();

    List<SimulationResult> simulationsPerDay = new ArrayList<>();

    SimulationResult currentSimulation = new SimulationResult(new ArrayList<>(initialInternalTimers), 0);
    for (int day = 0; day < days; day++) {
      System.out.println(currentSimulation.internalTimers());
      currentSimulation = simulate(currentSimulation);
      simulationsPerDay.add(currentSimulation);
    }

    return  simulationsPerDay.stream()
                             .mapToLong(simulation -> simulation.totalFishProduced)
                             .sum() + initialInternalTimers.size();


  }

  public long partTwoCountLanternfishesAfter(int days, String input) {
    List<Long> inputList = Arrays.stream(input.split(","))
                                 .map(Long::parseLong)
                                 .collect(Collectors.toList());

    List<Long> timers = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      timers.add(0l);
    }

    for (long inputItem : inputList) {
      timers.set((int) inputItem, timers.get((int) inputItem) + 1);
    }

    for (int currentDay = 0; currentDay < days; currentDay++) {
      List<Long> newTimers = timers.subList(1, timers.size());
      newTimers.add(0l);

      newTimers.set((int) 8, newTimers.get((int) 8) + timers.get((int) 0));
      newTimers.set((int) 6, newTimers.get((int) 6) + timers.get((int) 0));

      timers = newTimers;
      System.out.println();
    }

    return timers.stream().mapToLong( x-> x).sum();
  }

  private long applyFishRules(long num) {
    if (num == 0) {
      return 6;
    }
    return --num;
  }

  private SimulationResult simulate(SimulationResult currentSimulation) {
    List<Long> internalTimerz = new ArrayList<>(currentSimulation.internalTimers());
    int totalNewInternalTimers = 0;
    for (int i = 0; i < internalTimerz.size(); i++) {
      if (internalTimerz.get(i) == 0) {
        internalTimerz.set(i, 6l);
        totalNewInternalTimers++;
      } else {
        long newValue = internalTimerz.get(i) - 1;
        internalTimerz.set(i, newValue);
      }
    }

    List<Long> currentInternalTimers = new ArrayList<>(internalTimerz);
    for (int i = 0; i < totalNewInternalTimers; i++) {
      currentInternalTimers.add(8l);
    }

    return new SimulationResult(currentInternalTimers, totalNewInternalTimers);
  }

}
