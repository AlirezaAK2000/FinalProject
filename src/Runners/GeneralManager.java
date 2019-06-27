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
    private Client client;
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
        ArrayList<Song>sharedeSongList=new ArrayList<>();
        for (SongPanel songPanel:boxoffice.getSharedList().getSongPanelList()) {
            sharedeSongList.add(songPanel.getSong());
        }
        Server server=new Server(sharedeSongList,musicBox);
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
                                    client = new Client(friendAdder.getjTextField().getText(), 13000);
                                    ArrayList<SerializedData> data = (ArrayList<SerializedData>) client.getObjectInputStream().readObject();
                                    FriendList friendList = new FriendList();
                                    for (SerializedData serializedData : data) {
                                        FriendSong friendSong = new FriendSong(serializedData.getTitle(), serializedData.getNameOfArtist());
                                        friendList.addSong(friendSong);
                                        friendSong.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                super.mouseClicked(e);
                                                client.getPrintWriter().println("getSong");
                                                client.getPrintWriter().println(data.indexOf(serializedData));
                                                client.getPrintWriter().flush();
                                                downloadSong(client, serializedData);

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
    private void downloadSong(Client client,SerializedData serializedData) {
        try {


            byte[] bytes = (byte[]) client.getObjectInputStream().readObject();
            System.out.println(serializedData.getTitle());
            FileWriter fileWriter = new FileWriter(serializedData.getTitle() + ".txt");
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
            FileOutputStream file = new FileOutputStream(serializedData.getTitle() + ".txt");
            file.write(bytes);
            boxoffice.getSongRepository().addSong(new SongPanel(new Song(serializedData.getTitle() + ".txt")));
            if (musicBox.getSongPanels().equals(boxoffice.getSongRepository())) {
                musicBox.getSongPanels().repaintList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public OnlineUsers getOnlineUsers() {
        return onlineUsers;
    }
}
