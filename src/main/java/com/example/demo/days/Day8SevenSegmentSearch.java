package com.example.demo.days;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day8SevenSegmentSearch
{
  public record Entry(List<String> signalPatterns, List<String> output) {}

  // part one
  public long getTotalUniqueNumbers(String input) {
     return parseInput(input).stream()
                             .map(Entry::output)
                             .flatMap(Collection::stream)
                             .collect(groupingBy(String::length, counting()))
                             .entrySet()
                             .stream()
                             .mapMultiToLong((k, consumer) -> {
                                 if (k.getKey() == 2 || k.getKey() == 3 || k.getKey() == 4 || k.getKey() == 7)
                                   consumer.accept(k.getValue());})
                             .sum();
  }

  public long calculateOutputSum(String input) {
    return parseInput(input).stream()
                            .mapToLong(this::decodeOutput)
                            .sum();
  }

  private long decodeOutput(Entry entries) {
    Map<Integer, List<String>> numsByLength = entries.signalPatterns()
                                                     .stream()
                                                     .collect(groupingBy(String::length));

    String[] nums = new String[10];

    nums[1] = numsByLength.get(2).get(0); // this is the 1 , length 2
    nums[4] = numsByLength.get(4).get(0); // this is the 1 , length 4
    nums[7] = numsByLength.get(3).get(0); // this is the 7 , length 3
    nums[8] = numsByLength.get(7).get(0); // this is the 8 , length 7

    // numbers with 6 leds
    deduceZeroSixAndNine(nums, numsByLength);

    // numbers with 5 leds
    deduceThreeTwoAndFive(nums, numsByLength);

    Map<String, String> decodedDigits = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      char[] chars = nums[i].toCharArray();
      Arrays.sort(chars);
      decodedDigits.put(String.valueOf(chars), String.valueOf(i));
    }

    StringBuilder sb = new StringBuilder();
    for (String out : entries.output()) {
      char[] chars = out.toCharArray();
      Arrays.sort(chars);
      sb.append(decodedDigits.get(String.valueOf(chars)));
    }

    return Long.parseLong(sb.toString());
  }

  private void deduceZeroSixAndNine(String[] nums, Map<Integer, List<String>> numsByLength) {
    List<String> numsWithLengthSix = numsByLength.get(6);
    for (int i = 0; i < numsWithLengthSix.size(); i++) {
      if (areSevenSegNumsMatching(numsWithLengthSix.get(i), nums[4])) {
        nums[9] = numsWithLengthSix.remove(i);
        break;
      }
    }

    // decode zero and six
    nums[6] = numsWithLengthSix.get(0);
    nums[0] = numsWithLengthSix.get(1);
    if (areSevenSegNumsMatching(numsWithLengthSix.get(0), nums[7])) {
      nums[0] = numsWithLengthSix.get(0);
      nums[6] = numsWithLengthSix.get(1);
    }
  }

  private void deduceThreeTwoAndFive(String[] nums, Map<Integer, List<String>> numsByLength) {
    List<String> numsWithLengthFive = numsByLength.get(5);
    for (int i = 0; i < numsWithLengthFive.size(); i++) {
      if (areSevenSegNumsMatching(numsWithLengthFive.get(i), nums[1])) {
        nums[3] = numsWithLengthFive.remove(i);
        break;
      }
    }

    // deduce two and five
    long totalNotIncludedAtFour = 0;
    for (int i = 0; i < nums[4].length(); i++) {
        if (!numsWithLengthFive.get(0).contains(Character.toString(nums[4].charAt(i)))) {
          totalNotIncludedAtFour++;
        }
    }

    nums[2] = numsWithLengthFive.get(1);
    nums[5] = numsWithLengthFive.get(0);
    if (totalNotIncludedAtFour == 2) {
      nums[2] = numsWithLengthFive.get(0);
      nums[5] = numsWithLengthFive.get(1);
    }
  }

  private boolean areSevenSegNumsMatching(String candidateNum, String num) {
    return IntStream.range(0, num.length())
                    .allMatch(i -> candidateNum.contains(Character.toString(num.charAt(i))));
  }

  private List<Entry> parseInput(String input) {
    return Arrays.stream(input.split("\n"))
                 .map(this::parseEntry)
                 .toList();
  }

  private Entry parseEntry(String line) {
    String[] separatedSignalOut = line.split("\\|\\s");
    List<String> signalPatterns = Arrays.stream(separatedSignalOut[0].split(" ")).toList();
    List<String> output = Arrays.stream(separatedSignalOut[1].split(" ")).toList();
    return new Entry(signalPatterns, output);
  }

}
