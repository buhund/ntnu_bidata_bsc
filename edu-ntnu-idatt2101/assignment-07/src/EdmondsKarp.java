import java.util.*;
import java.io.*;

public class EdmondsKarp {
  static List<List<Edge>> graph;

  public static void main(String[] args) throws IOException {
    // Read data from file
    BufferedReader br = new BufferedReader(new FileReader("flytgraf5")); // Insert file name to read from
    String[] parts = br.readLine().trim().split("\\s+"); // Trim whitespace
    int N = Integer.parseInt(parts[0].trim());
    int M = Integer.parseInt(parts[1].trim());
    graph = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      graph.add(new ArrayList<>());
    }

    for (int i = 0; i < M; i++) {
      parts = br.readLine().trim().split("\\s+");
      int from = Integer.parseInt(parts[0].trim());
      int to = Integer.parseInt(parts[1].trim());
      int capacity = Integer.parseInt(parts[2].trim());

      Edge edge = new Edge(to, capacity);
      Edge reverseEdge = new Edge(from, 0);

      edge.reverse = reverseEdge;
      reverseEdge.reverse = edge;

      graph.get(from).add(edge);
      graph.get(to).add(reverseEdge);
    }

    br.close();

    int source = 0; // Insert source node here
    int sink = 7; // Insert sink node here
    int maxFlow = edmondsKarp(source, sink);
    System.out.println("Kilde: " + source);
    System.out.println("Sluk: " + sink);
//    System.out.println("Maksimal flyt: " + maxFlow);
  }

  public static int edmondsKarp(int source, int sink) {
    System.out.println("Maksimal flyt fra " + source + " til " + sink + " med Edmond-Karp");
    System.out.println("Økning : Flytøkende vei");
    int maxFlow = 0;

    while (true) {
      Edge[] parentEdge = BFS.bfs(graph, source, sink);
      if (parentEdge == null) {
        break;
      }

      int pathFlow = Integer.MAX_VALUE;
      int current = sink;
      StringBuilder path = new StringBuilder();

      while (current != source) {
        Edge edge = parentEdge[current];
        pathFlow = Math.min(pathFlow, edge.remainingCapacity());
        path.insert(0, current + " ");
        current = edge.reverse.to;
      }
      path.insert(0, source + " ");

      System.out.println(pathFlow + " : " + path.toString().trim());

      maxFlow += pathFlow;
      current = sink;

      while (current != source) {
        Edge edge = parentEdge[current];
        edge.flow += pathFlow;
        edge.reverse.flow -= pathFlow;
        current = edge.reverse.to;
      }
    }

    System.out.println("Maksimal flyt: " + maxFlow);
    return maxFlow;
  }


}
