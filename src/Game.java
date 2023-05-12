import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends Observable {

    private int width = 600;
    private int height = 600;

    private int life = 3;
    private int stage = 0;
    private Paddle paddle;
    private List<Bullet> bullets;
    private List<Brick> bricks;
    private List<Item> items;
    private Thread mainLoop;
    private BulletFactory bulletFactory;
    private boolean alive;

    public Game() {
        alive = true;
        bullets = new ArrayList<Bullet>();
        bricks = new ArrayList<>();
        items = new ArrayList<>();
        bulletFactory = new BulletFactory();
        paddle = new Paddle(250, 550, 100, 10, 10);
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
        checkBrick();
        moveItem();
        checkItem();
        cleanupBullets();
    }

    public void initBullet() {
        int x = paddle.getX() + paddle.getWidth() / 2;
        int y = paddle.getY() - 10;
        Bullet bullet = bulletFactory.getBullet(x, y, 0, -10);
        bullets.add(bullet);
    }


    public void initBrick() {
        String[][] brickShapes = BrickShape.values()[stage].getBrickShapes();
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
                if (brickShape == "%") {
                    ItemType itemType = generateRandomItemType();
                    String imageName = itemType.getImageName();
                    Item item = new Item(x + brickWidth/3, y, 20, 20, itemType, imageName);
                    brick.setItem(item);
                }
                bricks.add(brick);
                col++;
            }
            col = 0;
            row++;
        }
    }
    private void moveItem() {
        if (items.isEmpty()) {
            return;
        }
        for(Item item: items) {
            item.move();
            handleItemCollision(item);
        }
    }

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
            handleBulletCollision(bullet);
        }
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

    private void checkItem() {
        for( Item item: items) {
            if (item.getY() > height) {
                bullets.remove(item);
            }
        }
    }

    private void checkBrick() {
        if (bricks.isEmpty()) {
            stage++;
            if (stage > BrickShape.values().length - 1) {
                alive = false;
                mainLoop.interrupt();
            } else {
                resetPaddle();
                initBullet();
                initBrick();
            }
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

    public List<Item> getItems() {
        return items;
    }

    public Paddle getPaddle() {
        return paddle;
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
                    bullet.reverseY();
                    items.add(brick.getItem());
                    bricks.remove(brick);
                    break;
                }
            }
        }
    }

    public  void handleItemCollision(Item item) {
        if (item.collidesWith(paddle)) {
            if (item.getType() == ItemType.EXTRA_LIFE) {
                life += 1;
            }
            else if (item.getType() == ItemType.Bullet_SPLIT) {
                Bullet baseBullet = bullets.get(0);
                Bullet bullet1 = bulletFactory.getBullet(baseBullet.getX(), baseBullet.getY(), baseBullet.getDx() + 5, baseBullet.getDy());
                Bullet bullet2 = bulletFactory.getBullet(baseBullet.getX(), baseBullet.getY(), baseBullet.getDx() - 5, baseBullet.getDy());
                bullet1.unfreeze();
                bullet1.setSpeed(3);
                bullet2.unfreeze();
                bullet2.setSpeed(3);
                bullets.add(bullet1);
                bullets.add(bullet2);
            }
            item.setHitPaddle(true);
            item.setY(height);
        }
    }

    private void cleanupBullets() {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets) {
            if(bullet.getY() > width) {
                toRemove.add(bullet);
            }
        }
        for(Bullet bullet : toRemove) {
            bullets.remove(bullet);
        }
    }

    public int getLife() {
        return life;
    }

    private boolean checkWin() {
        if (life == 0) {
            alive = false;
        }
        return false;
    }

    private ItemType generateRandomItemType() {
        Random random = new Random();
        int randomNum = random.nextInt(100) + 1;

        if (randomNum <= 50) {
            return ItemType.Bullet_SPLIT;
        } else if (randomNum <= 100) {
            return ItemType.EXTRA_LIFE;
        } else {
            return null;
        }
    }
    public void update() {
        setChanged();
        notifyObservers();
    }
}
