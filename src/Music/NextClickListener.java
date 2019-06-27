package Music;

import Musicbox.MusicBox;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static Logic.Song.playTheread;

//********************************************************************
public class NextClickListener implements ActionListener {
    private MusicBox musicBox;
    private SongPanels songPanels;


    public NextClickListener(MusicBox musicBox, SongPanels songPanels) {
        this.musicBox = musicBox;
        this.songPanels = songPanels;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (songPanels == musicBox.getSongPanels()) {
            if (songPanels.getSongPanelList().size() != 0 && musicBox.getSongPanel()!=null) {
                int nextSong = songPanels.getSongPanelList().indexOf(musicBox.getSongPanel()) + 1;
                if (songPanels.getSongPanelList().size() - 1 != nextSong - 1) {
                    if (playTheread != null)
                        playTheread.stop();
                    musicBox.getSongPanel().setBackground(Color.BLACK);


                    musicBox.getSlider().setValue(0);
                    try {
                        musicBox.setSongPanel(songPanels.getSongPanelList().get(nextSong));
                    } catch (InvalidDataException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedTagException e1) {
                        e1.printStackTrace();
                    }


                    try {
                        musicBox.getSlider().setMaximum(musicBox.getSongPanel().getSong().getSize());
                        musicBox.getPlayb().setIcon(musicBox.getStopI());
                        musicBox.getSongPanel().getSong().play(0);
                        musicBox.getSongPanel().setBackground(new Color(0x308320));
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
                    if (playTheread != null)
                        playTheread.stop();
                    musicBox.getSongPanel().setBackground(Color.BLACK);
                    musicBox.getSlider().setValue(0);
                    try {
                        musicBox.setSongPanel(songPanels.getSongPanelList().get(0));
                    } catch (InvalidDataException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedTagException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        musicBox.getSlider().setMaximum(musicBox.getSongPanel().getSong().getSize());
                        musicBox.getPlayb().setIcon(musicBox.getStopI());
                        musicBox.getSongPanel().getSong().play(0);
                        musicBox.getSongPanel().getSong().continuee();
                        musicBox.getSongPanel().setBackground(new Color(0x308320));
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
                }
            }
        }
    }
}
