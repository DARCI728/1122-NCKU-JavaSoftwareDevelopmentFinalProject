classDiagram
    class EnvironmentManager {
        - GamePanel gp
        - Lighting lighting
        + EnvironmentManager(GamePanel gp)
        + void setUpEnvironment()
        + void draw(Graphics2D g2d)
    }

    class GamePanel {
    }

    class Lighting {
        + Lighting(GamePanel gp, int size)
        + void draw(Graphics2D g2d)
    }

    EnvironmentManager --> GamePanel
    EnvironmentManager --> Lighting
