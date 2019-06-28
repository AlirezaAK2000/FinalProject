package Runners;

import Boxoffice.Boxoffice;
import ServerAndClient.*;
import Center.Center;
import OnlineUsers.*;
import Logic.Song;
import Music.SongPanel;
import Musicbox.MusicBox;
import OnlineUsers.OnlineUsers;
import ServerAndClient.Server;
import Tools.Saver;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GeneralManager extends JFrame {
    private Boxoffice boxoffice;
    private OnlineUsers onlineUsers;
    private Center center;
    private MusicBox musicBox;
    private Image img;
    private ObjectInputStream loader;
    private Saver saver;
    public GeneralManager() throws IOException, InterruptedException, UnsupportedTagException, InvalidDataException, JavaLayerException, ClassNotFoundException {
        this.setMinimumSize(new Dimension(900 , 800));
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

        Server server=new Server(boxoffice.getSharedList().getSongPanelList(),musicBox);
        new Thread(server).start();
        onlineUsers.getAddFriend().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FriendAdder friendAdder = new FriendAdder();
                    friendAdder.getCancelButt().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            friendAdder.setVisible(false);
                        }
                    });
                    friendAdder.getoKClick().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            friendAdder.setVisible(false);
                            if (!friendAdder.getjTextField().getText().equals("")) {
                                try {
                                       Client client = new Client(friendAdder.getjTextField().getText(), 13000);
                                    ArrayList<SerializedData> data = (ArrayList<SerializedData>) client.getObjectInputStream().readObject();
                                    FriendList friendList = new FriendList();
                                    for (SerializedData serializedData : data) {
                                        FriendSong friendSong = new FriendSong(serializedData.getTitle(), serializedData.getNameOfArtist());
                                        friendSong.addMouseListener(new FriendSongListener(data.indexOf(serializedData),client,friendSong,musicBox,boxoffice.getArtwork()));
                                        friendList.addSong(friendSong);
                                        friendSong.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                super.mouseClicked(e);
                                                try {


                                                    client.getPrintWriter().println("getSong");
                                                    client.getPrintWriter().println(data.indexOf(serializedData));
                                                    client.getPrintWriter().flush();
                                                    byte[] bytes = (byte[]) client.getObjectInputStream().readObject();
                                                    System.out.println(serializedData.getTitle());
                                                    File file1=new File(serializedData.getTitle() + ".mp3");
                                                    file1.createNewFile();
                                                    FileOutputStream file = new FileOutputStream(serializedData.getTitle() + ".mp3");
                                                    file.write(bytes);
                                                    boxoffice.getSongRepository().addSong(new SongPanel(new Song(serializedData.getTitle() + ".mp3")));
                                                    if (musicBox.getSongPanels().equals(boxoffice.getSongRepository()) && musicBox.getSongPanels()!=null) {
                                                        musicBox.getSongPanels().repaintList();
                                                    }
                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                } catch (JavaLayerException ex) {
                                                    ex.printStackTrace();
                                                } catch (UnsupportedTagException ex) {
                                                    ex.printStackTrace();
                                                } catch (InvalidDataException ex) {
                                                    ex.printStackTrace();
                                                } catch (ClassNotFoundException ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                    Friend friend = new Friend("on", friendList);
                                    onlineUsers.addOnlineUser(friend);

                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                } catch (ClassNotFoundException ex) {
                                    System.err.println("IP is Wrong!");
                                }

                            }
                        }
                    });



                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
            }
        });


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
