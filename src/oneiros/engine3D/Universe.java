package oneiros.engine3D;

import java.util.ArrayList;
import oneiros.engine3D.geo.Object3D;

/**
 *
 * @author oneiros
 */
public class Universe {
    
    ArrayList<Object3D> objects;

    public Universe() {
        this.objects = new ArrayList<>();
    }

    public Universe(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    public void addObject(Object3D obj) {
        this.objects.add(obj);
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }
    
}
