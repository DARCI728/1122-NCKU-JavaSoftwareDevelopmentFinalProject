classDiagram
    class Entity {
        - GamePanel gp
        - String name
        - String type
        - int worldX
        - int worldY
        - Rectangle solidArea
        - int solidAreaDefaultX
        - int solidAreaDefaultY
        - boolean collision
        - BufferedImage up1
        - BufferedImage up2
        - BufferedImage down1
        - BufferedImage down2
        - BufferedImage left1
        - BufferedImage left2
        - BufferedImage right1
        - BufferedImage right2
        - BufferedImage atkUp1
        - BufferedImage atkUp2
        - BufferedImage atkDown1
        - BufferedImage atkDown2
        - BufferedImage atkLeft1
        - BufferedImage atkLeft2
        - BufferedImage atkRight1
        - BufferedImage attackRight2
        - Rectangle atkArea
        - String direction
        - int maxLife
        - int life
        - int speed
        - int spriteCounter
        - int spriteNun
        - boolean moving
        - boolean attacking
        - boolean shooting
        - boolean collisionOn
        - Projectile projectile
        - BufferedImage mobImage
        - int dyingCnt
        - boolean alive
        - boolean dying
        - boolean invincible
        - BufferedImage itemImage1
        - BufferedImage itemImage2
        + Entity(GamePanel gp)
        + BufferedImage getImage(String path, int width, int height)
        + void dyingAnimation(Graphics2D g2d)
        + void update()
        + void draw(Graphics2D g2d)
    }

    class Projectile {
        - Entity user
        + Projectile(GamePanel gp)
        + void set(int worldX, int worldY, String direction, boolean alive, Entity user)
        + void update()
    }

    class GamePanel {
    }

    class OBJ_Arrow {
        + OBJ_Arrow(GamePanel gp)
    }

    Entity <|-- Projectile
    Projectile --> Entity
    Projectile --> OBJ_Arrow
    Projectile --> GamePanel
