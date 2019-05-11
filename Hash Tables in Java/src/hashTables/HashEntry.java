package hashTables;


// HashEntry class for use by FHhashQP -----------------------

/**
 * HashEntry object lets the hash table know whether an element in array is occupied
 * HashEntry acts as underlying data type for the array used in FHhashQP and derived class
 * @param <E>
 */
public class HashEntry<E>
{
    public E data;
    public int state;

    /**
     * overloaded constructor
      * @param x takes in an object of generic type
     * @param st takes in an integer representing the state of the element inside the array that makes up the hash table
     */
    public HashEntry( E x, int st )
    {
        data = x;
        state = st;
    }

    /**
     * default constructor
     */
    public HashEntry()
    {
        this(null, FHhashQP.EMPTY);
    }
}