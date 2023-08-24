package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void IntMaxArrayTest(){
        MaxArrayDeque<Integer> ad = new MaxArrayDeque<>(new IntComparator());
        ad.addFirst(2);
        ad.addFirst(44);
        ad.addFirst(89);
        ad.addFirst(21);
        ad.addFirst(89);
        assertEquals((Integer) 89,ad.max());

    }
    private static class IntComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer num1, Integer num2){
            return num1 - num2;
        }
    }
    @Test
    public void StringMaxArrayTest(){
        MaxArrayDeque<String> sad = new MaxArrayDeque<>(new StringComparator());
        sad.addFirst("freedom");
        sad.addFirst("try everything!!!");
        sad.addFirst("be proud of myself. :D ");
        sad.addFirst("2023/8/24");
        assertEquals("The longest string should be [be proud of myself. :D ]","be proud of myself. :D ",sad.max());

    }
    private static class StringComparator implements Comparator<String>{
        @Override
        public int compare(String s1,String s2){
            return s1.length()-s2.length();
        }
    }

}
