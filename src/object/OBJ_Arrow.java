package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Arrow extends Entity {

    public OBJ_Arrow(GamePanel gp) {
        super(gp);

        type = "object";
        name = "arrow";
        itemImage1 = getImage("/objects/sword.png", gp.tileSize, gp.tileSize);
    }
}
