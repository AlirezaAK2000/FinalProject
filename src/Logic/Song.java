package Logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Song {
    private int pause=0;
    private AdvancedPlayer advancedPlayer;
    private String fileName;
    private  Thread playTheread;

    public Song(String fileName) throws FileNotFoundException, JavaLayerException {
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
}
