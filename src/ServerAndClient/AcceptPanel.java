package ServerAndClient;

import Tools.ProButton;

import javax.swing.*;
import java.awt.*;

public class AcceptPanel extends JFrame {
    private ProButton acceptClick;
    private ProButton cancel;

    public AcceptPanel() throws HeadlessException {
        super();
        Font font=new Font("myFont",Font.BOLD,28);
        this.acceptClick = new ProButton("Accept");
        acceptClick.setFont(font);
        acceptClick.setBackground(new Color(0x085C09));
        acceptClick.setForeground(new Color(0x550A07));
        cancel=new ProButton("cancel");
        setLayout(new BorderLayout());
        this.add(acceptClick,BorderLayout.CENTER);
        this.add(cancel,BorderLayout.SOUTH);
        setVisible(true);

    }

    public ProButton getAcceptClick() {
        return acceptClick;
    }

    public ProButton getCancel() {
        return cancel;
    }
}
