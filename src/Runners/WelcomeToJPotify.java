package Runners;

import Tools.Background;
import Tools.ProButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

//C:\Users\lenovo\Desktop\APPROJ\backgrounds\welcome.jpg
public class WelcomeToJPotify extends JFrame {
    private Background theme;
    private ProButton signIn;
    private JTextField userName;
    private JPanel info;
    private Font bold;
    private Font plain;
    public WelcomeToJPotify() throws IOException {
        bold = new Font("serif" , Font.BOLD , 20);
        plain = new Font("serif" , Font.PLAIN  , 20);
        theme = new Background(ImageIO.read(new File("backgrounds\\welcome.jpg")));
        signIn = new ProButton("Sign In");
        signIn.setFont(new Font("serif" , Font.BOLD , 20));
        signIn.setForeground(Color.white);
        signIn.addMouseListener(new Bolder(signIn));
        userName = new JTextField();
        userName.setEditable(true);
        info = new JPanel();
        info.setLayout(new GridLayout(2 , 1));
        info.add(userName);
        info.add(signIn);
        info.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        this.add(theme , BorderLayout.CENTER);
        this.add(info , BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(300 , 300));
        this.setMaximumSize(new Dimension(300 , 300));
        this.setLocation(600 , 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private class Bolder extends MouseAdapter {
        private ProButton po;
        public Bolder(ProButton po){
            this.po = po;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            po.setFont(bold);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            po.setFont(plain);
        }
    }

    public ProButton getSignIn() {
        return signIn;
    }

    public JTextField getUserName() {
        return userName;
    }
}

