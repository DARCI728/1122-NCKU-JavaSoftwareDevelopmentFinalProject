package entity;

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
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;

    // Player
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";
    public int speed = 4;
    public int spriteCounter = 0;
    public int spriteNun = 1;
    public boolean collisionOn = false;

    // Monster
    public BufferedImage mobImage;
    public int maxLife;
    public int life;

    // Objects
    public BufferedImage itemImage1, itemImage2;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage getImage(String path) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(path));
            scaleImage = uTool.scaleImage(scaleImage, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scaleImage;
    }

    public void update() {
    }

    public void draw(Graphics2D g2d) {
    }
}
