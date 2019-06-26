package Tools;

import Center.Center;
import Music.SongPanel;
import Music.SongPanels;
import Musicbox.MusicBox;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BigPanel extends JPanel {
    private ProButton nameButton;
    private String name;
    private SongPanels songs;
    private Background background;
    private Center center;
    public BigPanel(BufferedImage originalImage, SongPanel song , MusicBox musicBox ,Center center) throws IOException, InvalidDataException, UnsupportedTagException {
        background = new Background(originalImage);
        this.center = center;
        this.setPreferredSize(new Dimension(200 , 200));
        background.setPreferredSize(new Dimension(150 , 150));
        this.songs = new SongPanels("backgrouns\\center8.png" , musicBox , "none");
        songs.addSong(song);
        name = song.getSong().getAlbum();
        nameButton = new ProButton(name);

        nameButton.setPreferredSize(new Dimension(150 , 30));
        nameButton.setFont(new Font("serif" ,  Font.PLAIN , 30));
        nameButton.setForeground(Color.white);
        nameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                center.setMain(songs);
                songs.repaintList();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(new Font("serif" ,  Font.BOLD , 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(new Font("serif" ,  Font.PLAIN , 30));
            }
        });
        this.setLayout(new BorderLayout());
        this.add(Box.createHorizontalStrut(5) , BorderLayout.WEST);
        this.add(Box.createHorizontalStrut(5) , BorderLayout.EAST);
        this.add(Box.createVerticalStrut(5) , BorderLayout.NORTH);
        this.add(background , BorderLayout.CENTER);
        this.add(nameButton , BorderLayout.SOUTH);
        this.setBackground(Color.BLACK);
    }
    public void addSong(SongPanel songPanel) throws InvalidDataException, IOException, UnsupportedTagException {
        songs.addSong(songPanel);
    }

    public SongPanels getSongs() {
        return songs;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    @Override
    public String getName() {
        return name;
    }
}
