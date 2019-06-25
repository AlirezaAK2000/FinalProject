package Music;

import Logic.Song;
import Musicbox.MusicBox;
import Tools.Integer;
import Tools.ProSlider;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

//******************************************************************
public class SliderListener extends MouseAdapter {

    private ProSlider slider;
    private MusicBox musicBox;
    private Song song;
    private Integer position;

    public SliderListener(ProSlider slider, MusicBox musicBox, Song song) throws UnsupportedTagException, InvalidDataException, IOException {
        this.slider = slider;
        this.musicBox = musicBox;
        this.song = song;
        slider.setMaximum(song.getSize());
        slider.setValue(0);

        this.position = slider.getPosition();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (musicBox.getSongPanel().getSong() == song) {
            Point p = e.getPoint();
            double percent = p.x / ((double) slider.getWidth());
            int range = slider.getMaximum() - slider.getMinimum();
            double newVal = range * percent;
            int result = (int) (slider.getMinimum() + newVal);
            slider.setValue(result);
            position.setValue(result);
            try {
                song.play(result*1000);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (JavaLayerException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (musicBox.getSongPanel().getSong() == song) {
            try {
                song.play(position.getValue()*1000);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (JavaLayerException e1) {
                e1.printStackTrace();
            }
        }
    }
}
