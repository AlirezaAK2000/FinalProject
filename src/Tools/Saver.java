package Tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Saver implements Serializable {
    private HashMap<String , ArrayList<String>> data;
    public Saver(HashMap<String , ArrayList<String>> data){
        this.data = data;
    }

    public HashMap<String, ArrayList<String>> getData() {
        return data;
    }
}
