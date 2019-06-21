package Logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Album {
    private ArrayList<Song> albumSongs;
    private BufferedImage albumImage;
    public Album(){
        albumSongs=new ArrayList<>();
    }
    public void removeSong(Song song){
        albumSongs.remove(albumSongs.indexOf(song));
    }
    public void addSong(Song song){
        albumSongs.add(song);
    }
    public void setAlbumImage(BufferedImage image){
        this.albumImage=image;
    }

    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }

    public BufferedImage getAlbumImage() {
        return albumImage;
    }
}
