package Musicbox;

import Tools.ProButton;
import Tools.ProSlider;
import Tools.SliderDemoSkin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MusicBox extends JPanel  {
    private ProButton playb;
    private ImageIcon playI;
    private ImageIcon stopI;
    private ProButton nextb;
    private ProButton backb;
    private ProButton unliked;
    private ImageIcon ulikedI;
    private ProButton shuffle;
    private ImageIcon liked;
    private SliderDemoSkin volumeSet;
    private SliderDemoSkin songSetter;
    private ProButton muter;
    private ProSlider volume;
    private Icon mute;
    private ImageIcon volumeOn;
    private JPanel center;
    private JPanel auxPanel;
    private JPanel songInformation;
    private JLabel songName;
    private JLabel artist;
    private ImageIcon nextbIc;
    private ImageIcon backbIc;
    private ImageIcon shuffleIc;
    public MusicBox() throws IOException {
        super();
        center = new JPanel();
        auxPanel = new JPanel();
        center.setLayout(new FlowLayout(1,50,20));
        center.setBackground(new Color(245 , 245 , 245));
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
        ulikedI =  new ImageIcon();
        ulikedI.setImage(ImageIO.read(new File("Icons\\unliked.png")));
        unliked = new ProButton (ulikedI);
        shuffleIc = new ImageIcon();
        shuffleIc.setImage(ImageIO.read(new File("Icons\\shuffle.png")));
        shuffle = new ProButton(shuffleIc);
        liked =  new ImageIcon();
        liked.setImage(ImageIO.read(new File("Icons\\liked.png")));
        volumeOn = new ImageIcon();
        volumeOn.setImage(ImageIO.read(new File("Icons\\sound.png")));
        muter = new ProButton(volumeOn);

        playb.setPreferredSize(new Dimension(28,28));
        nextb.setPreferredSize(new Dimension(28,28));
        backb.setPreferredSize(new Dimension(28,28));
        shuffle.setPreferredSize(new Dimension(28,28));
        unliked.setPreferredSize(new Dimension(28,28));
        muter.setPreferredSize(new Dimension(28,28));

        songInformation = new JPanel();
        songInformation.setLayout(new BoxLayout(songInformation, BoxLayout.Y_AXIS));
        songName = new JLabel();

        volumeSet = new SliderDemoSkin();
        volumeSet.remove(volumeSet.getSlider());
        volumeSet.setLayout(new FlowLayout());
        volumeSet.add(muter , FlowLayout.LEFT);
        volumeSet.add(volumeSet.getSlider() , FlowLayout.CENTER);

        volumeSet.getSlider().setPreferredSize(new Dimension(100 , 15));
        volumeSet.getSlider().setValue(50);


        songSetter = new SliderDemoSkin();
        songSetter.getSlider().setPreferredSize(new Dimension(600 , 15));
        songSetter.setPreferredSize(new Dimension(600 , 10));
        auxPanel.add(songSetter.getSlider());
        auxPanel.setBackground(new Color(245,245,245));
        songSetter.getSlider().setValue(0);
        this.setLayout(new BorderLayout());
        center.add(shuffle, FlowLayout.LEFT );
        center.add(unliked, FlowLayout.CENTER);
        center.add( nextb , FlowLayout.LEFT,1);
        center.add( playb, FlowLayout.CENTER, 1);
        center.add(backb , FlowLayout.CENTER , 1);
        this.add(center ,BorderLayout.CENTER);
        this.add(volumeSet, BorderLayout.EAST);
        this.add(auxPanel , BorderLayout.NORTH);



        playb.addMouseListener(new Mousehandler());
        unliked.addMouseListener(new Mousehandler());


    }
    private class Mousehandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==unliked) {
                if (e.getClickCount() % 2 == 0)
                    unliked.setIcon(ulikedI);
                else
                    unliked.setIcon(liked);
            }
            if (e.getSource() == playb){
                if(e.getClickCount() % 2==0)
                    playb.setIcon(playI);
                else
                    playb.setIcon(stopI);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==unliked) {
                if (e.getClickCount() % 2 == 0)
                    unliked.setIcon(ulikedI);
                else
                    unliked.setIcon(liked);
            }
            if (e.getSource() == playb){
                if(e.getClickCount() % 2==0)
                    playb.setIcon(playI);
                else
                    playb.setIcon(stopI);
            }
        }

    }

    public JPanel getSongInformation() {
        return songInformation;
    }
}
