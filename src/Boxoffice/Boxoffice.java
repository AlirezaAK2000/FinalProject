package Boxoffice;

import Runners.GeneralManager;
import Tools.ProButton;
import javazoom.jl.decoder.Manager;

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
    private ProButton songs;
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
        b = this;
        playlists = new ArrayList<>();
        this.setBackground(Color.darkGray);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tools = new ProButton("...");
        tools.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setFont(new Font("serif", Font.BOLD, 30));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setFont(new Font("serif", Font.PLAIN, 30));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        tools.setFont(new Font("serif", Font.PLAIN, 30));
        tools.setForeground(Color.white);
        headFont = new Font("serif", Font.BOLD, 30);
        pubFont = new Font("serif ", Font.PLAIN, 15);

        MenuItem file = new MenuItem("File");
        file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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


        this.add(tools, BorderLayout.NORTH);


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
        songs = new ProButton("Songs");
        songs.setFont(pubFont);
        songs.setForeground(Color.white);
        songs.setBackground(Color.darkGray);
        songs.addMouseListener(new Bolder());
        this.add(songs);
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        originalImage = ImageIO.read(new File("backgrounds\\left2.jpg"));
        tools.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(tools, tools.getParent().getX(), tools.getParent().getY());
            }
        });


    }



    private class Bolder extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setFont(new Font("serif", Font.BOLD, 15));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setFont(new Font("serif ", Font.PLAIN, 15));
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }
    }


    public void paintComponent(Graphics g) {
        double widthScaleFactor = getWidth() / (double) originalImage.getWidth();
        double heightScaleFactor = getHeight() / (double) originalImage.getHeight();

        AffineTransform at = new AffineTransform();
        at.scale(widthScaleFactor, heightScaleFactor);

        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        scaledImage = scaleOp.filter(originalImage, null);


        g.drawImage(scaledImage, 0, 0, null);
        addComponentListener(new ResizerListener());
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double widthScaleFactor = getWidth() / (double) originalImage.getWidth();
            double heightScaleFactor = getHeight() / (double) originalImage.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(widthScaleFactor, heightScaleFactor);

            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaledImage = scaleOp.filter(originalImage, null);

            repaint();
        }


    }


    private class AddingPlaylist implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PlayListAdder playListAdder = new PlayListAdder(b , "add" , null);
        }
    }

    public void addPlaylist(String name) {
        ProButton playlist = new ProButton(name);
        playlist.setFont(pubFont);
        playlist.setBackground(Color.darkGray);
        playlist.setForeground(Color.white);
        playlist.addMouseListener(new Bolder());
        playlist.addMouseListener(new RemoveListener(playlists , playlist));
        playlists.add(playlist);
        this.add(playlist);
        this.revalidate();
        this.repaint();
    }
    public void editPlayList(String name , ProButton button){
        button.setText(name);
        this.revalidate();
        this.repaint();
    }

    public ArrayList<ProButton> getPlaylists() {
        return playlists;
    }

    public ProButton getTools() {
        return tools;
    }
    private class RemoveListener extends MouseAdapter{
        private ProButton button;
        private ArrayList<ProButton> buttons;
        public RemoveListener(ArrayList<ProButton> playlists, ProButton button) {
            super();
            this.button=button;
            buttons= playlists;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(e.isMetaDown()){
                PopupMenu popupMenu=new PopupMenu();
                MenuItem remove=new MenuItem("Remove");
                MenuItem edit=new MenuItem("EditName");
                popupMenu.add(remove);
                popupMenu.add(edit);
                button.add(popupMenu);

                popupMenu.show(button,button.getParent().getX(),button.getParent().getY());
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttons.remove(buttons.indexOf(button));
                        Boxoffice.this.remove(button);
                        Boxoffice.this.repaint();

                    }

                });

                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PlayListAdder playListAdder = new PlayListAdder(b , "edit" , button);
                    }
                });
            }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

}








