package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.*;

public class Player extends Entity {

    KeyHandler keyH;

    int stopPosition = -1;
    public boolean hasArrow;

    public ArrayList<Entity> inventory = new ArrayList<Entity>();

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        maxLife = 1;

        solidArea = new Rectangle(2, 2, 44, 44);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        atkArea = new Rectangle(0, 0, 48, 48);

        getPlayerImage();
        getPlayerAttackImage();
        setDefaultValue();
    }

    public void getPlayerImage() {
        up1 = getImage("/player/player_up_1.png", gp.tileSize, gp.tileSize);
        up2 = getImage("/player/player_up_2.png", gp.tileSize, gp.tileSize);
        down1 = getImage("/player/player_down_1.png", gp.tileSize, gp.tileSize);
        down2 = getImage("/player/player_down_2.png", gp.tileSize, gp.tileSize);
        left1 = getImage("/player/player_left_1.png", gp.tileSize, gp.tileSize);
        left2 = getImage("/player/player_left_2.png", gp.tileSize, gp.tileSize);
        right1 = getImage("/player/player_right_1.png", gp.tileSize, gp.tileSize);
        right2 = getImage("/player/player_right_2.png", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        atkUp1 = getImage("/player/player_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
        atkUp2 = getImage("/player/player_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
        atkDown1 = getImage("/player/player_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
        atkDown2 = getImage("/player/player_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
        atkLeft1 = getImage("/player/player_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
        atkLeft2 = getImage("/player/player_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        atkRight1 = getImage("/player/player_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        attackRight2 = getImage("/player/player_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
    }

    public void setDefaultValue() {
        switch (gp.currentMap) {
            case 0:
                worldX = gp.tileSize * 9;
                worldY = gp.tileSize * 9;
                break;

            case 1:
                worldX = gp.tileSize * 11;
                worldY = gp.tileSize * 10;
                break;

            default:
                break;
        }

        life = maxLife;
        direction = "down";
        hasArrow = false;
        inventory.clear();
        inventory.add(new OBJ_Gloves(gp));
        projectile = new OBJ_Arrow(gp);
    }

    public void update() {
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
        }

        if (attacking) {
            attacking();
        }

        if (shooting && hasArrow) {
            shooting();
        }

        if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
                || keyH.move == true && attacking == false) {

            if (moving == false) {
                if (keyH.upPressed) {
                    direction = "up";
                }
                if (keyH.downPressed) {
                    direction = "down";
                }
                if (keyH.leftPressed) {
                    direction = "left";
                }
                if (keyH.rightPressed) {
                    direction = "right";
                }
            }

            moving = true;

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK MOB COLLISION
            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);

            // CHECK OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            stopPosition = pickUpObject(objIndex, stopPosition);

            // CHECK EVENT
            gp.eventH.checkEvent();

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

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNun = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNun = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= atkArea.height;
                    break;

                case "down":
                    worldY += atkArea.height;
                    break;

                case "left":
                    worldX -= atkArea.width;
                    break;

                case "right":
                    worldX += atkArea.width;
                    break;
            }

            solidArea.width = atkArea.width;
            solidArea.height = atkArea.height;

            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);
            damageMonster(mobIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNun = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void shooting() {
        projectile.set(worldX, worldY, direction, true, this);
        gp.projectile.add(projectile);
        hasArrow = false;
        shooting = false;
    }

    public void damageMonster(int i) {
        if (i != -1) {
            if (gp.mob[i].invincible == false) {
                gp.mob[i].invincible = true;
                gp.mob[i].life -= 1;

                if (gp.mob[i].life <= 0) {
                    gp.mob[i].dying = true;
                }
            }
        }
    }

    public int pickUpObject(int i, int originaStopPosition) {
        int stopPosition = -1;

        if (i != -1) {
            String objectName = gp.obj.get(i).name;

            switch (direction) {
                case "up":
                    stopPosition = gp.obj.get(i).worldY;
                    break;

                case "down":
                    stopPosition = gp.obj.get(i).worldY;
                    break;

                case "left":
                    stopPosition = gp.obj.get(i).worldX;
                    break;

                case "right":
                    stopPosition = gp.obj.get(i).worldX;
                    break;
            }

            switch (objectName) {
                case "Sword":
                    if (inventory.size() == 1) {
                        inventory.add(new OBJ_Sword(gp));
                    } else {
                        inventory.set(1, new OBJ_Sword(gp));
                    }
                    break;

                case "Bow":
                    hasArrow = true;
                    if (inventory.size() == 1) {
                        inventory.add(new OBJ_Null(gp));
                        inventory.add(new OBJ_Bow(gp));
                    } else {
                        inventory.add(new OBJ_Bow(gp));
                    }
                    break;

                case "Arrow":
                    hasArrow = true;
                    break;

                default:
                    break;
            }

            gp.obj.remove(i);
        }

        if (stopPosition == -1) {
            stopPosition = originaStopPosition;
        }

        return stopPosition;
    }

    public void pickUpArrow(Entity arrow) {
        this.solidArea.x = this.worldX + this.solidArea.x;
        this.solidArea.y = this.worldY + this.solidArea.y;
        arrow.solidArea.x = arrow.worldX + arrow.solidArea.x;
        arrow.solidArea.y = arrow.worldY + arrow.solidArea.y;

        if (this.solidArea.intersects(arrow.solidArea)) {
            gp.obj.remove(arrow);
            hasArrow = true;
        }

        this.solidArea.x = this.solidAreaDefaultX;
        this.solidArea.y = this.solidAreaDefaultY;
        arrow.solidArea.x = arrow.solidAreaDefaultX;
        arrow.solidArea.y = arrow.solidAreaDefaultY;
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        BufferedImage image = null;
        int tempScreenX = worldX;
        int tempScreenY = worldY;

        switch (direction) {
            case "up":
                if (attacking == true) {
                    tempScreenY = worldY - gp.tileSize;
                    if (spriteNun == 1) {
                        image = atkUp1;
                    }
                    if (spriteNun == 2) {
                        image = atkUp2;
                    }
                }

                if (attacking == false) {
                    if (spriteNun == 1) {
                        image = up1;
                    }
                    if (spriteNun == 2) {
                        image = up2;
                    }
                }

                break;

            case "down":
                if (attacking == true) {
                    if (spriteNun == 1) {
                        image = atkDown1;
                    }
                    if (spriteNun == 2) {
                        image = atkDown2;
                    }
                }

                if (attacking == false) {
                    if (spriteNun == 1) {
                        image = down1;
                    }
                    if (spriteNun == 2) {
                        image = down2;
                    }
                }

                break;

            case "left":
                if (attacking == true) {
                    tempScreenX = worldX - gp.tileSize;
                    if (spriteNun == 1) {
                        image = atkLeft1;
                    }
                    if (spriteNun == 2) {
                        image = atkLeft2;
                    }
                }

                if (attacking == false) {
                    if (spriteNun == 1) {
                        image = left1;
                    }
                    if (spriteNun == 2) {
                        image = left2;
                    }
                }

                break;

            case "right":
                if (attacking == true) {
                    if (spriteNun == 1) {
                        image = atkRight1;
                    }
                    if (spriteNun == 2) {
                        image = attackRight2;
                    }
                }

                if (attacking == false) {
                    if (spriteNun == 1) {
                        image = right1;
                    }
                    if (spriteNun == 2) {
                        image = right2;
                    }
                }

                break;
        }

        g2d.drawImage(image, tempScreenX, tempScreenY, null);
    }
}
