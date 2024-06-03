package object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

    public OBJ_Bow(GamePanel gp) {
        super(gp);

        name = "Bow";
        itemImage1 = getImage("/objects/bow_and_arrow.png", gp.tileSize, gp.tileSize);
        itemImage2 = getImage("/objects/bow.png", gp.tileSize, gp.tileSize);

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.drawImage(itemImage1, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
