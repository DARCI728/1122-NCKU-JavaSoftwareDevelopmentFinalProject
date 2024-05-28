package monster;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class MOB_Slime extends Entity {

    public MOB_Slime(GamePanel gp) {
        super(gp);

        name = "Slime";
        maxLife = 1;
        life = maxLife;

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        mobImage = getImage("/monsters/monster_2.png");
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(mobImage, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
