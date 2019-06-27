package ServerAndClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializedData implements Serializable {
    //key for title and value for artist
    //fielde song ro ezafe nakrdam fahgat
    private String nameOfArtist;
    private String title;

    public SerializedData(String nameOfArtist, String title) {
        this.nameOfArtist = nameOfArtist;
        this.title = title;
    }

    public String getNameOfArtist() {
        return nameOfArtist;
    }

    public String getTitle() {
        return title;
    }
}
