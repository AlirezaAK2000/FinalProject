package Musicbox;

import Tools.ProButton;
import Tools.ProSlider;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MusicBox extends JPanel  {
    private ProButton playb;
    private Icon playI;
    private Icon stopI;
    private ProButton nextb;
    private ProButton backb;
    private ProButton unliked;
    private ImageIcon ulikedI;
    private ProButton shuffle;
    private ImageIcon liked;
    private JPanel volumeSet;
    private ProButton muter;
    private ProSlider volume;
    private Icon mute;
    private Icon volumeOn;
    private JPanel center;
    public MusicBox() throws IOException {
        super();
        center = new JPanel();
        center.setLayout(new FlowLayout(1,50,20));
        center.setBackground(new Color(245 , 245 , 245));
        stopI =new ImageIcon("Icons\\stop.png");
        nextb = new ProButton (new ImageIcon("Icons\\nextt.png"));
        backb = new  ProButton (new ImageIcon("Icons\\back.png"));
        playI = new ImageIcon("Icons\\play.png");
        playb = new ProButton ((ImageIcon) playI);
        ulikedI =  new ImageIcon("Icons\\unliked.png");
        unliked = new ProButton (ulikedI);
        shuffle = new ProButton( new ImageIcon("Icons\\shuffle.png"));
        liked =  new ImageIcon("Icons\\liked.png");
//        mute = new ImageIcon(getClass().getResource("C:\\Users\\lenovo\\Desktop\\p2\\Project\\src\\mute"));
//        muter = new JButton(mute);

        playb.setPreferredSize(new Dimension(26,28));
        nextb.setPreferredSize(new Dimension(26,28));
        backb.setPreferredSize(new Dimension(26,28));
        shuffle.setPreferredSize(new Dimension(26,28));
        unliked.setPreferredSize(new Dimension(26,28));
//        muter.setPreferredSize(new Dimension(33,34));


        volume = new ProSlider();
        volumeSet = new JPanel();
        volume.setPreferredSize(new Dimension(30,30));
        volume.setPreferredSize(new Dimension(100 , 30));
        volumeSet.setLayout(new BorderLayout());
        muter = new ProButton();
        volume.setValue(60);

        volumeSet.add(muter , BorderLayout.WEST);
        volumeSet.add(volume,BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        center.add(unliked , FlowLayout.LEFT );
        center.add(shuffle, FlowLayout.CENTER);
        center.add( nextb , FlowLayout.LEFT,1);
        center.add( playb, FlowLayout.CENTER, 1);
        center.add(backb , FlowLayout.CENTER , 1);
        this.add(center ,BorderLayout.CENTER);
        this.add( volumeSet , BorderLayout.EAST);

        playb.addMouseListener(new Mousehandler());
        unliked.addMouseListener(new Mousehandler());


    }
    private class Mousehandler implements MouseListener {
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

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
