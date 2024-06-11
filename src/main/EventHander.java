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
                eventRect[i][col][row].x = 2;
                eventRect[i][col][row].y = 2;
                eventRect[i][col][row].width = 44;
                eventRect[i][col][row].height = 44;
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
                if (hit(entity, isPlayer, true, 0, 8, 5, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 8;
                        coordinate[1] = 5;
                    }
                }
                break;

            case 1:
                if (hit(entity, isPlayer, true, 1, 9, 10, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 9;
                        coordinate[1] = 10;
                    }

                }

                if (hit(entity, isPlayer, true, 1, 7, 9, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 7;
                        coordinate[1] = 9;
                    }
                }

                if (hit(entity, isPlayer, true, 1, 11, 5, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 11;
                        coordinate[1] = 5;
                    }
                }
                break;

            case 2:
                if (hit(entity, isPlayer, true, 2, 7, 10, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 7;
                        coordinate[1] = 10;
                    }
                }

                if (hit(entity, isPlayer, true, 2, 11, 7, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 11;
                        coordinate[1] = 7;
                    }
                }

                if (hit(entity, isPlayer, true, 2, 12, 4, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 12;
                        coordinate[1] = 4;
                    }
                }

                if (hit(entity, isPlayer, true, 2, 11, 2, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 11;
                        coordinate[1] = 2;
                    }
                }
                break;

            case 3:
                if (hit(entity, isPlayer, true, 0, 8, 4, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 8;
                        coordinate[1] = 4;
                    }
                }
                break;

            case 4:
                if (hit(entity, isPlayer, true, 4, 3, 2, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 3;
                        coordinate[1] = 2;
                    }
                }

                if (hit(entity, isPlayer, true, 4, 5, 4, "any")) {
                    if (isPlayer) {
                        trap(gp.dialogueState);
                    } else {
                        coordinate[0] = 5;
                        coordinate[1] = 3;
                    }
                }

                if (hit(entity, isPlayer, false, 4, 4, 5, "any")) {
                    if (!isPlayer) {
                        coordinate[0] = 4;
                        coordinate[1] = 5;
                    }
                }

                if (hit(entity, isPlayer, false, 4, 13, 10, "any")) {
                    if (!isPlayer) {
                        coordinate[0] = 13;
                        coordinate[1] = 10;
                    }
                }

                if (hit(entity, isPlayer, true, 4, 12, 6, "any")) {
                    if (isPlayer) {
                        nextLevel(gp.dialogueState);
                    } else {
                        coordinate[0] = 12;
                        coordinate[1] = 6;
                    }
                }
                break;

            default:
                break;
        }

        return coordinate;
    }

    public boolean hit(Entity entity, boolean isPlayer, boolean oneDoneEvent, int mapNum, int col, int row,
            String reqDirection) {
        boolean hit = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        eventRect[mapNum][col][row].x = col * gp.tileSize + eventRect[mapNum][col][row].x;
        eventRect[mapNum][col][row].y = row * gp.tileSize + eventRect[mapNum][col][row].y;

        if (entity.solidArea.intersects(eventRect[mapNum][col][row])) {
            if (entity.direction.contentEquals(reqDirection)
                    || reqDirection.contentEquals("any") && eventRect[mapNum][col][row].eventDone == false) {
                if (isPlayer && oneDoneEvent) {
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
        gp.ui.currentDialouge = "You fall into a hole!";
        gp.gameState = gameState;
        gp.player.life -= 1;
    }

    public void teleport(int gameState, int col, int row) {
        gp.gameState = gameState;

        gp.player.hasteleported = true;

        gp.platSE(6);

        gp.steps++;

        if (col == 4 && row == 5) {
            gp.player.worldX = gp.tileSize * 13;
            gp.player.worldY = gp.tileSize * 10;
        }

        if (col == 13 && row == 10) {
            gp.player.worldX = gp.tileSize * 4;
            gp.player.worldY = gp.tileSize * 5;
        }

        gp.player.stopX = gp.player.worldX;
        gp.player.stopY = gp.player.worldY;
    }

    public void nextLevel(int gameState) {
        gp.ui.currentDialouge = "Go to next level!";
        gp.gameState = gameState;

        gp.steps++;
        gp.totalSteps += gp.steps;
        
        if (gp.currentMap + 1 <= 4) {
            gp.currentMap++;
            gp.retry();
        } else {
            gp.gameState = gp.gameClearState;
            gp.stopMusic();
            gp.platSE(12);
        }
    }
}
