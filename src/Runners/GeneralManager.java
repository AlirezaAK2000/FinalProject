package Runners;

import Boxoffice.Boxoffice;
import Center.Center;
import Musicbox.MusicBox;
import OnlineUsers.OnlineUsers;
import Tools.Saver;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GeneralManager extends JFrame {
    private Boxoffice boxoffice;
    private OnlineUsers onlineUsers;
    private Center center;
    private MusicBox musicBox;
    private Image img;
    private ObjectInputStream loader;
    private Saver saver;
    public GeneralManager() throws IOException, InterruptedException, UnsupportedTagException, InvalidDataException, JavaLayerException, ClassNotFoundException {
        super();
        onlineUsers = new OnlineUsers();
        musicBox = new MusicBox();
        center = new Center(musicBox);

        onlineUsers = new OnlineUsers();

        try {
            File file = new File("everyThing.ser");
            loader = new ObjectInputStream(new FileInputStream(file));
            saver = (Saver) loader.readObject();
            boxoffice = new Boxoffice(center, "loading");
            boxoffice.setData(saver.getData());
            loader.close();
        }catch (FileNotFoundException e){
            boxoffice = new Boxoffice(center, "");
        }


        JScrollPane j1 = new JScrollPane(onlineUsers);
        j1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLayout(new BorderLayout());
        this.add(center , BorderLayout.CENTER);
        this.add(j1 , BorderLayout.EAST);
        this.add(boxoffice , BorderLayout.WEST);
        this.add(musicBox , BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension min = new Dimension(500, 500);
        this.setMinimumSize(min);
        this.setSize(800 , 800);
        this.setVisible(true);
    }

    public Boxoffice getBoxoffice() {
        return boxoffice;
    }

    public MusicBox getMusicBox() {
        return musicBox;
    }

    public OnlineUsers getOnlineUsers() {
        return onlineUsers;
    }
}
