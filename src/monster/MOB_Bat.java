package monster;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class MOB_Bat extends Entity {

    public MOB_Bat(GamePanel gp) {
        super(gp);

        name = "Slime";
        maxLife = 1;
        life = maxLife;

        solidArea = new Rectangle(2, 2, 44, 44);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        mobImage = getImage("/monsters/monster_1.png", gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2d) {

        if (invincible == true) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (dying == true) {
            dyingAnimation(g2d);
        }

        g2d.drawImage(mobImage, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
