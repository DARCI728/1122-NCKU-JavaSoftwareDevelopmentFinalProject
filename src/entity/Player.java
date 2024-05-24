package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Boots;

public class Player extends Entity {

    KeyHandler keyH;

    int stopPosition = -1;
    boolean moving = false;

    public Entity currentEquipment;
    public ArrayList<Entity> inventory = new ArrayList<Entity>();

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        type = "player";
        worldX = gp.tileSize;
        worldY = gp.tileSize;
        speed = 4;
        direction = "down";

        solidArea = new Rectangle(4, 4, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        inventory.add(new OBJ_Boots(gp));

        getPlayerImage();
    }

    public void getPlayerImage() {
        up1 = setUpImage("/player/player_up_1.png");
        up2 = setUpImage("/player/player_up_2.png");
        down1 = setUpImage("/player/player_down_1.png");
        down2 = setUpImage("/player/player_down_2.png");
        left1 = setUpImage("/player/player_left_1.png");
        left2 = setUpImage("/player/player_left_2.png");
        right1 = setUpImage("/player/player_right_1.png");
        right2 = setUpImage("/player/player_right_2.png");
    }

    public int pickUpObject(int i, int originaStopPosition) {

        int stopPosition = -1;

        if (i != -1) {
            String objectName = gp.obj[i].name;

            switch (direction) {
                case "up":
                    stopPosition = gp.obj[i].worldY;
                    break;

                case "down":
                    stopPosition = gp.obj[i].worldY;
                    break;

                case "left":
                    stopPosition = gp.obj[i].worldX;
                    break;

                case "right":
                    stopPosition = gp.obj[i].worldX;
                    break;
            }

            switch (objectName) {
                case "Sword":
                    gp.obj[i] = null;
                    break;

                default:
                    break;
            }
        }

        if (originaStopPosition != -1) {
            stopPosition = originaStopPosition;
        }

        return stopPosition;
    }

    public void update() {
        if ((keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) || keyH.move == true) {

            if (moving == false) {
                if (keyH.upPressed == true) {
                    direction = "up";
                }
                if (keyH.downPressed == true) {
                    direction = "down";
                }
                if (keyH.leftPressed == true) {
                    direction = "left";
                }
                if (keyH.rightPressed == true) {
                    direction = "right";
                }
            }

            moving = true;

            // Collision check
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            stopPosition = pickUpObject(objIndex, stopPosition);

            if (stopPosition != -1) {

                switch (direction) {
                    case "up":
                        if (worldY - speed < stopPosition) {
                            collisionOn = true;
                        }
                        break;

                    case "down":
                        if (worldY + speed > stopPosition) {
                            collisionOn = true;
                        }
                        break;

                    case "left":
                        if (worldX - speed < stopPosition) {
                            collisionOn = true;
                        }
                        break;

                    case "right":
                        if (worldX + speed > stopPosition) {
                            collisionOn = true;
                        }
                        break;
                }
            }

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;

                    case "down":
                        worldY += speed;
                        break;

                    case "left":
                        worldX -= speed;
                        break;

                    case "right":
                        worldX += speed;
                        break;
                }
            } else if (collisionOn == true) {
                stopPosition = -1;
                keyH.move = false;
                moving = false;
            }

            spriteCounter++;
            if (spriteCounter >= 12) {
                if (spriteNun == 1) {
                    spriteNun = 2;
                } else if (spriteNun == 2) {
                    spriteNun = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNun == 1) {
                    image = up1;
                }
                if (spriteNun == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNun == 1) {
                    image = down1;
                }
                if (spriteNun == 2) {
                    image = down2;
                }
                break;

            case "left":
                if (spriteNun == 1) {
                    image = left1;
                }
                if (spriteNun == 2) {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNun == 1) {
                    image = right1;
                }
                if (spriteNun == 2) {
                    image = right2;
                }
                break;
        }

        g2d.drawImage(image, worldX, worldY, null);
    }
}
