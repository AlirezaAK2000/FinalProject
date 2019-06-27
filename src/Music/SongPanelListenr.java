package Music;

import Musicbox.MusicBox;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static Logic.Song.playTheread;

public class SongPanelListenr extends MouseAdapter {

    private MusicBox myMusicBox;
    private SongPanel songPanel;
    private ArrayList<SongPanel> songPanelList;
    private SongPanels songPanels;


    public SongPanelListenr(MusicBox myMusicBox, SongPanel songPanel, ArrayList<SongPanel> songPanelList, SongPanels songPanels) {
        this.myMusicBox = myMusicBox;
        this.songPanel = songPanel;
        this.songPanelList = songPanelList;
        this.songPanels = songPanels;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (playTheread!= null) {
                playTheread.stop();
            }
            if(myMusicBox.getSongPanel()!=null)
                myMusicBox.getSongPanel().setBackground(Color.BLACK);
            myMusicBox.getSlider().setValue(0);
            myMusicBox.setMove(true);

            myMusicBox.setSongPanel(songPanel);
            songPanel.setBackground(new Color(0x308320));
            myMusicBox.getSlider().setMaximum(songPanel.getSong().getSize());

        } catch (UnsupportedTagException e1) {
            e1.printStackTrace();
        } catch (InvalidDataException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            myMusicBox.getPlayb().setIcon(myMusicBox.getStopI());
            songPanel.getSong().play(0);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (JavaLayerException e1) {
            e1.printStackTrace();
        }

    }


}
