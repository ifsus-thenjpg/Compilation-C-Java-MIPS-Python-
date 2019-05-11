package cellularData;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedList class contains Nodes objects in the list
 * @author Foothill College, Bita M. Team 8
 */
public class LinkedList<T> implements Iterable<T>{
    private Node<T> head;            // keeps track of the front of the list
    private int length;              // keeps track of the number of nodes in the list

    /**
     *  Creates a new list that is empty.
     */
    public LinkedList()
    {
        head = null;
        this.length = 0;
    }

    /**
     * Checks if head is pointing to any node.
     * @return                True, if head doesn't pointing to any node.
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * Adds new Node object to the end of the list.
     * @param theData       A new data item to be added to our list.
     */
    public void add(T theData){
        Node<T> current = new Node<T>(theData);

        if(isEmpty()) {
            head = current;
        }
        else {
            Node<T> tail = getNodeAtIndex(size()-1);
            tail.setNext(current);
        }
        this.length++;
    }

    /**
     * Gets a node at a specific index.
     * @param index         The index after the start of the list
     * @return              Node object at the specific index
     */
    private Node<T> getNodeAtIndex(int index){
        Node<T> walker = head;

        for(int i = 0; walker != null && i < index; i++){
            walker = walker.getNext();
        }
        return walker;
    }

    /**
     * Returns Country from the CountryList according to the requested index number
     * @param index        Number of country in the list
     * @return             Country object
     * @throws IndexOutOfBoundsException  Is thrown when index less than 0 or greater than list size
     */
    public T getIndex(int index) throws IndexOutOfBoundsException {
        Node<T> walker = head;
        if(index < 0 || index >= length){
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        for(int i = 0; i < index; i++){
            walker = walker.getNext();
        }

        return walker.getData();
    }

    /**
     * The number of nodes in the list.
     * @return             Number of Nodes in the LinkedList
     */
    public int size() {
        return this.length;
    }

    /**
     * Checks if the data exists in the list
     * @param data         The data we are searching for
     * @return             found country or null if country is not found
     */
    public T contains(T data){
        for(T d: this) {
            if (d.equals(data)) return d;
        }
        return null;
    }

    /**
     * String representation of the list of countries
     * @return         A string containing information about every country in the list
     */
    public String toString() {
        String res = "";
        for(T data: this) {
            res += "\n" + data;
        }
        return res;
    }

    /**
     * A method that returns an object of type ListIterator
     * @return        Object of type ListIterator
     */
    public Iterator iterator(){
        ListIterator a = new ListIterator();
        return a;
    }


    /**
     * inner class ListIterator
     */
    private class ListIterator implements Iterator<T> {
        private Node<T> current;           // keeps track of the current location being traversed

        /**
         * A constructor that initializes current to the beginning of the list
         */
        public ListIterator(){
            current = head;
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public T next(){
            if (current == null) {
                throw new NoSuchElementException();
            }
            T result = current.getData();
            current = current.getNext();
            return result;
        }

        /**
         * Returns true if LinkedList has more elements.
         * @return    boolean result
         */
        public boolean hasNext() {
            return current != null;
        }
    }
}
