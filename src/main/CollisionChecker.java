package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = entityTopRow * gp.maxScreenCol + entityLeftCol;
                tileNum2 = entityTopRow * gp.maxScreenCol + entityRightCol;
                if (gp.tileM.tiles[gp.currentMap][tileNum1].collision == true || gp.tileM.tiles[gp.currentMap][tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = entityBottomRow * gp.maxScreenCol + entityLeftCol;
                tileNum2 = entityBottomRow * gp.maxScreenCol + entityRightCol;
                if (gp.tileM.tiles[gp.currentMap][tileNum1].collision == true || gp.tileM.tiles[gp.currentMap][tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = entityTopRow * gp.maxScreenCol + entityLeftCol;
                tileNum2 = entityBottomRow * gp.maxScreenCol + entityLeftCol;
                if (gp.tileM.tiles[gp.currentMap][tileNum1].collision == true || gp.tileM.tiles[gp.currentMap][tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = entityTopRow * gp.maxScreenCol + entityRightCol;
                tileNum2 = entityBottomRow * gp.maxScreenCol + entityRightCol;
                if (gp.tileM.tiles[gp.currentMap][tileNum1].collision == true || gp.tileM.tiles[gp.currentMap][tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gp.obj.size(); i++) {
            if (gp.obj.get(i) != null) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.obj.get(i).solidArea.x = gp.obj.get(i).worldX + gp.obj.get(i).solidArea.x;
                gp.obj.get(i).solidArea.y = gp.obj.get(i).worldY + gp.obj.get(i).solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(gp.obj.get(i).solidArea)) {
                    if (gp.obj.get(i).collision == true) {
                        entity.collisionOn = true;
                    }

                    if (player == true) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj.get(i).solidArea.x = entity.solidAreaDefaultX;
                gp.obj.get(i).solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = -1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = entity.solidAreaDefaultX;
                target[i].solidArea.y = entity.solidAreaDefaultY;
            }
        }

        return index;
    }
}
