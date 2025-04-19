import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * ALT algorithm for finding the shortest path between two nodes.
 */
public class AltAlgorithm {
    private final Node[] nodes;
    private final ArrayList<ArrayList<Integer>> landmarkDistancesAll;

    /**
     * Constructor for the AltAlgorithm class.
     *
     * @param nodes                The nodes in the map.
     * @param landmarkDistancesAll The distances from each node to each landmark.
     */
    public AltAlgorithm(Node[] nodes, ArrayList<ArrayList<Integer>> landmarkDistancesAll) {
        this.nodes = nodes;
        this.landmarkDistancesAll = landmarkDistancesAll;
    }

    /**
     * Runs the ALT algorithm on the map.
     *
     * @param startNode The node to start from.
     * @param endNode   The node to end at.
     */
    public void executeALT(int startNode, int endNode) {
        resetNodesAndEdges();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.fullDistance));

        long startTime = System.nanoTime();
        nodes[startNode].distance = 0;
        queue.add(nodes[startNode]);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visited.add(node);
            if (node.nodeId == endNode) {
                break;
            }
            for (Edge edge : node.edges) {
                if (!visited.contains(edge.toNode())) {
                    int alternativeDistance = node.distance + edge.travelTime();
                    if (alternativeDistance < edge.toNode().distance) {
                        queue.remove(edge.toNode());
                        edge.toNode().distance = alternativeDistance;
                        edge.toNode().distanceToEnd = estimate(edge.toNode().nodeId, endNode);
                        edge.toNode().fullDistance = edge.toNode().distance + edge.toNode().distanceToEnd;
                        edge.toNode().from = node;
                        queue.add(edge.toNode());
                    }
                }
            }
        }

        getShortestPathALT(startNode, endNode);

        long totalTime = (System.nanoTime() - startTime) / 1_000_000;
        MapRouting.runStats("ALT", endNode, totalTime, nodes, visited);
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
     * Estimates the distance from a node to the end node.
     *
     * @param currentNode The node to estimate from.
     * @param endNode     The node to estimate to.
     * @return The estimated distance.
     */
    private int estimate(int currentNode, int endNode) {
        int LANDMARKS = 3;
        int currentBest = 0;
        for (int i = 0; i < LANDMARKS; i++) {
            if (landmarkDistancesAll.size() > i) {
                ArrayList<Integer> landmarkDistances = landmarkDistancesAll.get(i);

                int distanceFromLandmarkToEnd = landmarkDistances.get(endNode);
                int distanceFromLandmarkToCurrent = landmarkDistances.get(currentNode);
                int estimate = distanceFromLandmarkToEnd - distanceFromLandmarkToCurrent;
                if (estimate < 0) {
                    estimate = 0;
                }

                if (estimate > currentBest) {
                    currentBest = estimate;
                }
            }
        }
        return currentBest;
    }

    /**
     * Writer method for writing Latitude and Longitude to file.
     *
     * @param path The path to write to file.
     */
    private void writePathToFile(List<Node> path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alt-trip.txt"))) {
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
    public void getShortestPathALT(int startNode, int endNode) {
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
//            System.out.println(node.latitude + "," + node.longitude); // Print "Lat, Lon" in console.
            writePathToFile(path); // Print "Lat, Lon" to file
        }

        System.out.println("\nPath written to file: alt-trip.txt"); // Comment out if printing to console.
        System.out.println("\nTotal nodes in the path: " + totalNodesInPath);
    }
}
