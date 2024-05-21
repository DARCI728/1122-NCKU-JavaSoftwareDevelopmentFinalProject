package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class UI {

    GamePanel gp;
    Graphics2D g2d;
    BufferedImage menuBackGround;
    Font hanyiSentyCrayon, silver;

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
        Color color = new Color(0, 0, 0, 128);
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, 35, 35);
    }

    public int getCenteredX(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
