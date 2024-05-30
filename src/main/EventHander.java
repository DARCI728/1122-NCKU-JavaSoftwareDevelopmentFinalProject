package main;

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

    public void checkEvent() {
        switch (gp.currentMap) {
            case 0:
                if (hit(0, 8, 5, "any")) {
                    teleport(gp.dialogueState);
                }
                break;

            case 1:
                if (hit(1, 9, 10, "any")) {
                    trap(gp.dialogueState);
                }

                if (hit(1, 7, 9, "any")) {
                    trap(gp.dialogueState);
                }
                break;

            default:
                break;
        }
    }

    public boolean hit(int mapNum, int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[mapNum][col][row].x = col * gp.tileSize + eventRect[mapNum][col][row].x;
        eventRect[mapNum][col][row].y = row * gp.tileSize + eventRect[mapNum][col][row].y;

        if (gp.player.solidArea.intersects(eventRect[mapNum][col][row])) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")
                    && eventRect[mapNum][col][row].eventDown == false) {
                eventRect[mapNum][col][row].eventDown = true;
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[mapNum][col][row].x = eventRect[mapNum][col][row].eventRectDefaultX;
        eventRect[mapNum][col][row].y = eventRect[mapNum][col][row].eventRectDefaultY;

        return hit;
    }

    public void trap(int gameState) {
        gp.ui.currentDialouge = "You fall into a pit!";
        gp.gameState = gameState;
        gp.player.life -= 1;
    }

    public void teleport(int gameState) {
        gp.ui.currentDialouge = "Go to next level!";
        gp.gameState = gameState;
        gp.currentMap++;
        gp.retry();
    }
}
