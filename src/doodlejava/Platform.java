package doodlejava;

import java.awt.Rectangle;
import oneiros.games.OSprite;
import util.Resource;

public class Platform extends OSprite{

    public Platform() {
        super(Resource.getImage("platform.png"));
    }

    public Rectangle getBase(){
        return new Rectangle(getX(), getY(), getWidth(), 2);
    }


}
