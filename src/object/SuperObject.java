package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {

    public UtilityTool uTool = new UtilityTool();
    public BufferedImage image;
    public String name;
    public int worldX, worldY;
    public boolean collision = false;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2d, GamePanel gp) {
        g2d.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
