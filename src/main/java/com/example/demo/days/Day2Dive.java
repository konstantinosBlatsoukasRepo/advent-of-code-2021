package com.example.demo.days;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class Day2Dive {

  public record Command(String command, int unit) { }

  public record Position(int horizontal, int depth) { }

  public record PositionWithAim(int horizontal, int depth, int aim) { }

  // part 1
  public int multiplyHorizontalAndDepthPosition(String input) {
    List<Command> commands = parseInput(input);
    Position position = calculateFinalPosition(commands);
    return position.depth() * position.horizontal();
  }

  // part 2
  public int multiplyHorizontalAndDepthPositionWithAim(String input) {
    List<Command> commands = parseInput(input);
    PositionWithAim position = calculateFinalPositionWithAim(commands);
    return position.depth() * position.horizontal();
  }

  private List<Command> parseInput(String input) {
    return Stream.of(input.split("\n"))
                 .map(s -> parseCommand(s.split(" ")))
                 .toList();
  }

  private Command parseCommand(String[] commandParts) {
    return new Command(commandParts[0], Integer.parseInt(commandParts[1]));
  }

  // enhanced switch!! save break statements!!
  private Position calculateFinalPosition(List<Command> commands) {
    var currentPosition = new Position(0, 0);
    for (Command command:commands) {
      switch (command.command) {
        case "forward" -> {
          int newHorizontalPos = currentPosition.horizontal() + command.unit();
          currentPosition = new Position(newHorizontalPos, currentPosition.depth());
        }
        case "down" -> {
          int newDepthDown = currentPosition.depth() + command.unit();
          currentPosition = new Position(currentPosition.horizontal(), newDepthDown);
        }
        case "up" -> {
          int newDepthUp = currentPosition.depth() - command.unit();
          currentPosition = new Position(currentPosition.horizontal(), newDepthUp);
        }
      }
    }
    return currentPosition;
  }

  private PositionWithAim calculateFinalPositionWithAim(List<Command> commands) {
    var currentPosition = new PositionWithAim(0, 0, 0);
    for (Command command:commands) {
      switch (command.command) {
        case "forward" -> {
          int newHorizontalPos = currentPosition.horizontal() + command.unit();
          if (currentPosition.aim() != 0) {
            int newDepth = currentPosition.depth() + (currentPosition.aim() * command.unit());
            currentPosition = new PositionWithAim(newHorizontalPos, newDepth, currentPosition.aim());
          }
          else {
            currentPosition = new PositionWithAim(newHorizontalPos, currentPosition.depth(), 0);
          }
        }
        case "down" -> {
          int newDownAim = currentPosition.aim() + command.unit();
          currentPosition = new PositionWithAim(currentPosition.horizontal(), currentPosition.depth(), newDownAim);
        }
        case "up" -> {
          int newUpAim = currentPosition.aim() - command.unit();
          currentPosition = new PositionWithAim(currentPosition.horizontal(), currentPosition.depth(), newUpAim);
        }
      }
    }
    return currentPosition;
  }

}
