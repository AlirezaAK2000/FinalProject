package Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
