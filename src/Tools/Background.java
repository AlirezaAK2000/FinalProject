package Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;

public class Background extends JPanel {
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    public Background(BufferedImage originalImage){
        this.originalImage = originalImage;
    }
    public void paintComponent(Graphics g) {
        double widthScaleFactor = getParent().getWidth() / (double)originalImage.getWidth();
        double heightScaleFactor = getParent().getHeight() / (double)originalImage.getHeight();

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
