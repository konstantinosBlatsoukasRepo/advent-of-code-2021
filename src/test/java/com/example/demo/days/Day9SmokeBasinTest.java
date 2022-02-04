package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day9SmokeBasinTest {

  Day9SmokeBasin day9 = new Day9SmokeBasin();

  @Test
  public void partOneTestDay9SmokeBasin_baseOnInput_shouldReturn15() {
    // prepare
    String input = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
    """;

    // act
    long result = day9.calculateSumOfRiskLevels(input);

    // assert
    assertThat(result).isEqualTo(15L);
  }

  @Test
  public void partOneDay9SmokeBasin_baseOnInput_shouldReturn562() {
    // prepare
    String input = Day9SmokeBasinInput.INPUT;

    // act
    long result = day9.calculateSumOfRiskLevels(input);

    // assert
    assertThat(result).isEqualTo(562L);
  }

  @Test
  public void parTwoTestDay9SmokeBasin_baseOnInput_shouldReturn1134() {
    // prepare
    String input = """
    2199943210
    3987894921
    9856789892
    8767896789
    9899965678
    """;

    // act
    long result = day9.calculateThreeLargestBasinsProduct(input);

    // assert
    assertThat(result).isEqualTo(1134L);
  }

  @Test
  public void parTwoDay9SmokeBasin_baseOnInput_shouldReturn1076922() {
    // prepare
    String input = Day9SmokeBasinInput.INPUT;

    // act
    int result = day9.calculateThreeLargestBasinsProduct(input);

    // assert
    assertThat(result).isEqualTo(1076922);
  }
}
