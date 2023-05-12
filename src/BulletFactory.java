import java.util.ArrayList;
import java.util.List;

class BulletFactory {
    private List<Bullet> bulletPool = new ArrayList<Bullet>();

    private int index;
    public BulletFactory() {
        for(int i = 0; i < 100; i++) {
            bulletPool.add(new Bullet(999, 999, 0, 0));
        }
    }

    public Bullet getBullet(int x, int y, int dx, int dy) {
        Bullet bullet = bulletPool.get(index);
        bullet.reset(x, y, dx, dy);
        index = (index + 1) % bulletPool.size();
        return bullet;
    }
}