package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import entity.*;
import environment.EnvironmentManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTitleSize = 24;
    final int scale = 2;

    public final int tileSize = originalTitleSize * scale;
    public final int maxScreenCol = 17;
    public final int maxScreenRow = 13;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    // FPS
    int fps = 60;

    // System settings
    Thread gameThread;
    TileManager tileM = new TileManager(this);
    EnvironmentManager envM = new EnvironmentManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EventHander eventH = new EventHander(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);

    // Entity
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity mob[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    // Maps
    public final int maxMap = 10;
    public int currentMap = 0;

    // Game State
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int pauseState = 3;
    public final int gameOverState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
    }

    public void setUpGame() {
        aSetter.setMonster();
        aSetter.setObject();
        envM.setUpEnvironment();
        gameState = menuState;
    }

    public void retry() {
        obj = new Entity[10];
        mob = new Entity[10];
        entityList.clear();
        aSetter.setMonster();
        aSetter.setObject();
        eventH = new EventHander(this);
        player.setDefaultValue();
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

                for (int i = 0; i < mob.length; i++) {
                    if (mob[i] != null) {
                        if (mob[i].alive == false) {
                            mob[i] = null;
                        }
                    }
                }

                int mobCnt = 0;

                for (int i = 0; i < mob.length; i++) {
                    if (mob[i] != null) {
                        mobCnt++;
                    }
                }

                if (mobCnt == 0) {
                    switch (currentMap) {
                        case 0:
                            tileM.tiles[0][93].collision = false;
                            break;

                        case 1:
                            tileM.tiles[1][96].collision = false;
                            break;

                        default:
                            break;
                    }
                } else {
                    switch (currentMap) {
                        case 0:
                            tileM.tiles[0][93].collision = true;
                            break;

                        case 1:
                            tileM.tiles[1][96].collision = true;
                            break;

                        default:
                            break;
                    }
                }

                break;

            default:
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (gameState != menuState) {
            tileM.draw(g2d, currentMap);

            entityList.add(player);

            for (Entity mob : mob) {
                if (mob != null) {
                    entityList.add(mob);
                }
            }

            for (Entity obj : obj) {
                if (obj != null) {
                    entityList.add(obj);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {

                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2d);
            }

            entityList.clear();

            envM.draw(g2d);
        }

        ui.draw(g2d);

        g2d.dispose();
    }
}
