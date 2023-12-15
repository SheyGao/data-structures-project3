package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import cse332.graph.GraphUtil;
import paralleltasks.RelaxInTask;
import paralleltasks.ArrayCopyTask;
import main.Parser;

import java.util.List;
import java.util.Map;

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parseInverse(adjMatrix);

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

        for (int i = 0; i < numVertices; i++){
            int[] distCopy;
            distCopy = ArrayCopyTask.copy(dist);
            RelaxInTask.parallel(dist, distCopy, pred, g, 0, numVertices);
        }
        return GraphUtil.getCycle(pred);
    }


}