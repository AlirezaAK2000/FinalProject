package Music;

import Boxoffice.Boxoffice;
import Logic.Song;
import Tools.Background;
import Tools.ProButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.Serializable;

public class SongPanel extends JPanel implements Serializable {
    private JLabel title;
    private JLabel artist;
    private JLabel album;
    private ProButton liker;
    private ImageIcon liked;
    private ImageIcon unliked;
    private Song song;
    private Boxoffice boxoffice;

    public SongPanel(Song song){
        super();
        this.boxoffice = boxoffice;
        this.song = song;
        this.setBackground(Color.BLACK);
        System.out.println(song.getTitle());
        System.out.println(song.getTitle().length());
        title = new JLabel(song.getTitle());
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        title.setFont(new Font("serif" , Font.BOLD ,15));
        title.setBorder(BorderFactory.createEmptyBorder());

        artist = new JLabel(song.getArtist());
        artist.setBackground(Color.BLACK);
        artist.setForeground(Color.white);
        artist.setFont(new Font("serif" , Font.BOLD ,15));
        artist.setBorder(BorderFactory.createEmptyBorder());

        album = new JLabel(song.getAlbum());
        album.setBackground(Color.BLACK);
        album.setForeground(Color.white);
        album.setFont(new Font("serif" , Font.BOLD ,15));
        album.setBorder(BorderFactory.createEmptyBorder());

        liked = new ImageIcon("Icons\\unlike2.png");
        unliked = new ImageIcon("Icons\\like1.png");
        liker = new ProButton(unliked);
        liker.setPreferredSize(new Dimension(28,28));

        JPanel auxPanel = new JPanel();
        auxPanel.setBackground(Color.BLACK);
        auxPanel.setLayout(new FlowLayout());
        auxPanel.add(liker , FlowLayout.LEFT);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE , 30));
        this.setLayout(new GridLayout(1 , 4 , 10 ,10));
        this.add(liker);
        this.add(title);
        this.add(album);
        this.add(artist);

    }
    public SongPanel(String titleText , String artistText , String albumText)
    {
        super();
        this.setBackground(Color.BLACK);
        title = new JLabel();
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        title.setFont(new Font("serif" , Font.BOLD ,15));
        title.setBorder(BorderFactory.createEmptyBorder());
        artist = new JLabel();
        artist.setBackground(Color.BLACK);
        artist.setForeground(Color.white);
        artist.setFont(new Font("serif" , Font.BOLD ,15));
        artist.setBorder(BorderFactory.createEmptyBorder());
        album = new JLabel();
        album.setBackground(Color.BLACK);
        album.setForeground(Color.white);
        album.setFont(new Font("serif" , Font.BOLD ,15));
        album.setBorder(BorderFactory.createEmptyBorder());
        album.setText(albumText);
        title.setText(titleText);
        artist.setText(artistText);
    }

    public void setArtistText(String artistText) {
        artist.setText(artistText);
    }

    public void setAlbumText(String albumText) {
        album.setText(albumText);
    }

    public void setTitleText(String titleText) {
        title.setText(titleText);
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    public Boxoffice getBoxoffice() {
        return boxoffice;
    }
    public void paintComponent(Graphics g) {
        addComponentListener(new ResizerListener());
    }


    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            setPreferredSize(new Dimension(getParent().getWidth() , 30));
            repaint();
        }
    }
}
