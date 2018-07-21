package doodlejava;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oneiros.games.AnimationListener;
import oneiros.games.NoHoldingKeyListener;
import oneiros.games.OSprite;
import oneiros.physic.OVector2D;
import oneiros.sound.SoundManager;
import util.Resource;

public class GamePanel extends OSprite {

    static {
        SoundManager.add("jump", Resource.getSoundFile("jump.wav"));
        SoundManager.add("fall", Resource.getSoundFile("pada.wav"));
    }
    private static final OVector2D GRAVITY_VECTOR = new OVector2D(0.2, 270);
    private static final double AIR_FRICTION = 0.02;
    private static final int STAGE_SCROLL_LIMIT = 300;
    private Doodle doodle;
    private ArrayList<Platform> platforms;
    private int score;
    private NewGameListener gameListener;
    private MovingDoodleKeyListener movingDoodleKeyListener = new MovingDoodleKeyListener();

    public GamePanel(Dimension size) {
        super(Resource.getImage("bg.png"));
        this.setSize(size);

        this.addKeyListener(new DoodleGameKeyListener());
        this.addKeyListener(this.movingDoodleKeyListener);

        this.newGame();
    }

    public void start() {
        this.doodle.startAnimation();
        this.doodle.jump();
    }

    private void newGame() {
        this.doodle = new Doodle();
        this.doodle.addAnimationListener(new DoodleAnimationListener());
        this.add(this.doodle);
        this.doodle.setLocation((this.getWidth() - this.doodle.getWidth()) / 2,
                this.getHeight() - this.doodle.getHeight());
        this.doodle.setAcceleration(GRAVITY_VECTOR);
        this.platforms = new ArrayList<Platform>();

        for (int i = 0; i < 10; i++) {
            Platform p = new Platform();
            p.setLocation((int) (Math.random() * (getWidth() - p.getWidth())),
                    i * ((int) (Math.random() * 50) + 50));
            this.add(p);
            this.platforms.add(p);
        }
        this.score = 0;
    }

    public void gameOver() {
        this.doodle.stopAnimation();
        this.doodle.stopAnyMotion();
        this.movingDoodleKeyListener.left = false;
        this.movingDoodleKeyListener.right = false;
        this.movingDoodleKeyListener.flush();
        JOptionPane.showMessageDialog(null, "Hai totalizzato " + score + " punti.",
                "Game Over", JOptionPane.WARNING_MESSAGE);
        this.removeAll();
        this.newGame();
        this.start();
        this.repaint();
        if (this.gameListener != null) {
            this.gameListener.gameOver();
        }
    }

    private void moveStageUp() {
        if (this.doodle.getY() < STAGE_SCROLL_LIMIT) {
            int offset = STAGE_SCROLL_LIMIT - this.doodle.getY();
            this.doodle.setY(STAGE_SCROLL_LIMIT);
            for (Platform p : platforms) {
                p.setLocation(p.getX(), p.getY() + offset);
                if (p.getY() > this.getHeight()) {
                    p.setLocation((int) (Math.random() * (getWidth() - p.getWidth())),
                            ((int) (Math.random() * 50) - 50));
                }
            }
            this.score += offset / 2;
            if (this.gameListener != null) {
                this.gameListener.updateScore(this.score);
            }
        }
    }

    public void setNewGameListener(NewGameListener scoreUpdater) {
        this.gameListener = scoreUpdater;
    }

    private class DoodleAnimationListener extends AnimationListener {

        @Override
        public void postAction() {
            if (doodle.getY() > getHeight()) {
                SoundManager.play("fall");
                gameOver();
            } else {

                double x = doodle.getVelocity().getX();
                if (Math.abs(x) < 0.1) {
                    doodle.setVelocityX(0);
                } else {
                    double friction = Math.abs(x) * AIR_FRICTION;
                    if (x > 0) {
                        doodle.setVelocityX(x - friction);
                    } else if (x < 0) {
                        doodle.setVelocityX(x + friction);
                    }
                }

                if (doodle.getVelocity().x > Doodle.MOVING_VECTOR.x) {
                    doodle.turnRight();
                } else if (doodle.getVelocity().x < -Doodle.MOVING_VECTOR.x) {
                    doodle.turnLeft();
                }

                int halfDoodle = doodle.getWidth() / 2;
                if (doodle.getX() < -halfDoodle) {
                    doodle.setX(getWidth() - halfDoodle);
                } else if (doodle.getX() > getWidth() - halfDoodle) {
                    doodle.setX(-halfDoodle);
                }

                for (Platform p : platforms) {
                    if (doodle.getVelocity().y < 0 && doodle.getFeet().intersects(p.getBase())) {
                        doodle.setY((int) p.getBounds().getY() - doodle.getHeight());
                        doodle.jump();
                    }
                }

                moveStageUp();
            }
        }
    }

    private class MovingDoodleKeyListener extends NoHoldingKeyListener {

        private boolean right = false;
        private boolean left = false;

        @Override
        public void keyPressed2(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                doodle.startMovingLeft();
                left = true;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                doodle.startMovingRight();
                right = true;
            }
        }

        @Override
        public void keyReleased2(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && left) {
                doodle.stopMovingLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && right) {
                doodle.stopMovingRight();
            }
        }
    }

    private class DoodleGameKeyListener extends KeyAdapter {

        private int pauseCount = 0;

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                pauseCount++;
                if (pauseCount % 2 == 0) {
                    doodle.startAnimation();
                } else {
                    doodle.stopAnimation();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                gameOver();
            }
        }
    }
}

interface NewGameListener {

    public void updateScore(int score);

    public void gameOver();
}
