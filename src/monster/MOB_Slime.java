package monster;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class MOB_Slime extends Entity {

    public MOB_Slime(GamePanel gp) {
        super(gp);

        maxLife = 1;
        life = maxLife;

        solidArea = new Rectangle(1, 1, 46, 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        mobImage = getImage("/monsters/monster_2.png", gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2d) {

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (invincible == true) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (dying == true) {
            dyingAnimation(g2d);
        }

        g2d.drawImage(mobImage, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
