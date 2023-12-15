package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        // parsing adjMatrix to create adjacency lists
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);

        // initialization
        int numVertices = g.size();

        int[] dist = new int[numVertices];
        for (int i = 0; i < dist.length; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;

        int[] pred = new int[numVertices];
        for (int i = 0; i < dist.length; i++){
            pred[i] = -1;
        }

        // 0: {1=2, 2=2}
        // relaxing the edges
        for (int i = 0; i < numVertices; i++) {
            // array copying
            int[] distCopy = new int[dist.length];
            for (int j = 0; j < numVertices; j++) {
                distCopy[j] = dist[j];
            }

            for (int v = 0; v < numVertices; v++) {
                Map<Integer, Integer> pair = g.get(v);
                for (int neighbor: pair.keySet()) {
                        if (distCopy[v] != Integer.MAX_VALUE && distCopy[v] + pair.get(neighbor) < dist[neighbor]) {
                            dist[neighbor] = distCopy[v] + pair.get(neighbor);
                            pred[neighbor] = v;
                        }
                    }
            }
        }
        //System.out.println(GraphUtil.getCycle(pred));
        return GraphUtil.getCycle(pred);

    }



}