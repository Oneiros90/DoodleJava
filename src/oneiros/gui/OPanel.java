package oneiros.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import oneiros.images.OImage;

public class OPanel extends JPanel {

    protected Image bg;

    public OPanel() {
        super(null);
        this.setOpaque(false);
    }

    public OPanel(Image bg) {
        this();
        this.setBackground(bg);
    }

    public OPanel(OImage o) {
        this(o.getOriginalImage());
    }

    public final void setBackground(Image image) {
        this.bg = image;
    }

    public Image getBackgroundImage() {
        return bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.bg != null) {
            g.drawImage(this.bg, 0, 0, this.bg.getWidth(null), this.bg.getHeight(null), this);
        }
    }
}
