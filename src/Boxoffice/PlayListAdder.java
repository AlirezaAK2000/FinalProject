package Boxoffice;

import Tools.ProButton;
import Tools.RoundedCornerBorder;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayListAdder extends JFrame {
    private ProButton cancel;
    private ProButton ok;
    private JTextField name;
    private JLabel help;
    private JPanel buttons;
    private Boxoffice boxoffice;
    private String prompt;
    private ProButton button;
    public PlayListAdder(Boxoffice b , String prompt , ProButton button){
        super();
        this.prompt = prompt;
        boxoffice =b;
        this.button = button;
        this.setBackground(Color.BLACK);
        buttons = new JPanel();
        buttons.setBackground(Color.BLACK);

        cancel = new ProButton("Cancel");
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.BLACK);
        cancel.addActionListener(new CancelButton());

        ok = new ProButton("Ok");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.white);
        ok.addActionListener(new OkButton());
        ok.addMouseListener(new Bolder());
        cancel.addMouseListener(new Bolder());
        help = new JLabel("                              Enter playlist name:");
        name = new JTextField("Search") {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder());
            }
        };
        ok.setFont(new Font("serif " , Font.PLAIN , 13));
        cancel.setFont(new Font("serif " , Font.PLAIN , 13));

        name.setToolTipText("enter name");
        this.setLayout(new BorderLayout());

        buttons.setLayout(new FlowLayout());
        buttons.add(cancel , FlowLayout.LEFT);
        buttons.add(ok , FlowLayout.CENTER);
        this.add(buttons,BorderLayout.SOUTH);
        this.add(name , BorderLayout.CENTER);
        this.add(help , BorderLayout.NORTH);
        setMinimumSize(new Dimension(300 , 120));
        setMaximumSize(new Dimension(300 , 120));
        setVisible(true);
    }
    private class OkButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(prompt.equals("add"))
            boxoffice.addPlaylist(name.getText());
            else
                boxoffice.editPlayList(name.getText() ,button );
            setVisible(false);
        }
    }
    private class CancelButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            setVisible(false);
        }
    }
    private class Bolder extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setFont(new Font( "serif",Font.BOLD , 13));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setFont(new Font("serif " , Font.PLAIN , 13));
        }
    }

    public JTextField getStr(){
        return name;
    }

    public ProButton getOk() {
        return ok;
    }

    public ProButton getCancel() {
        return cancel;
    }
}
