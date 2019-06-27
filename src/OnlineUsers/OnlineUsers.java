package OnlineUsers;

import ServerAndClient.FriendList;
import ServerAndClient.FriendSong;
import Tools.ProButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private ProButton addFriend;
    public OnlineUsers() throws IOException, InterruptedException {
        super();
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY , 1));
        users = new ArrayList<>();
        frinedsActivity = new JLabel("Friend Activity     ");
        originalImage = ImageIO.read(new File("backgrounds\\left1.jpg"));
        headFont = new Font("serif" , Font.BOLD , 20);
        pubFont = new Font("serif " , Font.PLAIN , 15);
        frinedsActivity.setForeground(Color.white);
        frinedsActivity.setFont(headFont);
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS ));

        addFriend = new ProButton("  Add Friend");
        addFriend.setBackground(Color.BLACK);
        addFriend.setForeground(Color.white);
        addFriend.setFont(pubFont);
        addFriend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                addFriend.setFont(new Font("serif " , Font.BOLD , 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addFriend.setFont(pubFont);
            }
        });

        this.add(addFriend);
        this.add(frinedsActivity);



        //******test

        FriendList friendList = new FriendList();
        friendList.addSong(new FriendSong("harvaght" , "tatalo mother ghahbe"));
        friendList.addSong(new FriendSong("harvaght" , "tatalo mother ghahbe"));
        friendList.addSong(new FriendSong("harvaght" , "tatalo mother ghahbe"));
        friendList.addSong(new FriendSong("harvaght" , "tatalo mother ghahbe"));
        friendList.addSong(new FriendSong("harvaght" , "tatalo mother ghahbe"));
        Friend u1 = new Friend("on or of" ,friendList );
        addOnlineUser(u1);

    }

    public ProButton getAddFriend() {
        return addFriend;
    }

    //    public void paintComponent(Graphics g) {
//        double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
//        double heightScaleFactor = getHeight() / (double)originalImage.getHeight();
//
//        AffineTransform at = new AffineTransform();
//        at.scale(widthScaleFactor, heightScaleFactor );
//
//        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//        scaledImage = scaleOp.filter(originalImage, null);
//
//
//        g.drawImage(scaledImage, 0, 0,null);
//        addComponentListener(new ResizerListener());
//    }
//
//
//    private class ResizerListener extends ComponentAdapter {
//        @Override
//        public void componentResized(ComponentEvent e) {
//            double widthScaleFactor = getWidth() / (double)originalImage.getWidth();
//            double heightScaleFactor = getHeight() / (double)originalImage.getHeight();
//
//            AffineTransform at = new AffineTransform();
//            at.scale(widthScaleFactor, heightScaleFactor );
//
//            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//            scaledImage = scaleOp.filter(originalImage, null);
//
//            repaint();
//        }
//
//
//    }
    public void addOnlineUser(JPanel user){
        add(user);
        revalidate();
        repaint();
    }
    public void removeUser(JPanel user){

    }

}
