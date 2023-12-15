package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;
import cse332.graph.GraphUtil;
import main.Parser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class OutParallelLock implements BellmanFordSolver {

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

        ReentrantLock[] locks = new ReentrantLock[numVertices];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new ReentrantLock();
        }

        for (int i = 0; i < numVertices; i++){
            int[] distCopy;
            distCopy = ArrayCopyTask.copy(dist);
            RelaxOutTaskLock.parallel(locks, dist, distCopy, pred, 0, numVertices, g);
        }
        return GraphUtil.getCycle(pred);

    }

}
