import java.util.*;

public class BFS {

    public static Edge[] bfs(List<List<Edge>> graph, int source, int sink) {
      int[] parent = new int[graph.size()];
      Edge[] parentEdge = new Edge[graph.size()];
      Arrays.fill(parent, -1);

      Queue<Integer> queue = new LinkedList<>();
      queue.add(source);
      parent[source] = source;

      while (!queue.isEmpty() && parent[sink] == -1) {
        int current = queue.poll();

        List<Edge> neighbours = graph.get(current);
        neighbours.sort(Comparator.comparingInt(edge -> edge.to));

//        for (Edge edge : graph.get(current)) {
        for (Edge edge : neighbours) {
          if (parent[edge.to] == -1 && edge.remainingCapacity() > 0) {
            parent[edge.to] = current;
            parentEdge[edge.to] = edge;

            if (edge.to == sink) {
              return parentEdge;
            }

            queue.add(edge.to);
          }
        }
      }
      return null;
    }
  }