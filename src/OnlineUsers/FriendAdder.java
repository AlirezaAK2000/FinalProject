package OnlineUsers;


import Tools.ProButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FriendAdder extends JFrame {
    private ProButton oKClick;
    private ProButton cancelButt;
    private JTextField jTextField;
    private JPanel buttons;
    private Font bold;
    private Font plain;

    public FriendAdder() throws HeadlessException, UnknownHostException {
        super();
        bold = new Font("serif" , Font.BOLD , 15);
        plain = new Font("serif" , Font.PLAIN  , 15);
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        oKClick=new ProButton("ok");
        oKClick.setFont(plain);
        cancelButt=new ProButton("cancel");
        cancelButt.setFont(plain);
        oKClick.setForeground(Color.white);
        oKClick.addMouseListener(new Bolder(oKClick));
        cancelButt.addMouseListener(new Bolder(cancelButt));
        cancelButt.setForeground(Color.white);

        buttons.setBackground(Color.BLACK);

        buttons.add(oKClick);
        buttons.add(cancelButt);
        JLabel label = new JLabel(""+InetAddress.getLocalHost());
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.white);

        jTextField=new JTextField();
        setSize(100,100);
        setLayout(new BorderLayout());
        add(jTextField,BorderLayout.CENTER);
        add(buttons , BorderLayout.SOUTH);
        add(label,BorderLayout.NORTH);
        this.setMinimumSize(new Dimension(220 , 120));
        setVisible(true);
    }
    private class Bolder extends MouseAdapter{
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

    public JTextField getjTextField() {
        return jTextField;
    }

    public ProButton getCancelButt() {
        return cancelButt;
    }

    public ProButton getoKClick() {
        return oKClick;
    }
}
