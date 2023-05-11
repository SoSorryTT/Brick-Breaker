import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Bullet {

    private int x;
    private int y;

    private int dx;
    private int dy;

    private int speed = 1;

    private boolean frozen = true;

    public Bullet(int x, int y, int dx, int dy) {
        reset(x, y, dx, dy);
        // Creating a bullet takes up your computer resources
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        if (!frozen) { // move the bullet only if it is not frozen
            x += dx * speed;
            y += dy * speed;
        }
    }

    public void reset(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        frozen = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void freeze() {
        this.frozen = true;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void reverseX() {
        dx = -dx;
    }

    public void reverseY() {
        dy = -dy;
    }

    public boolean collidesWith(Paddle paddle) {
        Rectangle bulletRect = new Rectangle(x, y, 10, 10);
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        return bulletRect.intersects(paddleRect);
    }


    public void setDirection(double direction) {
        speed = 3;
        double radians = Math.toRadians(direction); // convert the angle from degrees to radians
        dx = (int) Math.round(speed * Math.sin(radians)); // calculate the new horizontal direction
        dy = (int) Math.round(speed * Math.cos(radians)); // calculate the new vertical direction
    }

    public int getSpeed() {
        return speed;
    }
}
