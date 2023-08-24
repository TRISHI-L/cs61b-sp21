package deque;

import org.junit.Test;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>,Deque<T> {
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode prev;
        public StuffNode(T i) {
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
    public LinkedListDeque() {
        size=0;
        sentinel = new StuffNode(null);
        sentinel.next = sentinel.prev=sentinel;
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        helperAddFirst(item);
        size = size + 1;
    }
    private void helperAddFirst(T i) {
        StuffNode newNode = new StuffNode(i);
        if (size == 0) {
            this.sentinel.next = newNode;
            this.sentinel.prev = newNode;
            newNode.prev = this.sentinel;
            newNode.next = this.sentinel;
        } else {
            newNode.prev = this.sentinel;
            newNode.next = this.sentinel.next;
            this.sentinel.next = newNode;
            this.sentinel.prev.prev = newNode;
        }
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        addLastHelper(item);
        size = size + 1;
    }
    private void addLastHelper(T i) {
        StuffNode newNode = new StuffNode(i);
        if (size == 0) {
            this.sentinel.next = newNode;
            this.sentinel.prev = newNode;
            newNode.prev = this.sentinel;
            newNode.next = this.sentinel;
        } else {
            newNode.prev = this.sentinel.prev;
            newNode.next = this.sentinel;
            this.sentinel.prev.next = newNode;
            this.sentinel.prev = newNode;
        }
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque() {
        StuffNode P = sentinel.next;
        while (P != sentinel) {
            System.out.print (P.item+" ");
            P = P.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    @Override
    public T removeFirst() {

        if (size == 0) {
            return null;
        }
        else {
            T i = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size = size - 1;
            return i;
        }

    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        else {
            T i = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size = size - 1;
            return i;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque! */
    @Override
    public T get(int index) {
        if (size == 0){
            return null;
        } else {
            int i = 0;
            StuffNode p = sentinel.next;
            while (i != index) {
                i += 1;
                p = p.next;
            }
            return p.item;
        }
    }

    public T getRecursive(int index){
        return getRecursiveHelper(index,sentinel.next);
    }
    public T getRecursiveHelper(int index,StuffNode p) {
        if (p == sentinel)
            return null;
        if (index == 0)
            return p.item;
        else {
            return getRecursiveHelper(index-1,p.next);
        }
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     *  so we must provide this method to return an iterator. */


    private class LinkedListDequeIterator implements Iterator<T> {
        private StuffNode p = sentinel.next;
        public LinkedListDequeIterator() {}
        public boolean hasNext() {
           return p != sentinel;
        }
        public T next() {
            T returnItem = p.item;
            p = p.next;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    /** Returns whether or not the parameter o is equal to the Deque.
     *  o is considered equal if it is a Deque and if it contains the same contents
     *  (as goverened by the generic T’s equals method) in the same order. */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> olld = (LinkedListDeque<T>) o;
            if (this.size != olld.size)
                return false;
            for ( T x : this) {
                if (!olld.contains(x))
                    return false;
            }
            return true;
        }
        return false;
    }
    public boolean contains(T x) {
        StuffNode p =sentinel.next;
        while (p!=sentinel) {
            if (p.item == x)
                return true;
            p = p.next;
        }
        return false;
    }

}

