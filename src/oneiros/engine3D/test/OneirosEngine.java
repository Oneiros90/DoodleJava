package oneiros.engine3D.test;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import oneiros.engine3D.Camera;
import oneiros.engine3D.RenderEngine;
import oneiros.engine3D.RenderPanel;
import oneiros.engine3D.Universe;
import oneiros.engine3D.geo.Face3D;
import oneiros.engine3D.geo.Line3D;
import oneiros.engine3D.geo.Object3D;
import oneiros.engine3D.geo.Vector3D;
import oneiros.gui.OFrame;

/**
 *
 * @author oneiros
 */
public class OneirosEngine {

    private static OFrame frame;
    private static RenderPanel display;
    private static Universe universe;
    private static Camera camera;

    public static void main(String[] args) {
        
        ArrayList points = new ArrayList<>();
        points.add(new Vector3D(0, 0, 0));
        points.add(new Vector3D(0, 3, 0));
        points.add(new Vector3D(3, 0, 0));
        points.add(new Vector3D(3, 3, 0));
        points.add(new Vector3D(0, 0, 3));
        points.add(new Vector3D(0, 3, 3));
        points.add(new Vector3D(3, 0, 3));
        points.add(new Vector3D(3, 3, 3));
        Object3D cube = new Object3D(points);
        cube.addLine(new Line3D(0, 1));
        cube.addLine(new Line3D(0, 2));
        cube.addLine(new Line3D(1, 3));
        cube.addLine(new Line3D(2, 3));
        cube.addLine(new Line3D(4, 5));
        cube.addLine(new Line3D(4, 6));
        cube.addLine(new Line3D(5, 7));
        cube.addLine(new Line3D(6, 7));
        cube.addLine(new Line3D(0, 4));
        cube.addLine(new Line3D(1, 5));
        cube.addLine(new Line3D(2, 6));
        cube.addLine(new Line3D(3, 7));
        cube.addFace(new Face3D(Color.red, 0, 1, 3, 2));
        cube.addFace(new Face3D(Color.blue, 0, 1, 5, 4));
        cube.addFace(new Face3D(Color.yellow, 0, 2, 6, 4));
        cube.addFace(new Face3D(Color.green, 1, 3, 7, 5));
        cube.addFace(new Face3D(Color.magenta, 2, 3, 7, 6));
        cube.addFace(new Face3D(Color.orange, 4, 5, 7, 6));
        
        universe = new Universe();
        universe.addObject(cube);
        
        camera = new Camera(new Dimension(20, 20), 20);
        camera.translate(new Vector3D(0, 0, 20));
        new Thread(){

            @Override
            public void run() {
                while(true){
                    try {
                        camera.rotateX(Math.PI/160);
                        camera.rotateY(Math.PI/160);
                        camera.rotateZ(Math.PI/160);
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }.start();

        display = new RenderPanel(600, 500);
        RenderEngine renderer = new RenderEngine(universe, camera, display);
        renderer.start();
                
        frame = new OFrame(600, 500);
        frame.add(display);
        frame.setVisible(true);
    }
}
