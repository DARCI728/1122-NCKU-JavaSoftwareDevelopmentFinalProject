package main;

import monster.*;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setMonster() {
        switch (gp.currentMap) {
            case 0:
                gp.mob[0] = new MOB_Slime(gp);
                gp.mob[0].worldX = gp.tileSize * 8;
                gp.mob[0].worldY = gp.tileSize * 6;
                break;

            case 1:
                gp.mob[0] = new MOB_Slime(gp);
                gp.mob[0].worldX = gp.tileSize * 9;
                gp.mob[0].worldY = gp.tileSize * 9;
                break;

            default:
                break;
        }
    }

    public void setObject() {
        switch (gp.currentMap) {
            case 0:
                gp.obj[0] = new OBJ_Sword(gp);
                gp.obj[0].worldX = gp.tileSize * 10;
                gp.obj[0].worldY = gp.tileSize * 7;
                gp.obj[1] = new OBJ_Bow(gp);
                gp.obj[1].worldX = gp.tileSize * 9;
                gp.obj[1].worldY = gp.tileSize * 7;
                
                break;

            case 1:
                gp.obj[0] = new OBJ_Sword(gp);
                gp.obj[0].worldX = gp.tileSize * 7;
                gp.obj[0].worldY = gp.tileSize * 10;
                break;

            default:
                break;
        }
    }

}
