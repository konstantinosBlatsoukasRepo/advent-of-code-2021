package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day11DumboOctopusTest {
  Day11DumboOctopus day11 = new Day11DumboOctopus();

  @Test
  public void partOneTestDay11DumboOctopus_baseOnInput_shouldReturn26397() {
    // prepare
    String input = """
    5483143223
    2745854711
    5264556173
    6141336146
    6357385478
    4167524645
    2176841721
    6882881134
    4846848554
    5283751526
    """;

    // act
    int result = day11.computeTotalFlashesAfter(100, input);

    // assert
    assertThat(result).isEqualTo(1656);
  }

  @Test
  public void partOneDay11DumboOctopus_baseOnInput_shouldReturn1603() {
    // prepare
    String input = Day11DumboOctopusInput.INPUT;

    // act
    int result = day11.computeTotalFlashesAfter(100, input);

    // assert
    assertThat(result).isEqualTo(1603);
  }

  @Test
  public void partTwoTestDay11DumboOctopus_baseOnInput_shouldReturn195() {
    // prepare
    String input = """
    5483143223
    2745854711
    5264556173
    6141336146
    6357385478
    4167524645
    2176841721
    6882881134
    4846848554
    5283751526
    """;

    // act
    int result = day11.computeStepThatAllFlashing(input);

    // assert
    assertThat(result).isEqualTo(195L);
  }

  @Test
  public void partTwoDay11DumboOctopus_baseOnInput_shouldReturn195() {
    // prepare
    String input = Day11DumboOctopusInput.INPUT;

    // act
    int result = day11.computeStepThatAllFlashing(input);

    // assert
    assertThat(result).isEqualTo(222);
  }

}