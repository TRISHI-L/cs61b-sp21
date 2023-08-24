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
    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        for (int i = 0; i < 20; i++) {
            arrayDeque.addLast(i);
        }

        int index = 0;
        for (int item : arrayDeque) {
            assertEquals("Should be equal", index, item);
            index += 1;
        }
    }
    @Test
    public void equalsTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();

        ad1.addLast(0);
        ad2.addLast(0);
        assertEquals(ad1, ad2);

        ad1.addLast(1);
        assertNotEquals(ad1, ad2);

        ad2.addLast(2);
        assertNotEquals(ad1, ad2);
    }

}
