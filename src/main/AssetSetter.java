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

            case 2:
                gp.mob[0] = new MOB_Slime(gp);
                gp.mob[0].worldX = gp.tileSize * 10;
                gp.mob[0].worldY = gp.tileSize * 5;

                gp.mob[1] = new MOB_Bat(gp);
                gp.mob[1].worldX = gp.tileSize * 6;
                gp.mob[1].worldY = gp.tileSize * 4;
                break;

            case 3:
                gp.mob[0] = new MOB_Skeleton(gp);
                gp.mob[0].worldX = gp.tileSize * 5;
                gp.mob[0].worldY = gp.tileSize * 6;

                gp.mob[1] = new MOB_Bull(gp);
                gp.mob[1].worldX = gp.tileSize * 6;
                gp.mob[1].worldY = gp.tileSize * 9;
                break;

            case 4:
                gp.mob[0] = new MOB_Slime(gp);
                gp.mob[0].worldX = gp.tileSize * 5;
                gp.mob[0].worldY = gp.tileSize * 2;

                gp.mob[1] = new MOB_Bull(gp);
                gp.mob[1].worldX = gp.tileSize * 5;
                gp.mob[1].worldY = gp.tileSize * 4;

                gp.mob[2] = new MOB_Gargoyle(gp);
                gp.mob[2].worldX = gp.tileSize * 10;
                gp.mob[2].worldY = gp.tileSize * 7;

                gp.mob[3] = new MOB_Ghost(gp);
                gp.mob[3].worldX = gp.tileSize * 13;
                gp.mob[3].worldY = gp.tileSize * 7;

                gp.mob[4] = new MOB_Devil(gp);
                gp.mob[4].worldX = gp.tileSize * 11;
                gp.mob[4].worldY = gp.tileSize * 8;
                break;

            default:
                break;
        }
    }

    public void setObject() {
        switch (gp.currentMap) {
            case 0:
                gp.obj.add(new OBJ_Sword(gp));
                gp.obj.get(0).worldX = gp.tileSize * 10;
                gp.obj.get(0).worldY = gp.tileSize * 7;
                break;

            case 1:
                gp.obj.add(new OBJ_Sword(gp));
                gp.obj.get(0).worldX = gp.tileSize * 7;
                gp.obj.get(0).worldY = gp.tileSize * 10;
                break;

            case 2:
                gp.obj.add(new OBJ_Sword(gp));
                gp.obj.get(0).worldX = gp.tileSize * 12;
                gp.obj.get(0).worldY = gp.tileSize * 10;

                gp.obj.add(new OBJ_Chair(gp));
                gp.obj.get(1).worldX = gp.tileSize * 12;
                gp.obj.get(1).worldY = gp.tileSize * 6;

                gp.obj.add(new OBJ_Chair(gp));
                gp.obj.get(2).worldX = gp.tileSize * 11;
                gp.obj.get(2).worldY = gp.tileSize * 5;
                break;

            case 3:
                gp.obj.add(new OBJ_Sword(gp));
                gp.obj.get(0).worldX = gp.tileSize * 7;
                gp.obj.get(0).worldY = gp.tileSize * 7;

                gp.obj.add(new OBJ_Bow(gp));
                gp.obj.get(1).worldX = gp.tileSize * 7;
                gp.obj.get(1).worldY = gp.tileSize * 5;

                gp.obj.add(new OBJ_Chair(gp));
                gp.obj.get(2).worldX = gp.tileSize * 9;
                gp.obj.get(2).worldY = gp.tileSize * 8;
                break;

            case 4:
                gp.obj.add(new OBJ_Sword(gp));
                gp.obj.get(0).worldX = gp.tileSize * 3;
                gp.obj.get(0).worldY = gp.tileSize * 6;

                gp.obj.add(new OBJ_Bow(gp));
                gp.obj.get(1).worldX = gp.tileSize * 12;
                gp.obj.get(1).worldY = gp.tileSize * 7;

                gp.obj.add(new OBJ_Portal(gp));
                gp.obj.get(2).worldX = gp.tileSize * 4;
                gp.obj.get(2).worldY = gp.tileSize * 5;

                gp.obj.add(new OBJ_Portal(gp));
                gp.obj.get(3).worldX = gp.tileSize * 13;
                gp.obj.get(3).worldY = gp.tileSize * 10;
                break;

            default:
                break;
        }
    }

}
