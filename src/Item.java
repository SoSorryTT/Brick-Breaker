import javax.swing.*;
import java.awt.*;

public class Item {
    private int x;
    private int y;
    private int width;
    private int height;
    private ItemType type;
    private String image;

    private boolean hitPaddle;

    public Item(int x, int y, int width, int height, ItemType type, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = image;
        this.hitPaddle = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ItemType getType() {
        return type;
    }

    public void move() {
        y += 5;
    }

    public String getImage() {
        return image;
    }

    public void setHitPaddle(boolean hitPaddle) {
        this.hitPaddle = hitPaddle;
    }

    public boolean isHitPaddle() {
        return hitPaddle;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean collidesWith(Paddle paddle) {
        Rectangle bulletRect = new Rectangle(x, y, width, height);
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        return bulletRect.intersects(paddleRect);
    }
}
