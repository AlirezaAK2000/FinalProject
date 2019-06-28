package ServerAndClient;
import javax.swing.*;
import java.awt.*;

public class AcceptPanel extends JFrame {
    private JButton acceptClick;
    private JButton cancel;

    public AcceptPanel() throws HeadlessException {
        super();
        Font font=new Font("myFont",Font.BOLD,28);
        this.acceptClick = new JButton("Accept");
        acceptClick.setOpaque(true);
        acceptClick.setContentAreaFilled(true);
        acceptClick.setBorderPainted(true);

        acceptClick.setFont(font);
        acceptClick.setBackground(Color.GREEN);
        acceptClick.setBorder(BorderFactory.createLineBorder(new Color(0x308320) , 8));
        acceptClick.setForeground(Color.white);


        cancel=new JButton("cancel");
        cancel.setFont(new Font("mmyfont" , Font.BOLD , 15));
        cancel.setSize(new Dimension(200 , 50));
        cancel.setContentAreaFilled(true);
        cancel.setOpaque(true);
        cancel.setBackground(Color.red);



        JPanel aux = new JPanel();
        aux.setLayout(new GridLayout(2 , 1));


        aux.add(acceptClick);
        aux.add(cancel);
        this.add(aux);
        this.setMaximumSize(new Dimension(200 , 200));
        this.setMinimumSize(new Dimension(200 , 200));
        setVisible(true);

    }

    public JButton getAcceptClick() {
        return acceptClick;
    }

    public JButton getCancel() {
        return cancel;
    }
}
