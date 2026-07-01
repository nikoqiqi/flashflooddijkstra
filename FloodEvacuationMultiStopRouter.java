import java.util.*;


public class FloodEvacuationMultiStopRouter {


    static class Edge {
        char target;
        int weight;


        Edge(char target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }


    static class Node implements Comparable<Node> {
        char name;
        int distance;


        Node(char name, int distance) {
            this.name = name;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }


    public static void main(String[] args) {
        Map<Character, List<Edge>> graph = new HashMap<>();
        char[] nodes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};
        for (char node : nodes) {
            graph.put(node, new ArrayList<>());
        }


        // Graph edges mapping (Strictly One-Way / Directed Graph)
        addEdge(graph, 'A', 'B', 5);
        addEdge(graph, 'A', 'C', 8);
        addEdge(graph, 'A', 'H', 12);
        addEdge(graph, 'B', 'G', 7);
        addEdge(graph, 'B', 'D', 6);
        addEdge(graph, 'C', 'D', 4);
        addEdge(graph, 'C', 'F', 9);
        addEdge(graph, 'D', 'G', 3);
        addEdge(graph, 'D', 'E', 5);
        addEdge(graph, 'F', 'G', 4);
        addEdge(graph, 'G', 'H', 5);
        addEdge(graph, 'H', 'J', 4);
        addEdge(graph, 'H', 'K', 9);
        addEdge(graph, 'E', 'J', 8);
        addEdge(graph, 'E', 'I', 6);
        addEdge(graph, 'I', 'J', 3);
        addEdge(graph, 'I', 'K', 7);
        addEdge(graph, 'J', 'K', 5);


        // Map node characters to full descriptions for output readability
        Map<Character, String> names = new HashMap<>();
        names.put('A', "Emergency Operation Center");
        names.put('B', "Highway Junction 1");
        names.put('C', "Highway Junction 2");
        names.put('D', "Residential Area 1");
        names.put('E', "Residential Area 2");
        names.put('F', "Highway Interchange");
        names.put('G', "Flood Affected Road Section");
        names.put('H', "Highway Checkpoint");
        names.put('I', "Residential Area 3");
        names.put('J', "Evacuation Center 1");
        names.put('K', "Evacuation Center 2");


        System.out.println("=================================================================");
        System.out.println("  MANDATORY RESIDENTIAL SUPPLY ROUTE (BYPASSING FLOOD ZONE G)");
        System.out.println("  Required Stops: Residential Area 1 (D) -> 2 (E) -> 3 (I)");
        System.out.println("=================================================================\n");


        // Define the final endpoints we want to test after clearing all residential zones
        char[] finalDestinations = {'J', 'K'};


        for (char dest : finalDestinations) {
            StringBuilder fullPath = new StringBuilder();
            int totalDistance = 0;
            boolean routeValid = true;


            // Sequential segments: A -> D -> E -> I -> Destination
            char[] checkpoints = {'A', 'D', 'E', 'I', dest};


            for (int i = 0; i < checkpoints.length - 1; i++) {
                DijkstraResult result = runDijkstra(graph, checkpoints[i], checkpoints[i + 1]);
                
                if (result.distance == Integer.MAX_VALUE) {
                    routeValid = false;
                    break;
                }
                
                totalDistance += result.distance;
                
                // Append path segments seamlessly
                if (i == 0) {
                    fullPath.append(result.path);
                } else {
                    // Avoid duplicating the start node of the next leg (skips the first 5 characters "X -> ")
                    fullPath.append(" -> ").append(result.path.substring(5)); 
                }
            }


            System.out.println("Final Destination: " + names.get(dest) + " (" + dest + ")");
            if (routeValid) {
                System.out.println("  Total Multi-Stop Distance: " + totalDistance + " km");
                System.out.println("  Complete Itinerary       : " + fullPath.toString());
            } else {
                System.out.println("  Route status: NO VIABLE ROUTE satisfying all mandatory stops.");
            }
            System.out.println("-----------------------------------------------------------------");
        }
    }


    private static void addEdge(Map<Character, List<Edge>> graph, char source, char target, int weight) {
        graph.get(source).add(new Edge(target, weight));
    }


    // Helper class to store individual segment outcomes
    static class DijkstraResult {
        int distance;
        String path;


        DijkstraResult(int distance, String path) {
            this.distance = distance;
            this.path = path;
        }
    }


    // Dijkstra execution targeting a specific single destination node, ignoring flooded nodes
    private static DijkstraResult runDijkstra(Map<Character, List<Edge>> graph, char start, char end) {
        Map<Character, Integer> distances = new HashMap<>();
        Map<Character, Character> predecessors = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();


        for (char node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new Node(start, 0));


        while (!pq.isEmpty()) {
            Node current = pq.poll();
            char u = current.name;


            if (current.distance > distances.get(u)) continue;
            if (u == end) break; // Optimization: destination reached


            for (Edge edge : graph.get(u)) {
                char v = edge.target;


                // FLOOD SAFETY CHECK: Skip node G entirely
                if (v == 'G') {
                    continue;
                }


                int newDist = distances.get(u) + edge.weight;


                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    predecessors.put(v, u);
                    pq.add(new Node(v, newDist));
                }
            }
        }


        int targetDist = distances.get(end);
        if (targetDist == Integer.MAX_VALUE) {
            return new DijkstraResult(Integer.MAX_VALUE, "");
        }


        // Reconstruct path string
        List<Character> pathList = new ArrayList<>();
        char curr = end;
        while (predecessors.containsKey(curr)) {
            pathList.add(curr);
            curr = predecessors.get(curr);
        }
        pathList.add(curr);
        Collections.reverse(pathList);


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pathList.size(); i++) {
            sb.append(pathList.get(i));
            if (i < pathList.size() - 1) sb.append(" -> ");
        }


        return new DijkstraResult(targetDist, sb.toString());
    }
}
