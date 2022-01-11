package com.example.demo.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day3BinaryDiagnostic {

  public record GammaAndEpsilonBinaries(String gamma, String epsilon) {}

  public record Counters(int zeroCounter, int oneCounter) {}

  public int calculateBinaryConsumption(String input) {
    List<String> binaries = parseBinaries(input);

    GammaAndEpsilonBinaries gammaAndEpsilonBinaries = calculateGammaEpsilonRates(binaries);
    var gammaDec = Integer.parseInt(gammaAndEpsilonBinaries.gamma(), 2);
    var epsilonDec = Integer.parseInt(gammaAndEpsilonBinaries.epsilon(), 2);

    return gammaDec * epsilonDec;
  }

  public int calculateLifeSupportRating(String input) {
    List<String> binaries = parseBinaries(input);

    String oxygenRating = calculateOxygenGeneratorRating(binaries);
    String co2Rating = calculateCo2Rating(binaries);

    var oxygenDec = Integer.parseInt(oxygenRating, 2);
    var co2Dec = Integer.parseInt(co2Rating, 2);
    return oxygenDec * co2Dec;
  }

  private List<String> parseBinaries(String input) {
    return Stream.of(input.split("\n"))
                 .toList();
  }

  private GammaAndEpsilonBinaries calculateGammaEpsilonRates(List<String> binaries) {
    var gamaBuilder = new StringBuilder();
    var epsilonBuilder = new StringBuilder();
    for (int currentBit = 0; currentBit < binaries.get(0).length(); currentBit++) {
      Counters counters = countZerosOnes(binaries, currentBit);

      if (counters.zeroCounter() > counters.oneCounter()) {
        gamaBuilder.append('0');
        epsilonBuilder.append('1');
      } else {
        gamaBuilder.append('1');
        epsilonBuilder.append('0');
      }
    }

    return new GammaAndEpsilonBinaries(gamaBuilder.toString(), epsilonBuilder.toString());
  }

  // unmodifiable to modifiable
  private String calculateOxygenGeneratorRating(List<String> binaries) {
    var filteredList = new ArrayList<>(binaries);

    for (int currentBit = 0; currentBit < binaries.get(0).length(); currentBit++) {
      Counters counters = countZerosOnes(filteredList, currentBit);

      int finalCurrentBit = currentBit;
      if (counters.oneCounter() < counters.zeroCounter()) {
        filteredList.removeIf(s -> s.charAt(finalCurrentBit) == '1');
      } else {
        filteredList.removeIf(s -> s.charAt(finalCurrentBit) == '0');
      }

      if (filteredList.size() == 1) {
        break;
      }
    }
    return filteredList.get(0);
  }

  // unmodifiable to modifiable
  private String calculateCo2Rating(List<String> binaries) {
    var filteredList = new ArrayList<>(binaries);

    for (int currentBit = 0; currentBit < binaries.get(0).length(); currentBit++) {
      Counters counters = countZerosOnes(filteredList, currentBit);

      int finalCurrentBit = currentBit;
      if (counters.oneCounter() < counters.zeroCounter()) {
        filteredList.removeIf(s -> s.charAt(finalCurrentBit) == '0');
      } else {
        filteredList.removeIf(s -> s.charAt(finalCurrentBit) == '1');
      }

      if (filteredList.size() == 1) {
        break;
      }
    }
    return filteredList.get(0);
  }

  private Counters countZerosOnes(List<String> binaries, int currentBit) {
    int zeroCounter = 0;
    int oneCounter = 0;
    for (String binary : binaries) {
      if (binary.charAt(currentBit) == '0') {
        zeroCounter++;
      } else {
        oneCounter++;
      }
    }
    return new Counters(zeroCounter, oneCounter);
  }

}
