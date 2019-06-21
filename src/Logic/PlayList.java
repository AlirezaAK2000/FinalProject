package Logic;

import java.util.ArrayList;

public class PlayList {
    private ArrayList<Song> playListSongs;

    public PlayList() {
        playListSongs=new ArrayList<>();
    }
    public void addSong(Song song){
        playListSongs.add(song);
    }
    public void removeSong(Song song){
        playListSongs.remove(playListSongs.indexOf(song));
    }

    public ArrayList<Song> getPlayListSongs() {
        return playListSongs;
    }
}
