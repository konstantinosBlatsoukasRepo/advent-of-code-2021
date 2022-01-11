package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day1SonarSweepTest
{

  Day1SonarSweep day1SonarSweep = new Day1SonarSweep();

  @Test
  public void PartOneTestCaseNumberOfTimesADepthIncreases_baseOnInput_shouldReturnSevenTimes() {
    // prepare
    String input = """
    199
    200
    208
    210
    200
    207
    240
    269
    260
    263
    """;

    // act
    int result = day1SonarSweep.numberOfTimesADepthIncreases(input);

    // assert
    assertThat(result).isEqualTo(7);
  }

  @Test
  public void PartOneNumberOfTimesADepthIncreases_baseOnInput_shouldReturn1553() {
    // prepare
    String input = Day1SonarSweepInput.INPUT;

    // act
    int result = day1SonarSweep.numberOfTimesADepthIncreases(input);

    // assert
    assertThat(result).isEqualTo(1553);
  }

  @Test
  public void PartTwoTestCaseNumberOfTimesSlidingWindowIncreases_baseOnInput_shouldReturn5() {
    // prepare
    String input = """
    199
    200
    208
    210
    200
    207
    240
    269
    260
    263
    """;

    // act
    int result = day1SonarSweep.numberOfTimesSlidingWindowIncreases(input);

    // assert
    assertThat(result).isEqualTo(5);
  }

  @Test
  public void PartTwoNumberOfTimesSlidingWindowIncreases_baseOnInput_shouldReturn1597() {
    // prepare
    String input = Day1SonarSweepInput.INPUT;

    // act
    int result = day1SonarSweep.numberOfTimesSlidingWindowIncreases(input);

    // assert
    assertThat(result).isEqualTo(1597);
  }

}