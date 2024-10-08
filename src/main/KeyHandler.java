package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, undoPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.menuState) {
            menuState(code);
        }

        if (gp.gameState == gp.playState) {
            playState(code);
        }

        if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        if (gp.gameState == gp.gameClearState) {
            gameClearState(code);
        }
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_Z) {
            undoPressed = false; 
        }
    }

    public void menuState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.platSE(3);
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.platSE(3);
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playMusic(4);
                    break;

                case 1:
                    break;

                case 2:
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }

    public void playState(int code) {
        if (gp.player.inventory.size() == 1
                || (gp.ui.slotCol == 1 && gp.player.hasSword == false)
                || (gp.ui.slotCol == 2 && gp.player.hasArrow == false)) {
            gp.ui.slotCol = 0;
        }

        if (code == KeyEvent.VK_W) {
            switch (gp.ui.slotCol) {
                case 0:
                    upPressed = true;
                    break;

                case 1:
                    gp.player.direction = "up";
                    if (gp.player.hasSword) {
                        gp.player.attacking = true;
                    }
                    break;

                case 2:
                    gp.player.direction = "up";
                    if (gp.player.hasArrow) {
                        gp.player.shooting = true;
                    }

                    break;

                default:
                    break;
            }
        }

        if (code == KeyEvent.VK_S) {
            switch (gp.ui.slotCol) {
                case 0:
                    downPressed = true;
                    break;

                case 1:
                    gp.player.direction = "down";
                    if (gp.player.hasSword) {
                        gp.player.attacking = true;
                    }
                    break;

                case 2:
                    gp.player.direction = "down";
                    if (gp.player.hasArrow) {
                        gp.player.shooting = true;
                    }

                    break;

                default:
                    break;
            }
        }

        if (code == KeyEvent.VK_A) {
            switch (gp.ui.slotCol) {
                case 0:
                    leftPressed = true;
                    break;

                case 1:
                    gp.player.direction = "left";
                    if (gp.player.hasSword) {
                        gp.player.attacking = true;
                    }
                    break;

                case 2:
                    gp.player.direction = "left";
                    if (gp.player.hasArrow) {
                        gp.player.shooting = true;
                    }

                    break;
                default:
                    break;
            }
        }

        if (code == KeyEvent.VK_D) {
            switch (gp.ui.slotCol) {
                case 0:
                    rightPressed = true;
                    break;

                case 1:
                    gp.player.direction = "right";
                    if (gp.player.hasSword) {
                        gp.player.attacking = true;
                    }
                    break;

                case 2:
                    gp.player.direction = "right";
                    if (gp.player.hasArrow) {
                        gp.player.shooting = true;
                    }
                    break;

                default:
                    break;
            }

        }

        if (code == KeyEvent.VK_1) {
            gp.platSE(3);
            gp.ui.slotCol = 0;
        }

        if (code == KeyEvent.VK_2) {
            gp.platSE(3);
            gp.ui.slotCol = 1;
        }

        if (code == KeyEvent.VK_3) {
            gp.platSE(3);
            gp.ui.slotCol = 2;
        }

        if (code == KeyEvent.VK_R) {
            gp.retry();
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_Z) {
            undoPressed = true;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.platSE(5);
            gp.gameState = gp.playState;
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.platSE(3);
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.platSE(3);
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.retry();
                    gp.playMusic(4);
                    gp.gameState = gp.playState;
                    break;

                case 1:
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }

    public void gameClearState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.platSE(3);
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.platSE(3);
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.gameState = gp.menuState;
                    gp.setUpGame();
                    break;

                case 1:
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }
}
