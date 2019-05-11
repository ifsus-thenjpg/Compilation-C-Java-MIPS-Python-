package queues;

import cs1c.SongEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The class Jukebox manages three objects of type Queue.
 * An instance of the class may read a file which includes the user's requests for a
 * the name of a song to be added to a specific playlist. It will then add songs
 * to the three playlists "favorites", "lounge", and "road trip" accordingly.
 */
public class Jukebox {

    //attributes
    String songNotFound = "";
    Queue<SongEntry> favoritePL;
    Queue<SongEntry> roadTripPL;
    Queue<SongEntry> loungePL;

    /**
     * constructor
     */
    public Jukebox() {
        this.favoritePL = new Queue<SongEntry>("favorites");
        this.roadTripPL = new Queue<SongEntry>("road trip");
        this.loungePL = new Queue<SongEntry>("lounge");
    }

    /**
     * accessor method
     * @return
     */
    public Queue<SongEntry> getFavoritePL() {
        return favoritePL;
    }

    /**
     * accessor method
     * @return
     */
    public Queue<SongEntry> getLoungePL() {
        return loungePL;
    }

    /**
     * accessor method
     * @return
     */
    public Queue<SongEntry> getRoadTripPL() {
        return roadTripPL;
    }

    /**
     * This method reads the test file and then adds songs to one of the three queues
     * Then the first song found that equals the title will be placed in the favorites playlist.
     * @param requestFile takes in a string onject with the title of the playlist to add to
     * @param allSongs takes in an array of songs
     */
    public void fillPlaylists(String requestFile, SongEntry[] allSongs) {

        String songName, playlistName;
        String line = "";
        File infile = new File(requestFile);
        String[] tokens = null;

        try {
            Scanner scanner = new Scanner((infile));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                tokens = line.split(",");

                playlistName = tokens[0];
                songName = tokens[1];

                SongEntry foundMatch = findFirstOccurrence(songName, allSongs);

                if(foundMatch != null){
                    switch(playlistName){
                        case "favorites":
                            this.favoritePL.enqueue(foundMatch);
                            break;
                        case "road trip":
                            this.roadTripPL.enqueue(foundMatch);
                            break;
                        case "lounge":
                            this.loungePL.enqueue(foundMatch);
                            break;
                    }
                }
                else{
                    System.out.println("\n[PLAYLIST NOT FOUND]: " + playlistName.toUpperCase()
                    + " - CANNOT ADD \"" + songName.toUpperCase() + "\"\n");
                }


            }

            scanner.close(); //close the file
        } catch (FileNotFoundException e) {
            System.out.println("File " + requestFile + " not found!");
        }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("\n[SONG(S) NOT FOUND]: \"" + songNotFound.toUpperCase() + "\" \n");
        }
    }

    /**
     * helper method will find first occurrence of song title of interest
     * @param songToMatch holds the song that we want to search
     * @param masterList the master list that we will search in and compare each object title with the song of interest
     * @return SongEntry object that matched to the song of interest
     */
    public SongEntry findFirstOccurrence(String songToMatch, SongEntry[] masterList){
        SongEntry found = null;
        for(int i = 0; i < masterList.length; i++) {
            if(songToMatch.equals(masterList[i].getTitle())) {
                found =  masterList[i];
                break; //after found first occurrence break from the loop
            }
        }
        if(found == null){
            songNotFound += songToMatch + ", ";
        }
        return found;
    }
}
