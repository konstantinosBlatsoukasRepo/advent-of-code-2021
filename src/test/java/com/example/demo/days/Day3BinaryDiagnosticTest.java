package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day3BinaryDiagnosticTest {

  Day3BinaryDiagnostic day3BinaryDiagnostic = new Day3BinaryDiagnostic();

  @Test
  public void PartOneTestCasePowerConsumption_baseOnInput_shouldReturn198() {
    // prepare
    String input = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010
    """;

    // act
    int result = day3BinaryDiagnostic.calculateBinaryConsumption(input);
    // assert
    assertThat(result).isEqualTo(198);
  }

  @Test
  public void PartOnePowerConsumption_baseOnInput_shouldReturn4006064() {
    // prepare
    String input = Day3BinaryDiagnosticInput.INPUT;

    // act
    int result = day3BinaryDiagnostic.calculateBinaryConsumption(input);
    // assert
    assertThat(result).isEqualTo(4006064);
  }

  @Test
  public void PartTwoTestCaseLifeSupportRating_baseOnInput_shouldReturn230() {
    // prepare
    String input = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010
    """;

    // act
    int result = day3BinaryDiagnostic.calculateLifeSupportRating(input);
    // assert
    assertThat(result).isEqualTo(230);
  }
  @Test
  public void PartTwoLifeSupportRating_baseOnInput_shouldReturn5941884() {
    // prepare
    String input = Day3BinaryDiagnosticInput.INPUT;

    // act
    int result = day3BinaryDiagnostic.calculateLifeSupportRating(input);
    // assert
    assertThat(result).isEqualTo(5941884);
  }
}