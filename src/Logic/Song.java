package Logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Song {
    private int pause=0;
    private AdvancedPlayer advancedPlayer;
    private String fileName;
    private  Thread playTheread;
    private FindTags findTags;
    private String artist;
    private String album;
    private String  title;
    private  String track;
    private File file;

    public Song(String fileName) throws IOException, JavaLayerException {
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


    }
    public Song(File file) throws IOException, JavaLayerException {
        playTheread=new Thread();
        this.file = file;
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
        }
        else if(playTheread==null){
            System.out.println("sd");
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

    public FindTags getFindTags() {
        return findTags;
    }
}
