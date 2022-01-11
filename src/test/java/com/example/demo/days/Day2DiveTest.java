package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day2DiveTest {

  Day2Dive day2Dive = new Day2Dive();

  @Test
  public void PartOneTestCaseHorizontalMultipliedByDepthPosition_baseOnInput_shouldReturn150() {
    // prepare
    String input = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
    """;

    // act
    int result = day2Dive.multiplyHorizontalAndDepthPosition(input);

    // assert
    assertThat(result).isEqualTo(150);
  }

  @Test
  public void PartOneHorizontalMultipliedByDepthPosition_baseOnInput_shouldReturn1507611() {
    // prepare
    String input = Day2DiveInput.INPUT;

    // act
    int result = day2Dive.multiplyHorizontalAndDepthPosition(input);

    // assert
    assertThat(result).isEqualTo(1507611);
  }

  @Test
  public void PartTwoTestCaseHorizontalMultipliedByDepthPositionWithAim_baseOnInput_shouldReturn900() {
    // prepare
    String input = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
    """;

    // act
    int result = day2Dive.multiplyHorizontalAndDepthPositionWithAim(input);

    // assert
    assertThat(result).isEqualTo(900);
  }

  @Test
  public void PartTwoHorizontalMultipliedByDepthPositionWithAim_baseOnInput_shouldReturn1880593125() {
    // prepare
    String input = Day2DiveInput.INPUT;

    // act
    int result = day2Dive.multiplyHorizontalAndDepthPositionWithAim(input);

    // assert
    assertThat(result).isEqualTo(1880593125);
  }

}