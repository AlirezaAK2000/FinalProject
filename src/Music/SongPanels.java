package Music;

import Boxoffice.Boxoffice;
import Logic.Song;
import Musicbox.MusicBox;
import Tools.Integer;
import Tools.ProSlider;
import Tools.SliderDemoSkin;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static Logic.Song.playTheread;

public class SongPanels extends JPanel implements Serializable , Adder {
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private ArrayList<SongPanel> songPanelList;
    private JPanel headPanel;
    private MusicBox musicBox;

    public SongPanels(String address, MusicBox musicBox) throws IOException {
        super();
        headPanel = new JPanel();
        this.musicBox = musicBox;
        headPanel.setBackground(Color.BLACK);
        headPanel.setMaximumSize(new Dimension(java.lang.Integer.MAX_VALUE, 30));
        headPanel.setLayout(new GridLayout(1, 4, 10, 10));
        headPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel empty = new JLabel();
        empty.setBackground(Color.BLACK);
        empty.setBorder(BorderFactory.createEmptyBorder());
        headPanel.add(empty);

        JLabel title = new JLabel("Title");
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        title.setFont(new Font("serif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder());
        headPanel.add(title);

        JLabel artist = new JLabel("Artist");
        artist.setBackground(Color.BLACK);
        artist.setForeground(Color.white);
        artist.setFont(new Font("serif", Font.BOLD, 20));
        artist.setBorder(BorderFactory.createEmptyBorder());
        headPanel.add(artist);

        JLabel album = new JLabel("Album");
        album.setBackground(Color.BLACK);
        album.setForeground(Color.white);
        album.setFont(new Font("serif", Font.BOLD, 20));
        album.setBorder(BorderFactory.createEmptyBorder());
        headPanel.add(album);

        songPanelList = new ArrayList<>();
        originalImage = ImageIO.read(new File(address));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(5));
        this.add(headPanel);
        this.add(Box.createVerticalStrut(5));
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

    public void addSong(SongPanel songPanel) {
        songPanelList.add(songPanel);
        songPanel.addMouseListener(new SongPanelListenr(musicBox, songPanelList, songPanel));

        songPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.darkGray);
                songPanel.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.BLACK);
                songPanel.getParent().repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

    }


    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double widthScaleFactor = getParent().getWidth() / (double) originalImage.getWidth();
            double heightScaleFactor = getParent().getHeight() / (double) originalImage.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(widthScaleFactor, heightScaleFactor);

            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaledImage = scaleOp.filter(originalImage, null);

            repaint();
        }
    }

    public ArrayList<SongPanel> getSongPanelList() {
        return songPanelList;
    }

    public void isSelect(SongPanel songPanel) {
        int index = songPanelList.indexOf(songPanel);
        for (int i = index; i >= 0; i--) {

        }
    }
    public void repaintList() {
        removeAll();
        add(Box.createVerticalStrut(5));
        add(headPanel);
        for (SongPanel p : songPanelList) {
            add(Box.createVerticalStrut(5));
            add(p);
        }
        if (this == null)
            System.out.println("fuck");
        revalidate();
        repaint();


    }
    public void setSongPanelList(ArrayList<SongPanel> songPanels1) {
        songPanelList = songPanels1;

    }


    public void removeSong(Component comp) {
        songPanelList.remove(comp);
        remove(comp);
    }


    class SongPanelListenr extends MouseAdapter {

        private MusicBox myMusicBox;
        private SongPanel songPanel;
        private ArrayList<SongPanel> songPanelList;


        public SongPanelListenr(MusicBox myMusicBox, ArrayList<SongPanel> songPanelList, SongPanel songPanel) {
            this.myMusicBox = myMusicBox;
            this.songPanelList = songPanelList;
            this.songPanel = songPanel;

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                if (playTheread!= null) {
                    playTheread.stop();
                }

                musicBox.getSlider().setValue(0);
                musicBox.setMove(true);
                myMusicBox.setSongPanels(SongPanels.this);
                myMusicBox.setSongPanel(songPanel);
                myMusicBox.getSlider().addMouseListener(new SliderListener(myMusicBox.getSlider(), myMusicBox, songPanel.getSong()));

            } catch (UnsupportedTagException e1) {
                e1.printStackTrace();
            } catch (InvalidDataException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            myMusicBox.getPlayb().addActionListener(new PlaybListener(songPanel.getSong(), myMusicBox));
            try {
                songPanel.getSong().play(0);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (JavaLayerException e1) {
                e1.printStackTrace();
            }

        }



        //****************************************************************************
        class SliderListener extends MouseAdapter {

            private ProSlider slider;
            private MusicBox musicBox;
            private Song song;
            private Integer position;

            public SliderListener(ProSlider slider, MusicBox musicBox, Song song) throws UnsupportedTagException, InvalidDataException, IOException{
                this.slider = slider;
                this.musicBox = musicBox;
                this.song = song;
                slider.setMaximum(song.getSize());
                slider.setValue(0);

                this.position = slider.getPosition();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (musicBox.getSongPanel().getSong() == song) {
                    Point p = e.getPoint();
                    double percent = p.x / ((double) slider.getWidth());
                    int range = slider.getMaximum() - slider.getMinimum();
                    double newVal = range * percent;
                    int result = (int) (slider.getMinimum() + newVal);
                    slider.setValue(result);
                    position.setValue(result);
                    try {
                        song.play(result);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (musicBox.getSongPanel().getSong() == song) {
                    try {
                        song.play(position.getValue());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        class PlaybListener implements ActionListener {
            private int a = 0;
            private Song song;
            private MusicBox musicBox;

            public PlaybListener(Song song, MusicBox musicBox) {
                this.song = song;
                this.musicBox = musicBox;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicBox.getSongPanel().getSong() == song) {
                    if (a % 2 == 1) {
                        ++a;
                        try {
                            musicBox.setMove(true);
                            song.continuee();

                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (JavaLayerException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        a++;
                        musicBox.setMove(false);
                        song.pause();

                }
            }
        }
    }
    //********************************************************************
    class NextClickListener implements ActionListener {
        private MusicBox musicBox;
        private SongPanels songPanels;


        public NextClickListener(MusicBox musicBox, SongPanels songPanels) {
            this.musicBox = musicBox;
            this.songPanels = songPanels;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (songPanels == musicBox.getSongPanels()) {
                int nextSong = songPanels.getSongPanelList().indexOf(musicBox.getSongPanel().getSong()) + 1;
                if (songPanels.getSongPanelList().size() - 1 != nextSong - 1) {
                    try {
                        if (playTheread != null)
                            playTheread.stop();
                        musicBox.getSlider().addMouseListener(new SliderListener(musicBox.getSlider(), musicBox, musicBox.getSongPanels().getSongPanelList().get(nextSong).getSong()));
                    } catch (UnsupportedTagException e1) {
                        e1.printStackTrace();
                    } catch (InvalidDataException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    musicBox.getSlider().setValue(0);
                    musicBox.setSongPanel(songPanels.getSongPanelList().get(nextSong));
                    try {
                        musicBox.getSongPanel().getSong().play(0);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        }
    }}}
