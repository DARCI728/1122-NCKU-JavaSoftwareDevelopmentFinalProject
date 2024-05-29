package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

    public OBJ_Bow(GamePanel gp) {
        super(gp);

        name = "Bow";
        itemImage1 = getImage("/objects/Bow.png", gp.tileSize, gp.tileSize);

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(itemImage1, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
