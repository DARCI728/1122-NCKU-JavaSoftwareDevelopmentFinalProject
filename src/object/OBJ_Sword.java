package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        type = "object";
        name = "Sword";
        itemImage = setUpImage("/objects/sword.png");
        
        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(itemImage, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
