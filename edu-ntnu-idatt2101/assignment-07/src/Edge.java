public class Edge {
  int to, capacity;
  Edge reverse;
  int flow;

  public Edge(int to, int capacity) {
    this.to = to;
    this.capacity = capacity;
    this.flow = 0;
  }

  public int remainingCapacity() {
    return capacity - flow;
  }
}
