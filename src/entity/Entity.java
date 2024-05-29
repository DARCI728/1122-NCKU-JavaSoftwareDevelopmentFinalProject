package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public GamePanel gp;
    public String name;
    public String type;
    public int worldX, worldY;

    // Collision
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;

    // Player
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, attackRight2;
    public Rectangle atkArea = new Rectangle(0, 0, 0, 0);
    public String direction = "down";
    public int speed = 4;
    public int spriteCounter = 0;
    public int spriteNun = 1;
    public boolean attacking = false;
    public boolean collisionOn = false;

    // Monster
    public BufferedImage mobImage;
    public int maxLife;
    public int life;
    public int dyingCnt = 0;
    public boolean alive = true;
    public boolean dying = false;
    public boolean invincible = false;

    // Objects
    public BufferedImage itemImage1, itemImage2;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage getImage(String path, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(path));
            scaleImage = uTool.scaleImage(scaleImage, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scaleImage;
    }

    public void dyingAnimation(Graphics2D g2d) {
        dyingCnt++;

        if (dyingCnt <= 5) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }

        if (dyingCnt > 5 && dyingCnt <= 10) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        if (dyingCnt > 10 && dyingCnt <= 15) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }

        if (dyingCnt > 15 && dyingCnt <= 20) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        if (dyingCnt > 20 && dyingCnt <= 25) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }

        if (dyingCnt > 25 && dyingCnt <= 30) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        if (dyingCnt > 30) {
            dying = false;
            alive = false;
        }
    }

    public void update() {
    }

    public void draw(Graphics2D g2d) {
    }
}
