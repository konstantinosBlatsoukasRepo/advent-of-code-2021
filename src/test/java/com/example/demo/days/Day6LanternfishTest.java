package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day6LanternfishTest {

  Day6Lanternfish day6L = new Day6Lanternfish();

  @Test
  public void partOneTestCaseTotalFishes_baseOnInput_shouldReturn5934() {
    // prepare
    String input = "3,4,3,1,2";

    // act
    long result = day6L.countLanternfishesAfter(30, input);


    // assert
    assertThat(result).isEqualTo(5934l);
  }

  @Test
  public void partOneTotalFishes_baseOnInput_shouldReturn360268() {
    // prepare
    String input = Day6LanternfishTestInput.INPUT;

    // act
    long result = day6L.countLanternfishesAfter(80, input);

    // assert
    assertThat(result).isEqualTo(360268l);
  }

  @Test
  public void partTwoTestCaseTotalFishes_baseOnInput_shouldReturn360268() {
    // prepare
    String input = "3,4,3,1,2";

    // act
    long result = day6L.partTwoCountLanternfishesAfter(256, input);

    // assert
    assertThat(result).isEqualTo(26984457539l);
  }

  @Test
  public void partTwoTotalFishes_baseOnInput_shouldReturn360268() {
    // prepare
    String input = Day6LanternfishTestInput.INPUT;

    // act
    long result = day6L.partTwoCountLanternfishesAfter(256, input);

    // assert
    assertThat(result).isEqualTo(5934l);
  }

}