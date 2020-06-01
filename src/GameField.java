import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 376, KUSOK_SIZE = 16, ALL_KUSKI = 400;
    private Image kusok, coin;
    private int coinX;
    private int coinY;
    private int[] x = new int[ALL_KUSKI];
    private int[] y = new int[ALL_KUSKI];
    private int kuski;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField() {
        setBackground(Color.DARK_GRAY);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        kuski = 2;
        for (int i = 0; i < kuski; i++) {
            x[i] = 48 - i * KUSOK_SIZE;
            y[i] = 48;
        }
        timer = new Timer(70, this);
        timer.start();
        createCoin();
    }

    public void createCoin() {
        coinX = new Random().nextInt(20) * KUSOK_SIZE;
        coinY = new Random().nextInt(20) * KUSOK_SIZE;
    }

    public void loadImages() {
        coin = new ImageIcon("E:\\coin.png").getImage();
        kusok = new ImageIcon("E:\\kusok.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(coin, coinX, coinY, this);
            for (int i = 0; i < kuski; i++) {
                g.drawImage(kusok, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str, SIZE / 2 -32, SIZE / 2);
        }
    }

    public void move() {
        for (int i = kuski; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) x[0] -= KUSOK_SIZE;
        if (right) x[0] += KUSOK_SIZE;
        if (up) y[0] -= KUSOK_SIZE;
        if (down) y[0] += KUSOK_SIZE;
    }

    public void checkCoin() {
        if (x[0] == coinX && y[0] == coinY) {
            kuski++;
            createCoin();
        }
    }

    public void checkCollisions() {
        for (int i = kuski; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if ((x[0] > SIZE) || (x[0] < 0) || (y[0] > SIZE) || (y[0] < 0 - 96)) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCoin();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}


