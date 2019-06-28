package ServerAndClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializedData implements Serializable {

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
