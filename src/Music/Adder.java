package Music;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public interface Adder {
    public void addSong(SongPanel songPanel) throws InvalidDataException, IOException, UnsupportedTagException;
}
