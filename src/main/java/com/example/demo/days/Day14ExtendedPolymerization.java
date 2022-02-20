package com.example.demo.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14ExtendedPolymerization {

  public record Input(List<String> template, Map<String, String> rules) {}

  public long computeDiff(String Input, int totalSteps) {
    Input parsedInput = parse(Input);

    List<String> stepRes = new ArrayList<>();

    for (int step = 0; step < totalSteps; step++) {
      stepRes = step(parsedInput);
      parsedInput = new Input(stepRes, parsedInput.rules());
    }

    Map<String, Long> histogram = stepRes.stream()
                                         .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    Long max = histogram.values()
                        .stream()
                        .max(Comparator.comparing(Function.identity()))
                        .get();

    Long min = histogram.values()
                        .stream()
                        .min(Comparator.comparing(Function.identity()))
                        .get();

    return max - min;
  }

  public long computeDiffPartTwo(String input, int totalSteps) {
    Input parsedInput = parse(input);

    List<String> template = parsedInput.template();
    Map<String, Long> pairHistogram = new HashMap<>();
    for (int i = 0; i < template.size() - 1; i++) {
      pairHistogram.compute(template.get(i) + template.get(i + 1), (k, v) -> v == null ? 1 : v + 1L);
    }

    Map<String, String> rules = parsedInput.rules();
    for (int i = 0; i < totalSteps; i++) {
      Map<String, Long> newPairHistogram = new HashMap<>();

      for (String pair : pairHistogram.keySet()) {
        String newMiddle = rules.get(pair);

        String newPairOne = pair.substring(0, 1) + newMiddle;
        String newPairTwo = newMiddle + pair.substring(1, 2);

        long pairFreq = pairHistogram.get(pair);

        newPairHistogram.compute(newPairOne, (k, v) -> v == null ? pairFreq : newPairHistogram.get(newPairOne) + pairFreq);
        newPairHistogram.compute(newPairTwo, (k, v) -> v == null ? pairFreq : newPairHistogram.get(newPairTwo) + pairFreq);
      }
      pairHistogram = newPairHistogram;
    }

    Map<String, Long> charsHistogram = new HashMap<>();
    for (Entry<String, Long> entry : pairHistogram.entrySet()) {
      String[] characters = entry.getKey().split("");

      charsHistogram.compute(characters[0], (k,v) -> v == null ? entry.getValue() : v + entry.getValue());
      charsHistogram.compute(characters[1], (k,v) -> v == null ? entry.getValue()  : v + entry.getValue());
    }

    charsHistogram.computeIfPresent(template.get(0), (k, v) -> v + 1);
    charsHistogram.computeIfPresent(template.get(template.size() - 1), (k, v) -> v + 1);

    long max = charsHistogram.values()
                           .stream()
                           .mapToLong(k -> k)
                           .max()
                           .getAsLong() / 2L;


    long min = charsHistogram.values()
                             .stream()
                             .mapToLong(k -> k)
                             .min()
                             .getAsLong() / 2L;

    return max - min;
  }


  private Input parse(String Input) {
    List<String> template = new ArrayList<>();
    Map<String, String> rules = new HashMap<>();

    List<String> lines = Arrays.stream(Input.split("\n"))
                               .toList();

    for (String line : lines) {
      if (line.contains( "-> ")) {
        String[] keyValue = line.split(" -> ");
        rules.put(keyValue[0], keyValue[1]);
      } else if (!line.isEmpty()) {
        template = Arrays.stream(line.replace(" ", "").split("")).toList();
      }
    }

    return new Input(template, rules);
  }

  private List<String> step(Input parsedInput) {
    List<String> template = parsedInput.template();
    Map<String, String> rules = parsedInput.rules();
    List<String> newChars = new ArrayList<>();
    for (int i = 0; i < template.size() - 1; i++) {
      newChars.add(rules.get(template.get(i) + template.get(i + 1)));
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < newChars.size(); i++) {
      result.add(template.get(i));
      result.add(newChars.get(i));
    }

    result.add(template.get(template.size() - 1));

    return result;

  }
}
