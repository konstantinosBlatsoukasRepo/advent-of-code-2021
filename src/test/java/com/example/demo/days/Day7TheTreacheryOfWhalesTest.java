package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day7TheTreacheryOfWhalesTest {

  Day7TheTreacheryOfWhales day7 = new Day7TheTreacheryOfWhales();

  @Test
  public void partOneTestCaseCheapestOutcome_baseOnInput_shouldReturn37() {
    // prepare
    String input = "16,1,2,0,4,2,7,1,2,14";

    // act
    long result = day7.calculateCheapestOutcomePartOne(input);


    // assert
    assertThat(result).isEqualTo(37l);
  }

  @Test
  public void partOneCheapestOutcome_baseOnInput_shouldReturn340052() {
    // prepare
    String input = Day7TheTreacheryOfWhalesTestInput.INPUT;

    // act
    long result = day7.calculateCheapestOutcomePartOne(input);

    // assert
    assertThat(result).isEqualTo(340052L);
  }

  @Test
  public void partTwoTestCaseCheapestOutcome_baseOnInput_shouldReturn37() {
    // prepare
    String input = "16,1,2,0,4,2,7,1,2,14";

    // act
    long result = day7.calculateCheapestOutcomePartTwo(input);


    // assert
    assertThat(result).isEqualTo(168l);
  }

  @Test
  public void partTwoCheapestOutcome_baseOnInput_shouldReturn92948968() {
    // prepare
    String input = Day7TheTreacheryOfWhalesTestInput.INPUT;

    // act
    long result = day7.calculateCheapestOutcomePartTwo(input);

    // assert
    assertThat(result).isEqualTo(92948968l);
  }

}