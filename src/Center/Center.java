package Center;


import Music.SearchList;
import Music.SongPanel;
import Music.SongPanels;
import Musicbox.MusicBox;
import Tools.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Center extends JPanel {
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    private SearchBox searchBox;
    private JPanel main;
    private JPanel background;
    private MusicBox musicBox;
    private ArrayList<SongPanel> repos;
    private SearchList searchList;
    private boolean searchBoxOpened = false;
    private String username;

    public Center(MusicBox musicBox  , String username) throws IOException {
        super();
        this.username = username;
        this.musicBox = musicBox;
        originalImage = ImageIO.read(new File("backgrounds\\center4.jpg"));
        background = new Background(originalImage);
        this.setLayout(new BorderLayout());
        searchBox = new SearchBox(username);
        this.add(searchBox , BorderLayout.NORTH);
        this.add(new JScrollPane(background) , BorderLayout.CENTER);


        searchBox.getSearcher().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                searchBox.getSearcher().setText("");

            }
        });

        searchBox.getSearcher().setEditable(true);
       searchBox.getSearcher().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               searchList.referesh(searchBox.getSearcher().getText());
               searchBoxOpened = true;
               setMain(searchList);

           }
       });



    }
    public boolean searchBoxOpened(){
        return searchBoxOpened;
    }

    public String getUsername() {
        return username;
    }

    public SearchList getSearchList() {
        return searchList;
    }

    public void setRepos(ArrayList<SongPanel> repos) {
        this.repos = repos;
        try {
            searchList = new SearchList(Background.toBufferedImage(ImageIO.read(new File("backgrounds\\center5.jpg"))) , this.repos);
        } catch (IOException e1) {
            System.out.println("search box didnt load");
        }
    }

    public MusicBox getMusicBox() {
        return musicBox;
    }

    public void setMain(JPanel main) {
        if (!main.equals(searchList))
            searchBoxOpened = false;
        removeAll();
        this.add(searchBox , BorderLayout.NORTH);
        background = main;
        this.add(new JScrollPane(background) , BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public SearchBox getSearchBox() {
        return searchBox;
    }

    public JPanel getBack(){
        return background;
    }
}



