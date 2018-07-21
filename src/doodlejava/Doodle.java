package doodlejava;

import java.awt.Image;
import java.awt.Rectangle;
import oneiros.games.OSprite;
import oneiros.physic.OVector2D;
import oneiros.sound.SoundManager;
import util.Resource;

public class Doodle extends OSprite {

    public static final OVector2D JUMP_VECTOR = new OVector2D(12, 90);
    public static final OVector2D MOVING_VECTOR = new OVector2D(0.3, 0);
    public static final Image DOODLE_RIGHT = Resource.getImage("doodleR.png");
    public static final Image DOODLE_LEFT = Resource.getImage("doodleL.png");

    public Doodle() {
        super(DOODLE_RIGHT);
    }

    public void jump() {
        this.setVelocityY(0);
        this.addVelocity(JUMP_VECTOR);
        SoundManager.play("jump");
    }

    public void stopAnyMotion() {
        this.setVelocity(null);
        this.setAcceleration(null);
    }

    public void startMovingLeft() {
        this.subAcceleration(MOVING_VECTOR);
    }

    public void stopMovingLeft() {
            this.addAcceleration(MOVING_VECTOR);
    }

    public void startMovingRight() {
        this.addAcceleration(MOVING_VECTOR);
    }

    public void stopMovingRight() {
            this.subAcceleration(MOVING_VECTOR);
    }

    public void turnRight() {
        this.setBackground(DOODLE_RIGHT);
    }

    public void turnLeft() {
        this.setBackground(DOODLE_LEFT);
    }

    public Rectangle getFeet() {
        if (this.getBackgroundImage() == DOODLE_RIGHT) {
            return new Rectangle(getX(), getY() + getHeight() - 15, 55, 15);
        } else {
            return new Rectangle(getX() + getWidth() - 55, getY() + getHeight() - 15, 55, 15);
        }
    }
}
