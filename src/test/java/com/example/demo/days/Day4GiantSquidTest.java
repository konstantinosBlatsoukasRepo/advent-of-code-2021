package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day4GiantSquidTest {

  Day4GiantSquid day4 = new Day4GiantSquid();

  @Test
  public void PartOneTestCasePowerFinalScore_baseOnInput_shouldReturn4512() {
    // prepare
    String input = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
    """;

    // act
    int result = day4.calculateWinningBoardFinalScore(input);

    // assert
    assertThat(result).isEqualTo(4512);
  }

  @Test
  public void PartOnePowerFinalScore_baseOnInput_shouldReturn28082() {
    // prepare
    String input = Day4GiantSquidTestInput.INPUT;

    // act
    int result = day4.calculateWinningBoardFinalScore(input);

    // assert
    assertThat(result).isEqualTo(28082);
  }

  @Test
  public void PartTwoTestCasePowerLastFinalScore_baseOnInput_shouldReturn4512() {
    // prepare
    String input = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
    """;

    // act
    int result = day4.calculateLastWinningTableFinalScore(input);

    // assert
    assertThat(result).isEqualTo(1924);
  }

  @Disabled
  @Test
  public void PartTwoPowerLastFinalScore_baseOnInput_shouldReturn8224() {
    // prepare
    String input = Day4GiantSquidTestInput.INPUT;

    // act
    int result = day4.calculateLastWinningTableFinalScore(input);

    // assert
    assertThat(result).isEqualTo(8224);
  }

}