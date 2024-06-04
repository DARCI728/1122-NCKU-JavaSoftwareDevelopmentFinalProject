package object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile {

    GamePanel gp;

    public OBJ_Arrow(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Arrow";
        speed = 8;

        solidArea = new Rectangle(2, 2, 44, 44);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;

        getImage();
    }

    public void getImage() {
        up1 = getImage("/objects/arrow_up.png", gp.tileSize, gp.tileSize);
        down1 = getImage("/objects/arrow_down.png", gp.tileSize, gp.tileSize);
        left1 = getImage("/objects/arrow_left.png", gp.tileSize, gp.tileSize);
        right1 = getImage("/objects/arrow_right.png", gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = up1;
                break;

            case "down":
                image = down1;
                break;

            case "left":
                image = left1;
                break;

            case "right":
                image = right1;
                break;
        }

        g2d.drawImage(image, worldX, worldY, null);
    }
}
