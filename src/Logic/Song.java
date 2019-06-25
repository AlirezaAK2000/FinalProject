package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Song {
    private int pause=0;
    private AdvancedPlayer advancedPlayer;
    private String fileName;
    private FindTags findTags;
    private String artist;
    private String album;
    private String  title;
    private  String track;
    private ImageIcon artWork;
    private File file;
    public static Thread playTheread;

    public Song(String fileName) throws IOException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        playTheread=new Thread();
        advancedPlayer=new AdvancedPlayer(new FileInputStream(fileName));
        advancedPlayer.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent playbackEvent) {
                super.playbackFinished(playbackEvent);
                pause =playbackEvent.getFrame();
            }
        });
        this.fileName = fileName;
        findTags=new FindTags(fileName);
        artist=findTags.getArtist();
        album=findTags.getAlbum();
        title=findTags.getTitle();
        track=findTags.getTrack();
        Mp3File mp3File=new Mp3File(fileName);
        artWork=new ImageIcon(mp3File.getId3v2Tag().getAlbumImage());



    }
    public Song(File file) throws IOException, JavaLayerException, InvalidDataException, UnsupportedTagException {
        playTheread=new Thread();
        this.file = file;
        fileName=file.getPath();
        advancedPlayer=new AdvancedPlayer(new FileInputStream(file));
        advancedPlayer.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent playbackEvent) {
                super.playbackFinished(playbackEvent);
                pause =playbackEvent.getFrame();
            }
        });

        findTags=new FindTags(file);
        artist=findTags.getArtist();
        album=findTags.getAlbum();
        title=findTags.getTitle();
        track=findTags.getTrack();
        Mp3File mp3File=new Mp3File(file.getPath());
        try {
            artWork = new ImageIcon(mp3File.getId3v2Tag().getAlbumImage());
        }
        catch (NullPointerException e){
            System.out.println("no artwork");
        }

    }
    public void continuee() throws FileNotFoundException, JavaLayerException {
        if(playTheread==null)
        {

            advancedPlayer=new AdvancedPlayer(new FileInputStream(fileName));
            advancedPlayer.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent playbackEvent) {
                    super.playbackFinished(playbackEvent);
                    pause+=playbackEvent.getFrame();
                }
            });
            playTheread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        advancedPlayer.play((pause)/26,Integer.MAX_VALUE);
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            });
            playTheread.start();
        }
    }

    public void play(int place) throws FileNotFoundException, JavaLayerException {
        if(playTheread!=null)
        {
            pause=place;
            playTheread.stop();
            System.out.println("First"+Thread.activeCount());
            advancedPlayer=new AdvancedPlayer(new FileInputStream(fileName));
            advancedPlayer.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent playbackEvent) {
                    super.playbackFinished(playbackEvent);
                    pause+=playbackEvent.getFrame();
                }
            });
            playTheread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        advancedPlayer.play((0+pause)/26,Integer.MAX_VALUE);
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            });
            playTheread.start();
            System.out.println("Second" +Thread.activeCount());
        }
        else if(playTheread==null){
               pause=place;
        }
    }
    public void pause(){
        if(playTheread!=null && playTheread.isAlive()) {
            advancedPlayer.stop();
            playTheread.stop();
            playTheread=null;
        }



    }

    public Thread getPlayTheread() {
        return playTheread;
    }

    public String getTrack() {
        return track;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public ImageIcon getArtWork() {
        return artWork;
    }

    public FindTags getFindTags() {
        return findTags;
    }
    public int getSize() throws IOException, InvalidDataException, UnsupportedTagException {
        return (int)new Mp3File(fileName).getLengthInMilliseconds();
    }
}
