package hashTables;

import cs1c.SongEntry;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class will create and populate two hash tables of type FHhashQPwFind class, one for each wrapper class
 */
public class TableGenerator {
    protected FHhashQPwFind<Integer,SongCompInt> tableOfSongIDs;
    protected FHhashQPwFind<String,SongsCompGenre> tableOfSongGenres;
    protected ArrayList<String> genreNames;

    /**
     * populates hash table of type FHhashQPwFind class for wrapper class SongCompInt
     * @param allSongs array of SongEntry objects holding the IDs
     * @return generated ID table
     */
    public FHhashQPwFind<Integer,SongCompInt> populateIDtable(SongEntry[] allSongs) {
      tableOfSongIDs = new FHhashQPwFind<>(allSongs.length); //allocates array
      for(int i = 0; i < allSongs.length; i++){
          //creates an object that allows find-on-key mechanism using song ID
          SongCompInt songwID = new SongCompInt(allSongs[i], allSongs[i].getID()); //each ID is unique
          tableOfSongIDs.insert(songwID);
      }
        return tableOfSongIDs;
    }

    /**
     * populates hash table of type FHhashQPwFind class for wrapper class SongsCompGenre
     * @param allSongs
     * @return
     */
    public FHhashQPwFind<String,SongsCompGenre> populateGenreTable(SongEntry[] allSongs) {
        tableOfSongGenres = new FHhashQPwFind<>(allSongs.length);
        genreNames = new ArrayList<>();

        for(int i = 0; i < allSongs.length; i++){
            //creates an object that allows find-on-key mechanism using song genre
            try{
                SongsCompGenre result = tableOfSongGenres.find(allSongs[i].getGenre());//each ID not unique so we have to filter
                result.addSong(allSongs[i]);
            }catch(NoSuchElementException e){
                //only create SongCompGenre obj if genre is not in tableOfSongGenre
                SongsCompGenre songwGenre = new SongsCompGenre(allSongs[i], allSongs[i].getGenre());
                tableOfSongGenres.insert(songwGenre);
                genreNames.add(songwGenre.getGenreName());
            }
        }
        return tableOfSongGenres;
    }

    /**
     * accessor method
     * @return
     */
    public ArrayList<String> getGenreNames() {
        return genreNames;
    }
}
