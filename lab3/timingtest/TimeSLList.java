package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int ListLength = 1000;
        int M=10000;
        for(int k=1;k<=8;k++){

            SLList<Integer> testList = new SLList<>();

            for (int i = 0; i < ListLength; i++) {
                testList.addLast(i);
            }
            Stopwatch sw = new Stopwatch();
            for(int j=0;j<M;j++) {
                testList.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            Ns.addLast(ListLength);
            times.addLast(timeInSeconds);
            opCounts.addLast(M);

            ListLength*=2;
        }
        printTimingTable(Ns,times,opCounts);
    }
    }


