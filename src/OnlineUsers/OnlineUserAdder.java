package OnlineUsers;

import Tools.RoundedCornerBorder;

import javax.swing.*;
import java.awt.*;

public class OnlineUserAdder extends JPanel {

    public OnlineUserAdder(){
        super();
        this.setBackground(Color.BLACK);
        this.setMaximumSize(new Dimension(300 ,100 ));
    }

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
}
