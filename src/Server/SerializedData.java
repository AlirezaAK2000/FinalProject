package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializedData implements Serializable {
    //key for title and value for artist
    //fielde song ro ezafe nakrdam fahgat
    HashMap<String , String> data;
    public SerializedData( HashMap<String , String> data){
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
