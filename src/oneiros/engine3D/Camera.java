package oneiros.engine3D;

import java.awt.geom.Dimension2D;
import oneiros.engine3D.geo.Object3D;
import oneiros.engine3D.geo.Vector3D;
import oneiros.engine3D.math.Matrix;

/**
 *
 * @author oneiros
 */
public class Camera extends Object3D {
    
    private Dimension2D resolution;
    private double focus;
    private Matrix intrinsic;
    private Matrix extrinsic;

    public Camera(Dimension2D resolution, double focus) {
        super();
        this.resolution = resolution;
        this.focus = focus;
        //this.setPoint(0, new Vector3D());
        
        this.updateIntrinsicMatrix();
        this.updateExtrinsicMatrix();
    }

    public Vector3D project(Vector3D p) {
        Matrix projectedPoints = (this.intrinsic.mul(this.extrinsic)).mul(p);
        Vector3D v =  new Vector3D(projectedPoints);
        v.setX(v.x()/v.z());
        v.setY(v.y()/v.z());
        return v;
    }

    public void setFocus(double focus) {
        this.focus = focus;
        this.updateIntrinsicMatrix();
    }

    public void setResolution(Dimension2D resolution) {
        this.resolution = resolution;
        this.updateIntrinsicMatrix();
        this.updateExtrinsicMatrix();
    }

    @Override
    public void translate(Vector3D t) {
        super.translate(t);
        this.updateExtrinsicMatrix();
    }

    @Override
    public void rotateX(Double t) {
        super.rotateX(t);
        this.updateExtrinsicMatrix();
    }

    @Override
    public void rotateY(Double t) {
        super.rotateY(t);
        this.updateExtrinsicMatrix();
    }

    @Override
    public void rotateZ(Double t) {
        super.rotateZ(t);
        this.updateExtrinsicMatrix();
    }

    public double getFocus() {
        return focus;
    }

    public Dimension2D getResolution() {
        return resolution;
    }
    
    private void updateIntrinsicMatrix(){
        this.intrinsic = buildIntrinsicMatrix(this.focus, this.resolution.getWidth(), this.resolution.getHeight());
    }
    
    private void updateExtrinsicMatrix(){
        this.extrinsic = buildExtrinsicMatrix(this.rMatrix, this.tMatrix, this.resolution.getWidth(), this.resolution.getHeight());
    }
    
    private static Matrix buildIntrinsicMatrix(Double f, Double w, Double h){
        return new Matrix(new Double[][]{
            {-f, 0d, w/2},
            {0d, -f, h/2},
            {0d, 0d, 1d}
        });
    }
    
    private static Matrix buildExtrinsicMatrix(Matrix m, Matrix t, Double w, Double h){
        return new Matrix(new Double[][]{
            {m.get(0, 0), m.get(1, 0), m.get(2, 0), -(t.get(0, 3))},
            {m.get(0, 1), m.get(1, 1), m.get(2, 1), -(t.get(1, 3))},
            {m.get(0, 2), m.get(1, 2), m.get(2, 2), -(t.get(2, 3))}
        });
    }
    
}
