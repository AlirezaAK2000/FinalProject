package Music;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class SongPanels extends JPanel implements Serializable {
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private ArrayList<SongPanel> songPanels;
    public SongPanels(String address) throws IOException {
        super();
        songPanels = new ArrayList<>();
        originalImage = ImageIO.read(new File(address));
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(5));
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
    public void addSong(SongPanel songPanel){
        songPanels.add(songPanel);
        this.add(songPanel);
        this.add(Box.createVerticalStrut(5));
        SwingUtilities.updateComponentTreeUI(this);
    }


    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double widthScaleFactor = getParent().getWidth() / (double)originalImage.getWidth();
            double heightScaleFactor = getParent().getHeight() / (double)originalImage.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(widthScaleFactor, heightScaleFactor );

            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaledImage = scaleOp.filter(originalImage, null);

            repaint();
        }
    }


}
