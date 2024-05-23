package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean move;

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
    }

    public void menuState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.gameState = gp.playState;
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
        if (code == KeyEvent.VK_W) {
            upPressed = true;
            move = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
            move = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
            move = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
            move = true;
        }

        if (code == KeyEvent.VK_1) {
            gp.ui.slotCol = 0;
        }

        if (code == KeyEvent.VK_2) {
            gp.ui.slotCol = 1;
        }

        if (code == KeyEvent.VK_3) {
            gp.ui.slotCol = 2;
        }

        if (code == KeyEvent.VK_4) {
            gp.ui.slotCol = 3;
        }

        if (code == KeyEvent.VK_5) {
            gp.ui.slotCol = 4;
        }

        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
    }

}
