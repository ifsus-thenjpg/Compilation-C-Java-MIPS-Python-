package hashTables;


import cs1c.SongEntry;

import java.util.ArrayList;

/**
 * A class that gives us the ability to compare a SongEntry object with a String genre
 */
public class SongsCompGenre implements Comparable<String>{
    protected String genreName;
    protected ArrayList<SongEntry> data;

    /**
     * default constructor that initializes the attributes to default values
     */
    public SongsCompGenre() {
        genreName = null;
        data = new ArrayList<SongEntry>(); //contains all songs in the genre
    }

    /**
     * overloaded constructor
     * @param songObj song object that will be matched to the key also being passed in for find() operation
     * @param key genre key that will be used to search for matching object
     */
    public SongsCompGenre(SongEntry songObj, String key){
        this();
        genreName = key;
        addSong(songObj);
    }

    /**
     * accessor method
     * @return name of genre
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * accessor method
     * @return an array list of songEntry objects that all share same genre
     */
    public ArrayList<SongEntry> getData() {
        return data;
    }

    /**
     * adds a song to the list of songs
     * @param e song to add
     */
    public void addSong(SongEntry e){
        data.add(e);
    }

    @Override
    /**
     * If first string is lexicographically greater than second string, it returns positive number
     * (difference of character value). If first string is less than second string lexicographically,
     * it returns negative number and if first string is lexicographically equal to second string, it returns 0.
     * @param String value representing the genre
     * @return 0 if there is a match, otherwise, returns -1, or 1
     */
    public int compareTo(String key) {
        return genreName.compareTo(key); //uses the String class compareTo()
    }

    @Override
    /**
     * generates a hashcode by calling on the String class hashCode() that returns int hash code for string
     */
    public int hashCode() {
        return genreName.hashCode();
    }

//    @Override
    // let equals() preserve the equals() provided by embedded data
    public boolean equals(SongsCompGenre rhs) {
        return data.equals(rhs.data);
    }

    @Override
    /**
     * returns String representation of attributes
     */
    public String toString() {
        return genreName;
    }
}
