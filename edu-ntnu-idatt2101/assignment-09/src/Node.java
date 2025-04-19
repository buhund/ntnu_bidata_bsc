import java.util.ArrayList;

public class Node implements Comparable<Node> {
    public int nodeId;
    public double latitude;
    public double longitude;
    public int type;
    String name;
    Node from;
    int distance;
    int distanceToEnd;
    int fullDistance;
    ArrayList<Edge> edges = new ArrayList<>();

    public Node(int nodeId, double latitude, double longitude) {
        this.nodeId = nodeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.distance, node.distance);
    }
}
