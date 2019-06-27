package OnlineUsers;

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
import java.util.ArrayList;

public class OnlineUsers extends JPanel {
    private JLabel frinedsActivity;
    private Font headFont;
    private Font pubFont;
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private ArrayList<JPanel> users;
    public OnlineUsers() throws IOException, InterruptedException {
        super();
        this.setBackground(Color.darkGray);
        users = new ArrayList<>();
        frinedsActivity = new JLabel("Friend Activity");
        originalImage = ImageIO.read(new File("backgrounds\\left1.jpg"));
        headFont = new Font("serif" , Font.BOLD , 20);
        pubFont = new Font("serif " , Font.PLAIN , 15);
        frinedsActivity.setForeground(Color.white);
        frinedsActivity.setFont(headFont);
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS ));
        this.add(frinedsActivity);

        Friend u1 = new Friend("title" , "artist" ,"on or of");
        addOnlineUser(u1);
        Friend u2 = new Friend("title" , "artist" ,"on or of");
        addOnlineUser(u2);
        Friend u3 = new Friend("title" , "artist" ,"on or of");
        addOnlineUser(u3);
        Friend u4 = new Friend("title" , "artist" ,"on or of");

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
    public void addOnlineUser(JPanel user){
        add(user);
        revalidate();
        repaint();
    }
    public void removeUser(JPanel user){

    }

}
