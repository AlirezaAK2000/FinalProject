package Tools;

import Logic.Song;

import javax.swing.*;
import java.awt.*;

public class SongPanel extends JPanel {
    private JLabel title;
    private JLabel artist;
    private JLabel album;
    private ProButton liker;
    private ImageIcon liked;
    private ImageIcon unliked;
    private Song song;


    public SongPanel(){
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

        liked = new ImageIcon("Icons\\unliked.png");
        unliked = new ImageIcon("Icons\\liked.png");
        liker = new ProButton(unliked);
        liker.setPreferredSize(new Dimension(26,28));

        this.setLayout(new FlowLayout());
        this.add(liker , FlowLayout.LEFT);
        this.add(title , FlowLayout.CENTER);
        this.add(artist, FlowLayout.RIGHT);
        this.add(album , FlowLayout.LEFT , 1);

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
}
