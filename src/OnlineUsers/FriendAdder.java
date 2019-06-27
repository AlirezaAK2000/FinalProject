package OnlineUsers;

import Tools.ProButton;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FriendAdder extends JFrame {
    private ProButton oKClick;
    private ProButton cancelButt;
    private JTextField jTextField;


    public FriendAdder() throws HeadlessException, UnknownHostException {
        super();
        oKClick=new ProButton("ok");
        cancelButt=new ProButton("cancel");
        jTextField=new JTextField();
        setSize(100,100);
        setLayout(new BorderLayout());
        add(oKClick,BorderLayout.WEST);
        add(cancelButt,BorderLayout.EAST);
        add(jTextField,BorderLayout.CENTER);
        add(new JLabel(""+InetAddress.getLocalHost()),BorderLayout.NORTH);
        setVisible(true);
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
