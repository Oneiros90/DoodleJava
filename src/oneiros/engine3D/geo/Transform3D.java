package oneiros.engine3D.geo;

import oneiros.engine3D.math.Matrix;

/**
 *
 * @author oneiros
 */
public class Transform3D {
    
    public static Matrix eye(){
        return new Matrix(new Double[][]{{1d,0d,0d,0d},{0d,1d,0d,0d},{0d,0d,1d,0d},{0d,0d,0d,1d}});
    }
    
    public static Matrix transMatrix(Vector3D v){
        return new Matrix(new Double[][]{
            {1d,0d,0d,v.x()},
            {0d,1d,0d,v.y()},
            {0d,0d,1d,v.z()},
            {0d,0d,0d,1d}
        });
    }
    
    public static Matrix scaleMatrix(Vector3D v){
        return new Matrix(new Double[][]{
            {v.x(),0d,0d,0d},
            {0d,v.y(),0d,0d},
            {0d,0d,v.z(),0d},
            {0d,0d,0d,1d}
        });
    }
    
    public static Matrix rotXMatrix(Double teta){
        Double sin = Math.sin(teta), cos = Math.cos(teta);
        return new Matrix(new Double[][]{
            {1d,0d,0d,0d},
            {0d,cos,-sin,0d},
            {0d,sin,cos,0d},
            {0d,0d,0d,1d}
        });
    }
    
    public static Matrix rotYMatrix(Double teta){
        Double sin = Math.sin(teta), cos = Math.cos(teta);
        return new Matrix(new Double[][]{
            {cos,0d,-sin,0d},
            {0d,1d,0d,0d},
            {sin,0d,cos,0d},
            {0d,0d,0d,1d}
        });
    }
    
    public static Matrix rotZMatrix(Double teta){
        Double sin = Math.sin(teta), cos = Math.cos(teta);
        return new Matrix(new Double[][]{
            {cos,-sin,0d,0d},
            {sin,cos,0d,0d},
            {0d,0d,1d,0d},
            {0d,0d,0d,1d}
        });
    }
    
    public static Vector3D transform(Vector3D v, Matrix m){
        Matrix mul = m.mul(v);
        return new Vector3D(mul.get(0, 0), mul.get(1, 0), mul.get(2, 0));
    }
    
}
