package Tools;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ProSlider extends JSlider {
    private Integer position;

    public ProSlider() {
        super(0,100,100);
        position=new Integer(0);
        setMaximum(1000000);
        setMinimum(0);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                double percent = p.x / ((double) ProSlider.this.getWidth());
                int range = ProSlider.this.getMaximum() - ProSlider.this.getMinimum();
                double newVal = range * percent;
                int result = (int) (ProSlider.this.getMinimum() + newVal);
                ProSlider.this.setValue(result);
            }
        });
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                position.setValue(ProSlider.this.getValue());
            }
        });


        this.setOpaque(false);

    }

    public Integer getPosition() {
        return position;
    }
}