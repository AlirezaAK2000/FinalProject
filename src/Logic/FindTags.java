package Logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FindTags {
    private FileInputStream myFile;
    private byte[] allBytes;
    public FindTags(String nameOfFile) throws IOException {
        myFile=new FileInputStream(nameOfFile);
        allBytes = new byte[myFile.available()];
        myFile.read(allBytes);
    }
    public String getTitle(){
        byte[] my=new byte[30];
        for(int i=allBytes.length-129+4,t=0;i<allBytes.length-129+30+4;++i,++t)
        {
            my[t]=allBytes[i];
        }
        return new String(my);

    }
    public String getArtist(){
        byte[] my=new byte[30];
        for(int i=allBytes.length-129+4+30,t=0;i<allBytes.length-129+30+4+30;++i,++t){
            my[t]=allBytes[i];
        }
        return new String(my);
    }
    public String getAlbum(){
        byte[] my=new byte[30];
        for(int i=allBytes.length-129+4+30+30,t=0;i<allBytes.length-129+30+4+30+30;++i,++t){
            my[t]=allBytes[i];

        }
        return new String(my);
    }
    public String getYear(){
        byte[] my=new byte[4];
        for(int i=allBytes.length-129+4+30+30+30,t=0;i<allBytes.length-129+4+30+30+30+4;++i,++t)
        {
            my[t]=allBytes[i];

        }
        return ""+my[0]+my[1]+my[2]+my[3];
    }
    public String getComment(){
        byte[] my=new byte[30];
        for(int i=allBytes.length-129+30+4+30+30+4,t=0;i<allBytes.length-129+30+4+30+30+4+30-1;++i,++t)
        {
            my[t]=allBytes[i];

        }
        return new String(my);
    }
    public String getGenre(){
        return ""+allBytes[allBytes.length-1];
    }
    public String getTrack(){
        return ""+allBytes[allBytes.length-2];
    }
    public int getZeroByte(){
        return  allBytes[allBytes.length-3];
    }

}
