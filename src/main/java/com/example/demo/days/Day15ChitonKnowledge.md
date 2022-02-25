# Day 15 Takeaways

## Algorithm to find the shortest path

One way to find the shortest path, is by applying dijkstra's algorithm.

Dijkstra's algorithm, requires a graph with edge costs and two parameters:

1. the starting node
2. the node that you want to end the path

Also, you will need:

1. a priority queue / heap (you will keep the node ids along with the cost to reach the end node)

   The priority queue has the following important properties:

- the root the node, is the node with the lowest cost (or max)
- the child nodes have always cost lower cost than the parent node

   In case that you add a node with lower cost the data structure moves the new node at the
   correct position in the binary tree 
   (the priority queue/heap is a binary tree, with the above properties)

2. a list with the visited nodes (each node must be visited exactly once)
3. a map with the edge costs (i.e. the cost from node A to B is 10)
4. a map that you keep the best costs to the given target

A pseudocode version of the algorithm:

```
1. Initialize the costs. All the cost are set to infinity, except from the node that you
   start the path (is set to zero)
2. Initialize an empty set for the visited nodes
3. add to the priority queue the starting node with cost zero.
4. While there are elements to the priority queue do:
5.     remove the node with the lowest point
6.     add node to visited nodes
7.     if the already found cost (if any) is lower than the current continue
8.     For all adjecent nodes do:
9.         if the cost to the adjecent node through the current node
10.        is lower then update the costs, otherwise continue
11. get the lowest cost from the dostances map
```

## Java passes parameters by value (always)

example with code:

```java
  public void buzz() {
    String test = "test";
    foo(test);
    System.out.println(test);
  }

  private void foo(String test) {
    test = "testz";
    System.out.println(test);
  }
  
  output: 

          testz
          test
```

String is an immutable object reference. (works like a local variable)

## How to concatenate a list of strings using a Stream

```java
  private String increaseLine(String line) {
    return Arrays.stream(line.split(""))
                 .map(risk -> String.valueOf(1 + ((Integer.parseInt(risk + 1) - 1) % 9)))
                 .collect(Collectors.joining(""));
  }
```

## How to specify a custom comparator for a priority queue

```java
    private record PointWithCost(String rowColumn, int cost) {}

    // priority queue that contains the points along the costs
    Comparator<PointWithCost> comparingCost = Comparator.comparing(pointWithCost -> pointWithCost.cost());
    PriorityQueue<PointWithCost> pointsWithCost = new PriorityQueue<>(totalNodes, comparingCost);
```

## WIth java 17 you can replace the Arrays.asList().stream with the Stream.of

### with Arrays.asList(...).stream()

```java
    return  Arrays.asList(new Point(row + 1, column),
                          new Point(row - 1, column),
                          new Point(row, column + 1),
                          new Point(row, column - 1))
                  .stream()
                  .filter(pointz -> isPointValid(pointz, riskLevels))
                  .map(point -> getId(point.row(), point.column()))
                  .toList();
```

### with Stream.of(...)

```java
    return  Stream.of(new Point(row + 1, column),
                          new Point(row - 1, column),
                          new Point(row, column + 1),
                          new Point(row, column - 1))
                  .filter(pointz -> isPointValid(pointz, riskLevels))
                  .map(point -> getId(point.row(), point.column()))
                  .toList();
```

## Use method reference when you can

### without method reference

```java
Arrays.stream(line.split(""))
       .map(val -> Integer.parseInt(val))
       .toList();
```

### with method reference

```java
Arrays.stream(line.split(""))
       .map(Integer::parseInt)
       .toList();
```