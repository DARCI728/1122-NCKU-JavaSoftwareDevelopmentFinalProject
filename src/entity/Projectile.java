package entity;

import main.GamePanel;
import object.OBJ_Arrow;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
    }

    public void update() {

        int objIndex = gp.cChecker.checkObject(this, true);

        if (objIndex != -1) {
            Entity arrow = new OBJ_Arrow(gp);
            switch (direction) {
                case "up":
                    arrow.worldX = gp.obj.get(objIndex).worldX;
                    arrow.worldY = gp.obj.get(objIndex).worldY + gp.tileSize;
                    break;

                case "down":
                    arrow.worldX = gp.obj.get(objIndex).worldX;
                    arrow.worldY = gp.obj.get(objIndex).worldY - gp.tileSize;
                    break;

                case "left":
                    arrow.worldX = gp.obj.get(objIndex).worldX + gp.tileSize;
                    arrow.worldY = gp.obj.get(objIndex).worldY;
                    break;

                case "right":
                    arrow.worldX = gp.obj.get(objIndex).worldX - gp.tileSize;
                    arrow.worldY = gp.obj.get(objIndex).worldY;
                    break;
            }

            arrow.direction = direction;
            gp.obj.add(arrow);
            gp.player.pickUpArrow(arrow);
            alive = false;
        }

        int mobIndex = gp.cChecker.checkEntity(this, gp.mob);

        if (mobIndex != -1) {
            Entity arrow = new OBJ_Arrow(gp);
            arrow.worldX = gp.mob[mobIndex].worldX;
            arrow.worldY = gp.mob[mobIndex].worldY;
            arrow.direction = direction;
            gp.obj.add(arrow);
            gp.player.pickUpArrow(arrow);
            gp.player.damageMonster(mobIndex);
            alive = false;
        }

        int coordinate[] = new int[2];
        coordinate = gp.eventH.checkEvent(this, false);

        if (coordinate[0] > 0 && coordinate[1] > 0) {
            Entity arrow = new OBJ_Arrow(gp);
            switch (direction) {
                case "up":
                    arrow.worldX = coordinate[0] * gp.tileSize;
                    arrow.worldY = coordinate[1] * gp.tileSize + gp.tileSize;
                    break;

                case "down":
                    arrow.worldX = coordinate[0] * gp.tileSize;
                    arrow.worldY = coordinate[1] * gp.tileSize - gp.tileSize;
                    break;

                case "left":
                    arrow.worldX = coordinate[0] * gp.tileSize + gp.tileSize;
                    arrow.worldY = coordinate[1] * gp.tileSize;
                    break;

                case "right":
                    arrow.worldX = coordinate[0] * gp.tileSize - gp.tileSize;
                    arrow.worldY = coordinate[1] * gp.tileSize;
                    break;
            }

            arrow.direction = direction;
            gp.obj.add(arrow);
            gp.player.pickUpArrow(arrow);
            alive = false;
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;

                case "down":
                    worldY += speed;
                    break;

                case "left":
                    worldX -= speed;
                    break;

                case "right":
                    worldX += speed;
                    break;
            }
        } else if (collisionOn == true) {
            Entity arrow = new OBJ_Arrow(gp);
            arrow.worldX = worldX;
            arrow.worldY = worldY;
            arrow.direction = direction;
            gp.obj.add(arrow);
            gp.player.pickUpArrow(arrow);
            alive = false;
        }
    }
}
