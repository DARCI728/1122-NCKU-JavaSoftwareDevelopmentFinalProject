package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {
    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setUpEnvironment() {
        lighting = new Lighting(gp, 624);
    }

    public void draw(Graphics2D g2d) {
        lighting.draw(g2d);
    }
}
