//Author:sumorin

package stacks;

import java.util.*;
import java.util.NoSuchElementException;

/**
 * Keeps track of the current link, links previous to the current link, and links seen after the current link
 * @param <AnyType>
 */
public class StackList<AnyType> implements Iterable {

    private String name; //the name of this instance (for testing and debugging)
    private Node top; //points to the top of the stack
    private int mSize;


    /**
     * constructor sets the member attributes
     * @param name holds the name of the stack (to be used by toString())
     */
    public StackList(String name) {
        this.name = name;
        clear();
    }
    /**
     * accessor method
     * @return number of elements in the stack.
     */
    public int size(){
        return mSize;
    }

    /**
     * removes all the elements from the list
     */
    public void clear(){
        mSize = 0;
        this.top = null;
    }

    /**
     * prints the name of the stack passed in by the Navigator class in addition to the number of links in the stack
     */
    @Override
    public String toString(){
        Iterator<AnyType> iter = iterator();
        String result = "";
        while(iter.hasNext()){
            result += iter.next() + ", ";
        }
        return this.name + " has " + this.mSize + " link(s): \n[" + result + "]";
    }

    /**
     * Everytime a user wants to move to the beginning of the list, StackList return a new iterator pointing
     * to beginning of list
     * @return
     */
    @Override
    public Iterator<AnyType> iterator() {
        return new StackIterator();
    }

    // required stack operations ---------------------------------------------------------------------
    /**
     * Pushes an element onto the stack represented by this list.
     * @param x
     */
    public void push(AnyType x)
    {
            Node newNode = new Node(x);
            newNode.next = this.top;
            this.top = newNode;
            mSize++;
    }

    /**
     * Pops an element from the stack represented by this list.
     * @return the generic item popped.
     */
    public AnyType pop()
    {
        if (isEmpty()){
            throw new EmptyStackException(); //in case pop() gets called in method besides goBack() and goForward()
        }
        AnyType retValue = this.top.data;
        this.top = top.next;
        mSize--;
        return retValue;
    }

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     * @return  If the stack is empty, returns null. Otherwise, returns a generic type for the data seen at top of stack
     */
    public AnyType peek()
    {
        if (isEmpty()){
            return null;
        }else{
            AnyType retValue = this.top.data;
            return retValue;
        }
    }

    /**
     * checks if the top of the stack is pointing to anything.
     * @return true if top has not been initialized (is empty), false otherwise.
     */
    public boolean isEmpty(){
        if(mSize == 0){
            return true;
        }else{
            return false;
        }
    }

    //inner class
    // This class is only used in LinkedList, so it is private.
    // This class does not need to access any instance members of LinkedList, so it is defined static.
    private class Node{
        Node next;
        AnyType data;

        /**
         * Constructor
         * @param element takes in generic element as a parameter to be assigned as data
         */
        Node( AnyType element )
        {
            this.data = element;
            next = null;
        }
    }

    // required interface implementations ----------------------------------------------------------------
    /**
     * traverses a list similar like a "cursor" that can be placed before one element of a data structure at given time
     */
    private class StackIterator implements Iterator<AnyType>{

        protected Node mCurrentNode;

        /**
         * Constructors (default access for package only)
         */
        StackIterator()
        {
            mCurrentNode = top; //iterator is instantiated at start(far-left) of the list
        }

        /**
         * @return  returns true if there is something in the list to the immediate right of the iterator.
         * If there is no item there, meaning it is at the end of the list, it returns false.
         */
        public boolean hasNext() {
            if (mCurrentNode == null) {
                return false;
            } else {
                return true;
            }
        }

        /**
         *
         * @return  returns the item to the immediate right of the iterator, and moves the iterator up one, i.e.,
         * past the item just returned.
         */
        public AnyType next()
        {
            if(!hasNext()){
                throw new NoSuchElementException();
            }else{
              AnyType retVal = mCurrentNode.data;
              mCurrentNode = mCurrentNode.next;
              return retVal;
            }
        }

    }

}

