import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DijkstraAlgorithm {
    private final Node[] nodes;
    private Set<Node> visited;
    private PriorityQueue<Node> queue;

    public DijkstraAlgorithm(Node[] nodes) {
        this.nodes = nodes;
    }

    public void executeDijkstra(int startNode, int endNode) {
        resetNodesAndEdges();
        visited = new HashSet<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        long startTime = System.nanoTime();
        nodes[startNode].distance = 0;
        queue.add(nodes[startNode]);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visited.add(node);

            if (node.nodeId == endNode) {
                break;
            }

            adjacent(node);
        }

        if (endNode != -1) {
            getShortestPathDijkstra(startNode, endNode);
        }
        long totalTime = (System.nanoTime() - startTime) / 1_000_000;
        MapRouting.runStats("Dijkstra", endNode, totalTime, nodes, visited);
    }

    /**
     * Finds the closest interest points to a given node.
     *
     * @param startNode         The node to start from.
     * @param interestPointType The type of interest points to find.
     * @param count             The number of interest points to find.
     */
    public void findClosestInterestPoints(int startNode, int interestPointType, int count) {
        resetNodesAndEdges();
        visited = new HashSet<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        nodes[startNode].distance = 0;
        queue.add(nodes[startNode]);

        int foundCount = 0;
        while (!queue.isEmpty() && foundCount < count) {
            Node node = queue.poll();
            visited.add(node);

            if ((node.type & interestPointType) == interestPointType) {
                System.out.println("Interest Point Found: Node ID " + node.nodeId + ", Name: "
                        + node.name + ", Distance: " + node.distance + " metres");
                foundCount++;
            }

            if (foundCount < count) {
                adjacent(node);
            }
        }
    }

    /**
     * Resets the nodes and edges in the map.
     */
    private void resetNodesAndEdges() {
        for (Node node : nodes) {
            node.distance = Integer.MAX_VALUE;
            node.from = null;
        }
    }

    /**
     * Finds the adjacent nodes to a given node.
     *
     * @param node The node to find the adjacent nodes to.
     */
    private void adjacent(Node node) {
        for (Edge edge : node.edges) {
            if (!visited.contains(edge.toNode())) {
                int newDistance = node.distance + edge.travelTime();
                if (newDistance < edge.toNode().distance) {
                    queue.remove(edge.toNode());
                    edge.toNode().distance = newDistance;
                    edge.toNode().from = node;
                    queue.add(edge.toNode());
                }
            }
        }
    }

    /**
     * Writer method for writing Latitude and Longitude to file.
     *
     * @param path The path to write to file.
     */
    private void writePathToFile(List<Node> path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dijk-trip.txt"))) {
            for (Node node : path) {
                writer.write(node.latitude + "," + node.longitude + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the shortest path between two nodes.
     *
     * @param startNode The node to start from.
     * @param endNode   The node to end at.
     */
    public void getShortestPathDijkstra(int startNode, int endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = nodes[endNode];
        int totalNodesInPath = 0;

        while (currentNode != null && currentNode.nodeId != startNode) {
            path.add(currentNode);
            currentNode = currentNode.from;
            totalNodesInPath++;
        }
        if (currentNode != null) {
            path.add(currentNode);
        }

        Collections.reverse(path);

        for (Node node : path) {
//            System.out.println(node.latitude + "," + node.longitude); // Print "Lat, Lon" to console
            writePathToFile(path); // Print "Lat, Lon" to file
        }

        System.out.println("\nPath written to file: dijk-trip.txt"); // Comment out if printing to console.
        System.out.println("\nTotal nodes in the path: " + totalNodesInPath);
    }
}
