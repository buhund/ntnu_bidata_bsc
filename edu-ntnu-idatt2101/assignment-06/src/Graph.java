import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Queue;
import java.util.LinkedList;

public class Graph {
  private final List<List<Integer>> adjacentList;
  private final int[] inDegree;

  public Graph(int numNodes, List<int[]> edges) {
    adjacentList = new ArrayList<>();
    inDegree = new int[numNodes];
    for (int i = 0; i < numNodes; i++) {
      adjacentList.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
      adjacentList.get(edge[0]).add(edge[1]);
      inDegree[edge[1]]++;
    }
  }

  /**
   * Breadth-first search algorithm.
   * @param start
   */
  public void bfs(int start) {
    boolean[] visited = new boolean[adjacentList.size()];
    int[] distance = new int[adjacentList.size()];
    int[] predecessor = new int[adjacentList.size()];
    Arrays.fill(distance, -1);
    Arrays.fill(predecessor, -1);

    Queue<Integer> queue = new LinkedList<>();
    visited[start] = true;
    distance[start] = 0;
    queue.add(start);

    while (!queue.isEmpty()) {
      int node = queue.poll();
      List<Integer> neighbors = adjacentList.get(node);
      for (int neighbor : neighbors) {
        if (!visited[neighbor]) {
          visited[neighbor] = true;
          distance[neighbor] = distance[node] + 1;
          predecessor[neighbor] = node;
          queue.add(neighbor);
        }
      }
    }

    // Print the results.
    // Two styles: Markdown table and "open matrix". Comment/Uncomment as needed or wanted.
//    System.out.printf("%-10s %-10s %-10s%n", "Node", "Predecessor", "Dist"); // Open matrix formatted print, part 1
    System.out.printf("%-10s %-10s %-10s%n", "| Node", "| Predecessor", "| Dist |"); // Markdown formatted table print, part 1
    System.out.println("|-----------|-----------|-----------|"); // Markdown formatted table print, part 2
    for (int i = 0; i < adjacentList.size(); i++) {
      String predecessorString = distance[i] == -1 || i == start ? "" : Integer.toString(predecessor[i]);
      String distanceString = distance[i] == -1 ? "" : Integer.toString(distance[i]);
//      System.out.printf("%-10d %-10s %-10s%n", i, predecessorString, distanceString); // Open matrix formatted print, part 2
      System.out.printf("| %-9d | %-9s | %-9s |%n", i, predecessorString, distanceString); // Markdown formatted table print, part 3
    }
  }

  public static void main(String[] args) {
    String fileName = "ø6g7"; // Change this to select a file to read from
    try (Scanner scanner = new Scanner(new File(fileName))) {
      int numNodes = scanner.nextInt();
      int numEdges = scanner.nextInt();
      System.out.println("Number of nodes: " + numNodes);
      System.out.println("Number of edges: " + numEdges);
      List<int[]> edges = new ArrayList<>();
      while (scanner.hasNext()) {
        int from = scanner.nextInt();
        int to = scanner.nextInt();
        edges.add(new int[]{from, to});
      }

      Graph graph = new Graph(numNodes, edges);

      // Menu for choosing what to do with the graph
      Scanner inputScanner = new Scanner(System.in);
      System.out.println("Choose an operation to perform on graph " + fileName);
      System.out.println("1. Perform BFS");
      System.out.println("2. Perform Topological Sort");
      int choice = inputScanner.nextInt();

      switch (choice) {
        case 1:
          int startNode = -1;
          while (startNode < 0 || startNode >= numNodes) {
            System.out.println("Reading from file " + fileName);
            System.out.printf("Enter the starting node, from 0 to %d: ", numNodes - 1);
            try {
              startNode = inputScanner.nextInt();
              if (startNode < 0 || startNode >= numNodes) {
                System.out.println("Invalid node number. Please try again.");
              }
            } catch (InputMismatchException e) {
              System.out.println("Invalid input. Please enter a number.");
              inputScanner.next();  // Clear the invalid input
            }
          }
          graph.bfs(startNode);
          break;
        case 2:
          graph.topologicalSort();
          break;
        default:
          System.out.println("Invalid option. Exiting.");
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Topological sorting of directed acyclic graph.
   * For assignment 06, it must use file ø6g5 or ø6g7.
   *
   * A topological ordering is possible if and only if the graph has no directed cycles,
   * that is, if it is a directed acyclic graph (DAG). (Wikipedia)
   */
  public void topologicalSort() {
    Queue<Integer> queue = new LinkedList<>();
    int[] inDegreeCopy = Arrays.copyOf(inDegree, inDegree.length);

    for (int i = 0; i < inDegreeCopy.length; i++) {
      if (inDegreeCopy[i] == 0) {
        queue.add(i);
      }
    }

    List<Integer> topologicalOrder = new ArrayList<>();
    while (!queue.isEmpty()) {
      int node = queue.poll();
      topologicalOrder.add(node);

      List<Integer> neighbors = adjacentList.get(node);
      for (int neighbor : neighbors) {
        inDegreeCopy[neighbor]--;
        if (inDegreeCopy[neighbor] == 0) {
          queue.add(neighbor);
        }
      }
    }

    if (topologicalOrder.size() != adjacentList.size()) {
      System.out.println("The graph contains a cycle. Topological sorting is not possible.");
      return;
    }

    System.out.println("Topological Order: " + topologicalOrder);
  }



}
