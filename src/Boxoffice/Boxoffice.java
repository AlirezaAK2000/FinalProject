package Boxoffice;

import Center.Center;
import Logic.Song;
import Music.SongPanel;
import Music.SongPanels;
import Runners.GeneralManager;
import Tools.Background;
import Tools.BigPanel;
import Tools.BigPanelContainer;
import Tools.ProButton;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.*;
import java.util.List;

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
    private HashSet<ProButton> playlists;
    private HashMap<String , ProButton> playlistnames;
    private Boxoffice b;
    private JFileChooser JfileChooser;
    private SongPanels songRepository;
    private Center center;
    private SongPanels favorite;
    private SongPanels recentlyList;
    private ProButton buttonClicked;
    private Background artwork;
    private JPanel menubar;
    private BufferedImage c;
    private BigPanelContainer albumsContain;
    private HashMap<ProButton , SongPanels> playlistspanels;
    public Boxoffice(Center center) throws IOException {
        super();
        b = this;
        this.center = center;
        playlistnames = new HashMap<>();
        playlistspanels = new HashMap<>();
        albumsContain = new BigPanelContainer(Background.toBufferedImage(ImageIO.read(new File("backgrounds\\center7.jpg"))));
        recentlyList = new SongPanels("backgrounds\\center.png" , center.getMusicBox()){
          @Override
          public void addSong(SongPanel songPanel){
              ArrayList<SongPanel> songPanels = getSongPanelList();
              if(songPanels.contains(songPanel))
                  songPanels.remove(songPanel);
              ArrayList<SongPanel> songPanels1= new ArrayList<>();
              songPanels1.add(songPanel);
              songPanels1.addAll(songPanels);
              setSongPanelList(songPanels1);
              songPanel.addMouseListener(new MouseAdapter() {
                  @Override
                  public void mouseEntered(MouseEvent e) {
                      setBackground(Color.darkGray);
                  }

                  @Override
                  public void mouseExited(MouseEvent e) {
                      setBackground(Color.BLACK);
                  }

                  @Override
                  public void mouseClicked(MouseEvent e) {

                  }
              });

          }
        };

        songRepository = new SongPanels( "backgrounds\\center5.jpg" ,center.getMusicBox());
        favorite = new SongPanels("backgrounds\\center3.jpg" , center.getMusicBox());
        playlists = new HashSet<>();
        menubar = new JPanel();
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());
        menubar.setLayout(new BoxLayout(menubar, BoxLayout.Y_AXIS));
        menubar.setBackground(Color.BLACK);
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
            public void actionPerformed(ActionEvent e2) {
              JfileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                File selectedFile;
                int returnValue = JfileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = JfileChooser.getSelectedFile();
                    if(selectedFile != null) {

                        try {
                            SongPanel songPanel = new SongPanel(new Song(selectedFile));
                            try {

                                c = Background.toBufferedImage(songPanel.getSong().getArtWork().getImage());
                            } catch (NullPointerException e) {
                                c = ImageIO.read(new File("backgrounds\\center6.jpg"));
                            }
                            albumsContain.addBigPanel(new BigPanel(c, songPanel, center.getMusicBox(), center));
                            songRepository.addSong(songPanel);
                            songRepository.repaintList();
                            songPanel.getLiker().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (songPanel.getLiker().getIcon().equals(songPanel.getUnliked())) {
                                        songPanel.getLiker().setIcon(songPanel.getUnliked());
                                        favorite.addSong(songPanel);
                                        if (buttonClicked.equals(recently))
                                            recentlyList.repaintList();
                                        if (buttonClicked.equals(songs))
                                            songRepository.repaint();
                                        if (buttonClicked.equals(favorites))
                                            favorite.repaintList();
                                    } else {
                                        songPanel.getLiker().setIcon(songPanel.getLiked());

                                        if (buttonClicked.equals(songs))
                                            songRepository.repaintList();
                                        if (buttonClicked.equals(recently))
                                            recentlyList.repaintList();
                                        favorite.removeSong(songPanel);
                                        if (buttonClicked.equals(favorites))
                                            favorite.repaintList();

                                    }
                                }
                            });
                            songPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    if (songPanel.isAddedToRecently() == false) {
                                        recentlyList.addSong(songPanel);
                                        songPanel.setAddedToRecently(true);
                                        center.getMusicBox().setInfo(songPanel.getSong().getTitle(), songPanel.getSong().getArtist());
                                        if (buttonClicked.equals(recently))
                                            recentlyList.repaintList();
                                        if (buttonClicked.equals(songs))
                                            songRepository.repaintList();
                                        if (buttonClicked.equals(favorites))
                                            favorite.repaintList();

                                        artwork.SetBack((songPanel.getSong().getArtWork().getImage()));
                                    } else {
                                        recentlyList.removeSong(songPanel);
                                        recentlyList.addSong(songPanel);
                                        if (buttonClicked.equals(recently))
                                            recentlyList.repaintList();
                                        if (buttonClicked.equals(songs))
                                            songRepository.repaintList();
                                        if (buttonClicked.equals(favorites))
                                            favorite.repaintList();
                                        artwork.SetBack(songPanel.getSong().getArtWork().getImage());
                                    }


                                }
                            });
                        } catch (FileNotFoundException e1) {
                            System.out.println("1");
                        } catch (JavaLayerException e1) {
                            System.out.println("2");
                        } catch (IOException e1) {
                            System.out.println(3);
                        } catch (InvalidDataException e1) {
                            System.out.println(4);
                        } catch (UnsupportedTagException e1) {
                            System.out.println(5);
                        } catch (NullPointerException e1) {
                            System.out.println(6);
                        }
                    }
                }


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
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    center.setMain(new Background( ImageIO.read(new File("backgrounds\\center4.jpg"))));
                    buttonClicked = home;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        menubar.add(home);
        browse = new ProButton("Browse");
        browse.setForeground(Color.white);
        browse.setBackground(Color.darkGray);
        browse.setFont(pubFont);
        browse.addMouseListener(new Bolder());
        menubar.add(browse);
        sep = new JLabel("Library");
        sep.setFont(headFont);
        sep.setForeground(Color.white);
        sep.setBackground(Color.DARK_GRAY);
        menubar.add(sep);
        recently = new ProButton("Recently");
        recently.setFont(pubFont);
        recently.setForeground(Color.white);
        recently.setBackground(Color.darkGray);
        recently.addMouseListener(new Bolder());
        recently.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(recentlyList);
                recentlyList.repaintList();
                buttonClicked = recently;
            }
        });
        menubar.add(recently);
        favorites = new ProButton("Favorites");
        favorites.setFont(pubFont);
        favorites.setForeground(Color.white);
        favorites.setBackground(Color.darkGray);
        favorites.addMouseListener(new Bolder());
        favorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(favorite);
                favorite.repaintList();
                buttonClicked = favorites;
            }
        });
        menubar.add(favorites);
        sharedPlaylist = new ProButton("Shared PlayList");
        sharedPlaylist.setFont(pubFont);
        sharedPlaylist.setForeground(Color.white);
        sharedPlaylist.setBackground(Color.darkGray);
        sharedPlaylist.addMouseListener(new Bolder());
        menubar.add(sharedPlaylist);
        albums = new ProButton("Album");
        albums.setFont(pubFont);
        albums.setForeground(Color.white);
        albums.setBackground(Color.darkGray);
        albums.addMouseListener(new Bolder());
        albums.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(albumsContain);
                albumsContain.repaintList();
                buttonClicked = albums;
            }
        });
        menubar.add(albums);
        artist = new ProButton("Artist");
        artist.setFont(pubFont);
        artist.setForeground(Color.white);
        artist.setBackground(Color.darkGray);
        artist.addMouseListener(new Bolder());
        menubar.add(artist);
        songs = new ProButton("Songs");
        songs.setFont(pubFont);
        songs.setForeground(Color.white);
        songs.setBackground(Color.darkGray);
        songs.addMouseListener(new Bolder());
        songs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(songRepository);
                songRepository.repaintList();
                buttonClicked = songs;
            }
        });
        menubar.add(songs);
        videos = new ProButton("Videos");
        videos.setFont(pubFont);
        videos.setForeground(Color.white);
        videos.setBackground(Color.darkGray);
        videos.addMouseListener(new Bolder());
        menubar.add(videos);
        podcast = new ProButton("Podcasts");
        podcast.setFont(pubFont);
        podcast.setForeground(Color.white);
        podcast.setBackground(Color.darkGray);
        podcast.addMouseListener(new Bolder());
        menubar.add(podcast);
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
        menubar.add(addPlaylist);
        BufferedImage b = ImageIO.read(new File("backgrounds\\center6.jpg"));
        artwork = new Background(b);
        artwork.setBackground(Color.DARK_GRAY);
        artwork.setPreferredSize(new Dimension(getWidth() , 200));
        this.add(artwork , BorderLayout.SOUTH);
        JScrollPane j=new JScrollPane(menubar);
        j.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(j , BorderLayout.CENTER);

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

    public void addPlaylist(String name) throws IOException {
        ProButton playlist = new ProButton(name);
        playlist.setFont(pubFont);
        playlist.setBackground(Color.darkGray);
        playlist.setForeground(Color.white);
        playlist.addMouseListener(new Bolder());
        playlist.addMouseListener(new RemoveListener(playlists , playlist));
        SongPanels songPanels = new SongPanels("backgrounds\\center9.jpg" , center.getMusicBox());
        playlistspanels.put(playlist , songPanels);
        playlistnames.put(name , playlist);
        playlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(songPanels);
                songPanels.repaintList();
                buttonClicked = playlist;
            }
        });
        playlists.add(playlist);
        menubar.add(playlist);
        menubar.revalidate();
        menubar.repaint();
    }
    public void editPlayList(String name , ProButton button){
        button.setText(name);
        this.revalidate();
        this.repaint();
    }



    public ProButton getTools() {
        return tools;
    }
    private class RemoveListener extends MouseAdapter{
        private ProButton button;
        private HashSet<ProButton> buttons;
        public RemoveListener(HashSet<ProButton> playlists, ProButton button) {
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
                        buttons.remove(button);
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

    public Center getCenter() {
        return center;
    }

}
