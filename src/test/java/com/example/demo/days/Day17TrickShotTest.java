package com.example.demo.days;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class Day17TrickShotTest {

  Day17TrickShot day17 = new Day17TrickShot();

  @Test
  public void partOneTestOneDay17TrickShot_baseOnInput_shouldReturn16() {
    // act
    long result= day17.partOne(20, 30, -10, -5);

    // assert
    assertThat(result).isEqualTo(45);
  }

  @Test
  public void partOneOneDay17TrickShot_baseOnInput_shouldReturn16() {
    // act
    long result= day17.partOne(153, 199, -114, -75);

    // assert
    assertThat(result).isEqualTo(6441);
  }

  @Test
  public void partTwoTestOneDay17TrickShot_baseOnInput_shouldReturn16() {
    // act
    long result= day17.partTwo(20, 30, -10, -5);

    // assert
    assertThat(result).isEqualTo(112);
  }

  @Test
  public void partTwoOneDay17TrickShot_baseOnInput_shouldReturn16() {
    // act
    long result= day17.partTwo(153, 199, -114, -75);

    // assert
    assertThat(result).isEqualTo(1306);
  }

}