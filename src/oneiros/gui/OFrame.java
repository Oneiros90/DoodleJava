package oneiros.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.UIManager;


/**
 * La classe <code>SuperFrame</code> migliora ed estende le funzioni della classe <code>JFrame</code>
 * tramite alcune funzioni avanzate.
 * @author Lorenzo Valente (aka Oneiros)
 */
public class OFrame extends javax.swing.JFrame {

    public static final String DEFAULT_LOOK_AND_FEEL = "";
    public static final String METAL_LOOK_AND_FEEL = "metal.MetalLookAndFeel";
    public static final String NIMBUS_LOOK_AND_FEEL = "nimbus.NimbusLookAndFeel";
    public static final String MOTIF_LOOK_AND_FEEL = "motif.MotifLookAndFeel";
    public static final String WINDOWS_LOOK_AND_FEEL = "windows.WindowsLookAndFeel";
    public static final String WINDOWS_CLASSIC_LOOK_AND_FEEL = "windows.WindowsClassicLookAndFeel";
    private static final Dimension DEFAULT_DIMENSION = new Dimension(800, 600);
    private GraphicsDevice device;
    private boolean fullscreen;
    private Dimension backupDim;
    private TrayIcon trayIcon;

    /**
     * Crea un oggetto di tipo <code>SuperFrame</code> con la configurazione di default
     */
    public OFrame() {
        this(DEFAULT_DIMENSION);
    }

    public OFrame(String title) {
        this();
        this.setTitle(title);
    }

    /**
     * Crea un oggetto di tipo <code>SuperFrame</code> di dimensioni <code>d</code>
     * @param d Le dimensioni del frame
     */
    public OFrame(Dimension d) {
        this(d.width, d.height);
    }

    /**
     * Crea un oggetto di tipo <code>SuperFrame</code> di dimensioni <code>width</code>, <code>height</code>
     * @param width La larghezza del frame
     * @param height L'altezza del frame
     */
    public OFrame(int width, int height) {
        this.setLookAndFeel(DEFAULT_LOOK_AND_FEEL);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.fullscreen = false;
        this.backupDim = new Dimension();
        this.setSizeWithoutInsets(width, height);
        this.centerFrame();
    }

    @Override
    public void setResizable(boolean resizable) {
        Dimension size = this.getSizeWithoutInsets();
        super.setResizable(resizable);
        this.pack();
        this.setSizeWithoutInsets(size);
    }

    /**
     * Centra il frame all'interno dello schermo
     */
    public final void centerFrame() {
        setLocationRelativeTo(null);
    }

    /**
     * Imposta la grandezza del frame senza considerare i bordi
     * @param width La nuova larghezza del frame
     * @param height La nuova altezza del frame
     */
    public final void setSizeWithoutInsets(int width, int height) {
        if (System.getProperty("os.name").equals("Linux")) {
            this.setSize(width, height);
        } else {
            Insets i = this.getInsets();
            this.setSize(width + i.left + i.right, height + i.top + i.bottom);
        }
    }

    /**
     * Imposta la grandezza del frame senza considerare i bordi
     * @param width La nuova larghezza del frame
     * @param height La nuova altezza del frame
     */
    public final Dimension getSizeWithoutInsets() {
        Dimension d = new Dimension();
        if (System.getProperty("os.name").equals("Linux")) {
            d.setSize(this.getWidth(), this.getHeight());
        } else {
            Insets i = this.getInsets();
            d.setSize(this.getWidth() - i.left - i.right, this.getHeight() - i.top - i.bottom);
        }
        return d;
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.backupDim = d;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.backupDim.setSize(width, height);
    }

    /**
     * Imposta la grandezza del frame senza considerare i bordi
     * @param d Le nuove dimensioni del frame
     */
    public final void setSizeWithoutInsets(Dimension d) {
        this.setSizeWithoutInsets(d.width, d.height);
    }

    /*private class AppearingThread extends Thread {
    
    protected boolean appearing;
    protected int speed;
    
    @Override
    public void run() {
    if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
    try {
    float f = (isVisible()) ? 0.99f : 0.01f;
    if (!isVisible()) {
    AWTUtilities.setWindowOpacity(getWindows()[0], 0);
    setVisible(true);
    }
    while (f >= 0 && f <= 1) {
    AWTUtilities.setWindowOpacity(getWindows()[0], f);
    Thread.sleep(speed);
    f += (appearing) ? 0.01 : -0.01;
    }
    if (f <= 0) {
    setVisible(false);
    } else {
    AWTUtilities.setWindowOpacity(getWindows()[0], 1);
    }
    } catch (InterruptedException ex) {
    }
    }
    }
    };
    
    /**
     * Fa apparire il frame lentamente con effetto traslucido
     * @param b <code>true</code>: fa apparire il frame
     *          <code>false</code>: fa scomparire il frame
     * @param speed La velocità dell'effetto
     * @since 1.6
     */
    /*public void setVisibleSmoothly(boolean b, int speed) {
    if (speed <= 0 || speed > 100) {
    throw new IllegalArgumentException("the speed must be between 1 and 100");
    }
    this.appearingThread.appearing = b;
    this.appearingThread.speed = 100 - speed;
    if (!this.appearingThread.isAlive() && b != this.isVisible()) {
    this.appearingThread.run();
    }
    }*/

    @Override
    public final void setBackground(Color bgColor) {
        super.getContentPane().setBackground(bgColor);
    }
    
    /**
     * Imposta il Look And Feel del SuperFrame
     * @param lookAndFeel Il Look And Feel da impostare
     */
    public final void setLookAndFeel(String lookAndFeel) {
        try {
            if (lookAndFeel.equals(METAL_LOOK_AND_FEEL)
                    || lookAndFeel.equals(NIMBUS_LOOK_AND_FEEL)
                    || lookAndFeel.equals(MOTIF_LOOK_AND_FEEL)
                    || lookAndFeel.equals(WINDOWS_LOOK_AND_FEEL)
                    || lookAndFeel.equals(WINDOWS_CLASSIC_LOOK_AND_FEEL)) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf." + lookAndFeel);
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Imposta il frame in modalità a tutto schermo o in modalità finestra
     * @param b <code>true</code>: attiva la modalità a tutto schermo
     *          <code>false</code>: attiva la modalità finestra
     */
    public final void setFullscreen(boolean b) {
        if (this.fullscreen != b) {
            this.fullscreen = b;
            this.setVisible(false);
            this.dispose();
            this.setUndecorated(b);
            if (b) {
                this.backupDim = this.getSize();
                this.device.setFullScreenWindow(this);
            } else {
                this.device.setFullScreenWindow(null);
                this.getContentPane().setSize(this.getPreferredSize());
                this.setSizeWithoutInsets(this.backupDim);
                this.centerFrame();
            }
            this.setVisible(true);
        }
    }

    /**
     * Riduce la finestra nella System Tray (area di notifica) o la riporta in primo piano
     * @param b <code>true</code>: minimizza la finestra nell'area di notifica
     *          <code>false</code>: riporta la finestra in primo piano
     */
    public final void toTrayIcon(boolean b) {
        if (b) {
            try {
                SystemTray.getSystemTray().add(this.getTrayIcon());
            } catch (AWTException ex) {
            }
            this.setVisible(false);
        } else {
            this.setVisible(true);
            this.setState(javax.swing.JFrame.NORMAL);
            SystemTray.getSystemTray().remove(this.getTrayIcon());
        }
    }

    /**
     * Restituisce la <code>TrayIcon</code> del frame
     * @return la <code>TrayIcon</code> del frame
     */
    public final TrayIcon getTrayIcon() {
        if (this.trayIcon == null) {
            if (SystemTray.isSupported()) {
                this.trayIcon = new TrayIcon(createImage(16, 16));
                this.trayIcon.setImageAutoSize(true);
            } else {
                throw new RuntimeException("The System Tray is not supported on the current platform");
            }
        }
        return this.trayIcon;
    }
}
/**Listener che gestisce il passaggio da un pannello all'altro*/
class SwapPanelListener implements ActionListener {

    private JPanel pFrom;
    private JPanel pTo;

    public SwapPanelListener(JPanel from, JPanel to) {
        this.pFrom = from;
        this.pTo = to;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.pTo.setVisible(true);
        this.pFrom.setVisible(false);
    }
}
