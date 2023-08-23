package deque;

import com.sun.source.tree.NewArrayTree;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void randomizedTest(){
        ArrayDeque<Integer> L = new ArrayDeque<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");

            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }
            else if(operationNumber==2){
                //removeLast
                if (!L.isEmpty()) {
                    int remove = L.removeLast();
                    System.out.println("removeLast：" + remove);
                }
            } else if(operationNumber==3){
                //printDeque
                L.printDeque();
            } else if (operationNumber==4) {
                //addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                System.out.println("addFirst("+randVal+")");
            }else{
                //removeFirst
                if (!L.isEmpty()) {
                    int remove = L.removeFirst();
                    System.out.println("removeFirst：" + remove);
                }
            }
        }
    }
}
