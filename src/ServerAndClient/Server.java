package ServerAndClient;

import Logic.Song;
import Music.SongPanel;
import Musicbox.MusicBox;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static Logic.Song.playTheread;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket client;
    private InputStream input;
    private ObjectOutputStream out;
    private ArrayList<SongPanel>sharedPLayList;
    private MusicBox musicBox;

    public Server(ArrayList<SongPanel> sharedPLayList, MusicBox musicBox) throws IOException {
        this.sharedPLayList = sharedPLayList;
        serverSocket=new ServerSocket(13000);
        this.musicBox=musicBox;
    }

    @Override
    public void run() {

            try {
                client=serverSocket.accept();
                System.out.println("connected");
                input=client.getInputStream();
                out=new ObjectOutputStream(client.getOutputStream());
                out.writeObject(serializeSongs(sharedPLayList));
                out.flush();
                Scanner scan=new Scanner(input);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String whatCanIdo=scan.next();
                            if(whatCanIdo.equals("getSong")){
                                int number=scan.nextInt();
                                byte[] bytes=new byte[new FileInputStream(sharedPLayList.get(number).getSong().getFileName()).available()];
                                new FileInputStream(sharedPLayList.get(number).getSong().getFileName()).read(bytes);
                                out.writeObject(bytes);
                            }
                            else if(whatCanIdo.equals("removeFromGroup")){
                            }
                            else if(whatCanIdo.equals("addToGroup")){
                            }
                            else if(whatCanIdo.equals("playSimulaneously")){
                                int number=scan.nextInt();
                                byte[] bytes=new byte[new FileInputStream(sharedPLayList.get(number).getSong().getFileName()).available()];
                                new FileInputStream(sharedPLayList.get(number).getSong().getFileName()).read(bytes);
                                out.writeObject(bytes);
                                new Robot().delay(200);
                                if(playTheread!=null)
                                    playTheread.stop();
                                musicBox.setSongPanel(sharedPLayList.get(number));
                                sharedPLayList.get(number).getSong().play(0);
                                musicBox.setInfo(sharedPLayList.get(number).getSong().getTitle(),sharedPLayList.get(number).getSong().getArtist());
                            }
                            else if(whatCanIdo.equals("wichSong")){

                            }
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (AWTException e) {
                                e.printStackTrace();
                            } catch (InvalidDataException e) {
                                e.printStackTrace();
                            } catch (UnsupportedTagException e) {
                                e.printStackTrace();
                            } catch (JavaLayerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public Socket getClient() {
        return client;
    }

    private ArrayList<SerializedData> serializeSongs(ArrayList<SongPanel> sharedPLayList){
        ArrayList<SerializedData> dataArrayList=new ArrayList<>();
        for (SongPanel songPanel:sharedPLayList) {
            dataArrayList.add(new SerializedData(songPanel.getSong().getArtist(),songPanel.getSong().getTitle()));
        }
        return dataArrayList;
    }
}
