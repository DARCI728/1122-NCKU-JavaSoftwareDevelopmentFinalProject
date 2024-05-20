package main;

import object.OBJ_Sword;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Sword();
        gp.obj[0].worldX = 5 * gp.tileSize;
        gp.obj[0].worldY = 5 * gp.tileSize;

        gp.obj[1] = new OBJ_Sword();
        gp.obj[1].worldX = 3 * gp.tileSize;
        gp.obj[1].worldY = 1 * gp.tileSize;

        gp.obj[2] = new OBJ_Sword();
        gp.obj[2].worldX = 3 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new OBJ_Sword();
        gp.obj[3].worldX = 1 * gp.tileSize;
        gp.obj[3].worldY = 5 * gp.tileSize;
    }

}
