package com.example.demo.days;

public class Day17TrickShot {

  private record XRange(int minX, int maxX) {}

  private record YRange(int minY, int maxY) {}

  private record Target(XRange xRange, YRange yRange) {}

  private record DoesHit(boolean doesHit, int maxY) {}

  private record Position(int x, int y) {}

  private record Velocity(int x, int y) {}

  private record SimulationStep(Position position, Velocity velocity) {}

  public long partOne(int minX, int maxX, int minY, int maxY) {
    XRange xRange = new XRange(minX, maxX);
    YRange yRange = new YRange(minY, maxY);

    Target target = new Target(xRange, yRange);

    int maxYVelocity = Math.abs(yRange.minY());
    while (maxYVelocity >= target.yRange().minY()) {
      boolean done = false;
      for (int xVelocity = -100; xVelocity <= 101; xVelocity++ ) {

        DoesHit doesHit = doesHit(new Velocity(xVelocity, maxYVelocity), target);
        if (doesHit.doesHit()) {
          return doesHit.maxY();
        }
      }

      if (done) {
        break;
      }

      maxYVelocity -= 1;
    }

    return -1;
  }

  public long partTwo(int minX, int maxX, int minY, int maxY) {
    int count = 0;

    XRange xRange = new XRange(minX, maxX);
    YRange yRange = new YRange(minY, maxY);

    Target target = new Target(xRange, yRange);

    int maxYVelocity = Math.abs(yRange.minY());
    while (maxYVelocity >= target.yRange().minY()) {
      for (int xVelocity = -100; xVelocity <= 101; xVelocity++ ) {

        DoesHit doesHit = doesHit(new Velocity(xVelocity, maxYVelocity), target);
        if (doesHit.doesHit()) {
          count++;
        }
      }

      maxYVelocity -= 1;
    }

    return count;
  }

  private DoesHit doesHit(Velocity velocity, Target target) {
    var position = new Position(0,0);
    int maxYFound = 0;

    while (!isPast(position, velocity, target)) {
      maxYFound = Math.max(maxYFound, position.y());
      if (isWithin(position, target)) {
        return new DoesHit(true, maxYFound);
      }
      SimulationStep iterate = iterate(position, velocity);
      position = iterate.position();
      velocity = iterate.velocity();
    }

    return new DoesHit(false, 0);
  }
  private SimulationStep iterate(Position position, Velocity velocity) {
    Position newPosition = new Position(position.x() + velocity.x(), position.y() + velocity.y());
    int newVelY = velocity.y() - 1;

    int newVelX = 0;
    if (velocity.x() > 0) {
      newVelX = velocity.x() - 1;
    }
    if (velocity.x() < 0) {
      newVelX = velocity.x() + 1;
    }

    return new SimulationStep(newPosition, new Velocity(newVelX, newVelY));
  }

  private boolean isWithin(Position position, Target target) {
    boolean xWithInRange = target.xRange().minX() <= position.x() && target.xRange().maxX() >= position.x();
    boolean yWithInRange = target.yRange().minY() <= position.y() && target.yRange().maxY() >= position.y();
    return xWithInRange && yWithInRange;
  }

  private boolean isPast(Position position, Velocity velocity, Target target) {
    if (velocity.x() > 0 && position.x() > target.xRange().maxX()) { return true; }
    if (velocity.x() < 0 && position.x() < target.xRange().minX()) { return true; }
    return velocity.y() < 0 && position.y() < target.yRange().minY();
  }

}
