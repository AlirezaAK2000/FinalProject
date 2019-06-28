package ServerAndClient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FriendList extends JPanel {
    private JPanel container;
    private ArrayList<FriendSong> friendSongs;
    public FriendList(){
        super();
        friendSongs = new ArrayList<>();
        this.setBackground(Color.BLACK);
        this.setSize(new Dimension(400 ,400));
        this.setLayout(new BorderLayout());
        container = new JPanel();
        container.setBorder(BorderFactory.createLineBorder(Color.GRAY , 2));
        container.setBackground(Color.BLACK);
        container.setLayout(new BoxLayout(container , BoxLayout.Y_AXIS));

        this.add(new JScrollPane(container) ,BorderLayout.CENTER);
    }
    public void addSong(FriendSong friendSong){
        friendSongs.add(friendSong);
        container.add(friendSong);
        container.add(Box.createVerticalStrut(5));
    }

    public void setFriendSongs(ArrayList<FriendSong> friendSongs) {

        for (FriendSong f:friendSongs){
            this.friendSongs.add(f);
            container.add(f);
            container.add(Box.createVerticalStrut(5));
        }
    }

    public void show(){
//        this.setLocation(600 , 600);
        this.setVisible(true);
    }
    public void close(){
        this.setVisible(false);
    }
}
