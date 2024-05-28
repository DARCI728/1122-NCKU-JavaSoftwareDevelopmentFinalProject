package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Null extends Entity {

    public OBJ_Null(GamePanel gp) {
        super(gp);

        name = "Null";
        itemImage1 = getImage("/objects/null.png");
    }
}
