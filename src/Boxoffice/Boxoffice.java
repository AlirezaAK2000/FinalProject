package Boxoffice;

import Center.Center;
import Logic.Song;
import Music.SongPanel;
import Music.SongPanels;
import Tools.*;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static Logic.Song.playTheread;

public class Boxoffice extends JPanel implements Serializable {

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
    private ProButton save;
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
    private SongPanels sharedList;
    private JPanel menubar;
    private BufferedImage c;
    private HashMap<String ,ArrayList<String>> loadedData;
    private HashMap<String ,ArrayList<String>> data;
    private BigPanelContainer albumsContain;
    private HashMap<ProButton , SongPanels> playlistspanels;
    private  ArrayList<SongPanel> songPanelrepoos;
    private Thread thread;
    public Boxoffice(Center center , String flag) throws IOException {
        super();
        b = this;
        this.center = center;
        playlistnames = new HashMap<>();
        playlistspanels = new HashMap<>();
        albumsContain = new BigPanelContainer(Background.toBufferedImage(ImageIO.read(new File("backgrounds\\center9.jpg"))));
        sharedList = new SongPanels("backgrounds\\center10.jpg" , center.getMusicBox() , "sharedList");
        recentlyList = new SongPanels("backgrounds\\center.png" , center.getMusicBox() , "recentlyList"){
            @Override
            public void addSong(SongPanel songPanel){
                System.out.println("hello");
                if (!recentlyList.getAddresses().contains(songPanel.getSong().getFileName())) {
                     recentlyList.getAddresses().add(songPanel.getSong().getFileName());
                }
                ArrayList<String> adres = recentlyList.getAddresses();
                ArrayList<SongPanel> songPanels = getSongPanelList();
                if(songPanels.contains(songPanel)) {
                    songPanels.remove(songPanel);
                    adres.remove(songPanel.getSong().getFileName());
                }
                ArrayList<String> aux = new ArrayList<>();
                ArrayList<SongPanel> songPanels1= new ArrayList<>();
                songPanels1.add(songPanel);
                aux.add(songPanel.getSong().getFileName());
                songPanels1.addAll(songPanels);
                aux.addAll(adres);
                setSongPanelList(songPanels1);
                setAddresses(aux);
                System.out.println("aux"+aux.size());
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


        songRepository = new SongPanels( "backgrounds\\center5.jpg" ,center.getMusicBox()  , "songs");
        favorite = new SongPanels("backgrounds\\center3.jpg" , center.getMusicBox()  , "favorite");
        playlists = new HashSet<>();
        menubar = new JPanel();
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());
        data = new HashMap<>();
        data.put(sharedList.getName() , sharedList.getAddresses());
        data.put(recentlyList.getName() , recentlyList.getAddresses());
        data.put(songRepository.getName() , songRepository.getAddresses());
        data.put(favorite.getName() , favorite.getAddresses());
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
                if(playTheread!=null)
                    playTheread.stop();
                JfileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                JfileChooser.setMultiSelectionEnabled(true);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3" , "mp3");
                JfileChooser.addChoosableFileFilter(filter);
                File[] selectedFiles;
                int returnValue = JfileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = JfileChooser.getSelectedFiles();
                    if(selectedFiles != null) {
                        for (int i =0 ;i < selectedFiles.length;i++)

                        try {
                            addProcess(new SongPanel(new Song(selectedFiles[i])));
                        } catch (FileNotFoundException e1) {
                            System.out.println(1);
                        } catch (JavaLayerException e1) {
                            System.out.println(2);
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
                center.getMusicBox().setSongPanels(Boxoffice.this.getRecentlyList());
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
                center.getMusicBox().setSongPanels(Boxoffice.this.getFavorite());
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
        sharedPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(sharedList);
                center.getMusicBox().setSongPanels(sharedList);
                sharedList.repaintList();
                buttonClicked = sharedPlaylist;
            }
        });
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
                center.getMusicBox().setSongPanels(Boxoffice.this.songRepository);
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
        save = new ProButton("Save");
        save.setFont(pubFont);
        save.setForeground(Color.white);
        save.setBackground(Color.darkGray);
        save.addMouseListener(new Bolder());
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    data.put(songRepository.getName() , songRepository.getAddresses());
                    data.put("favorite" , favorite.getAddresses());
                    data.put(recentlyList.getName() , recentlyList.getAddresses());
                    data.put(sharedList.getName() , sharedList.getAddresses());
                    System.out.println("s:"+data.size());
                    System.out.println("a:" +sharedList.getAddresses().size() );
                    new File("everyThing.ser").delete();
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("everyThing.ser" ) );
                    out.writeObject(new Saver(data));
                    System.out.println("saved");
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        menubar.add(save);
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
        j.setViewportBorder(BorderFactory.createCompoundBorder());
        this.add(j , BorderLayout.CENTER);

        originalImage = ImageIO.read(new File("backgrounds\\left2.jpg"));
        tools.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(tools, tools.getParent().getX(), tools.getParent().getY());
            }
        });
        center.getMusicBox().getNextb().addActionListener(new NextClickListener(center.getMusicBox(),artwork));
        center.getMusicBox().getBackb().addActionListener(new NextClickListener(center.getMusicBox(),artwork));

    }

    private class AddToPlayListFrame extends JFrame implements ActionListener{
        private JComboBox<String> namesCombo;
        private String listName;
        private SongPanels list;
        private SongPanel song;
        private JFrame itSelf;
        public AddToPlayListFrame(SongPanel song){
            this.song = song;
            itSelf = this;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] names = playlistnames.keySet().toArray(new String[0]);
            namesCombo = new JComboBox<>(names);
            namesCombo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        listName = namesCombo.getItemAt(namesCombo.getSelectedIndex());
                        list=  playlistspanels.get(playlistnames.get(listName));
                        try {
                            list.addSong(song);
                        } catch (InvalidDataException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (UnsupportedTagException e1) {
                            e1.printStackTrace();
                        }
                        itSelf.setVisible(false);
                    }
                }
            });
            this.setBackground(Color.BLACK);
            this.add(namesCombo);
            this.setSize(new Dimension(300 , 80));
            this.setLocation(300 , 300);
            this.setVisible(true);

        }
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
        SongPanels songPanels = new SongPanels("backgrounds\\center9.jpg" , center.getMusicBox() , name);
        playlistspanels.put(playlist , songPanels);
        data.put(name , songPanels.getAddresses());
        playlistnames.put(name , playlist);
        playlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center.setMain(songPanels);
                center.getMusicBox().setSongPanels(songPanels);
                songPanels.repaintList();
                buttonClicked = playlist;
            }
        });
        playlists.add(playlist);
        menubar.add(playlist);
        menubar.revalidate();
        menubar.repaint();
    }
    public void addProcess(String name) throws JavaLayerException, UnsupportedTagException, InvalidDataException, IOException {
        SongPanel songPanel = new SongPanel(new Song(name));
        addProcess(songPanel);
    }


    public void setData(HashMap<String, ArrayList<String>> data) throws JavaLayerException, UnsupportedTagException, InvalidDataException, IOException {
        loadedData= data;
        load();
    }
    public void load() throws UnsupportedTagException, IOException, InvalidDataException, JavaLayerException {

        ArrayList<String> musics = loadedData.get("songs");
        for (String str:musics){
            addProcess(str);
        }
        data.put("songs" , songRepository.getAddresses());
        if (data.containsKey("favorite"))
        System.out.println("is ok");
        ArrayList<String> likedList = loadedData.get("favorite");
        ArrayList<SongPanel> panels = songRepository.getSongPanelList();
        SongPanels aux = new SongPanels("backgrounds\\center.png" , center.getMusicBox() , "recentlyList");
        for (String str:likedList) {
            for (SongPanel s : panels) {

                if (str.equals(s.getSong().getFileName())) {
                    s.getLiker().setIcon(s.getLiked());
                    favorite.addSong(s);
                }
            }
        }
        data.put("favorite" , favorite.getAddresses());
        data.put("recentlyList", recentlyList.getAddresses());
        ArrayList<String> recent = loadedData.get("recentlyList");
        for (int i=recent.size()-1;i>=0;i--) {
            for (SongPanel s : panels) {

                if (recent.get(i).equals(s.getSong().getFileName())) {
                    recentlyList.addSong(s);
                }
            }
        }
        data.put("recentlyList" , recent);

        try {
            ArrayList<String> songAddresses = loadedData.get("sharedList");
            ArrayList<SongPanel> all = songRepository.getSongPanelList();
            for (String str : songAddresses) {
                for (SongPanel s : all) {
                    if (str.equals(s.getSong().getFileName()))
                        sharedList.addSong(s);
                }
            }
            data.put("sharedList", sharedList.getAddresses());
            System.out.println("add"+sharedList.getAddresses().size());
            System.out.println("panel" + sharedList.getSongPanelList().size());

        }catch (NullPointerException e){
            System.out.println("fuck");
        }
        Set<String> pllists =  data.keySet();
        System.out.println("kir:" + pllists.size());

        pllists.remove("sharedList");
        pllists.remove("recentlyList");
        pllists.remove("favorite");
        pllists.remove("songs");
        for (String str:pllists){
            addPlaylist(str);
            loadList(str);
        }

    }


    public void loadList(String name) throws InvalidDataException, IOException, UnsupportedTagException {
        try {
            ArrayList<String> songAddresses = loadedData.get(name);
            ArrayList<SongPanel> all = songRepository.getSongPanelList();
            SongPanels songPanels = playlistspanels.get(playlistnames.get(name));
            for (String str : songAddresses) {
                for (SongPanel s : all) {
                    if (str.equals(s.getSong().getFileName()))
                        songPanels.addSong(s);
                }
            }
            data.put(name, songPanels.getAddresses());
            System.out.println("add"+songPanels.getAddresses().size());
            System.out.println("panel" + songPanels.getSongPanelList().size());
        }catch (NullPointerException e){
            System.out.println("fuck");
        }
    }


    public void addProcess(SongPanel songPanel) throws IOException, InvalidDataException, UnsupportedTagException {
        songPanelrepoos = songRepository.getSongPanelList();
        boolean isAvailable = false;
        for (SongPanel p : songPanelrepoos)
            if (songPanel.equals(p)) {
                isAvailable = true;
                break;
            }
        if (!isAvailable) {
            try {

                c = Background.toBufferedImage(songPanel.getSong().getArtWork().getImage());
            } catch (NullPointerException e) {
                c = ImageIO.read(new File("backgrounds\\center6.jpg"));
            }
            boolean haveSameAlbum = false;
            for (SongPanel p : songPanelrepoos) {
                if (songPanel.sameAlbum(p)) {
                    haveSameAlbum = true;
                    songPanel.setAlbumPanel(p.getAlbumPanel());
                    p.getAlbumPanel().addSong(songPanel);

                    break;
                }
            }
            if (!haveSameAlbum) {
                BigPanel bigPanel = new BigPanel(c, songPanel, center.getMusicBox(), center);
                songPanel.setAlbumPanel(bigPanel);
                albumsContain.addBigPanel(bigPanel);
            }
            songRepository.addSong(songPanel);
            songRepository.repaintList();

            songPanel.getLiker().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (songPanel.getLiker().getIcon().equals(songPanel.getUnliked())) {
                        songPanel.getLiker().setIcon(songPanel.getUnliked());
                        try {
                            favorite.addSong(songPanel);
                        } catch (InvalidDataException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (UnsupportedTagException e1) {
                            e1.printStackTrace();
                        }
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
                public void mouseClicked(MouseEvent e1) {
                    if (!e1.isMetaDown()) {
                        if (thread != null)
                            try {
                                Song.playTheread.stop();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        if (songPanel.isAddedToRecently() == false) {
                            try {
                                recentlyList.addSong(songPanel);
                            } catch (InvalidDataException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (UnsupportedTagException e) {
                                e.printStackTrace();
                            }
                            songPanel.setAddedToRecently(true);
                            center.getMusicBox().setInfo(songPanel.getSong().getTitle(), songPanel.getSong().getArtist());
                            if (buttonClicked.equals(recently))
                                recentlyList.repaintList();
                            if (buttonClicked.equals(songs))
                                songRepository.repaintList();
                            if (buttonClicked.equals(favorites))
                                favorite.repaintList();
                            try {
                                artwork.SetBack((songPanel.getSong().getArtWork().getImage()));
                            }catch (NullPointerException e){
                            }
                        } else {
                            recentlyList.removeSong(songPanel);
                            try {
                                recentlyList.addSong(songPanel);
                            } catch (InvalidDataException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (UnsupportedTagException e) {
                                e.printStackTrace();
                            }
                            if (buttonClicked.equals(recently))
                                recentlyList.repaintList();
                            if (buttonClicked.equals(songs))
                                songRepository.repaintList();
                            if (buttonClicked.equals(favorites))
                                favorite.repaintList();
                            try {
                                artwork.SetBack(songPanel.getSong().getArtWork().getImage());
                            }catch (NullPointerException e){
                            }
                        }

                    }
                }
            });
            songPanel.getAddtoPlaylist().addActionListener(new AddToPlayListFrame(songPanel));
            songPanel.getAddtoShareList().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        sharedList.addSong(songPanel);
                    } catch (InvalidDataException e1) {
                        System.out.println("error1");
                    } catch (IOException e1) {
                        System.out.println("error2");
                    } catch (UnsupportedTagException e1) {
                        System.out.println("error3");
                    }
                    sharedList.repaintList();
                    System.out.println( "size"+sharedList.getSongPanelList().size());
                }
            });
        }
    }
    public void editPlayList(String name , ProButton button){
        button.setText(name);
        this.revalidate();
        this.repaint();
    }

    public SongPanels getFavorite() {
        return favorite;
    }

    public SongPanels getSharedList() {
        return sharedList;
    }

    public SongPanels getSongRepository() {
        return songRepository;
    }

    public Background getArtwork() {
        return artwork;
    }

    public SongPanels getRecentlyList() {
        return recentlyList;
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
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playlistnames.remove(button.getName());
                        if (data.containsKey(playlistspanels.get(button).getName()))
                        data.remove(playlistspanels.get(button).getName());
                        playlistspanels.remove(button);
                        buttons.remove(button);
                        menubar.remove(button);
                        menubar.revalidate();
                        menubar.repaint();
                    }

                });
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PlayListAdder playListAdder = new PlayListAdder(b , "edit" , button);
                    }
                });
                popupMenu.show(button,button.getParent().getX(),button.getParent().getY());
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
