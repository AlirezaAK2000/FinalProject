package Boxoffice;

import Musicbox.MusicBox;
import Tools.Background;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextClickListener implements ActionListener {
    private MusicBox musicBox;
    private Background artWork;

    public NextClickListener(MusicBox musicBox, Background artWork) {
        this.musicBox = musicBox;
        this.artWork = artWork;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(musicBox.getSongPanel()!=null){
        try {
            Robot r=new Robot();
            r.delay(100);
            artWork.SetBack(musicBox.getSongPanel().getSong().getArtWork().getImage());
            musicBox.getTitleLabel().setText(musicBox.getSongPanel().getSong().getTitle());
            try {
                musicBox.getArtist().setText(musicBox.getSongPanel().getSong().getArtist());
            }catch (NullPointerException e3){
            }


        } catch (AWTException e1) {
            e1.printStackTrace();
        }catch (NullPointerException e1){
        }
        }


    }
}
