package oneiros.images;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

/**
 * La classe <code>Image</code> consente di gestire un'immagine in maniera facile e veloce a partire da
 * un file in formato <code>JPG</code>, <code>GIF</code> o <code>PNG.</code>. Le immagini devono essere
 * posizionate nella sottocartella <code>src</code>del progetto.
 * L'immagine verrà inserita all'interno di un pannello trasparente (utilizzabile in contesti grafici
 * <code>Swing</code>) modificabile a proprio piacimento.
 * @author Oneiros
 */
public class OImage extends javax.swing.JComponent {

    private Image image;
    private Dimension imageSize;
    private Point imageLocation;
    private boolean tile;
    private String imageName;
    private static int counter = 0;

    public OImage() {
    }

    /**
     * TODO
     * Crea ed inizializza una Image a partire dal nome di un file immagine contenuto nella cartella
     * <code>src</code> del progetto. Il pannello contenente l'immagine verrà posizionato nel punto
     * <code>(0,0)</code> e ridimensionato con le stesse dimensioni dell'immagine.<br>
     * Sono supportati i seguenti formati: <code>JPG, GIF, PNG.</code>
     * @param pName Il nome del file immagine (estensione compresa)
     */
    public OImage(Class c, String pName) {
        this.setImage(getImage(c, pName));
        this.init();
    }

    /**
     * Crea ed inizializza una Image a partire da una <code>java.awt.Image</code>. Il pannello
     * contenente l'immagine verrà posizionato nel punto <code>(0,0)</code> e ridimensionato con
     * le stesse dimensioni dell'immagine.<br>
     * @param pImage L'immagine da utilizzare
     */
    public OImage(Image pImage) {
        this.setImage(pImage);
        this.init();
    }

    private void init() {
        this.setSize(this.imageSize);
        this.imageLocation = new Point(0, 0);
        this.tile = false;
    }

    /**
     * Carica un'immagine dalla cartella <code>src</code>del progetto e la posiziona nel pannello nelle
     * sue dimensioni originali ed in posizione <code>(0,0)</code>.<br>
     * Sono supportati i seguenti formati: <code>JPG, GIF, PNG.</code>
     * @param pName Il nome del file immagine (estensione compresa)
     */
    public final void setImage(String pName) {
        this.imageName = pName;
        this.image = OImage.getImage(this.getClass(), pName);
        this.imageSize = this.getOriginalImageSize();
        this.repaint();
    }

    /**
     * Imposta come immagine una <code>java.awt.Image</code> posizionandola in <code>(0,0)</code> e
     * nelle sue dimensioni originali.<br>
     * @param pImage L'immagine da utilizzare
     */
    public final void setImage(Image pImage) {
        this.imageName = "photo" + OImage.counter;
        OImage.counter++;
        this.image = pImage;
        this.imageSize = this.getOriginalImageSize();
        this.repaint();
    }

    /**
     * Imposta le dimensioni dell'immagine. Per impostare le dimensioni del pannello, utilizzare
     * <code>setSize()</code> oppure <code>setBounds()</code>.
     * @param pSize Le dimensioni finali dell'immagine
     */
    public void setImageSize(Dimension pSize) {
        this.setImageSize(pSize.width, pSize.height);
    }

    /**
     * Imposta le dimensioni dell'immagine. Per impostare le dimensioni del pannello, utilizzare
     * <code>setSize()</code> oppure <code>setBounds()</code>.
     * @param pWidth La larghezza finale dell'immagine
     * @param pHeight L'altezza finale dell'immagine
     */
    public void setImageSize(int pWidth, int pHeight) {
        this.imageSize.setSize(pWidth, pHeight);
    }

    /**
     * Sposta l'immagine all'interno del suo pannello. Per spostare il pannello, utilizzare
     * <code>setLocation()</code>.
     * @param pPoint La posizione finale dell'angolo in alto a sinistra dell'immagine
     */
    public void setImageLocation(Point pPoint) {
        this.imageLocation.setLocation(pPoint);
    }

    /**
     * Sposta l'immagine all'interno del suo pannello. Per spostare il pannello, utilizzare
     * <code>setLocation()</code>.
     * @param pX La coordinata <code>x</code> dell'angolo in alto a sinistra dell'immagine
     * @param pY La coordinata <code>y</code> dell'angolo in alto a sinistra dell'immagine
     */
    public void setImageLocation(int pX, int pY) {
        this.imageLocation.setLocation(pX, pY);
    }

    /**
     * Imposta la modalità "Affianca". La modalità "Affianca" duplica l'immagine in modo da ricoprire
     * completamente il pannello che la contiene.
     * @param pFlag <code>true</code> se si vuole attivare la modalità "Affianca";<br>
     *              <code>false</code> se si vuole disattivare la modalità "Affianca".
     */
    public void setTile(boolean pFlag) {
        this.tile = pFlag;
        this.repaint();
    }

    /**
     * Restituisce l'immagine come istanza di <code>java.awt.Image</code>.
     * @return L'immagine come istanza di java.awt.Image
     */
    public Image getImage() {
        BufferedImage bufferedImage = new BufferedImage(this.imageSize.width, this.imageSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = bufferedImage.createGraphics();
        g2D.setComposite(AlphaComposite.Src);
        g2D.drawImage(image, 0, 0, this.imageSize.width, this.imageSize.height, null);
        g2D.dispose();
        return bufferedImage;
    }

    /**
     * TODO
     */
    public static Image getImage(Class c, String pName) {
        return java.awt.Toolkit.getDefaultToolkit().getImage(c.getResource(pName));
    }

    /**
     * Restituisce l'immagine originale come istanza di <code>java.awt.Image</code>.
     * @return L'immagine orignale come istanza di java.awt.Image
     */
    public Image getOriginalImage() {
        return this.image;
    }

    public BufferedImage toBufferedImage() {
        BufferedImage bufferedImage = new BufferedImage(
                this.imageSize.width, this.imageSize.height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(this.image, 0, 0, null);
        return bufferedImage;
    }

    public OImage cropImage(Rectangle r) {
        return cropImage(r.x, r.y, r.width, r.height);
    }

    public OImage cropImage(int x, int y, int width, int height) {
        CropImageFilter cif = new CropImageFilter(x, y, width, height);
        FilteredImageSource fis = new FilteredImageSource(this.getImage().getSource(), cif);
        return new OImage(createImage(fis));
    }

    public OImage cropPanel(Rectangle r) {
        return cropPanel(r.x, r.y, r.width, r.height);
    }

    public OImage cropPanel(int x, int y, int width, int height) {
        return cropImage(x - this.imageLocation.x, y - this.imageLocation.y, width, height);
    }

    /**
     * Restituisce l'immagine come istanza di <code>javax.swing.ImageIcon</code>.
     * @return L'immagine come istanza di javax.swing.ImageIcon
     */
    public javax.swing.ImageIcon getImageIcon() {
        return new javax.swing.ImageIcon(getClass().getResource(this.getImageFullPath()));
    }

    /**
     * Restituisce le dimensioni attuali dell'immagine. Per le dimensioni del pannello, utilizzare
     * <code>getSize()</code>.
     * @return Le dimensioni dell'immagine
     */
    public Dimension getImageSize() {
        return new Dimension(this.imageSize);
    }

    /**
     * Restituisce la posizione dell'immagine. Per la posizione del pannello, utilizzare
     * <code>getLocation()</code>.
     * @return La posizione dell'angolo in alto a sinistra dell'immagine
     */
    public java.awt.Point getImageLocation() {
        return new Point(this.imageLocation);
    }

    public java.awt.Rectangle getImageBounds() {
        return new Rectangle(this.imageLocation, this.imageSize);
    }

    /**
     * Restituisce le dimensioni originali dell'immagine rileggendole direttamente dal file.
     * @return Le dimensioni originali dell'immagine
     */
    public Dimension getOriginalImageSize() {
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(this.image, 1);
        try {
            mt.waitForID(1);
        } catch (InterruptedException ex) {
        }
        return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
    }

    /**
     * Restituisce il nome dell'immagine
     * @return Il nome dell'immagine (estensione compresa).
     */
    public String getImageName() {
        return this.imageName;
    }

    /**
     * Restituisce il path completo dell'immagine.
     * @return Il path completo dell'immagine
     */
    public String getImageFullPath() {
        return this.imageName;
    }

    /**
     * Disegna l'immagine sul pannello nella posizione specificata da <code>imageLocation</code>. Se il flag
     * <code>tile</code> è attivo, l'immagine verrà duplicata fino a coprire l'intero pannello.
     * @param g L'oggetto <code>Graphics</code> da disegnare
     */
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            if (this.tile) {
                int y = 0;
                do {
                    int x = 0;
                    do {
                        g.drawImage(this.image, x, y, this.imageSize.width, this.imageSize.height, this);
                        x += this.imageSize.width;
                    } while (x < this.getWidth());
                    y += this.imageSize.height;
                } while (y < this.getHeight());
            } else {
                g.drawImage(this.image, this.imageLocation.x, this.imageLocation.y,
                        this.imageSize.width, this.imageSize.height, this);
            }
        }
    }

    /**
     * Restituisce una stringa contenente il path completo dell'immagine.
     * @return Una stringa contenente il path completo dell'immagine
     */
    @Override
    public String toString() {
        return "Image[" + this.imageName + "]";
    }
}
