package Music;

import Tools.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SearchList extends Background {
    private ArrayList<SongPanel> repository;
    private ArrayList<Component> components;
    private JLabel artistHead;
    private JLabel titleHead;
    public SearchList(BufferedImage originalImage ,ArrayList<SongPanel> repository ) {
        super(originalImage);
        this.repository = repository;
        components = new ArrayList<>();
        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));

        titleHead = new JLabel("SONGS");
        titleHead.setFont(new Font("serif" , Font.BOLD , 20));
        titleHead.setForeground(Color.white);
        titleHead.setBackground(Color.BLACK);
        titleHead.setMaximumSize(new Dimension(java.lang.Integer.MAX_VALUE, 30));

        artistHead = new JLabel("ARTISTS");
        artistHead.setFont(new Font("serif" , Font.BOLD , 20));
        artistHead.setForeground(Color.white);
        artistHead.setBackground(Color.BLACK);
        artistHead.setMaximumSize(new Dimension(java.lang.Integer.MAX_VALUE, 30));

    }

    public void referesh(String str){
        this.removeAll();
        components = new ArrayList<>();
        this.add(Box.createVerticalStrut(5));
        this.add(titleHead);
        for(SongPanel s: repository){
            if (s.getSong().getTitle().toLowerCase().contains(str.toLowerCase())) {
                this.add(Box.createVerticalStrut(5));
                this.add(s);
                components.add(s);
            }
        }
        this.add(Box.createVerticalStrut(5));
        this.add(artistHead);
        for(SongPanel s: repository){
            if (s.getSong().getArtist().toLowerCase().contains(str.toLowerCase())) {
                this.add(Box.createVerticalStrut(5));
                this.add(s);
            }
        }
        this.revalidate();
        this.repaint();
    }
    public void repaintList() {
        removeAll();
        for (Component component : components) {
            add(Box.createVerticalStrut(5));
            add(component);
        }
        revalidate();
        repaint();
    }

}
