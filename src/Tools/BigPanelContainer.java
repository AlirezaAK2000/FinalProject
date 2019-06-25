package Tools;

import Center.Center;
import Music.SongPanel;
import Music.SongPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BigPanelContainer extends JPanel{
    ArrayList<BigPanel> bigPanels;
    private Center center;
    private BufferedImage originalImage;
    private BufferedImage scaledImage;
    public BigPanelContainer(BufferedImage originalImage) {
        super();
        this.originalImage = originalImage;
        this.setBackground(Color.BLACK);
        bigPanels = new ArrayList<>();
        this.setLayout(new GridLayout(3, 40  , 10 , 10));
    }
    public void addBigPanel(BigPanel bigPanel){
        this.add(bigPanel);
        bigPanel.setCenter(center);
    }

    public void setCenter(Center center) {
        this.center = center;
    }
    public void repaintList() {
        revalidate();
        repaint();
    }

}
