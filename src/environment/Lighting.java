package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darknessFilter.getGraphics();
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        int centerX = gp.maxScreenCol * gp.tileSize / 2;
        int centerY = gp.maxScreenRow * gp.tileSize / 2;

        double x = centerX - circleSize / 2;
        double y = centerY - circleSize / 2;

        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        Area lightArea = new Area(circleShape);

        screenArea.subtract(lightArea);

        Color color[] = new Color[5];
        float fraction[] = new float[5];

        for (int i = 0; i < 5; i++) {
            color[i] = new Color(0, 0, 0, 0.24f * i);
            fraction[i] = 0.25f * i;
        }

        RadialGradientPaint paint = new RadialGradientPaint(centerX, centerY, circleSize / 2, fraction, color);

        g2d.setPaint(paint);

        g2d.fill(lightArea);

        g2d.fill(screenArea);

        g2d.dispose();
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.drawImage(darknessFilter, 0, 0, null);
    }
}
