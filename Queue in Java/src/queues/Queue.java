package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Objects of type Queue manage items in a singly linked list where we can enqueue() items to the end and dequeue() items from the front of the queue.
 * @param <AnyType>
 */
public class Queue<AnyType> implements Iterable {

    //attributes
    private String name; //name of the playlist
    private int currentSize;
    private Node head;  //points to front of the queue
    private Node tail;  //points to the end of the queue

    /**
     * constructor - initializes empty queue
     * @param name assigns the name of the queue
     */
    public Queue(String name) {
        this.name = name;
        this.currentSize = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     * @return element at the head of this queue, or null if this queue is empty
     */
    public AnyType peek() {
        if(currentSize == 0){
            return null;
        }
        return this.head.data;
    }

    /**
     * accessor
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * accessor method
     * @return currenSize that holds the size of the queue
     */
    public int size() {
        return currentSize;
    }

     /**
     * deletes (and returns) the element the element at the start of the list (front)
     * @return
     */
    public AnyType dequeue() {
        AnyType retValue = null;
        if(isEmpty()){ //case 1: where queue is empty
            throw new NoSuchElementException("Queue Underflow"); //handled in the test file: MyTunes.java
        }if(head == null){ //case 2: where there is one node and both head and tail point to it
            tail = null;
        }else{
            retValue = this.head.data; //case 3: head and tail point to different nodes in the queue
            head = head.next;
        }
        currentSize--;
        return retValue;
    }

    /**
     * inserts an element at the end of the list (rear)
     * @return
     */
    public void enqueue(AnyType x){

        Node temp = new Node(x); //assigns x to temp.data and temp.next to null
        if(isEmpty()){
           head = temp;
           tail = temp;
        }else{
            tail.next = temp;
            tail = tail.next;
        }
        currentSize++;
    }

    /**
     *checks if the top of the stack is pointing to anything.
     * @return true if top has not been initialized (is empty), false otherwise.
     */
    public boolean isEmpty(){
        if(currentSize == 0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Iterator<AnyType> iterator() {
        return new QueueIterator();
    }

    //inner class
    /**
     * class that holds the node objects in singly linked list
     */
    private class Node{

        //attributes
        Node next;
        AnyType data;

        /**
         * constructor
         * @param element takes in an element to be assigned as the data that the node holds in the singly linked list
         */
        public Node(AnyType element) {
            this.data = element;
            this.next = null;
        }
    }

    @Override
    public String toString() {
        Iterator<AnyType> iter = iterator();
        String result = "";
        while(iter.hasNext()){
            result += iter.next() + "; \n";
        }
        return this.name + ": \n[" + result + "]";
    }

    /**
     * traverses a list similar like a "cursor" that can be placed before one element of a data structure at given time
     */
    private class QueueIterator implements Iterator<AnyType> {
        protected Node currentNode;

        /**
         * Constructors (default access for package only)
         */
        QueueIterator() {
            currentNode = head;
        }

        /**
         * @return returns true if there is something in the list to the immediate right of the iterator.
         * If there is no item there, meaning it is at the end of the list, it returns false.
         */
        public boolean hasNext() {
            if (currentNode == null) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * @return returns the item to the immediate right of the iterator, and moves the iterator up one, i.e.,
         * past the item just returned.
         */
        public AnyType next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                AnyType retVal = currentNode.data;
                currentNode = currentNode.next;
                return retVal;
            }

        }
    }

}
