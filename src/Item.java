import javax.swing.*;
import java.awt.*;

public class Item {
    private int x;
    private int y;
    private int width;
    private int height;
    private ItemType type;
    private String image;

    public Item(int x, int y, int width, int height, ItemType type, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = image;
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
        // Implement movement logic if necessary
    }

    public String getImage() {
        return image;
    }


}
