project folder: ifsus-thenjpg-cs1c-project06/

Brief description of submitted files:

**src/cs1c/MillionSongDataSubset.java**
- One object of class MillionSongDataSubset parses a JSON data set and stores each entry in an array.

**src/cs1c/SongEntry.java**
- One object of class SongEntry stores a simplified version of the genre data set from the Million Song Dataset.

**src/cs1c/TimeConverter.java**
- Converts duration into a string representation.

**src/hashTables/FHhasQP.java**
- FHHashQP object creates a hash table and uses quadratic probing for collision resolution 

**src/hashTables/FHhashQPwFind.java**
- Hash table that extends from FHhashQP. FhhashQPwFind object uses a find-on-key mechanism
  that takes in a KeyType (ie, String or Integer) and searches for the object matching 
  that key. 

**src/hashTables/HashEntry.java**
- HashEntry object lets the hash table know whether an element in array is occupied
  HashEntry acts as underlying data type for the array used in FHhashQP and derived class

**src/hashTables/MyTunes.java**
- Tests the functionality of FHhashQPwFind.java.
  Specifically checks for implementation of find() function to return an object 
  associated with a given key input.

**src/hashTables/SongCompInt.java**  
- SongCompInt object gives us the ability to compare a SongEntry object with an Integer ID

**src/hashTables/SongCompGenre.java** 
- SongCompGenre gives us the ability to compare a SongEntry object with an genre name 

**src/hashTables/TableGenerator.java** 
- TableGenerator object will create and populate two hash tables of type FHhashQPwFind class, one for each wrapper class
 
**RUN.txt**
- console output of MyTunes.java

**README.md**
- description of submitted files