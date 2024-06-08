package object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Portal extends Entity {

    int drawCnt;

    public OBJ_Portal(GamePanel gp) {
        super(gp);

        name = "Portal";
        itemImage1 = getImage("/objects/portal_1.png", gp.tileSize, gp.tileSize);
        itemImage2 = getImage("/objects/portal_2.png", gp.tileSize, gp.tileSize);
        itemImage2 = getImage("/objects/portal_3.png", gp.tileSize, gp.tileSize);

        solidArea = new Rectangle(2, 2, 44, 44);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;

        drawCnt = 0;
    }

    public void update() {
        drawCnt++;

        if (drawCnt >= 40) {
            drawCnt = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (drawCnt >= 0 && drawCnt < 15) {
            g2d.drawImage(itemImage1, worldX, worldY, gp.tileSize, gp.tileSize, null);
        }

        if (drawCnt >= 15 && drawCnt < 30) {
            g2d.drawImage(itemImage2, worldX, worldY, gp.tileSize, gp.tileSize, null);
        }

        if (drawCnt >= 30 && drawCnt < 45) {
            g2d.drawImage(itemImage3, worldX, worldY, gp.tileSize, gp.tileSize, null);
        }
    }
}
