package Center;


import Musicbox.MusicBox;
import Tools.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.sql.BatchUpdateException;

public class Center extends JPanel {
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private SearchBox searchBox;
    private JPanel main;
    private JPanel background;
    private MusicBox musicBox;

    public Center(MusicBox musicBox) throws IOException {
        super();
        this.musicBox = musicBox;
        originalImage = ImageIO.read(new File("backgrounds\\center4.jpg"));
        background = new Background(originalImage);
        this.setLayout(new BorderLayout());
        searchBox = new SearchBox();
        this.add(searchBox , BorderLayout.NORTH);
        this.add(new JScrollPane(background) , BorderLayout.CENTER);

    }

    public MusicBox getMusicBox() {
        return musicBox;
    }

    public void setMain(JPanel main) {
        removeAll();
        this.add(searchBox , BorderLayout.NORTH);
        background = main;
        this.add(new JScrollPane(background) , BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public JPanel getBack(){
        return background;
    }
}



