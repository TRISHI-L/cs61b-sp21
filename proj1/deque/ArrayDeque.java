package deque;

public class ArrayDeque<T> {
    private T[] item;
    private int nextFirst;
    private int nextLast;
    private int size;
    private double usageRatio = 0.25;

    public ArrayDeque(){
        item = (T[]) new Object[8];
        size = 0;
        nextFirst=4;
        nextLast=5;
    }
    private boolean isFull(){
        if(size == item.length){
            return true;
        }
        return false;
    }

    private int minusOne(int curIndex){
        return (curIndex -1 + item.length) % item.length;

    }
    private int addOne(int curIndex){
        return (curIndex + 1) % item.length;
    }
    private int getExactIndexElement(int curIndex,int index){
        return (curIndex+index+1) % item.length;
    }

    public void resize(int capacity){

        T[] copyList = (T[]) new Object [capacity];
        int index = addOne(nextFirst);
        //System.arraycopy(item,0,copyList,0,size);
        for(int i = 0; i < size ; i++){
            copyList[i]=item[index];
            index = addOne(index);
        }
        item = copyList;
        nextFirst = capacity - 1;
        nextLast = size;
    }
    private void checkMul(){
        if(size==item.length)
            resize(size*2);
    }
    private void checkDiv(){
        if(item.length >= 16 && (double) size / item.length < usageRatio)
            resize(item.length/4);
    }
    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T x){
        checkMul();
        item[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T x) {
        checkMul();
        item[nextLast] = x;
        nextLast =addOne(nextLast);
        size += 1;
    }


    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }


    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        if(size == 0)
            return true;
        return false;
    }


    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        for(int i = 0 ; i < size ; i++){
            System.out.print(get(i)+" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        checkDiv();
        nextFirst=addOne(nextFirst);
        T removeItem = item[nextFirst];
        item[nextFirst]=null;
        size -= 1;

        return removeItem;

    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    public T removeLast(){
        if (size == 0){
            return null;
        }
        checkDiv();
        nextLast = minusOne(nextLast);
        T removeItem = item[nextLast];
        item[nextLast]=null;

        size -= 1;

        return removeItem;

    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque! */
    public T get(int index){
        return item[getExactIndexElement(nextFirst,index)];
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     *  so we must provide this method to return an iterator.
     public Iterator<T> iterator(){

     }

     /** Returns whether or not the parameter o is equal to the Deque.
     *  o is considered equal if it is a Deque and if it contains the same contents
     *  (as goverened by the generic T’s equals method) in the same order.
     *public boolean equals(Object o){

     }*/
}


