package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day10SyntaxScoringTest {
  Day10SyntaxScoring day10 = new Day10SyntaxScoring();

  @Test
  public void partOneTestDay10SyntaxScoring_baseOnInput_shouldReturn26397() {
    // prepare
    String input = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    {([(<{}[<>[]}>{[]{[(<()>
    (((({<>}<{<{<>}{[]{[]{}
    [[<[([]))<([[{}[[()]]]
    [{[{({}]{}}([{[{{{}}([]
    {<[[]]>}<{[{[{[]{()[[[]
    [<(<(<(<{}))><([]([]()
    <{([([[(<>()){}]>(<<{{
    <{([{{}}[<[[[<>{}]]]>[]]
    """;

    // act
    long result = day10.computeTotalSyntaxErrorScore(input);

    // assert
    assertThat(result).isEqualTo(26397L);
  }

  @Test
  public void partOneDay10SyntaxScoring_baseOnInput_shouldReturn299793() {
    // prepare
    String input = Day10SyntaxScoringInput.INPUT;

    // act
    long result = day10.computeTotalSyntaxErrorScore(input);

    // assert
    assertThat(result).isEqualTo(299793L);
  }

  @Test
  public void partTwoTestDay10SyntaxScoring_baseOnInput_shouldReturn288957() {
    // prepare
    String input = """
    [({(<(())[]>[[{[]{<()<>>
    [(()[<>])]({[<{<<[]>>(
    {([(<{}[<>[]}>{[]{[(<()>
    (((({<>}<{<{<>}{[]{[]{}
    [[<[([]))<([[{}[[()]]]
    [{[{({}]{}}([{[{{{}}([]
    {<[[]]>}<{[{[{[]{()[[[]
    [<(<(<(<{}))><([]([]()
    <{([([[(<>()){}]>(<<{{
    <{([{{}}[<[[[<>{}]]]>[]]
    """;

    // act
    long result = day10.computeMiddleScore(input);

    // assert
    assertThat(result).isEqualTo(288957L);
  }

  @Test
  public void partTwoDay10SyntaxScoring_baseOnInput_shouldReturn3654963618() {
    // prepare
    String input = Day10SyntaxScoringInput.INPUT;

    // act
    long result = day10.computeMiddleScore(input);

    // assert
    assertThat(result).isEqualTo(3654963618L);
  }


}