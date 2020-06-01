package oneiros.games;

import java.awt.Image;
import java.util.ArrayList;
import oneiros.gui.OPanel;
import oneiros.physic.OPoint;
import oneiros.physic.OVector2D;

public class OSprite extends OPanel {

    private OPoint location;
    private OVector2D velocity;
    private OVector2D acceleration;
    private ArrayList<AnimationListener> aListeners;
    private Animation animation;

    public OSprite() {
        super();
        this.location = new OPoint();
        this.velocity = new OVector2D();
        this.acceleration = new OVector2D();
        this.aListeners = new ArrayList<AnimationListener>();
    }

    public OSprite(Image bg) {
        this();
        this.setBackground(bg);
        this.setSize(bg.getWidth(null), bg.getHeight(null));
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        this.location.setLocation(x, y);
    }

    public void setX(int x) {
        this.setLocation(x, this.getY());
    }

    public void setY(int y) {
        this.setLocation(this.getX(), y);
    }

    public void setVelocity(OVector2D v) {
        if (v == null) {
            v = OVector2D.ZERO_VECTOR;
        }
        this.velocity.setLocation(v);
    }

    public void setVelocityX(double x) {
        this.velocity.setLocation(x, this.velocity.y);
    }

    public void setVelocityY(double y) {
        this.velocity.setLocation(this.velocity.x, y);
    }

    public void addVelocity(OVector2D v) {
        this.velocity.sum(v);
    }

    public void subVelocity(OVector2D v) {
        this.velocity.sub(v);
    }

    public void setAcceleration(OVector2D v) {
        if (v == null) {
            v = OVector2D.ZERO_VECTOR;
        }
        this.acceleration.setLocation(v);
    }

    public void setAccelerationX(double x) {
        this.acceleration.setLocation(x, this.acceleration.y);
    }

    public void setAccelerationY(double y) {
        this.acceleration.setLocation(this.acceleration.x, y);
    }

    public void addAcceleration(OVector2D v) {
        this.acceleration.sum(v);
    }

    public void subAcceleration(OVector2D v) {
        this.acceleration.sub(v);
    }

    public OVector2D getVelocity() {
        return this.velocity;
    }

    public OVector2D getAcceleration() {
        return this.acceleration;
    }

    public void addAnimationListener(AnimationListener al) {
        this.aListeners.add(al);
    }

    public void removeAnimationListener(AnimationListener al) {
        this.aListeners.remove(al);
    }

    public void startAnimation() {
        this.animation = new Animation();
        this.animation.start();
    }

    public void stopAnimation() {
        if (this.animation != null) {
            this.animation.interrupt();
        }
    }

    private class Animation extends Thread {

        private boolean interrupted = false;

        @Override
        public void run() {
            try {
                while (true) {

                    preActions();
                    if (interrupted) {
                        break;
                    }

                    double x = location.getX() + velocity.getX();
                    double y = location.getY() - velocity.getY();

                    setLocation((int) x, (int) y);
                    location.setLocation(x, y);

                    velocity.sum(acceleration);

                    postActions();
                    if (interrupted) {
                        break;
                    }

                    sleep(10);
                }
            } catch (InterruptedException ex) {
            }
        }

        @Override
        public void interrupt() {
            this.interrupted = true;
        }

        private void preActions() {
            for (AnimationListener al : aListeners) {
                al.preAction();
            }
        }

        private void postActions() {
            for (AnimationListener al : aListeners) {
                al.postAction();
            }
        }
    };
}