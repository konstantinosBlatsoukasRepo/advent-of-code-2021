package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day12PassagePathingTest {

  Day12PassagePathing day12 = new Day12PassagePathing();

  @Test
  public void partOneTestSmallDay12PassagePathing_baseOnInput_shouldReturn10() {
    // prepare
    String input = """
    start-A
    start-b
    A-c
    A-b
    b-d
    A-end
    b-end
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesOnce(input);

    // assert
    assertThat(result).isEqualTo(10);
  }

  @Test
  public void partOneTestMediumDay12PassagePathing_baseOnInput_shouldReturn19() {
    // prepare
    String input = """
    dc-end
    HN-start
    start-kj
    dc-start
    dc-HN
    LN-dc
    HN-end
    kj-sa
    kj-HN
    kj-dc
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesOnce(input);

    // assert
    assertThat(result).isEqualTo(19);
  }

  @Test
  public void partOneTestLargeDay12PassagePathing_baseOnInput_shouldReturn19() {
    // prepare
    String input = """
    fs-end
    he-DX
    fs-he
    start-DX
    pj-DX
    end-zg
    zg-sl
    zg-pj
    pj-he
    RW-he
    fs-DX
    pj-RW
    zg-RW
    start-pj
    he-WI
    zg-he
    pj-fs
    start-RW
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesOnce(input);

    // assert
    assertThat(result).isEqualTo(226);
  }

  @Test
  public void partOneDay12PassagePathing_baseOnInput_shouldReturn4970() {
    // prepare
    String input = Day12PassagePathingInput.INPUT;

    // act
    int result = day12.totalPathsVisitingSmallCavesOnce(input);

    // assert
    assertThat(result).isEqualTo(4970);
  }

  @Test
  public void partTwoTestSmallDay12PassagePathing_baseOnInput_shouldReturn36() {
    // prepare
    String input = """
    start-A
    start-b
    A-c
    A-b
    b-d
    A-end
    b-end
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesTwice(input);

    // assert
    assertThat(result).isEqualTo(36);
  }


  @Test
  public void partTwoTestMediumDay12PassagePathing_baseOnInput_shouldReturn103() {
    // prepare
    String input = """
    dc-end
    HN-start
    start-kj
    dc-start
    dc-HN
    LN-dc
    HN-end
    kj-sa
    kj-HN
    kj-dc
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesTwice(input);

    // assert
    assertThat(result).isEqualTo(103);
  }

  @Test
  public void partTwoTestLargeDay12PassagePathing_baseOnInput_shouldReturn3509() {
    // prepare
    String input = """
    fs-end
    he-DX
    fs-he
    start-DX
    pj-DX
    end-zg
    zg-sl
    zg-pj
    pj-he
    RW-he
    fs-DX
    pj-RW
    zg-RW
    start-pj
    he-WI
    zg-he
    pj-fs
    start-RW
    """;

    // act
    int result = day12.totalPathsVisitingSmallCavesTwice(input);

    // assert
    assertThat(result).isEqualTo(3509);
  }

  @Test
  public void partTwoDay12PassagePathing_baseOnInput_shouldReturn4970() {
    // prepare
    String input = Day12PassagePathingInput.INPUT;

    // act
    int result = day12.totalPathsVisitingSmallCavesTwice(input);

    // assert
    assertThat(result).isEqualTo(137948);
  }
}