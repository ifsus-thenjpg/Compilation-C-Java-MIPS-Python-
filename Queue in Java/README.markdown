**project folder: ifsus-thenjpg-cs1c-project04/**

*Brief description of submitted files:*

**src/cs1c/MillionSongDataSubset.java**
- One object of class MillionSongDataSubset parses a JSON data set and stores each entry in an array.

**src/cs1c/SongEntry.java**
- One object of class SongEntry stores a simplified version of the genre data set from the Million Song Dataset.

**src/cs1c/TimeConverter.java**
- Converts duration into a string representation.

**src/queues/Jukebox.java**
- The class Jukebox manages three objects of type Queue. An instance of the class may read a file which 
  includes the user's requests for a the name of a song to be added to a specific playlist. It will then 
  add songs to the three playlists "favorites", "lounge", and "road trip" accordingly.

**src/queues/MyTunes.java**
- Creates an object of type MyTunes which simulates a playlist queue.
   Enqueues and dequeues SongEntry objects from each playlist. 
   Simulates playing each song and finally checks the state of each playlist.

**src/queues/Queue.java**
- Objects of type Queue manage items in a singly linked list where we can enqueue() 
  items to the end and dequeue() items from the front of the queue.

**RUN.txt**
- console output of MyTunes.java

Additional Cases:
- Song_Not_Found.txt - tests for song not found 
- Playlist_Not_Found.txt - tests for playlist not found 


**README.markdown**
- description of submitted files