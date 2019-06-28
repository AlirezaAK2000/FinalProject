package Music;

import Boxoffice.Boxoffice;
import Logic.Song;
import Tools.Background;
import Tools.BigPanel;
import Tools.BigPanelContainer;
import Tools.ProButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private  boolean hasPlayListener;
    private Boxoffice boxoffice;
    private MenuItem addtoPlaylist;
    private MenuItem remove;
    private MenuItem addtoShareList;
    private PopupMenu adder;
    private boolean addedToRecently = false;
    private boolean hasSliderListener=false;
    private BigPanel albumPanel;
    private JPanel setting;
    private ProButton box;
    private SearchList searchList;

    public SongPanel(Song song){
        super();
        this.boxoffice = boxoffice;
        this.song = song;
        hasPlayListener=false;
        this.setBackground(Color.BLACK);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE , 30));

        title = new JLabel(song.getTitle());
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        title.setFont(new Font("serif" , Font.BOLD ,15));
        title.setBorder(BorderFactory.createEmptyBorder());

        artist = new JLabel(song.getAlbum());
        artist.setBackground(Color.BLACK);
        artist.setForeground(Color.white);
        artist.setFont(new Font("serif" , Font.BOLD ,15));
        artist.setBorder(BorderFactory.createEmptyBorder());

        album = new JLabel(song.getArtist());
        album.setBackground(Color.BLACK);
        album.setForeground(Color.white);
        album.setFont(new Font("serif" , Font.BOLD ,15));
        album.setBorder(BorderFactory.createEmptyBorder());

        liked = new ImageIcon("Icons\\unlike2.png");
        unliked = new ImageIcon("Icons\\like1.png");
        liker = new ProButton(unliked);
        liker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(liker.getIcon().equals(liked))
                    liker.setIcon(unliked);
                else
                    liker.setIcon(liked);
            }
        });
//        liker.setPreferredSize(new Dimension(28,28));

        JPanel auxPanel = new JPanel();
        auxPanel.setBackground(Color.BLACK);
        auxPanel.setLayout(new FlowLayout());
        auxPanel.add(liker , FlowLayout.LEFT);


        box = new ProButton(".");
        box.setBackground(Color.BLACK);
        box.setForeground(Color.white);
        box.setFont(new Font("serif" , Font.BOLD ,30));
        box.setBorder(BorderFactory.createEmptyBorder());

        setting = new JPanel();
        setting.setBackground(Color.BLACK);
        setting.setLayout(new GridLayout(1 , 2 , 1 ,1));
        setting.add(box);
        setting.add(liker);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE , 30));
        this.setLayout(new GridLayout(1 , 4 , 10 ,10));
        this.add(setting);
        this.add(title);
        this.add(album);
        this.add(artist);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY , 3));

        addtoPlaylist = new MenuItem("Add to playlist");
        addtoShareList = new MenuItem("Add to sharelist");
        remove=  new MenuItem("Remove");

        adder = new PopupMenu();
        adder.add(addtoPlaylist);
        adder.add(addtoShareList);
        adder.add(remove);
        this.add(adder);

        SongPanel b = this;
        box.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    adder.show(b , getParent().getParent().getX() , getParent().getParent().getY());

            }
        });


    }


    public PopupMenu getAdder() {
        return adder;
    }

    public void setArtistText(String artistText) {
        artist.setText(artistText);
    }
    public boolean isAddedToRecently() {
        return addedToRecently;
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

    public void setAlbumPanel(BigPanel albumPanel) {
        this.albumPanel = albumPanel;
    }
    public BigPanel getAlbumPanel() {
        return albumPanel;
    }
    public ProButton getLiker() {
        return liker;
    }
    public ImageIcon getLiked() {
        return liked;
    }
    public ImageIcon getUnliked() {
        return unliked;
    }
    public boolean getHasPlayListener(){return hasPlayListener;}
    public void setHasPlayListener(boolean flag){hasPlayListener=flag;}
    public boolean getHasSliderListener(){return hasSliderListener;}
    public void setHasSliderListener(boolean flag){hasSliderListener=flag;}

    public void setAddedToRecently(boolean addedToRecently) {
        this.addedToRecently = addedToRecently;
    }
    public Boxoffice getBoxoffice() {
        return boxoffice;
    }

    public MenuItem getAddtoPlaylist() {
        return addtoPlaylist;
    }

    public MenuItem getAddtoShareList() {
        return addtoShareList;
    }

    public MenuItem getRemove() {
        return remove;
    }


    public boolean equals(SongPanel songPanel) {
        SongPanel s = songPanel;
        if(s.getSong().getTitle().equals(this.getSong().getTitle())||s.getSong().equals(song))
            return true;
        return false;
    }

    public boolean sameAlbum(SongPanel songPanel){
        if (songPanel.getSong().getAlbum().toLowerCase().equals(song.getAlbum().toLowerCase()))
            return true;
        return false;
    }

    public boolean sameArtist(SongPanel songPanel){
        if (songPanel.getSong().getArtist().toLowerCase().equals(song.getArtist().toLowerCase()))
            return true;
        return false;
    }

    public void setSearchList(SearchList searchList) {
        this.searchList = searchList;
    }
}
