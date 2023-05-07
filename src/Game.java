import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends Observable {

    private int width = 600;
    private int height = 600;

    private int life = 3;

    private Paddle paddle;
    private List<Bullet> bullets;
    private List<Brick> bricks;
    private Thread mainLoop;
    private boolean alive;

    public Game() {
        alive = true;
        bullets = new CopyOnWriteArrayList<Bullet>();
        bricks = new ArrayList<>();
        paddle = new Paddle(250, 550, 100, 10, 5);
        initBullet();
        initBrick();
        mainLoop = new Thread() {
            @Override
            public void run() {
                while(alive) {
                    tick();
                    update();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mainLoop.start();
    }

    public void tick() {
        moveBullets();
        checkBullet();
    }

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
            handleBulletCollision(bullet);
        }
    }

    public void checkBullet() {
        if (bullets.isEmpty()) {
            life--;
            if (life <= 0) {
                alive = false;
                mainLoop.interrupt();
            } else {
                resetPaddle();
                initBullet();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Brick> getBricks() {return bricks;}

    public Paddle getPaddle() {
        return paddle;
    }

    public void movePaddleLeft() {
        if (paddle.getX()-paddle.getVelocity() > 0) {
            paddle.moveLeft();
        }
    }

    public void movePaddleRight() {
        if (paddle.getX()+ paddle.getWidth()+paddle.getVelocity() < width) {
            paddle.moveRight();
        }
    }

    public void initBullet() {
        int x = paddle.getX() + paddle.getWidth() / 2;
        int y = paddle.getY() - 10;
        bullets.add(new Bullet(x, y, 0, -10));
    }


    public void initBrick() {
        String[][] brickShapes = BrickShape.values()[new Random().nextInt(BrickShape.values().length)].getBrickShapes();
        int brickWidth = 50;
        int brickHeight = 15;
        int padding = 50;
        int margin = 5;
        int numCols = brickShapes[0].length;
        int totalWidth = numCols * (brickWidth + margin) - margin;
        int startX = (width - totalWidth) / 2;
        int row = 0;
        int col = 0;
        for (String[] rowShapes : brickShapes) {
            for (String brickShape : rowShapes) {
                int x = startX + col * (brickWidth + margin);
                int y = row * (brickHeight + margin) + padding;
                Brick brick = new Brick(x, y, brickWidth, brickHeight, brickShape);
                bricks.add(brick);
                col++;
            }
            col = 0;
            row++;
        }
    }

    public void resetPaddle() {
        paddle.setFreeze(true);
        paddle.setX(250);
    }

    public void unfreezePaddle() {
        paddle.setFreeze(false);
    }

    public void unfreezeBullets() {
        for (Bullet bullet : bullets) {
            bullet.unfreeze();
        }
    }

    private void handleBulletCollision(Bullet bullet) {
        if (bullet.getY() < 0) {
            bullet.reverseY();
        }
        if (bullet.getX() < 0 || bullet.getX() > width) {
            bullet.reverseX();
        }
        if (bullet.collidesWith(paddle)) {
            int collisionPoint = bullet.getX() - paddle.getX() - paddle.getWidth() / 2;
            double angle = Math.atan2(-1, collisionPoint);
            double direction = angle * 180 / Math.PI;
            if (paddle.isMoveLeft()) {
                direction -= 30;
            } else if (paddle.isMoveRight()) {
                direction += 30;
            }
            bullet.setDirection(direction);
        }
        if (bullet.getY() > height) {
            bullets.remove(bullet);
            bullet.freeze();
        }
        for (Brick brick : bricks) {
            if (brick.collidesWith(bullet)) {
                if (brick.getType() == "*") {
                    bricks.remove(brick);
                    bullet.reverseY();
                    break;
                }
                else if (brick.getType() == "-") {
                    bullet.reverseY();
                    break;
                }
                else if (brick.getType() == "%") {
                    // drop item
                    break;
                }
            }
        }
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public int getLife() {
        return life;
    }

    private boolean checkWin() {
        // System.out.println(board.numUncover);
        if (life == 0) {
            alive = false;
        }
        return false;
    }
}
