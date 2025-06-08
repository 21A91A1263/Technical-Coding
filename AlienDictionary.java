import java.util.*;

public class AlienDictionary {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();

        // Add all characters to graph and inDegree
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<>());
                inDegree.putIfAbsent(c, 0);
            }
        }

        // Build the graph
        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i], second = words[i + 1];
            if (first.length() > second.length() && first.startsWith(second)) {
                return ""; // Invalid order
            }
            for (int j = 0; j < Math.min(first.length(), second.length()); j++) {
                char c1 = first.charAt(j);
                char c2 = second.charAt(j);
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        // Topological sort using Queue
        Queue<Character> queue = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) queue.add(c);
        }

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char current = queue.poll();
            result.append(current);
            for (char neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) queue.add(neighbor);
            }
        }

        // If cycle exists, result won't include all characters
        return result.length() == inDegree.size() ? result.toString() : "";
    }

    // Test Cases
    public static void main(String[] args) {
        AlienDictionary ad = new AlienDictionary();

        String[] test1 = {"abc", "ab"};
        System.out.println("Test 1: " + ad.alienOrder(test1)); // Output: ""

        String[] test2 = {"x", "y", "z"};
        System.out.println("Test 2: " + ad.alienOrder(test2)); // Output: "xyz"

        String[] test3 = {"ba", "bc", "ac", "cab"};
        System.out.println("Test 3: " + ad.alienOrder(test3)); // Output: "bac" or similar valid result
    }
}
