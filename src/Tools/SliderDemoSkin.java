package Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SliderDemoSkin extends JPanel {

    private ProSlider slider;
    private   UIDefaults d;
    public SliderDemoSkin(){
        d = new UIDefaults();
        d.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<ProSlider>() {
            @Override public void paint(Graphics2D g, ProSlider c, int w, int h) {
                int arc         = 10;
                int trackHeight = 8;
                int trackWidth  = w - 2;
                int fillTop     = 4;
                int fillLeft    = 1;

                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(1.5f));
                g.setColor(Color.GRAY);
                g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

                int fillBottom = fillTop + trackHeight;
                int fillRight  = xPositionForValue(
                        c.getValue(), c,
                        new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

                g.setColor(Color.RED);
                g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

                g.setColor(Color.GRAY);
                g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
            }
            //@see javax/swing/plaf/basic/BasicSliderUI#xPositionForValue(int value)
            protected int xPositionForValue(int value, ProSlider slider, Rectangle trackRect) {
                int min = slider.getMinimum();
                int max = slider.getMaximum();
                int trackLength = trackRect.width;
                double valueRange = (double) max - (double) min;
                double pixelsPerValue = (double) trackLength / valueRange;
                int trackLeft = trackRect.x;
                int trackRight = trackRect.x + (trackRect.width - 1);
                int xPosition;

                xPosition = trackLeft;
                xPosition += Math.round(pixelsPerValue * ((double) value - min));

                xPosition = Math.max(trackLeft, xPosition);
                xPosition = Math.min(trackRight, xPosition);

                return xPosition;
            }
        });

        slider = new ProSlider();
        slider.putClientProperty("Nimbus.Overrides", d);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(245 , 245 , 245));
        this.add(Box.createRigidArea(new Dimension(10, 20)));
        this.add(slider);
    }

    public ProSlider getSlider() {
        return slider;
    }
}
