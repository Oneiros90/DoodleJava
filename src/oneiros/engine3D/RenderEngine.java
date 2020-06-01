package oneiros.engine3D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import oneiros.engine3D.geo.Line3D;
import oneiros.engine3D.geo.Object3D;
import oneiros.engine3D.geo.Vector3D;

/**
 *
 * @author oneiros
 */
public class RenderEngine implements Runnable {
    
    private static final int MAX_FPS = 60;
    private static final int MIN_SLEEP = 1000/MAX_FPS;
    
    private Universe universe;
    private Camera camera;
    private RenderPanel panel;
    private Thread renderThread;
    private BufferedImage canvas;
    private BufferedImage clear;

    public RenderEngine(Universe universe, Camera camera, RenderPanel panel) {
        this.universe = universe;
        this.camera = camera;
        this.panel = panel;
        this.panel.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                synchronized(RenderEngine.this){
                    initCanvas();
                }
            }
        });
        this.initCanvas();
    }
    
    private void initCanvas(){
        this.canvas = new BufferedImage(this.panel.getWidth(), this.panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.clear = new BufferedImage(this.panel.getWidth(), this.panel.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void run() {
        try {
            while (true) {
                long t = System.currentTimeMillis();
                BufferedImage b = render();
                this.panel.setCanvas(b);
                this.panel.repaint();
                long t2 = System.currentTimeMillis();
                long sleep = MIN_SLEEP - (t2 - t);
                if (sleep > 0){
                    Thread.sleep(sleep);
                }
                long t3 = System.currentTimeMillis();
                System.out.println("FPS: " + 1000/(t3-t));
            }
        } catch (InterruptedException ex) {
        }
    }
    
    private BufferedImage render(){
        synchronized(this){
            
            Graphics2D g2D = (Graphics2D) canvas.getGraphics();
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = this.canvas.getWidth();
            int h = this.canvas.getHeight();
            int cw = (int) this.camera.getResolution().getWidth();
            int ch = (int) this.camera.getResolution().getHeight();

            this.canvas.setData(clear.getRaster());
            for (Object3D obj : this.universe.getObjects()){
                ArrayList<Vector3D> points = new ArrayList<>(obj.getPoints());
                for (int i = 0; i < points.size(); i++){
                    
                    Vector3D v = points.get(i);
                    v = this.camera.project(v);
                    points.set(i, v);

                    v.setX(v.x() * (w / cw));
                    v.setY((ch - v.y()) * (h / ch));
                    if (v.x() > 0 && v.x() < w && v.y() > 0 && v.y() < h) {
                        canvas.setRGB((int) v.x(), (int) v.y(), Color.white.getRGB());
                    }
                }
                for (Line3D l : obj.getLines()){
                    Vector3D v1 = points.get(l.getP1());
                    Vector3D v2 = points.get(l.getP2());
                    g2D.drawLine((int) v1.x(), (int) v1.y(), (int) v2.x(), (int) v2.y());
                }
                /*for (Face3D f : obj.getFaces()){
                    int[] x = new int[f.getSize()];
                    int[] y = new int[f.getSize()];
                    for (int i = 0; i < f.getSize(); i++){
                        Vector3D v = points.get(f.getPoint(i));
                        x[i] = (int) v.x();
                        y[i] = (int) v.y();
                    }
                    g2D.setColor(f.getColor());
                    g2D.fillPolygon(x, y, f.getSize());
                }*/
            }
            return canvas;
        }
    }

    public void start() {
        stop();
        renderThread = new Thread(this, "Render Thread");
        renderThread.start();
    }

    public void stop() {
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
    }
    
}
