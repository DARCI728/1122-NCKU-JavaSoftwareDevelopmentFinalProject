package main;

import monster.*;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setMonster() {
        gp.mob[0] = new MOB_Slime(gp);
        gp.mob[0].worldX = gp.tileSize * 8;
        gp.mob[0].worldY = gp.tileSize * 6;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Sword(gp);
        gp.obj[0].worldX = gp.tileSize * 10;
        gp.obj[0].worldY = gp.tileSize * 7;

        gp.obj[1] = new OBJ_Bow(gp);
        gp.obj[1].worldX = 5 * gp.tileSize;
        gp.obj[1].worldY = 1 * gp.tileSize;
    }

}
