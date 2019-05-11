package cellularData;

/**
 * Holds data for one node in a data structure.
 * @author Foothill College, Yuliia Trubchyk
 */
public class Node<T> {
    private T data;
    private Node<T> next;

    /**
     * Constructs an object to hold the data
     * and point to null as the next node.
     * @param data The data portion of this node.
     */
    public Node(T data){
        this.data = data;
        this.next = null;
    }

    /**
     * Constructs an object to hold the data
     * and point to another element as the next node.
     * @param data	    	The data portion of this node.
     * @param another		The node following this node.
     */
    public Node(T data, Node<T> another){
        this.data = data;
        this.next = another;
    }

    /**
     * Mutator method returns the data portion of the node.
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * Mutator method get the next node.
     * @return the next node
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Mutator method sets the next node.
     * @param next		The node following this node.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * String representation of the node as follows:
     * data
     */
    public String toString()
    {
        String result = "";
        result += this.data;
        return result;
    }
}
