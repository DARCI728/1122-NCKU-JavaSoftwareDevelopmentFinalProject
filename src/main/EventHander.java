package main;

import entity.Entity;

public class EventHander {
    GamePanel gp;
    EventRect eventRect[][][];

    public EventHander(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];

        for (int i = 0; i < gp.maxMap; i++) {
            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                eventRect[i][col][row] = new EventRect();
                eventRect[i][col][row].x = 23;
                eventRect[i][col][row].y = 23;
                eventRect[i][col][row].width = 2;
                eventRect[i][col][row].height = 2;
                eventRect[i][col][row].eventRectDefaultX = eventRect[i][col][row].x;
                eventRect[i][col][row].eventRectDefaultY = eventRect[i][col][row].y;

                col++;
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    public int[] checkEvent(Entity entity, boolean isPlayer) {
        int coordinate[] = new int[2];

        switch (gp.currentMap) {
            case 0:
                if (hit(entity, isPlayer, 0, 8, 5, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 8;
                        coordinate[1] = 5;
                    }
                }
                break;

            case 1:
                if (hit(entity, isPlayer, 1, 9, 10, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 9;
                        coordinate[1] = 10;
                    }

                }

                if (hit(entity, isPlayer, 1, 7, 9, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 7;
                        coordinate[1] = 9;
                    }
                }
                break;

            default:
                break;
        }

        return coordinate;
    }

    public boolean hit(Entity entity, boolean isPlayer, int mapNum, int col, int row, String reqDirection) {
        boolean hit = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        eventRect[mapNum][col][row].x = col * gp.tileSize + eventRect[mapNum][col][row].x;
        eventRect[mapNum][col][row].y = row * gp.tileSize + eventRect[mapNum][col][row].y;

        if (entity.solidArea.intersects(eventRect[mapNum][col][row])) {
            if (entity.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")
                    && eventRect[mapNum][col][row].eventDone == false) {
                if (isPlayer) {
                    eventRect[mapNum][col][row].eventDone = true;
                }
                hit = true;
            }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        eventRect[mapNum][col][row].x = eventRect[mapNum][col][row].eventRectDefaultX;
        eventRect[mapNum][col][row].y = eventRect[mapNum][col][row].eventRectDefaultY;

        return hit;
    }

    public void trap(int gameState) {
        gp.ui.currentDialouge = "You fall into a pit!";
        gp.gameState = gameState;
        gp.player.life -= 1;
    }

    public void nextLevel(int gameState) {
        gp.ui.currentDialouge = "Go to next level!";
        gp.gameState = gameState;
        gp.currentMap++;
        gp.retry();
    }
}
