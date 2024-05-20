package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean move;

    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();

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
