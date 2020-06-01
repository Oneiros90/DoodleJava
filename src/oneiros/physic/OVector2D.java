package oneiros.physic;

/**
 * La classe <code>OVector2D</code> modella un vettore nello spazio vettoriale.
 * @author Oneiros
 */
public class OVector2D extends OPoint {

    public static final OVector2D ZERO_VECTOR = new OVector2D();

    /**
     * Crea ed inizializza un vettore in coordinate <code>(0,0)</code>.
     */
    public OVector2D() {
    }

    /**
     * Crea ed inizializza un vettore in coordinate <code>(p.x, p.y)</code>.
     * @param p La posizione del vettore
     */
    public OVector2D(OPoint p) {
        super(p.x, p.y);
    }

    /**
     * Crea ed inizializza un vettore di modulo <code>m</code> ed inclinazione <code>angle</code>
     * @param m Il modulo del vettore creato
     * @param angle L'inclinazione del vettore creato
     */
    public OVector2D(double m, double angle) {
        this.setPolarChoords(m, angle);
    }

    /**
     * Modifica il modulo del vettore senza modificarne l'inclinazione
     * @param m Il nuovo modulo del vettore
     */
    public void setMagnitude(double m) {
        double α = this.getAngularCoord();
        this.x = m * cos(α);
        this.y = m * sin(α);
    }

    /**
     * Modifica l'inclinazione del vettore senza modificarne il modulo
     * @param pAngle La nuova inclinazione del vettore
     */
    public void setAngle(double pAngle) {
        this.rotate(pAngle - this.getAngularCoord());
    }

    /**
     * Imposta il vettore in modo che il suo modulo sia <code>m</code> e
     * la sua inclinazione sia <code>angle</code>
     * @param m Il nuovo modulo del vettore
     * @param angle La nuova inclinazione del vettore
     */
    public final void setPolarChoords(double m, double angle) {
        angle = Math.toRadians(angle);
        super.setLocation(m * cos(angle), m * sin(angle));
    }

    /**
     * Restituisce il modulo del vettore
     * @return Il modulo del vettore
     */
    public double getMagnitude() {
        return super.distance(0, 0);
    }

    /**
     * Effettua la somma vettoriale tra il vettore d'istanza ed il vettore <code>v</code>
     * @param v Vettore addendo
     */
    public void sum(OVector2D v) {
        if (v != null) {
            this.setLocation(this.x + v.x, this.y + v.y);
        }
    }

    /**
     * Effettua la differenza vettoriale tra il vettore d'istanza ed il vettore <code>v</code>
     * @param v Vettore sottraendo
     */
    public void sub(OVector2D v) {
        if (v != null) {
            this.setLocation(this.x - v.x, this.y - v.y);
        }
    }

    @Override
    public String toString() {
        return "Vector[" + this.x + ", " + this.y + "]"
                + "[" + this.getMagnitude() + ", " + Math.toDegrees(this.getAngularCoord()) + "]";
    }

    private static double cos(double n){
        double cos = Math.cos(n);
        if (Math.abs(cos) < 0.00000001)
            return 0;
        return cos;
    }

    private static double sin(double n){
        double sin = Math.sin(n);
        if (Math.abs(sin) < 0.00000001)
            return 0;
        return sin;
    }
}
