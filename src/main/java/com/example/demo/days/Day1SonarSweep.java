package com.example.demo.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class Day1SonarSweep {

  // part 1
  public int numberOfTimesADepthIncreases(String input) {
    List<Integer> nums = parseInput(input);
    return calculateNumberOfIncreases(nums);
  }

  // part 2
  public int numberOfTimesSlidingWindowIncreases(String input) {
    List<Integer> nums = parseInput(input);

    var sums = new ArrayList<Integer>();
    for (int i = 0; i < nums.size() - 2; i++) {
      sums.add(sumOfThree(i, nums));
    }

    return calculateNumberOfIncreases(sums);
  }

  private int calculateNumberOfIncreases(List<Integer> nums) {
    int count = 0;
    for (int i = 0; i < nums.size() - 1; i++) {
      if (nums.get(i + 1) > nums.get(i)) {
        count++;
      }
    }
    return count;
  }

  private int sumOfThree(int startIndex, List<Integer> parsedInput) {
    return parsedInput.get(startIndex) + parsedInput.get(startIndex + 1) + parsedInput.get(startIndex + 2);
  }

  // stream of array
  // :: method reference usage
  // toList()
  private List<Integer> parseInput(String input) {
    return Stream.of(input.split("\n"))
                 .map(Integer::parseInt)
                 .toList();
  }

}
