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
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
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
    private String username;
    private ArrayList<String> users;
    public GeneralManager(String username) throws IOException, InterruptedException, UnsupportedTagException, InvalidDataException, JavaLayerException, ClassNotFoundException {
        this.setIconImage(new ImageIcon("C:\\Users\\hasein\\Desktop\\FinalProject\\backgrounds\\tataloo.jpg").getImage());
        this.setMinimumSize(new Dimension(900 , 800));
        this.username = username;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.ser"));
            users = (ArrayList<String>) in.readObject();
            in.close();
        }catch (FileNotFoundException e){
            users = new ArrayList<>();
        }
        musicBox = new MusicBox();
        center = new Center(musicBox , username);
        onlineUsers = new OnlineUsers();
        try {
            File file = new File(username+"everyThing.ser");
            loader = new ObjectInputStream(new FileInputStream(file));
            saver = (Saver) loader.readObject();
            boxoffice = new Boxoffice(center, "loading");
            musicBox.setArtWork(boxoffice.getArtwork());
            boxoffice.setData(saver.getData());
            loader.close();
        }catch (FileNotFoundException e){
            boxoffice = new Boxoffice(center, "");
            musicBox.setArtWork(boxoffice.getArtwork());
        }
        center.setRepos(boxoffice.getSongPanelrepoos());

        if(!users.contains(username)){
            users.add(username);
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.ser"));
        out.writeObject(users);
        out.close();

        JScrollPane j1 = new JScrollPane(onlineUsers);
        j1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLayout(new BorderLayout());
        this.add(center , BorderLayout.CENTER);
        this.add(j1 , BorderLayout.EAST);
        this.add(boxoffice , BorderLayout.WEST);
        this.add(musicBox , BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Server server=new Server(boxoffice.getSharedList().getSongPanelList(),musicBox,username);
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
                                    String userName = (String) client.getObjectInputStream().readObject();
                                    FriendList friendList=new FriendList();
                                    Friend friend = new Friend("Online", friendList);
                                    friend.setUserNameFriend(userName);
                                    Timer timerUpdateFriendInformation=new Timer(15000,new UpedateFriendIformation(client,friend));
                                    timerUpdateFriendInformation.start();
                                    new Robot().delay(7000);
                                    Timer updateFriendList=new Timer(15000,new UpdateFriendList(client,friend));
                                    updateFriendList.start();
                                    onlineUsers.addOnlineUser(friend);

                                } catch (ConnectException ex){
                                    System.out.println("not connect");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                } catch (ClassNotFoundException ex) {
                                    System.err.println("IP is Wrong!");
                                } catch (AWTException ex) {
                                    ex.printStackTrace();
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
    public void downLoadFilesFromServer(Client client, ArrayList<SerializedData> data, SerializedData serializedData){
        try {
            client.getPrintWriter().println("getSong");
            client.getPrintWriter().println(data.indexOf(serializedData));
            client.getPrintWriter().flush();
            byte[] bytes = (byte[]) client.getObjectInputStream().readObject();
            System.out.println(serializedData.getTitle());
            File file1=new File(serializedData.getTitle().trim() + ".mp3");
            if(!file1.exists())
                file1.createNewFile();
            FileOutputStream file = new FileOutputStream(serializedData.getTitle().trim() + ".mp3");
            file.write(bytes);
            boxoffice.addProcess(new SongPanel(new Song(file1)));
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
        }catch (NullPointerException eee){
        }
    }

    public MusicBox getMusicBox() {
        return musicBox;
    }


    public OnlineUsers getOnlineUsers() {
        return onlineUsers;
    }
    private Long getCurrentTime(){
        Long time= (Long)System.currentTimeMillis()/1000;
        return time;
    }
    private String calculateTime(Long time){

        return ""+time/3600+":"+(time%3600)/60+":"+(time%3600)%60;
    }
    //******************************************************************************8
    //****** first inner class
    class UpedateFriendIformation implements ActionListener{
        private  Client client;
        private  Friend friend;

        public UpedateFriendIformation(Client client, Friend friend) {
            this.client = client;
            this.friend = friend;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                client.getPrintWriter().println("WichSong");
                client.getPrintWriter().flush();
                SerializedData serializedData = (SerializedData) client.getObjectInputStream().readObject();
                friend.setArtist(serializedData.getNameOfArtist().trim());
                friend.setTitle(serializedData.getTitle().trim());
            } catch (SocketException e23) {
                friend.setOnORof(calculateTime(getCurrentTime()));
                FriendList friendList = new FriendList();
                friend.setFriendList(friendList);
            }
                catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    //****************************************************************
    //************** second inner class
    class UpdateFriendList implements ActionListener{
        private  Client client;
        private  Friend friend;

        public UpdateFriendList(Client client, Friend friend) {
            this.client = client;
            this.friend = friend;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            client.getPrintWriter().println("getPlayList");
            client.getPrintWriter().flush();
            ArrayList<SerializedData> data = (ArrayList<SerializedData>) client.getObjectInputStream().readObject();
            ArrayList<FriendSong> friendSongs=new ArrayList<>();
            FriendList friendList = new FriendList();
            for (SerializedData serializedData : data) {
                FriendSong friendSong = new FriendSong(serializedData.getTitle(), serializedData.getNameOfArtist());
                friendSong.addMouseListener(new FriendSongListener(data.indexOf(serializedData),client,friendSong,musicBox,boxoffice.getArtwork()));
                friendSongs.add(friendSong);
                friendSong.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        downLoadFilesFromServer(client,data,serializedData);
                    }
                });
        }
            friendList.setFriendSongs(friendSongs);
            friend.setFriendList(friendList);
             } catch (SocketException e23){
                friend.setOnORof(calculateTime(getCurrentTime()));
                FriendList friendList=new FriendList();
                friend.setFriendList(friendList);
             } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }}
}
