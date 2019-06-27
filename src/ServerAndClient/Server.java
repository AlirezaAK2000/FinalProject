package ServerAndClient;

import Logic.Song;
import Musicbox.MusicBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket client;
    private InputStream input;
    private ObjectOutputStream out;
    private ArrayList<Song>sharedPLayList;
    private MusicBox musicBox;

    public Server(ArrayList<Song> sharedPLayList, MusicBox musicBox) throws IOException {
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
                            String whatCanIdo=scan.next();
                            try {
                            if(whatCanIdo.equals("getSong")){
                                int number=scan.nextInt();
                                byte[] bytes=new byte[new FileInputStream(sharedPLayList.get(number).getFileName()).available()];
                                new FileInputStream(sharedPLayList.get(number).getFileName()).read(bytes);
                                out.writeObject(bytes);
                            }
                            else if(whatCanIdo.equals("removeFromGroup")){

                            }
                            else if(whatCanIdo.equals("addToGroup")){

                            }
                            else if(whatCanIdo.equals("playSimulaneously")){

                            }
                            else if(whatCanIdo.equals("wichSong")){

                            }
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();








            } catch (IOException e) {
                e.printStackTrace();
            }


    }
    private ArrayList<SerializedData> serializeSongs(ArrayList<Song> sharedPLayList){
        ArrayList<SerializedData> dataArrayList=new ArrayList<>();
        for (Song song:sharedPLayList) {
            dataArrayList.add(new SerializedData(song.getArtist(),song.getTitle()));
        }
        return dataArrayList;
    }
}
