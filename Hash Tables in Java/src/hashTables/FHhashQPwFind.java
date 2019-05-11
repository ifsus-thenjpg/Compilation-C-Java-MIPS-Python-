package hashTables;

import java.util.NoSuchElementException;

/**
 * A generic hash class that allows for find-on-key mechanism
 * a FHHashQPwFind object will take in a key of type String or Integer and return the SongEntry object that matches
 * @param <KeyType>
 * @param <E>
 */
public class FHhashQPwFind<KeyType, E extends Comparable<KeyType> >
        extends FHhashQP<E> {
    static final int MATCH = 0;

    public FHhashQPwFind(int tableSize) {
        super(tableSize); // superclass constructor with matching parameter is called - FHhashQp(int tableSize)
    }

    /**
     * method takes in a key field (Integer or String) and finds an object matching that key field or throw a NoSuchElementException
     *
     * @param key key value to search for
     * @return returns entire object with the key value we searched for
     */
    public E find(KeyType key) throws NoSuchElementException {
        //call on the findPosKey method to get index with key, if found
        int index = findPosKey(key);

        //throw exception because the index given does not hold anything and we cannot continue with QP
        if (mArray[index].state == EMPTY) {
            throw new NoSuchElementException();
        }

        return mArray[index].data;
    }

    /**
     * A hash function that takes in a key into an array index
     *
     * @param key takes in a key of type int or String
     * @return a hash value assigned to that key that will guarantee to fit in the table
     */
    protected int myHashKey(KeyType key) {
        int hashVal;

        hashVal = key.hashCode() % mTableSize;//makes sure it will fit in the table
        if (hashVal < 0) //because with mod operator it is possible to have neg number
            hashVal += mTableSize;

        return hashVal;
    }

    /**
     * Method that takes in a key and returns an index for the position in the table
     *
     * @param key takes in a String or Integer key
     * @return the index in the table array that holds the object that matches the key
     */
    protected int findPosKey(KeyType key) {
        int kthOddNum = 1;
        int index = myHashKey(key);
        // keep probing until we get an empty position or match
            while (mArray[index].state != EMPTY
                    && mArray[index].data.compareTo(key) != MATCH) {

                //now we can do the QP probing
                index += kthOddNum; // k squared = (k-1) squared + kth odd #
                kthOddNum += 2;     // compute next odd #
                if (index >= mTableSize) //we have reached outside of the table
                    index -= mTableSize; //lets go back to the beginning of the table and start QP from there
            }
            return index;
        }
}