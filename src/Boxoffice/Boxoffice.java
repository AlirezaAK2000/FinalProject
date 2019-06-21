package Boxoffice;

import Center.Center;
import Tools.ProButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Boxoffice extends JPanel {

    private PopupMenu popupMenu;
    private ProButton tools;
    private Font pubFont;
    private Font headFont;
    private ProButton home;
    private ProButton browse;
    private JLabel sep;
    private ProButton recently;
    private ProButton favorites;
    private ProButton albums;
    private ProButton artist;
    private ProButton stations;
    private ProButton videos;
    private ProButton podcast;
    private ProButton addPlaylist;
    private ProButton sharedPlaylist;
    private JLabel playListLabel;
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private ArrayList<ProButton> playlists;
    private Boxoffice b;
    private JList<ProButton> playList;
    private DefaultListModel<ProButton> playListModel;
    private int count;
    public Boxoffice() throws IOException {
        super();
        b= this;
        playlists = new ArrayList<>();
        this.setBackground(Color.darkGray);
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        tools = new ProButton("...");
        tools.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setFont( new Font("serif" , Font.BOLD , 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setFont(new Font("serif" , Font.PLAIN , 30));
            }
        });
        tools.setFont(new Font("serif" , Font.PLAIN , 30));
        tools.setForeground(Color.white);
        headFont = new Font("serif" , Font.BOLD , 30);
        pubFont = new Font("serif " , Font.PLAIN , 15);

        MenuItem file = new MenuItem("File");
        MenuItem edit = new MenuItem("Edit");
        MenuItem view = new MenuItem("View");
        MenuItem playBack = new MenuItem("Playback");
        MenuItem help = new MenuItem("Help");
        popupMenu = new PopupMenu("tools");
        popupMenu.add(file);
        popupMenu.add(edit);
        popupMenu.add(view);
        popupMenu.add(playBack);
        popupMenu.add(help);
        tools.add(popupMenu);


        this.add(tools , BorderLayout.NORTH);



        home = new ProButton("Home");
        home.setFont(pubFont);
        home.setForeground(Color.white);
        home.setBackground(Color.darkGray);
        home.addMouseListener(new Bolder());
        this.add(home);
        browse = new ProButton("Browse");
        browse.setForeground(Color.white);
        browse.setBackground(Color.darkGray);
        browse.setFont(pubFont);
        browse.addMouseListener(new Bolder());
        this.add(browse);
        sep = new JLabel("Library");
        sep.setFont(headFont);
        sep.setForeground(Color.white);
        sep.setBackground(Color.DARK_GRAY);
        this.add(sep);
        recently = new ProButton("Recently");
        recently.setFont(pubFont);
        recently.setForeground(Color.white);
        recently.setBackground(Color.darkGray);
        recently.addMouseListener(new Bolder());
        this.add(recently);
        favorites = new ProButton("Favorites");
        favorites.setFont(pubFont);
        favorites.setForeground(Color.white);
        favorites.setBackground(Color.darkGray);
        favorites.addMouseListener(new Bolder());
        this.add(favorites);
        sharedPlaylist = new ProButton("Shared PlayList");
        sharedPlaylist.setFont(pubFont);
        sharedPlaylist.setForeground(Color.white);
        sharedPlaylist.setBackground(Color.darkGray);
        sharedPlaylist.addMouseListener(new Bolder());
        this.add(sharedPlaylist);
        albums = new ProButton("Album");
        albums.setFont(pubFont);
        albums.setForeground(Color.white);
        albums.setBackground(Color.darkGray);
        albums.addMouseListener(new Bolder());
        this.add(albums);
        artist = new ProButton("Artist");
        artist.setFont(pubFont);
        artist.setForeground(Color.white);
        artist.setBackground(Color.darkGray);
        artist.addMouseListener(new Bolder());
        this.add(artist);
        stations = new ProButton("Stations");
        stations.setFont(pubFont);
        stations.setForeground(Color.white);
        stations.setBackground(Color.darkGray);
        stations.addMouseListener(new Bolder());
        this.add(stations);
        videos = new ProButton("Videos");
        videos.setFont(pubFont);
        videos.setForeground(Color.white);
        videos.setBackground(Color.darkGray);
        videos.addMouseListener(new Bolder());
        this.add(videos);
        podcast = new ProButton("Podcasts");
        podcast.setFont(pubFont);
        podcast.setForeground(Color.white);
        podcast.setBackground(Color.darkGray);
        podcast.addMouseListener(new Bolder());
        this.add(podcast);
        playListLabel = new JLabel("Playlist");
        playListLabel.setFont(headFont);
        playListLabel.setBackground(Color.DARK_GRAY);
        playListLabel.setForeground(Color.white);
        addPlaylist = new ProButton("+Add Playlist");
        addPlaylist.setFont(pubFont);
        addPlaylist.setBackground(Color.darkGray);
        addPlaylist.setForeground(Color.white);
        addPlaylist.addMouseListener(new Bolder());
        addPlaylist.addActionListener(new AddingPlaylist());
        this.add(addPlaylist);

        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.setLayout(new BoxLayout(this ,BoxLayout.Y_AXIS));


        originalImage = ImageIO.read(new File("backgrounds\\left2.jpg"));
        tools.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show( tools,tools.getX(),tools.getY());
            }
        });


    }
    private class Bolder extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setFont(new Font( "serif",Font.BOLD , 15));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setFont(new Font("serif " , Font.PLAIN , 15));
        }
    }


    public void paintComponent(Graphics g) {
        double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
        double heightScaleFactor = getHeight() / (double)originalImage.getHeight();

        AffineTransform at = new AffineTransform();
        at.scale(widthScaleFactor, heightScaleFactor );

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        scaledImage = scaleOp.filter(originalImage, null);


        g.drawImage(scaledImage, 0, 0,null);
        addComponentListener(new ResizerListener());
    }


    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
            double heightScaleFactor = getHeight() / (double)originalImage.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(widthScaleFactor, heightScaleFactor );

            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaledImage = scaleOp.filter(originalImage, null);

            repaint();
        }


    }

    private class AddingPlaylist implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            PlayListAdder playListAdder = new PlayListAdder(b);

        }
    }
    public void addPlaylist(String name){
        ProButton playlist = new ProButton(name);
        playlist.setFont(pubFont);
        playlist.setBackground(Color.darkGray);
        playlist.setForeground(Color.white);
        playlist.addMouseListener(new Bolder());
        playlists.add(playlist);
        this.add(playlist);
        this.revalidate();
        this.repaint();
    }


}