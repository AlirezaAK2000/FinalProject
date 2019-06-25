package Music;

import Logic.Song;
import Musicbox.MusicBox;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//*******************************************************************
public class PlaybListener implements ActionListener {
    private int a = 0;
    private Song song;
    private MusicBox musicBox;

    public PlaybListener(Song song, MusicBox musicBox) {
        this.song = song;
        this.musicBox = musicBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (musicBox.getSongPanel().getSong() == song) {

            if (a % 2 == 1) {
                ++a;
                try {
                    musicBox.getSlider().setMaximum(song.getSize());
                    musicBox.setMove(true);
                    song.continuee();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedTagException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvalidDataException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    musicBox.getSlider().setMaximum(song.getSize());
                    a++;
                    musicBox.setMove(false);
                    song.pause();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InvalidDataException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedTagException e1) {
                    e1.printStackTrace();
                }


            }
        }
    }
}
