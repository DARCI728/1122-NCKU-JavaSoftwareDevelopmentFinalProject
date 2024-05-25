package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        type = "object";
        name = "boots";
        itemImage1 = setUpImage("/objects/sword.png");
    }
}
