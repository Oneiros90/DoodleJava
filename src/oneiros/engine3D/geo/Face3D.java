package oneiros.engine3D.geo;

import java.awt.Color;

public class Face3D {
    
    private int[] points;
    private Color color;

    public Face3D(Color color, int... args) {
        this.points = args;
        this.color = color;
    }

    public int getPoint(int i) {
        return this.points[i];
    }

    public int getSize() {
        return points.length;
    }

    public Color getColor() {
        return color;
    }
    
}
