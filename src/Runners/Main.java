package Runners;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WelcomeToJPotify welcomeToJPotify = new WelcomeToJPotify();
        welcomeToJPotify.getSignIn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeToJPotify.setVisible(false);
                try {
                    GeneralManager manager = new GeneralManager(welcomeToJPotify.getUserName().getText());

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (JavaLayerException e1) {
                    e1.printStackTrace();
                } catch (InvalidDataException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedTagException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }
}

