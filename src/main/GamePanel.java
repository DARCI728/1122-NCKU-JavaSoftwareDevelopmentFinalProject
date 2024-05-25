package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTitleSize = 48;
    final int scale = 1;

    public final int tileSize = originalTitleSize * scale;
    public final int maxScreenCol = 17;
    public final int maxScreenRow = 13;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    // FPS
    int fps = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);

    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);

    // Entity and Object
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];

    // Game State
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int dialogueState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
    }

    public void setUpGame() {
        aSetter.setObject();
        gameState = menuState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timmer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timmer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (timmer >= 1000000000) {
                timmer = 0;
            }
        }
    }

    public void update() {
        switch (gameState) {
            case playState:
                player.update();
                break;

            default:
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (gameState == menuState) {
            ui.draw(g2d);
        } else {
            tileM.draw(g2d);

            for (Entity obj : obj) {
                if (obj != null) {
                    obj.draw(g2d);
                }
            }

            player.draw(g2d);

            ui.draw(g2d);
        }

        g2d.dispose();
    }
}
