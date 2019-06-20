package OnlineUsers;

import Center.Center;

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

public class OnlineUsers extends JPanel {
    private JLabel frinedsActivity;
    private Font headFont;
    private Font pubFont;
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    public OnlineUsers() throws IOException {
        super();
        this.setBackground(Color.darkGray);
        frinedsActivity = new JLabel("Friend Activity");
        originalImage = ImageIO.read(new File("backgrounds\\left1.jpg"));
        headFont = new Font("serif" , Font.BOLD , 30);
        pubFont = new Font("serif " , Font.PLAIN , 15);
        frinedsActivity.setForeground(Color.white);
        frinedsActivity.setFont(headFont);
        this.add(frinedsActivity);
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
