import java.io.*;
import java.util.*;

/**
 * Main class for running the program.
 */
public class MapRouting {
    private static Node[] nodes;

    /**
     * Main method for running the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws Exception {
        BufferedReader nodeReader = new BufferedReader(new FileReader("noder.txt"));
        BufferedReader edgeReader = new BufferedReader(new FileReader("kanter.txt"));
        BufferedReader interestPointReader = new BufferedReader(new FileReader("interessepkt.txt"));

        readNodes(nodeReader);
        readEdges(edgeReader);
        readInterestPoints(interestPointReader);

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("""
                    \nSelect an option:
                    1 to Preprocess Landmarks
                    2 to Run Dijkstra
                    3 to Run ALT
                    4 to Find Points of Interest (POI)
                    5 to Exit""");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    preprocessLandmarks();
                    System.out.println("All landmarks preprocessed.");
                    break;
                case 2:
                case 3:
                    System.out.println("Enter start node ID:");
                    int startNode = scanner.nextInt();
                    System.out.println("Enter end node ID:");
                    int endNode = scanner.nextInt();

                    if (choice == 2) {
                        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(nodes);
                        dijkstra.executeDijkstra(startNode, endNode);
                    } else {
                        ArrayList<ArrayList<Integer>> landmarkDistances = readLandmarkDistances();
                        AltAlgorithm alt = new AltAlgorithm(nodes, landmarkDistances);
                        alt.executeALT(startNode, endNode);
                    }
                    break;
                case 4:
                    System.out.println("Enter start node ID:");
                    startNode = scanner.nextInt();
                    int interestPointType = combineInterestPointTypes(scanner);
                    System.out.println("Enter number of closest Point of Interest to find:");
                    int numberOfPoints = scanner.nextInt();
                    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(nodes);
                    dijkstra.findClosestInterestPoints(startNode, interestPointType, numberOfPoints);
                    break;
                case 5:
                    isRunning = false;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    /**
     * Reads the nodes from a file.
     *
     * @param reader The file to read from.
     */
    private static void readNodes(BufferedReader reader) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int id = Integer.parseInt(tokenizer.nextToken());
            double latitude = Double.parseDouble(tokenizer.nextToken());
            double longitude = Double.parseDouble(tokenizer.nextToken());
            nodes[i] = new Node(id, latitude, longitude);
        }
    }

    /**
     * Reads the edges from a file.
     *
     * @param reader The file to read from.
     */
    private static void readEdges(BufferedReader reader) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int E = Integer.parseInt(tokenizer.nextToken());
        for (int i = 0; i < E; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());
            int weight = Integer.parseInt(tokenizer.nextToken());
            double distance = Double.parseDouble(tokenizer.nextToken());
            int speedLimit = Integer.parseInt(tokenizer.nextToken());
            nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight, distance, speedLimit));
        }
    }

    /**
     * Reads the interest points from a file.
     *
     * @param reader The file to read from.
     */
    private static void readInterestPoints(BufferedReader reader) throws Exception {
        String line;
        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (isFirstLine) {
                int totalCount = Integer.parseInt(line);
                System.out.println("Total Points of Interest: " + totalCount);
                isFirstLine = false;
                continue;
            }

            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            try {
                int id = Integer.parseInt(tokenizer.nextToken());
                int type = Integer.parseInt(tokenizer.nextToken());
                String name = tokenizer.nextToken().replaceAll("^\"|\"$", "");

                nodes[id].type = type;
                nodes[id].name = name;
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid line: " + line);
            } catch (NoSuchElementException e) {
                System.err.println("Line format error: " + line);
            }
        }
    }

    /**
     * Preprocesses the given landmarks.
     */
    public static void preprocessLandmarks() throws Exception {
        int[] landmarkIds = {7_727_221, 902_211, 3_673_411};
        String[] filenames = {"landmark1.txt", "landmark2.txt", "landmark3.txt"};

        for (int i = 0; i < landmarkIds.length; i++) {
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(nodes);
            dijkstra.executeDijkstra(landmarkIds[i], -1); // -1 indicates no specific end node
            saveLandmarkDataToFile(nodes, filenames[i]);

            // Do not call timeEstimator here as it's not relevant for preprocessing
            System.out.println("Landmark " + (i + 1) + " preprocessed.");
        }
    }


    /**
     * Saves the landmark data to a file.
     *
     * @param nodes    The nodes to save.
     * @param filename The file to save to.
     */
    private static void saveLandmarkDataToFile(Node[] nodes, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Node node : nodes) {
                writer.println(node.nodeId + " " + node.distance);
            }
        }
    }

    /**
     * Reads the landmark data from a file.
     *
     * @return The landmark data.
     */
    public static ArrayList<ArrayList<Integer>> readLandmarkDistances() throws IOException {
        String[] filenames = {"landmark1.txt", "landmark2.txt", "landmark3.txt"};
        ArrayList<ArrayList<Integer>> landmarkDistances = new ArrayList<>();

        for (String filename : filenames) {
            ArrayList<Integer> distances = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    distances.add(Integer.parseInt(parts[1]));
                }
            }
            landmarkDistances.add(distances);
        }

        return landmarkDistances;
    }

    /**
     * Calculates the estimated travel time and prints it to the console
     * in addition to the run time and number of nodes visited.
     *
     * @param Algorithm The algorithm used.
     * @param endNode   The end node.
     * @param totalTime The total time taken.
     * @param nodes     The nodes visited.
     * @param visited   The visited nodes.
     */
    static void runStats(String Algorithm, int endNode, long totalTime, Node[] nodes, Set<Node> visited) {
        if (endNode != -1) {
            int seconds = nodes[endNode].distance / 100;
            int minutes = (seconds % 3600) / 60;
            int hours = seconds / 3600;
            seconds = seconds % 60;

            System.out.println(Algorithm + " took: " + totalTime + " milliseconds");
            System.out.println(Algorithm + " visited: " + visited.size() + " nodes");
            System.out.println("Estimated travel time: " + hours + " hours, "
                    + minutes + " minutes, " + seconds + " seconds");
        } else {
            // Handle the case where there is no specific end node (e.g. during preprocessing)
            System.out.println("Processing...");
        }
    }

    /**
     * Lets user choose interest point types and combine them if multiple are chosen.
     *
     * @param scanner The scanner to read from.
     * @return The interest point type.
     */
    private static int combineInterestPointTypes(Scanner scanner) {
        Set<Integer> validTypes = new HashSet<>(Arrays.asList(1, 2, 4, 8, 16, 32));
        Set<Integer> enteredTypes = new HashSet<>();
        int combinedType = 0;

        System.out.println("""
                    Enter Point of Interest types, one at a time. Enter '0' when done.
                    1 for Place name
                    2 for Gas station
                    4 for Charging station
                    8 for Restaurant
                    16 for Nightlife
                    32 for Accommodation
                """);

        while (true) {
            System.out.println("Enter type (or 0 to finish):");
            int type = scanner.nextInt();

            if (type == 0) { // Exit loop
                break;
            }

            if (validTypes.contains(type) && !enteredTypes.contains(type)) {
                enteredTypes.add(type); // Add new type
                combinedType |= type; // Combine type using bitwise OR
            } else {
                System.out.println("Invalid or duplicate type. Please enter a different type.");
            }
        }

        return combinedType;
    }

}
