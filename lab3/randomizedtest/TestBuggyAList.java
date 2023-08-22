package randomizedtest;

import com.sun.source.tree.NewArrayTree;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public  void testThreeAddThreeRemove(){
        AListNoResizing ALNR=new AListNoResizing<>();
        BuggyAList BA=new BuggyAList<>();
        ALNR.addLast(4);
        ALNR.addLast(5);
        ALNR.addLast(6);

        BA.addLast(4);
        BA.addLast(5);
        BA.addLast(6);

        assertEquals(ALNR.size(),BA.size());
        assertEquals(ALNR.getLast(),BA.getLast());
        assertEquals(ALNR.removeLast(),BA.removeLast());
        assertEquals(ALNR.removeLast(),BA.removeLast());
        assertEquals(ALNR.removeLast(),BA.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
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
                if (L.size() > 0) {
                    int remove = L.removeLast();
                    System.out.println("removeLast：" + remove);
                }
            }
            else{
                // getLast
                if(L.size()>0){
                    int last=L.getLast();
                    System.out.println("getLast："+last);
                }
            }
        }
    }
    @Test
    public void randomizedComparisonsTest(){
        AListNoResizing<Integer> L=new AListNoResizing<>();
        BuggyAList<Integer> BL= new BuggyAList<>();
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
                assertEquals(L.getLast(),BL.getLast());
                System.out.println("addLast(" + randVal + ")");

            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(L.size(),BL.size());
                System.out.println("size: " + size);
            }
            else if(operationNumber==2){
                //removeLast
                if (L.size() > 0) {
                    int remove = L.removeLast();
                    assertEquals(remove,(int)BL.removeLast());
                    System.out.println("removeLast：" + remove);
                }
            }
            else{
                // getLast
                if(L.size()>0){
                    int last=L.getLast();
                    assertEquals(L.getLast(),BL.getLast());

                    System.out.println("getLast："+last);
                }
            }
        }

    }
}
