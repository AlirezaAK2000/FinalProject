package Musicbox;

import Center.Center;
import Logic.Song;
import Music.PlaybListener;
import Music.SliderListener;
import Music.SongPanel;
import Music.SongPanels;
import Tools.*;
import Tools.Integer;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static Logic.Song.playTheread;

public class MusicBox extends JPanel  {
    private boolean isShuffle;
    private boolean isRepeat;
    private ProButton playb;
    private ProButton shuffle;
    private ProButton nextb;
    private ProButton backb;
    private ProButton muter;
    private ProButton unrepeat;
    private ImageIcon playI;
    private ImageIcon stopI;
    private ImageIcon nextbIc;
    private ImageIcon backbIc;
    private ImageIcon shuffleIc;
    private ImageIcon unShuffle;
    private ImageIcon unrepeatI;
    private ImageIcon repeat;
    private SliderDemoSkin volumeSet;
    private SliderDemoSkin songSetter;
    private ProSlider volume;
    private ImageIcon mute;
    private ImageIcon volumeOn;
    private JPanel center;
    private JPanel auxPanel;
    private JPanel songInformation;
    private SongPanels songPanels;
    private SongPanel songPanel;
    private JPanel infoMusic;
    private JLabel songName;
    private JLabel artist;
    private JLabel artistLabel;
    private JLabel titleLabel;
    private Thread playThread;
    private Background artWork;
    private Thread moveSliderThread;
    private boolean move;
    public MusicBox() throws IOException {
        super();
        isRepeat=false;
        isRepeat=false;
        playThread=new Thread();
        move=false;
        center = new JPanel();
        auxPanel = new JPanel();
        center.setLayout(new FlowLayout(1,50,20));
        center.setBackground(Color.WHITE);
        stopI =new ImageIcon();
        stopI.setImage(ImageIO.read(new File("Icons\\stop.png")));
        nextbIc = new ImageIcon();
        nextbIc.setImage(ImageIO.read(new File("Icons\\nextt.png")));
        nextb = new ProButton (nextbIc);
        backbIc= new ImageIcon();
        backbIc.setImage(ImageIO.read(new File("Icons\\back.png")));
        backb = new  ProButton (backbIc);
        playI = new ImageIcon();
        playI.setImage(ImageIO.read(new File("Icons\\play.png")));
        playb = new ProButton (playI);
        unrepeatI =  new ImageIcon();
        unrepeatI.setImage(ImageIO.read(new File("Icons\\unrepeat.png")));
        unrepeat = new ProButton (unrepeatI);
        shuffleIc = new ImageIcon();
        shuffleIc.setImage(ImageIO.read(new File("Icons\\shuffle.png")));
        unShuffle = new ImageIcon();
        unShuffle.setImage(ImageIO.read(new File("Icons\\unshuffle.png")));
        shuffle = new ProButton(shuffleIc);
        shuffle.addActionListener(new Mousehandler());
        repeat =  new ImageIcon();
        repeat.setImage(ImageIO.read(new File("Icons\\repeat.png")));
        volumeOn = new ImageIcon();
        volumeOn.setImage(ImageIO.read(new File("Icons\\sound.png")));
        mute = new ImageIcon();
        mute.setImage(ImageIO.read(new File("Icons\\mute.png")));
        muter = new ProButton(volumeOn);
        muter.addActionListener(new Mousehandler());


        playb.setPreferredSize(new Dimension(28,28));
        nextb.setPreferredSize(new Dimension(28,28));
        backb.setPreferredSize(new Dimension(28,28));
        shuffle.setPreferredSize(new Dimension(28,28));
        unrepeat.setPreferredSize(new Dimension(28,28));
        muter.setPreferredSize(new Dimension(28,28));

        volumeSet = new SliderDemoSkin();
        volumeSet.remove(volumeSet.getSlider());
        volumeSet.setLayout(new FlowLayout());
        volumeSet.add(muter , FlowLayout.LEFT);
        volumeSet.add(volumeSet.getSlider() , FlowLayout.CENTER);

        volumeSet.getSlider().setPreferredSize(new Dimension(100 , 15));
        volumeSet.getSlider().setMaximum(100);
        volumeSet.getSlider().setMinimum(0);
        volumeSet.getSlider().setValue(50);
        Audio.setMasterOutputVolume((float) (50.0/100.0));
        volumeSet.getSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Audio.setMasterOutputVolume((float)(volumeSet.getSlider().getValue()/100.0));
                if(volumeSet.getSlider().getValue() == 0)
                    muter.setIcon(mute);
                else muter.setIcon(volumeOn);

            }
        });

        artistLabel = new JLabel();
        titleLabel = new JLabel();
        infoMusic = new JPanel();
        infoMusic.setLayout(new BoxLayout(infoMusic , BoxLayout.Y_AXIS));
        infoMusic.add(titleLabel);
        infoMusic.add(artistLabel);
        infoMusic.setBackground(Color.WHITE);
        infoMusic.setPreferredSize(new Dimension(200 , 20));
        titleLabel.setFont(new Font("serif" , Font.BOLD , 15));
        artistLabel.setFont(new Font("serif" , Font.BOLD , 15));

        songSetter = new SliderDemoSkin();
        songSetter.getSlider().setPreferredSize(new Dimension(600 , 15));
        songSetter.setPreferredSize(new Dimension(600 , 10));
        auxPanel.add(songSetter.getSlider());
        auxPanel.setBackground(Color.WHITE);
        songSetter.getSlider().setValue(0);
        this.setLayout(new BorderLayout());
        center.add(shuffle, FlowLayout.LEFT );
        center.add(unrepeat, FlowLayout.CENTER);
        center.add( nextb , FlowLayout.LEFT,1);
        center.add( playb, FlowLayout.CENTER, 1);
        center.add(backb , FlowLayout.CENTER , 1);

        this.add(new JLabel(""),BorderLayout.WEST);
        this.add(center ,BorderLayout.CENTER);
        this.add(volumeSet, BorderLayout.EAST);
        this.add(auxPanel , BorderLayout.NORTH);
        this.add(infoMusic , BorderLayout.WEST);

        playb.addActionListener(new Mousehandler());
        unrepeat.addActionListener(new Mousehandler());
        Timer mytimer = new Timer(1000,new MoveSlider(getSlider(),getSlider().getPosition()));
        mytimer.start();
    }
    private class Mousehandler implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==unrepeat){
                if (unrepeat.getIcon().equals(unrepeatI)) {
                    unrepeat.setIcon(repeat);
                    isRepeat=true;
                }
                else {
                    unrepeat.setIcon(unrepeatI);
                    isRepeat=false;
                }
            }
            if (e.getSource() == playb){
                if(playb.getIcon().equals(stopI))
                    playb.setIcon(playI);
                else
                    playb.setIcon(stopI);
            }
            if (e.getSource()==muter){
                if(muter.getIcon().equals(mute)) {
                    muter.setIcon(volumeOn);
                    Audio.setMasterOutputVolume((float)(volumeSet.getSlider().getValue()/100.0));
                }
                else {
                    muter.setIcon(mute);
                    Audio.setMasterOutputVolume(0.0f);
                }
            }
            if (e.getSource() == shuffle){
                if(shuffle.getIcon().equals(unShuffle)) {
                    shuffle.setIcon(shuffleIc);
                    isShuffle=true;
                }
                else {
                    shuffle.setIcon(unShuffle);
                    isShuffle=false;
                }
            }
        }
    }

    public JPanel getSongInformation() {
        return songInformation;
    }

    public void setSongPanels(SongPanels songPanels){
        this.songPanels=songPanels;
    }

    public void setSongPanel(SongPanel songPanel) throws InvalidDataException, IOException, UnsupportedTagException {
        this.songPanel = songPanel;
        this.getSlider().setMaximum(songPanel.getSong().getSize());
        SwingUtilities.updateComponentTreeUI(getSlider());
    }

    public SongPanel getSongPanel() {
        return songPanel;
    }

    public SongPanels getSongPanels() {
        return songPanels;
    }


    public  ProSlider getSlider(){
        return songSetter.getSlider();
    }
    public ProButton getPlayb(){
        return playb;
    }

    public ProButton getBackb() {
        return backb;
    }

    public ProButton getNextb() {
        return nextb;
    }
    class MoveSlider implements ActionListener{
        private ProSlider slider;
        private Integer position;

        public MoveSlider( ProSlider slider,Integer position) {
            this.slider = slider;
            this.position=position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                try {

                        if (move) {
                            slider.setMaximum(getSongPanel().getSong().getSize()/1000);
                            slider.setValue(position.getValue() + 1);
                            System.out.println(slider.getMaximum());
                            if (position.getValue() > slider.getMaximum() - 1) {
                                if (isRepeat) {
                                    position.setValue(0);
                                    slider.setValue(0);
                                    if (playThread != null) {
                                        playThread.stop();
                                    }
                                    getSongPanel().getSong().play(0);

                                } else if ((!isRepeat) && isShuffle) {
                                    Random random = new Random();
                                    int numberOfSong = random.nextInt(songPanels.getSongPanelList().size());
                                    position.setValue(0);
                                    slider.setValue(0);
                                    if (playThread != null) {
                                        playThread.stop();
                                    }
                                    songPanel.setBackground(Color.BLACK);
                                    MusicBox.this.setSongPanel(songPanels.getSongPanelList().get(numberOfSong));
                                    MusicBox.this.getSongPanel().setBackground(new Color(0x308320));

                                    if (!MusicBox.this.getSongPanel().getHasPlayListener()) {
                                        MusicBox.this.getSongPanel().setHasPlayListener(true);
                                        MusicBox.this.getPlayb().addActionListener(new PlaybListener(MusicBox.this.getSongPanel().getSong(), MusicBox.this));
                                    }
                                    songPanels.getSongPanelList().get(numberOfSong).getSong().play(0);
                                } else if (!(isRepeat) && !(isShuffle)) {
                                    if (songPanels == MusicBox.this.getSongPanels()) {
                                        int nextSong = songPanels.getSongPanelList().indexOf(MusicBox.this.getSongPanel()) + 1;
                                        if (songPanels.getSongPanelList().size() - 1 != nextSong - 1) {
                                            try {
                                                if (playTheread != null)
                                                    playTheread.stop();
                                                MusicBox.this.getSongPanel().setBackground(Color.BLACK);
                                                if (!songPanels.getSongPanelList().get(nextSong).getHasSliderListener()) {
                                                    songPanels.getSongPanelList().get(nextSong).setHasSliderListener(true);
                                                    MusicBox.this.getSlider().addMouseListener(new SliderListener(MusicBox.this.getSlider(), MusicBox.this, songPanels.getSongPanelList().get(nextSong).getSong()));
                                                }

                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                            MusicBox.this.getSlider().setValue(0);
                                            try {
                                                MusicBox.this.setSongPanel(songPanels.getSongPanelList().get(nextSong));
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            }
                                            if (!songPanels.getSongPanelList().get(nextSong).getHasPlayListener()) {
                                                MusicBox.this.getPlayb().addActionListener(new PlaybListener(songPanels.getSongPanelList().get(nextSong).getSong(), MusicBox.this));
                                                songPanels.getSongPanelList().get(nextSong).setHasPlayListener(true);
                                            }

                                            try {
                                                MusicBox.this.getSlider().setMaximum(MusicBox.this.getSongPanel().getSong().getSize());
                                                MusicBox.this.getSongPanel().getSong().play(0);
                                                MusicBox.this.getSongPanel().setBackground(new Color(0x308320));
                                            } catch (FileNotFoundException e1) {
                                                e1.printStackTrace();
                                            } catch (JavaLayerException e1) {
                                                e1.printStackTrace();
                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            }

                                        } else {
                                            try {
                                                if (playTheread != null)
                                                    playTheread.stop();
                                                MusicBox.this.getSongPanel().setBackground(Color.BLACK);
                                                if (!songPanels.getSongPanelList().get(0).getHasSliderListener()) {
                                                    songPanels.getSongPanelList().get(0).setHasSliderListener(true);
                                                    MusicBox.this.getSlider().addMouseListener(new SliderListener(MusicBox.this.getSlider(), MusicBox.this, songPanels.getSongPanelList().get(0).getSong()));
                                                }
                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                            MusicBox.this.getSlider().setValue(0);
                                            try {
                                                MusicBox.this.setSongPanel(songPanels.getSongPanelList().get(0));
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            }
                                            if (!songPanels.getSongPanelList().get(0).getHasPlayListener()) {
                                                MusicBox.this.getPlayb().addActionListener(new PlaybListener(songPanels.getSongPanelList().get(0).getSong(), MusicBox.this));
                                                songPanels.getSongPanelList().get(0).setHasPlayListener(true);
                                            }
                                            try {
                                                MusicBox.this.getSlider().setMaximum(MusicBox.this.getSongPanel().getSong().getSize());
                                                MusicBox.this.getSongPanel().getSong().play(0);
                                                MusicBox.this.getSongPanel().setBackground(new Color(0x308320));
                                            } catch (FileNotFoundException e1) {
                                                e1.printStackTrace();
                                            } catch (JavaLayerException e1) {
                                                e1.printStackTrace();
                                            } catch (UnsupportedTagException e1) {
                                                e1.printStackTrace();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (InvalidDataException e1) {
                                                e1.printStackTrace();
                                            }

                                        }
                                    }
                                }
                                try {
                                    artWork.SetBack(MusicBox.this.getSongPanel().getSong().getArtWork().getImage());
                                }catch (NullPointerException e1){ }
                                MusicBox.this.getTitleLabel().setText(MusicBox.this.getSongPanel().getSong().getTitle());
                                try {
                                    MusicBox.this.getArtist().setText(MusicBox.this.getSongPanel().getSong().getArtist());
                                }catch (NullPointerException e3){
                                }
                            }

                        }


                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedTagException e1) {
                    e1.printStackTrace();
                } catch (InvalidDataException e1) {
                    e1.printStackTrace();
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                }
        }
    }



    public void setInfo(String title , String artist){
        artistLabel.setText(artist);
        titleLabel.setText(title);
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JLabel getArtist() {
        return artist;
    }

    public void setArtWork(Background artWork) {
        this.artWork = artWork;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
    //****************************************************************************

}
