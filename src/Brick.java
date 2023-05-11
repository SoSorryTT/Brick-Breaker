import java.awt.*;

public class Brick {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean destroyed;
    private boolean visible = true;

    private String type;

    private Item item;

    public Brick(int x, int y, int width, int height, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.type = type;
        if (!type.equals("*")) {
            setVisible(false);
        }
    }

    public void draw(Graphics2D g2) {
        if (!destroyed) {
            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, width, height);
        }
    }

    public boolean collidesWith(Bullet bullet) {
        Rectangle brickRect = new Rectangle(x, y, width, height);
        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), 10, 10);
        return brickRect.intersects(bulletRect) && !destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
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

    public void setVisible(boolean stage) {
        visible = stage;
    }

    public String getType() {
        return type;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
