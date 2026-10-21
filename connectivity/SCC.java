package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SCC {

    public List<List<Integer>> findSCCs(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException(
                    "SCC requires a directed graph"
            );
        }

        int n = graph.getVertices();

        boolean[] visited =
                new boolean[n];

        List<Integer> finishOrder =
                new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(
                        graph,
                        i,
                        visited,
                        finishOrder
                );
            }
        }

        Graph reversed =
                reverseGraph(graph);

        visited = new boolean[n];

        List<List<Integer>> components =
                new ArrayList<>();

        Collections.reverse(finishOrder);

        for (int vertex : finishOrder) {

            if (!visited[vertex]) {

                List<Integer> component =
                        new ArrayList<>();

                dfs2(
                        reversed,
                        vertex,
                        visited,
                        component
                );

                components.add(component);
            }
        }

        return components;
    }

    private void dfs1(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> finishOrder) {

        visited[vertex] = true;

        for (Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            if (!visited[edge.to]) {
                dfs1(
                        graph,
                        edge.to,
                        visited,
                        finishOrder
                );
            }
        }

        finishOrder.add(vertex);
    }

    private void dfs2(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component) {

        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge :
                graph.getAdjacencyList().get(vertex)) {

            if (!visited[edge.to]) {
                dfs2(
                        graph,
                        edge.to,
                        visited,
                        component
                );
            }
        }
    }

    private Graph reverseGraph(Graph graph) {

        int n = graph.getVertices();

        Graph reversed =
                new Graph(n, true);

        for (Edge edge : graph.getEdges()) {

            reversed.addEdge(
                    edge.to,
                    edge.from,
                    edge.weight
            );
        }

        return reversed;
    }
}