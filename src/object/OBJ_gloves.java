package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_gloves extends Entity {

    public OBJ_gloves(GamePanel gp) {
        super(gp);

        name = "Gloves";
        itemImage1 = getImage("/objects/gloves.png");
    }
}
