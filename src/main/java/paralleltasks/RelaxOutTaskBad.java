package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxOutTaskBad extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private final int[] dist;
    private final int[] distCopy;
    private final int[] pred;
    private final int lo;
    private final int hi;
    private final List<Map<Integer, Integer>> g;

    public RelaxOutTaskBad(int[] dist, int[] distCopy, int[] pred, int lo, int hi, List<Map<Integer, Integer>> g) {
        this.dist = dist;
        this.distCopy = distCopy;
        this.pred = pred;
        this.lo = lo;
        this.hi = hi;
        this.g = g;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF){
            sequential(dist, distCopy, pred, lo, hi, g);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxOutTaskBad left = new RelaxOutTaskBad(dist, distCopy, pred, lo, mid, g);
            RelaxOutTaskBad right = new RelaxOutTaskBad(dist, distCopy, pred, mid, hi, g);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int[] dist, int[] distCopy, int[] pred, int lo, int hi,List<Map<Integer, Integer>> g) {
        //int numVertices = g.size();
        for (int v = lo; v < hi; v++) {
            Map<Integer, Integer> pair = g.get(v);
            for (int neighbor: pair.keySet()) {
                if (distCopy[v] != Integer.MAX_VALUE && distCopy[v] + pair.get(neighbor) < dist[neighbor]) {
                        dist[neighbor] = distCopy[v] + pair.get(neighbor);
                        pred[neighbor] = v;
                }
            }
        }
}

    public static void parallel(int[] dist, int[] distCopy, int[] pred, int lo, int hi,List<Map<Integer, Integer>> g) {
        pool.invoke(new RelaxOutTaskBad(dist,  distCopy, pred, lo, hi, g));
    }

}
