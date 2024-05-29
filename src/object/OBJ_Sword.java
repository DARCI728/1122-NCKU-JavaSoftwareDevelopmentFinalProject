package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        name = "Sword";
        itemImage1 = getImage("/objects/sword.png", gp.tileSize, gp.tileSize);

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(itemImage1, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
