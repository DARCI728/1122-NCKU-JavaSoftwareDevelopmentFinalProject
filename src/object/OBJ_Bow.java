package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

    public OBJ_Bow(GamePanel gp) {
        super(gp);

        type = "object";
        name = "bow";
        itemImage1 = setUpImage("/objects/sword.png");

    }
}
