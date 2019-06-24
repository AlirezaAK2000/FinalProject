package Music;

import Boxoffice.Boxoffice;
import Logic.Song;
import Tools.Background;
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
    private boolean hasNextListener=false;
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

        addtoPlaylist = new MenuItem("Add to playlist");
        addtoShareList = new MenuItem("Add to sharelist");
        remove=  new MenuItem("Remove");

        adder = new PopupMenu();
        adder.add(addtoPlaylist);
        adder.add(addtoShareList);
        adder.add(remove);
        this.add(adder);

        SongPanel b = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.isMetaDown())
                    adder.show(b , getParent().getX() , getParent().getY());
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
    public boolean getHasNextListener(){return hasNextListener;}
    public void setHasNextListener(boolean flag){hasNextListener=flag;}

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
}
