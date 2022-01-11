package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day5HydrothermalVentureTest {

  Day5HydrothermalVenture day5 = new Day5HydrothermalVenture();

  @Test
  public void PartOneTestCaseTotalOverlapPoints_baseOnInput_shouldReturn5() {
    // prepare
    String input = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
""";

    // act
    int result = day5.totalNumOFPointsAtlLeastTwoLinesOverlap(input);

    // assert
    assertThat(result).isEqualTo(5);
  }

  @Test
  public void PartOneTotalOverlapPoints_baseOnInput_shouldReturn4745() {
    // prepare
    String input = Day5HydrothermalVentureTestInput.INPUT;

    // act
    int result = day5.totalNumOFPointsAtlLeastTwoLinesOverlap(input);

    // assert
    assertThat(result).isEqualTo(4745);
  }

  @Test
  public void PartTwoTestCaseTotalOverlapPoints_baseOnInput_shouldReturn12() {
    // prepare
    String input = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
""";

    // act
    int result = day5.totalNumOFPointsAtlLeastTwoLinesOverlapPartTwo(input);

    // assert
    assertThat(result).isEqualTo(12);
  }

  @Test
  public void PartTwoTotalOverlapPoints_baseOnInput_shouldReturn18442() {
    // prepare
    String input = Day5HydrothermalVentureTestInput.INPUT;

    // act
    int result = day5.totalNumOFPointsAtlLeastTwoLinesOverlapPartTwo(input);

    // assert
    assertThat(result).isEqualTo(18442);
  }
}