package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import entity.Entity;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    BufferedImage menuBackGround;
    Font hanyiSentyCrayon, silver;

    public int slotCol = 0;
    public int slotRow = 0;

    public Boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;

    public int commandNum = 0;
    public double playTime = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        InputStream is;

        try {
            menuBackGround = ImageIO.read(getClass().getResourceAsStream("/backGround/WorldMap.png"));
            is = getClass().getResourceAsStream("/font/HanyiSentyCrayon.ttf");
            hanyiSentyCrayon = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Silver.ttf");
            silver = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        g2d.setFont(hanyiSentyCrayon);
        g2d.setColor(Color.WHITE);

        if (gp.gameState == gp.menuState) {
            g2d.setFont(silver);
            drawMenuScreen();
        }

        if (gp.gameState == gp.playState) {
            drawInventoryScreen();
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawMenuScreen() {
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 112f));
        String text = "Dungeon Adventure";
        int x = getCenteredX(text);
        int y = gp.tileSize * 4;

        g2d.drawImage(menuBackGround, 0, 0, null);

        g2d.setColor(Color.gray);
        g2d.drawString(text, x + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 80f));
        text = "New Game";
        x = getCenteredX(text);
        y += gp.tileSize * 4;
        g2d.setColor(Color.gray);
        g2d.drawString(text, x + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x - gp.tileSize, y);
        }

        text = "Load Game";
        x = getCenteredX(text);
        y += gp.tileSize * 2;
        g2d.setColor(Color.gray);
        g2d.drawString(text, x + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - gp.tileSize, y);
        }

        text = "Quit";
        x = getCenteredX(text);
        y += gp.tileSize * 2;
        g2d.setColor(Color.gray);
        g2d.drawString(text, x + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        if (commandNum == 2) {
            g2d.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawInventoryScreen() {
        // FRAME
        int frameWidth = gp.tileSize * 5 + 20 * 6;
        int frameHeight = gp.tileSize * 1 + 20 * 2;
        int frameX = (gp.screenWidth - frameWidth) / 2;
        int frameY = gp.tileSize * 11;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;

        // DRAW PLAYER'S INVENTORY
        for (Entity obj : gp.player.inventory) {
            g2d.drawImage(obj.itemImage, slotX, slotY, null);
            slotX += gp.tileSize;
        }

        // CURSOR
        int cursorX = slotXstart + gp.tileSize * slotCol + 20 * slotCol;
        int cursorY = slotYstart + gp.tileSize * slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 80f));
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        int y = gp.screenHeight / 2;
        g2d.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200);
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255, 255);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getCenteredX(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
