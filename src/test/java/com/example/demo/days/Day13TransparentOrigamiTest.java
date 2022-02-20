package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day13TransparentOrigamiTest {

  Day13TransparentOrigami day13 = new Day13TransparentOrigami();

  @Test
  public void partOneTestDay13TransparentOrigami_baseOnInput_shouldReturn17() {
    // prepare
    String input = """
    6,10
    0,14
    9,10
    0,3
    10,4
    4,11
    6,0
    6,12
    4,1
    0,13
    10,12
    3,4
    3,0
    8,4
    1,10
    2,14
    8,10
    9,0
    
    fold along y=7
    fold along x=5
    """;

    // act
    int result = day13.computeVisibleDots(input);

    // assert
    assertThat(result).isEqualTo(17);
  }

  @Test
  public void partOneDay13TransparentOrigami_baseOnInput_shouldReturn621() {
    // prepare
    String input = Day13TransparentOrigamiInput.INPUT;

    // act
    int result = day13.computeVisibleDots(input);

    // assert
    assertThat(result).isEqualTo(621);
  }

  @Test
  public void partTwoDay13TransparentOrigami_baseOnInput_shouldReturn621() {
    // prepare
    String input = Day13TransparentOrigamiInput.INPUT;

    // act
    day13.displayCoe(input);
  }

}