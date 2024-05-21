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

        // Menu State
        if (gp.gameState == gp.menuState) {
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
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
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
}
