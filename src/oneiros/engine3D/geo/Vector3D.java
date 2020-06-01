package oneiros.engine3D.geo;

import oneiros.engine3D.math.Matrix;

/**
 *
 * @author oneiros
 */
public class Vector3D extends Matrix{

    public Vector3D() {
        super(4,1);
    }

    public Vector3D(double x, double y, double z) {
        super(new Double[][]{{x},{y},{z},{1d}});
    }

    public Vector3D(Matrix m) {
        this();
        if (m.getRows() >= 3 && m.getColumns() == 1){
            this.set(m.get(0, 0), m.get(1, 0), m.get(2, 0));
        } else if (m.getRows() == 1 && m.getColumns() >= 3){
            this.set(m.get(0, 0), m.get(0, 1), m.get(0, 2));
        } else {
            throw new IllegalArgumentException("This is not a vector");
        }
    }
    
    public final void set(double x, double y, double z){
        this.matrix[0][0] = x;
        this.matrix[1][0] = y;
        this.matrix[2][0] = z;
    }

    public void setX(double x) {
        this.matrix[0][0] = x;
    }

    public void setY(double y) {
        this.matrix[1][0] = y;
    }

    public void setZ(double z) {
        this.matrix[2][0] = z;
    }

    public double x() {
        return this.matrix[0][0];
    }

    public double y() {
        return this.matrix[1][0];
    }

    public double z() {
        return this.matrix[2][0];
    }

    public Vector3D translate(Vector3D t) {
        return Transform3D.transform(this, Transform3D.transMatrix(t));
    }

    public Vector3D rotateX(Double t) {
        return Transform3D.transform(this, Transform3D.rotXMatrix(t));
    }

    public Vector3D rotateY(Double t) {
        return Transform3D.transform(this, Transform3D.rotYMatrix(t));
    }

    public Vector3D rotateZ(Double t) {
        return Transform3D.transform(this, Transform3D.rotZMatrix(t));
    }

    public Vector3D scale(Vector3D t) {
        return Transform3D.transform(this, Transform3D.scaleMatrix(t));
    }

    public void setLocation(Vector3D v) {
        this.translate(this.getDifference(v));
    }

    public void setScale(Vector3D v) {
        this.scale(this.getDifference(v));
    }

    public Vector3D getDifference(Vector3D v) {
        Vector3D d = new Vector3D();
        d.setX(this.x() - v.x());
        d.setY(this.y() - v.y());
        d.setZ(this.z() - v.z());
        return d;
    }

    @Override
    public String toString() {
        return "Vector3D[" + x() + ", " + y() + ", " + z() + "]";
    }
}
