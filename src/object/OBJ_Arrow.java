package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Arrow extends Entity {
    private String direction;
    private int speed = 8;
    boolean moving = false;
    int stopPosition = -1;

    public OBJ_Arrow(GamePanel gp, int x, int y, String direction) {
        super(gp);
        this.direction = direction;
        this.worldX = x;
        this.worldY = y;
        name = "Arrow";
        itemImage1 = getImage("/objects/arrow_" + direction + ".png", gp.tileSize, gp.tileSize);

        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void update() {

        moving = true;
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECK MOB COLLISION
        int mobIndex = gp.cChecker.checkEntity(this, gp.mob);
        // CHECK OBJ COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        stopPosition = shootOnObject(objIndex, stopPosition);
        if (stopPosition != -1) {
            switch (direction) {
                case "up":
                    if (worldY - speed <= stopPosition) {
                        collisionOn = true;
                    }
                    break;

                case "down":
                    if (worldY + speed >= stopPosition) {
                        collisionOn = true;
                    }
                    break;

                case "left":
                    if (worldX - speed <= stopPosition) {
                        collisionOn = true;
                    }
                    break;

                case "right":
                    if (worldX + speed >= stopPosition) {
                        collisionOn = true;
                    }
                    break;
            }
        }
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
            stopPosition = -1;
            moving = false;
        }
        if (mobIndex != -1) {
            gp.player.damageMonster(mobIndex);
            worldX = gp.mob[mobIndex].worldX;
            worldY = gp.mob[mobIndex].worldY;
        }

    }

    public int shootOnObject(int i, int originaStopPosition) {
        int stopPosition = -1;

        if (i != -1) {
            switch (direction) {
                case "up":
                    stopPosition = gp.obj[i].worldY + 1 * gp.tileSize;
                    stopPosition = gp.obj[i].worldY;
                    break;

                case "down":
                    stopPosition = gp.obj[i].worldY - 1 * gp.tileSize;
                    stopPosition = gp.obj[i].worldY;
                    break;

                case "left":
                    stopPosition = gp.obj[i].worldX + 1 * gp.tileSize;
                    stopPosition = gp.obj[i].worldX;
                    break;

                case "right":
                    stopPosition = gp.obj[i].worldX - 1 * gp.tileSize;
                    stopPosition = gp.obj[i].worldX;
                    break;
            }
        }

        if (originaStopPosition != -1) {
            stopPosition = originaStopPosition;
        }

        return stopPosition;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(itemImage1, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
