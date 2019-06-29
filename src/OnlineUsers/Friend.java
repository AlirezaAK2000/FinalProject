package OnlineUsers;

import ServerAndClient.FriendList;
import ServerAndClient.FriendSongListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Friend extends JPanel {
    private JPanel information;
    private JLabel title;
    private JLabel artist;
    private JLabel onORof;
    private JLabel userNameFriend;
    private FriendList friendList;
    public Friend( String onOro , FriendList friendList){
        super();
        this.friendList=friendList;
        Font font = new Font("serif" , Font.BOLD , 15);
        information = new JPanel();
        information.setBackground(Color.BLACK);
        information.setLayout(new BoxLayout(information , BoxLayout.Y_AXIS));
        this.setMaximumSize(new Dimension(300 ,120 ));
        information.setBorder(BorderFactory.createLineBorder(Color.gray , 1 , true));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.add(information,BorderLayout.CENTER);
        this.add(Box.createVerticalStrut(5) , BorderLayout.NORTH);
        this.add(Box.createHorizontalStrut(5) , BorderLayout.WEST);
        this.add(Box.createHorizontalStrut(5) , BorderLayout.EAST);
        this.add(Box.createVerticalStrut(5) , BorderLayout.SOUTH);
        title = new JLabel("  ");
        title.setForeground(Color.white);
        title.setBackground(Color.BLACK);
        title.setFont(font);
        artist = new JLabel("  ");
        artist.setForeground(Color.white);
        artist.setBackground(Color.BLACK);
        artist.setFont(font);
        onORof = new JLabel("  "+onOro);
        onORof.setForeground(Color.white);
        onORof.setBackground(Color.BLACK);
        onORof.setFont(font);
        userNameFriend=new JLabel(" ");
        userNameFriend.setForeground(Color.white);
        userNameFriend.setBackground(Color.BLACK);
        userNameFriend.setFont(font);

        information.add(userNameFriend);
        information.add(Box.createVerticalStrut(8));
        information.add(title);
        information.add(Box.createVerticalStrut(8));
        information.add(artist);
        information.add(Box.createVerticalStrut(8));
        information.add(onORof);


        information.addMouseListener(new FriendClickListener());
    }

    public void setArtist(String artist) {
        this.artist.setText(artist);
        revalidate();
        repaint();
    }
    public void setFriendList(FriendList friendList){
        this.friendList=friendList;
    }

    public void setOnORof(String onORof) {
        this.onORof.setText(onORof);
        revalidate();
        repaint();
    }
    public void setUserNameFriend(String userNameFriend) {
        this.userNameFriend.setText(userNameFriend);
        revalidate();
        repaint();
    }

    public void setTitle(String title) {
        this.title.setText(title);
        revalidate();
        repaint();
    }
    class FriendClickListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            JFrame shower = new JFrame();
            shower.setBackground(Color.BLACK);
            shower.add(friendList);
            shower.setSize(new Dimension(400 ,200));
            shower.setVisible(true);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            information.getParent().setBackground(Color.DARK_GRAY);
            information.setBackground(Color.DARK_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            information.getParent().setBackground(Color.BLACK);
            information.setBackground(Color.BLACK);
        }
}}
