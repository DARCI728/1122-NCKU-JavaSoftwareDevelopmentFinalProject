package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
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
    public Sound music = new Sound();
    public Sound sound = new Sound();
    public UI ui = new UI(this);

    // Entity
    public Player player = new Player(this, keyH);
    public ArrayList<Entity> obj = new ArrayList<Entity>();
    public ArrayList<Entity> projectile = new ArrayList<Entity>();
    public Entity mob[] = new Entity[10];

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
    public final int gameClearState = 5;

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
        player.setDefaultValue();
        gameState = menuState;
        currentMap = 0;
        playMusic(0);
        retry();
    }

    public void retry() {
        obj.clear();
        mob = new Entity[10];
        aSetter.setMonster();
        aSetter.setObject();
        eventH = new EventHander(this);
        player = new Player(this, keyH);
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

                for (int i = 0; i < projectile.size(); i++) {
                    if (projectile.get(i).alive) {
                        projectile.get(i).update();
                    } else {
                        projectile.remove(i);
                    }
                }

                for (int i = 0; i < obj.size(); i++) {
                    if (obj.get(i).name == "Portal") {
                        obj.get(i).update();
                    }
                }

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

                        case 2:
                            tileM.tiles[2][45].collision = false;
                            break;

                        case 3:
                            tileM.tiles[3][76].collision = false;
                            break;

                        case 4:
                            tileM.tiles[4][114].collision = false;
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

                        case 2:
                            tileM.tiles[2][45].collision = true;
                            break;

                        case 3:
                            tileM.tiles[3][76].collision = true;
                            break;

                        case 4:
                            tileM.tiles[4][114].collision = true;
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

            for (int i = 0; i < obj.size(); i++) {
                obj.get(i).draw(g2d);
            }

            for (int i = 0; i < projectile.size(); i++) {
                projectile.get(i).draw(g2d);
            }

            for (int i = 0; i < 10; i++) {
                if (mob[i] != null) {
                    mob[i].draw(g2d);
                }
            }

            player.draw(g2d);

            envM.draw(g2d);
        }

        ui.draw(g2d);

        g2d.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.setVolume(-25f);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void platSE(int i) {
        sound.setFile(i);

        switch (i) {
            case 6:
                sound.setVolume(-25f);
                break;

            case 7:
                sound.setVolume(-30f);
                break;

            case 8:
                sound.setVolume(-30f);
                break;

            case 9:
                sound.setVolume(-25f);
                break;

            case 10:
                sound.setVolume(-30f);
                break;

            case 11:
                sound.setVolume(-30f);
                break;

            case 12:
                sound.setVolume(-25f);
                break;

            default:
                sound.setVolume(-10f);
                break;
        }

        sound.play();
    }
}
