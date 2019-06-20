package Tools;

import javax.swing.*;

public class ProButton extends JButton {
    public ProButton(String name){
        super(name);
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);

    }
    public ProButton(String name , Icon icon){
        super(name , icon);
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }
    public ProButton(ImageIcon icon){
        super(icon);
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }
    public ProButton(){
        super();
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }


}
