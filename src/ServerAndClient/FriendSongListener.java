package ServerAndClient;

import Logic.Song;
import Music.PlaybListener;
import Music.SliderListener;
import Musicbox.MusicBox;
import Tools.Background;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FriendSongListener extends MouseAdapter {
    private int numberOfsong;
    private Client client;
    private FriendSong friendSong;
    private MusicBox musicBox;
    private Background artwork;

    public FriendSongListener(int numberOfsong, Client client, FriendSong friendSong, MusicBox musicBox, Background artwork) {
        this.numberOfsong = numberOfsong;
        this.client = client;
        this.friendSong = friendSong;
        this.musicBox = musicBox;
        this.artwork = artwork;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON3){
            PopupMenu popupMenu=new PopupMenu();
            MenuItem menuItem1=new MenuItem("PLAY Simulneously");
            popupMenu.add(menuItem1);
            friendSong.add(popupMenu);
            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                    client.getPrintWriter().println("playSimulaneously");
                    client.getPrintWriter().println(numberOfsong);
                    client.getPrintWriter().flush();
                    FileOutputStream fileOutputStream=new FileOutputStream("playSimulaneonly.txt");
                    fileOutputStream.write((byte[])client.getObjectInputStream().readObject());
                    Song song=new Song("playSimulaneonly.txt");
                    musicBox.getPlayb().addActionListener(new PlaybListener(song,musicBox));
                    musicBox.getSlider().addMouseListener(new SliderListener(musicBox.getSlider(),musicBox,song));
                    artwork.SetBack(song.getArtWork().getImage());
                    musicBox.setInfo(song.getTitle(),song.getArtist());
                    musicBox.getSlider().setValue(0);
                    musicBox.getSlider().setMaximum(song.getSize()/1000);
                    song.play(0);



                } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedTagException ex) {
                        ex.printStackTrace();
                    } catch (JavaLayerException ex) {
                        ex.printStackTrace();
                    } catch (InvalidDataException ex) {
                        ex.printStackTrace();
                    }
                }
            });


        }
    }
}
