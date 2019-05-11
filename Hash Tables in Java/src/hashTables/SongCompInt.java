//Author: sumorin

package hashTables;

import cs1c.SongEntry;


/**
 * a wrapper class that gives us the ability to compare a SongEntry object with an Integer ID
 */
public class SongCompInt implements Comparable<Integer>{
    protected int id;
    protected SongEntry data;

    /**
     * default constructor
     */
    public SongCompInt() {
        //initialize to default variables to avoid unpredictable variable behavior
        id = 0;
        data = null;
    }

    /**
     * overloaded constructor
     * @param songObj takes in a song object that is paired to an Integer key for find() operation
     * @param key an Integer that represents the ID that the object will be paired with
     */
    public SongCompInt(SongEntry songObj, int key){
        this();
        id = key;
        data = songObj;
    }

    @Override
    /**
     * This method returns the value 0 if this Integer is equal to the argument Integer,
     * a value less than 0 if this Integer is numerically less than the argument Integer and a
     * value greater than 0 if this Integer is numerically greater than the argument Integer.
     * @param Integer value representing the ID
     * @return 0 if there is a match, otherwise, returns -1, or 1
     */
    public int compareTo(Integer key) {
        //compares int to value of key as an int using the Integer class method intValue()
        return Integer.compare(id, key.intValue()); // we only care if it is 0 - a match !
//        return id - key;
    }

    @Override
    /**
     * returns the key that will be the unique hashcode for the song object
     */
    public int hashCode() {
        return id;
    }


    /**
     * compares two objects of same type - SongEntry using the built in equals() from Object class
     * @param rhs a SongCompInt object that has SongEntry member
     * @return  true if this object is the same as the obj argument; false otherwise.
     */
    public boolean equals(SongCompInt rhs) {
        // let equals() preserve the equals() provided by embedded data
         return data.equals(rhs.data);
    }

    @Override
    /**
     * Outputs a String representation of the class members
     */
    public String toString() {
        return data.toString();
    }
}
