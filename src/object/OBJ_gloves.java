package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Gloves extends Entity {

    public OBJ_Gloves(GamePanel gp) {
        super(gp);

        name = "Gloves";
        itemImage1 = getImage("/objects/gloves.png", gp.tileSize, gp.tileSize);
    }
}
