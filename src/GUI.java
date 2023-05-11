import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class GUI extends JFrame implements Observer {

    private Game game;

    private Renderer renderer;

    public GUI() {
        game = new Game();
        game.addObserver(this);
        setTitle("Brick Breaker");

        renderer = new Renderer();
        renderer.setFocusable(true);
        renderer.addKeyListener(new Controller());

        setLayout(new BorderLayout());
        add(renderer);



        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public void start() {
        setVisible(true);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    class Renderer extends JPanel {

        private final Image heartImage;

        public Renderer() {
            setDoubleBuffered(true);
            setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
            ImageIcon icon = new ImageIcon("Images/heart.png");
            heartImage = icon.getImage();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            // Draw space
            g.setColor(Color.black);
            g.fillRect(0, 0, game.getWidth(), game.getHeight());

            // Draw life that player has left
            int heartSize = 25;
            int heartPadding = 5;
            for (int i = 0; i < game.getLife(); i++) {
                g.drawImage(heartImage, 5 + i * (heartSize + heartPadding), 10, heartSize, heartSize, this);
            }

            // Draw bullets
            g.setColor(Color.green);
            for(Bullet bullet : game.getBullets()) {
                g.fillOval(bullet.getX(), bullet.getY(), 10, 10);
            }
            //Draw Paddle
            g.setColor(Color.white);
            Paddle paddle = game.getPaddle();
            g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

            //Draw Brick

            for (Brick brick : game.getBricks()) {
                if (brick.getType() == "*") {
                    g.setColor(Color.ORANGE);
                } else if (brick.getType() == "-") {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

    class Controller extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                game.movePaddleLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                game.movePaddleRight();
            } else if (keyCode == KeyEvent.VK_SPACE) {
                game.unfreezeBullets();
                game.unfreezePaddle();
            }
            game.update();
        }
    }

}
