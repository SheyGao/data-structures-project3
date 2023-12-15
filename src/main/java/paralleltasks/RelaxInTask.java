package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.List;
import java.util.Map;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    final int lo, hi;
    private final int[] dist;
    private final int[] distCopy;
    private final int[] pred;
    private final List<Map<Integer, Integer>> g;

    public RelaxInTask(int[] dist, int[] distCopy, int[] pred, List<Map<Integer, Integer>> g, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.dist = dist;
        this.distCopy = distCopy;
        this.pred = pred;
        this.g = g;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF){
            sequential(dist, distCopy, pred, g, lo, hi);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxInTask left = new RelaxInTask(dist, distCopy, pred, g, lo, mid);
            RelaxInTask right = new RelaxInTask(dist, distCopy, pred,g, mid, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

        public static void sequential(int[] dist, int[] distCopy, int[] pred, List<Map<Integer, Integer>> g, int lo, int hi) {
        //int numVertices = g.size();
        for (int v = lo; v < hi; v++) {
            Map<Integer, Integer> pair = g.get(v);
            for (int neighbor: pair.keySet()) {
                if (distCopy[neighbor] != Integer.MAX_VALUE && distCopy[neighbor] + pair.get(neighbor) < dist[v]) {
                        dist[v] = distCopy[neighbor] + pair.get(neighbor);
                        pred[v] = neighbor;
                }
            }
        }
    }

    public static void parallel(int[] dist, int[] distCopy, int[] pred, List<Map<Integer, Integer>> g, int lo, int hi) {
        pool.invoke(new RelaxInTask(dist, distCopy, pred, g, lo, hi));
    }

}
