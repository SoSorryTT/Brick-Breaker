public class Paddle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int velocity;
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private boolean freeze;

    public Paddle(int x, int y, int width, int height, int velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.isMoveLeft = false;
        this.isMoveRight = false;
        this.freeze = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }


    public void stopMoving() {
        this.isMoveLeft = false;
        this.isMoveRight = false;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void moveLeft() {
        if (!freeze) {
            x -= velocity;
            isMoveLeft = true;
            isMoveRight = false;
        }
    }

    public void moveRight() {
        if (!freeze) {
            x += velocity;
            isMoveLeft = false;
            isMoveRight = true;
        }
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }
}
