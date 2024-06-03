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

        if (user == gp.player) {
            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);
            if (mobIndex != -1) {
                Entity arrow = new OBJ_Arrow(gp);
                arrow.worldX = gp.mob[mobIndex].worldX;
                arrow.worldY = gp.mob[mobIndex].worldY;
                arrow.direction = direction;
                gp.obj.add(arrow);

                gp.player.damageMonster(mobIndex);
                alive = false;
            }
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
