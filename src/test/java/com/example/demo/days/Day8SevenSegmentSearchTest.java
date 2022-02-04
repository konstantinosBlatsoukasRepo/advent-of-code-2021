package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day8SevenSegmentSearchTest
{
  Day8SevenSegmentSearch day8 = new Day8SevenSegmentSearch();

  @Test
  public void partOneTestDay8SevenSegmentSearch_baseOnInput_shouldReturn26() {
    // prepare
    String input = """
    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
    edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
    fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
    fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
    aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
    fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
    dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
    bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
    egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
    gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """;

    // act
    long result = day8.getTotalUniqueNumbers(input);

    // assert
    assertThat(result).isEqualTo(26L);
  }

  @Test
  public void partOneDay8SevenSegmentSearch_baseOnInput_shouldReturn421() {
    // prepare
    String input = Day8SevenSegmentSearchInput.INPUT;

    // act
    long result = day8.getTotalUniqueNumbers(input);

    // assert
    assertThat(result).isEqualTo(421L);
  }

  @Test
  public void partTwoTestDay8SevenSegmentSearch_baseOnInput_shouldReturn61229() {
    // prepare
    String input = """
    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
    edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
    fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
    fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
    aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
    fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
    dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
    bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
    egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
    gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """;

    // act
    long result = day8.calculateOutputSum(input);

    // assert
    assertThat(result).isEqualTo(61229L);
  }

  @Test
  public void partTwoDay8SevenSegmentSearch_baseOnInput_shouldReturn986163() {
    // prepare
    String input = Day8SevenSegmentSearchInput.INPUT;

    // act
    long result = day8.calculateOutputSum(input);

    // assert
    assertThat(result).isEqualTo(986163L);
  }

}