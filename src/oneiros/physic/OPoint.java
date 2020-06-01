package oneiros.physic;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * La classe <code>Point</code> modella un punto nel piano mediante coordinate {@code (x,y)} a doppia precisione.
 * <br><br>
 * Questa classe inoltre fornisce metodi per eseguire operazioni avanzate come la rotazione
 * e la traslazione del sistema di assi cartesiani.
 *
 * @author Oneiros
 */
public class OPoint extends Point2D.Double {

    /**
     * Crea ed inizializza un punto in coordinate <code>(0,0)</code>.
     */
    public OPoint() {
    }

    /**
     * Crea ed inizializza un punto in coordinate <code>(x,y)</code>.
     * @param x La coordinata x del punto creato
     * @param y La coordinata y del punto creato
     */
    public OPoint(double x, double y) {
        super(x, y);
    }

    /**
     * Crea ed inizializza un punto a partire delle coordinate intere di un <code>java.awt.Point</code>.
     * @param p Il punto a coordinate intere dal quale costruire un <code>OPoint</code>
     */
    public OPoint(Point p) {
        super(p.x, p.y);
    }

    /**
     * Sposta in <code>pCenter</code> il centro del sistema di assi cartesiani nel quale è inserita
     * questa istanza.
     * Questo metodo sostituisce le coordinate del punto traslato alle coordinate attuali dell'istanza.
     * @param c Il nuovo centro del sistema di assi cartesiani
     */
    public void translate(Point2D.Double c) {
        this.x -= c.x;
        this.y -= c.y;
    }

    /**
     * Effettua una rotazione di ampiezza <code>angle</code> del sistema di assi cartesiani (nel quale è
     * inserita questa istanza) intorno al suo centro a partire dal semiasse positivo delle ascisse, in senso
     * antiorario.
     * Questo metodo sostituisce le coordinate del punto ruotato alle coordinate attuali dell'istanza.
     * @param angle L'angolo di rotazione degli assi espresso in radianti
     */
    public void rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double oldx = this.x;
        this.x = this.x * cos - this.y * sin;
        this.y = oldx * sin + this.y * cos;
    }

    /**
     * Effettua una rototraslazione del sistema di assi cartesiani nel quale è inserita questa istanza.
     * Questo metodo sostituisce le coordinate del punto rototraslato alle coordinate attuali dell'istanza.
     * @param c Il nuovo centro del sistema di assi cartesiani
     * @param angle L'angolo di rotazione degli assi espresso in radianti
     */
    public void rototranslate(OPoint c, double angle) {
        this.translate(c);
        this.rotate(angle);
    }

    /**
     * Restituisce l'angolo ("anomalia" o "ascissa angolare") formato dall'asse polare e dal raggio
     * vettore, assumendo l'asse polare come origine e positivo il senso antiorario.
     * @return L'ascissa angolare del punto espressa in radianti
     */
    public double getAngularCoord() {
        return Math.atan2(this.y, this.x);
    }

    /**
     * Restituisce l'angolo ("anomalia" o "ascissa angolare") espresso in radianti formato dall'asse
     * polare e dal raggio vettore del sistema di assi cartesiani di centro <code>pPoint</code>,
     * assumendo l'asse polare come origine e positivo il senso antiorario.
     * @param pCenter Il centro del sistema di assi cartesiani nel quale calcolare l'ascissa angolare
     * @return L'ascissa angolare del punto nel sistema traslato
     */
    public double getAngularCoordFromPoint(OPoint pCenter) {
        if (this.equals(pCenter)) {
            throw new IllegalArgumentException("Argument can't be equal to this instance of Point");
        }
        return Math.atan2(this.y - pCenter.y, pCenter.x - this.x);
    }

    /**
     * Sposta l'istanza del punto lungo la retta passante per essa ed il punto <code>p</code>
     * in modo che la distanza tra i due punti sia uguale a <code>d</code>
     * @param p Il punto dal quale distanziare l'istanza del punto
     * @param d La distanza finale tra l'istanza del punto e <code>p</code>
     */
    public void setDistanceFrom(OPoint p, double d) {
        double α = Math.atan2(this.y - p.y, this.x - p.x) % Math.PI;
        double aux = d;
        while (this.distance(p) < d) {
            this.x = aux * Math.cos(α) + p.x;
            this.y = aux * Math.sin(α) + p.y;
            aux += 0.001;
        }
    }

    /**
     * Restituisce una stringa contenente le coordinate del punto.
     * @return Una stringa del tipo "Point[x, y]"
     */
    @Override
    public String toString() {
        return "Point[" + this.x + ", " + this.y + "]";
    }

    /**
     * Determina se l'istanza del punto ed <code>obj</code> possiedono le stesse coordinate x e y.
     * @param obj un oggetto da comparare con questo punto
     * @return <code>true</code> se obj è una istanza di <code>Point</code> e ha le stesse coordinate;<br>
     *         <code>false</code> altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OPoint) {
            OPoint secondPoint = (OPoint) obj;
            return this.x == secondPoint.x && this.y == secondPoint.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
