package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskBad;
import cse332.graph.GraphUtil;

import java.util.List;
import java.util.Map;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);

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
            RelaxOutTaskBad.parallel(dist, distCopy, pred, 0, numVertices, g);
        }
        return GraphUtil.getCycle(pred);
    }

}
