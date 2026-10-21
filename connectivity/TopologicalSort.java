package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

    public List<Integer> sort(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "Topological sorting requires a directed graph"
            );
        }

        int n = graph.getVertices();

        int[] indegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (Edge edge :
                    graph.getAdjacencyList().get(u)) {

                indegree[edge.to]++;
            }
        }

        Queue<Integer> queue =
                new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order =
                new ArrayList<>();

        while (!queue.isEmpty()) {

            int current = queue.poll();

            order.add(current);

            for (Edge edge :
                    graph.getAdjacencyList()
                            .get(current)) {

                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        if (order.size() != n) {
            return new ArrayList<>();
        }

        return order;
    }

    public boolean isDAG(Graph graph) {
        return sort(graph).size()
                == graph.getVertices();
    }
}