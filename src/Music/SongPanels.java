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
        try {
            originalImage = ImageIO.read(new File(address));
        }catch (IOException e){
            originalImage = ImageIO.read(new File("backgrounds\\center6.jpg"));
        }
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

    public void addSong(SongPanel songPanel) throws InvalidDataException, IOException, UnsupportedTagException {
        songPanelList.add(songPanel);

            songPanel.addMouseListener(new SongPanelListenr(musicBox, songPanel, songPanelList, this));

        if(!songPanel.getHasSliderListener()) {
            musicBox.getSlider().addMouseListener(new SliderListener(musicBox.getSlider(), musicBox, songPanel.getSong()));
            songPanel.setHasSliderListener(true);
        }
        if(!songPanel.getHasPlayListener()) {
            musicBox.getPlayb().addActionListener(new PlaybListener(songPanel.getSong(), musicBox));
            songPanel.setHasPlayListener(true);
        }

        if(songPanelList.size()==1) {
            musicBox.getNextb().addActionListener(new NextClickListener(musicBox, this));
            musicBox.getBackb().addActionListener(new BackClickListener(musicBox,this));
        }

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
        this.remove(comp);
    }


    }

