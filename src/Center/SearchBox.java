package Center;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SearchBox extends JPanel {
    private JTextField searcher;
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    public SearchBox() throws IOException {
        searcher = new JTextField("Search");
        searcher.setPreferredSize(new Dimension(250 ,30 ));
        searcher.setEditable(true);
        originalImage = ImageIO.read(new File("backgrounds\\left2.jpg"));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.darkGray);
        this.add(searcher , BorderLayout.WEST);
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


    private class ResizerListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
            double heightScaleFactor = getHeight() / (double)originalImage.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(widthScaleFactor, heightScaleFactor );

            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaledImage = scaleOp.filter(originalImage, null);

            repaint();
        }


    }
}
