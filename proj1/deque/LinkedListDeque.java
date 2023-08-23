package deque;

import org.junit.Test;

public class LinkedListDeque<T> {
    private class StuffNode{
        private T item;
        private StuffNode next;
        private StuffNode prev;
        public StuffNode(T i){
            item=i;
            next=null;
            prev=null;
        }

    }

    private int size;
    private StuffNode sentinel;

    /** The first item (if it exits) is at sentinel.next.
     *  Creates an empty LinkedListDeque.
     */
    public LinkedListDeque(){
        size=0;
        sentinel = new StuffNode(null);
        sentinel.next=sentinel.prev=sentinel;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        helperAddFirst(item);
        size = size + 1;
    }
    private void helperAddFirst(T i){
        StuffNode newNode = new StuffNode(i);
        if (size == 0){
            this.sentinel.next = newNode;
            this.sentinel.prev=newNode;
            newNode.prev = this.sentinel;
            newNode.next=this.sentinel;
        } else{
            newNode.prev=this.sentinel;
            newNode.next=this.sentinel.next;
            this.sentinel.next=newNode;
            this.sentinel.prev.prev=newNode;
        }
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        addLastHelper(item);
        size = size + 1;
    }
    private void addLastHelper(T i){
        StuffNode newNode = new StuffNode(i);
        if(size == 0){
            this.sentinel.next=newNode;
            this.sentinel.prev=newNode;
            newNode.prev=this.sentinel;
            newNode.next=this.sentinel;
        }else{
            newNode.prev=this.sentinel.prev;
            newNode.next=this.sentinel;
            this.sentinel.prev.next=newNode;
            this.sentinel.prev=newNode;

        }
    }


    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        if(size == 0)
            return true;
        return false;
    }

    /** Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    public void printDeque(){
        StuffNode P = sentinel.next;
        while(P != sentinel){
            System.out.print(P.item+" ");
            P = P.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst(){

        if(size == 0){
            return null;
        }
        else {
            T i=sentinel.next.item;
            sentinel.next=sentinel.next.next;
            sentinel.next.prev=sentinel;
            size = size - 1;
            return i;
        }

    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    public T removeLast(){
        if(size == 0){
            return null;
        }
        else {
            T i=sentinel.prev.item;
            sentinel.prev=sentinel.prev.prev;
            sentinel.prev.next=sentinel;
            size = size - 1;
            return i;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque! */
    public T get(int index){
        if (size == 0){
            return null;
        }else{
            int i=0;
            StuffNode p = sentinel.next;
            while(i != index){
                i += 1;
                p = p.next;
            }
            return p.item;
        }
    }

    public T getRecursive(int index){
        return getRecursiveHelper(index,sentinel.next);
    }
    public T getRecursiveHelper(int index,StuffNode p){
        if(p == sentinel)
            return null;
        if(index == 0)
            return p.item;
        else {
            return getRecursiveHelper(index-1,p.next);
        }
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

