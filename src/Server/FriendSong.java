package Server;

import OnlineUsers.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FriendSong extends JPanel {
    private JLabel title;
    private JLabel artist;
    public FriendSong(String titl , String artis){
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY , 1));
        this.setLayout(new GridLayout(1 , 2 , 5 , 5));
        Font font = new Font("serif" , Font.BOLD,  15);
        title = new JLabel(" "+titl);
        title.setFont(font);
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        this.add(title);


        artist = new JLabel(artis);
        artist.setFont(font);
        artist.setBackground(Color.BLACK);
        artist.setForeground(Color.white);
        this.add(artist);

        this.setPreferredSize(new Dimension(getWidth()  ,30 ));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.darkGray);
                revalidate();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.BLACK);
                revalidate();
                repaint();
            }
        });

    }

    public void setArtist(String artist) {
        this.artist.setText(artist);
    }
    public void setTitle(String title){
        this.title.setText(title);
    }
}
