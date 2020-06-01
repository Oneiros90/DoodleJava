package oneiros.engine3D.geo;

import java.util.ArrayList;
import oneiros.engine3D.math.Matrix;

/**
 *
 * @author oneiros
 */
public class Object3D {
    
    protected Matrix tMatrix;
    protected Matrix rMatrix;
    protected Matrix sMatrix;
    protected ArrayList<Vector3D> points;
    //private ArrayList<Vector3D> cpoints;
    protected ArrayList<Line3D> lines;
    protected ArrayList<Face3D> faces;

    public Object3D(ArrayList<Vector3D> points, ArrayList<Line3D> lines, ArrayList<Face3D> faces) {
        this.setPoints(points);
        this.lines = lines;
        this.faces = faces;
        this.tMatrix = Transform3D.eye();
        this.rMatrix = Transform3D.eye();
        this.sMatrix = Transform3D.eye();
    }

    public Object3D(ArrayList<Vector3D> points, ArrayList<Line3D> lines) {
        this(points, lines, new ArrayList<Face3D>());
    }

    public Object3D(ArrayList<Vector3D> points) {
        this(points, new ArrayList<Line3D>());
    }

    public Object3D() {
        this(new ArrayList<Vector3D>(), new ArrayList<Line3D>());
    }

    public final void setPoints(ArrayList<Vector3D> points) {
        this.points = points;
        //this.cpoints = new ArrayList<>(this.points);
    }

    public void setPoint(int index, Vector3D point) {
        this.points.set(index, point);
        //this.cpoints.set(index, point);
    }

    public void addPoint(Vector3D point) {
        this.points.add(point);
        //this.cpoints.add(point);
    }
    
    public void addLine(Line3D l){
        this.lines.add(l);
    }
    
    public void addFace(Face3D f){
        this.faces.add(f);
    }
    
    public void translate(Vector3D t){
        this.tMatrix.add(0, 3, t.x());
        this.tMatrix.add(1, 3, t.y());
        this.tMatrix.add(2, 3, t.z());
        for (int i = 0; i < this.points.size(); i++){
            this.points.set(i, this.points.get(i).translate(t));
        }
    }
    
    public void rotateX(Double t){
        Matrix m = Transform3D.rotXMatrix(t);
        this.rMatrix = this.rMatrix.mul(m);
        for (int i = 0; i < this.points.size(); i++){
            this.points.set(i, this.points.get(i).rotateX(t));
        }
    }
    
    public void rotateY(Double t){
        Matrix m = Transform3D.rotYMatrix(t);
        this.rMatrix = this.rMatrix.mul(m);
        for (int i = 0; i < this.points.size(); i++){
            this.points.set(i, this.points.get(i).rotateY(t));
        }
    }
    
    public void rotateZ(Double t){
        Matrix m = Transform3D.rotZMatrix(t);
        this.rMatrix = this.rMatrix.mul(m);
        for (int i = 0; i < this.points.size(); i++){
            this.points.set(i, this.points.get(i).rotateZ(t));
        }
    }
    
    public void scale(Vector3D t){
        Matrix m = Transform3D.scaleMatrix(t);
        this.sMatrix = this.sMatrix.times(m);
        for (int i = 0; i < this.points.size(); i++){
            this.points.set(i, this.points.get(i).scale(t));
        }
    }

    public ArrayList<Vector3D> getPoints() {
        return points;
    }

    public ArrayList<Line3D> getLines() {
        return lines;
    }

    public ArrayList<Face3D> getFaces() {
        return faces;
    }
    
}
