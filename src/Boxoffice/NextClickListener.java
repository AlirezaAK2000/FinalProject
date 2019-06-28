package Boxoffice;

import Musicbox.MusicBox;
import Tools.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
            try {
                artWork.SetBack(musicBox.getSongPanel().getSong().getArtWork().getImage());
            }catch (IllegalArgumentException e1){
                System.out.println("is there");
                artWork.SetBack(ImageIO.read(new File("backgrounds\\defaultartwork.jpg")));
            }
            musicBox.getTitleLabel().setText(musicBox.getSongPanel().getSong().getTitle());
            try {
                musicBox.getArtist().setText(musicBox.getSongPanel().getSong().getArtist());
            }catch (NullPointerException e3){
            }


        } catch (AWTException e1) {
            e1.printStackTrace();
        }catch (NullPointerException | IOException e1){
        }
        }


    }
}
