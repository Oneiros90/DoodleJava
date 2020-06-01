package oneiros.engine3D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author oneiros
 */
public class RenderPanel extends JPanel {
    
    private BufferedImage canvas;

    public RenderPanel(int x, int y) {
        super(null);
        this.setDoubleBuffered(true);
        this.setSize(y, y);
    }

    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.drawImage(this.canvas, null, this);
    }
}
