package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.GamePanel;
import main.KeyHandler;
import monster.*;
import object.*;

public class Player extends Entity {

    KeyHandler keyH;

    public int stopX = -1, stopY = -1;
    public int teleportCnt = 0;
    public boolean hasSword = false;
    public boolean hasArrow = false;
    public boolean hasSat = false;
    public boolean hasteleported = false;
    public boolean killedMob = false;
    public boolean teleportInProgress = false;

    public List<PlayerState> stateHistory = new LinkedList<>();

    public ArrayList<Entity> inventory = new ArrayList<Entity>();

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        maxLife = 1;

        speed = 4;
        teleportInProgress = false;

        solidArea = new Rectangle(2, 2, 44, 44);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        atkArea = new Rectangle(12, 12, 24, 24);

        getPlayerImage();
        getPlayerAttackImage();
        setDefaultValue();
        saveState();
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
        stopX = -1;
        stopY = -1;

        moving = false;
        attacking = false;
        shooting = false;

        collisionOn = false;

        life = maxLife;
        direction = "down";
        hasArrow = false;
        hasSword = false;
        hasSat = false;
        hasteleported = false;
        inventory.clear();
        inventory.add(new OBJ_Gloves(gp));
        projectile = new OBJ_Arrow(gp, direction);

        switch (gp.currentMap) {
            case 0:
                worldX = gp.tileSize * 9;
                worldY = gp.tileSize * 9;
                break;

            case 1:
                worldX = gp.tileSize * 11;
                worldY = gp.tileSize * 10;
                break;

            case 2:
                worldX = gp.tileSize * 5;
                worldY = gp.tileSize * 8;
                break;

            case 3:
                worldX = gp.tileSize * 6;
                worldY = gp.tileSize * 5;
                break;

            case 4:
                worldX = gp.tileSize * 3;
                worldY = gp.tileSize * 4;
                break;

            default:
                break;
        }

        saveState();
    }

    public void saveState() {
        List<Entity> inventoryCopy = new ArrayList<>(inventory);
        List<MonsterState> monstersCopy = new ArrayList<>();
        List<ObjectState> objectsCopy = new ArrayList<>();
        List<ProjectileState> projectilesCopy = new ArrayList<>();

        if (gp.mob != null) {
            for (Entity mob : gp.mob) {
                if (mob != null) {
                    String type = "";
                    if (mob instanceof MOB_Bat) {
                        type = "MOB_Bat";
                    } else if (mob instanceof MOB_Slime) {
                        type = "MOB_Slime";
                    } else if (mob instanceof MOB_Bull) {
                        type = "MOB_Bull";
                    } else if (mob instanceof MOB_Devil) {
                        type = "MOB_Devil";
                    } else if (mob instanceof MOB_Gargoyle) {
                        type = "MOB_Gargoyle";
                    } else if (mob instanceof MOB_Ghost) {
                        type = "MOB_Ghost";
                    } else if (mob instanceof MOB_Skeleton) {
                        type = "MOB_Skeleton";
                    }
                    monstersCopy.add(new MonsterState(mob.worldX, mob.worldY, mob.life, mob.alive, mob.invincible, mob.dying, type));
                }
            }
        }

        if (gp.obj != null) {
            for (Entity obj : gp.obj) {
                String type = "";
                if (obj instanceof OBJ_Bow) {
                    type = "OBJ_Bow";
                } else if (obj instanceof OBJ_Sword) {
                    type = "OBJ_Sword";
                } else if (obj instanceof OBJ_Arrow) {
                    type = "OBJ_Arrow";
                } else if (obj instanceof OBJ_Chair) {
                    type = "OBJ_Chair";
                } else if (obj instanceof OBJ_Portal) {
                    type = "OBJ_Portal";
                }
                objectsCopy.add(new ObjectState(obj.worldX, obj.worldY, type, obj.direction));
            }
        }

        if (gp.projectile != null) {
            for (Entity arrow : gp.projectile) {
                if (arrow instanceof Projectile) {
                    Projectile p = (Projectile) arrow;
                    projectilesCopy.add(new ProjectileState(p.alive));
                }
            }
        }

        PlayerState newState = new PlayerState(worldX, worldY, direction, inventoryCopy, monstersCopy, objectsCopy,
            projectilesCopy, attacking, shooting, gp.steps, gp.ui.slotCol, hasteleported, hasArrow);
        stateHistory.add(newState);
    }

    public void undoMove() {
        if (stateHistory.size() > 1) {
            stateHistory.remove(stateHistory.size() - 1); 
            PlayerState lastState = stateHistory.get(stateHistory.size() - 1);
            worldX = lastState.worldX;
            worldY = lastState.worldY;
            direction = lastState.direction;
            gp.steps = lastState.steps;
            attacking = lastState.attacking;
            shooting = lastState.shooting;
            hasteleported = lastState.hasteleported;
            hasArrow = lastState.hasArrow;
            inventory = new ArrayList<>(lastState.inventory);
            gp.ui.slotCol = lastState.slotCol;

            if (gp.mob != null && gp.steps != 0) {
                int i = 0;
                while (i < gp.mob.length) {
                    if(shooting == false){

                    }
                    if (i < lastState.monsters.size()) {
                        MonsterState monsterState = lastState.monsters.get(i);
                        if (gp.mob[i] == null) {
                            switch (monsterState.type) {
                                case "MOB_Bat":
                                    gp.mob[i] = new MOB_Bat(gp);
                                    break;
                                case "MOB_Slime":
                                    gp.mob[i] = new MOB_Slime(gp);
                                    break;
                                case "MOB_Bull":
                                    gp.mob[i] = new MOB_Bull(gp);
                                    break;
                                case "MOB_Devil":
                                    gp.mob[i] = new MOB_Devil(gp);
                                    break;
                                case "MOB_Gargoyle":
                                    gp.mob[i] = new MOB_Gargoyle(gp);
                                    break;
                                case "MOB_Ghost":
                                    gp.mob[i] = new MOB_Ghost(gp);
                                    break;
                                case "MOB_Skeleton":
                                    gp.mob[i] = new MOB_Skeleton(gp);
                                    break;
                            }
                        }
            
                        gp.mob[i].worldX = monsterState.worldX;
                        gp.mob[i].worldY = monsterState.worldY;
                        gp.mob[i].life = monsterState.life;
                        gp.mob[i].alive = monsterState.alive;
                        gp.mob[i].invincible = monsterState.invincible;
                        gp.mob[i].dying = monsterState.dying;
                    } else {
                        gp.mob[i] = null;
                    }
                    i++;
                }
            }

            if (gp.steps < 1) {
                gp.retry();
            }

            else{
                gp.projectile.clear();
                for (ProjectileState projectileState : lastState.projectiles) {
                    Projectile arrow = new Projectile(gp);
                    arrow.alive = projectileState.alive;
                    gp.projectile.add(arrow);
                }

                gp.obj.clear();
                for (ObjectState objState : lastState.objects) {
                    Entity obj = null;
                    switch (objState.type) {
                        case "OBJ_Bow":
                            obj = new OBJ_Bow(gp);
                            break;
                        case "OBJ_Sword":
                            obj = new OBJ_Sword(gp);
                            break;
                        case "OBJ_Arrow":
                            obj = new OBJ_Arrow(gp, direction); 
                            break;
                        case "OBJ_Chair":
                            obj = new OBJ_Chair(gp);
                            break;
                        case "OBJ_Portal":
                            obj = new OBJ_Portal(gp);
                            break;
                    }
                    if (obj != null) {
                        obj.worldX = objState.worldX;
                        obj.worldY = objState.worldY;
                        gp.obj.add(obj);
                    }
                }
            }
        }
    }
    
    public void update() {
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.stopMusic();
            gp.platSE(2);
        }

        if (moving) {
            attacking = false;
            shooting = false;
        }

        if (attacking && hasSword) {
            attacking();
        }

        if (shooting && hasArrow) {
            shooting();
        }

        if (hasteleported) {
            teleportCnt++;
        }

        if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) || moving) {

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

            // CHECK OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);

            if (objIndex != -1 && hasSat == false && hasteleported == false) {
                pickUpObject(objIndex);
                if (gp.obj.get(objIndex).name != "Portal") {
                    stopX = gp.obj.get(objIndex).worldX;
                    stopY = gp.obj.get(objIndex).worldY;
                }

                if (gp.obj.get(objIndex).name != "Chair" && gp.obj.get(objIndex).name != "Portal") {
                    gp.obj.remove(objIndex);
                }
            } else if (objIndex != -1
                    && gp.obj.get(objIndex).name != "Chair"
                    && gp.obj.get(objIndex).name != "Portal") {
                pickUpObject(objIndex);
                gp.obj.remove(objIndex);
                hasSat = false;
            } else if (objIndex == -1) {
                hasSat = false;
                hasteleported = false;
                teleportCnt = 0;
            }
            // CHECK MOB COLLISION
            gp.cChecker.checkEntity(this, gp.mob);

            // CHECK EVENT
            gp.eventH.checkEvent(this, true);

            if (stopX != -1 || stopY != -1) {
                switch (direction) {
                    case "up":
                        if (worldY - speed < stopY) {
                            collisionOn = true;
                            worldX = stopX;
                            worldY = stopY;
                        }
                        break;

                    case "down":
                        if (worldY + speed > stopY) {
                            collisionOn = true;
                            worldX = stopX;
                            worldY = stopY;
                        }
                        break;

                    case "left":
                        if (worldX - speed < stopX) {
                            collisionOn = true;
                            worldX = stopX;
                            worldY = stopY;
                        }
                        break;

                    case "right":
                        if (worldX + speed > stopX) {
                            collisionOn = true;
                            worldX = stopX;
                            worldY = stopY;
                        }
                        break;
                }
            }

            if (collisionOn == false && ((hasteleported && teleportCnt >= 10) || !hasteleported)) {
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
            } else {
                moving = false;

                stopX = -1;
                stopY = -1;
                if (!hasteleported) {
                    gp.steps++;
                    saveState();
                }
            }

            spriteCounter++;
            if (spriteCounter >= 12) {
                if (spriteNun == 1) {
                    gp.platSE(7);
                    spriteNun = 2;
                } else if (spriteNun == 2) {
                    gp.platSE(8);
                    spriteNun = 1;
                }
                spriteCounter = 0;
            }
        }
        if (teleportInProgress && teleportCnt >= 10) {
            teleportInProgress = false;
            saveState();
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNun = 1;
        }

        if (spriteCounter == 5) {
            gp.platSE(10);
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

            if (killedMob) {
                gp.steps++;
                killedMob = false;
                saveState();
            }

            attacking = false;
        }
    }

    public void shooting() {
        gp.platSE(11);
        gp.steps++;
        projectile.set(worldX, worldY, direction, true, this);
        gp.projectile.add(projectile);
        hasArrow = false;
        shooting = false;
        saveState();
    }

    public void damageMonster(int i) {
        if (i != -1) {
            if (gp.mob[i].invincible == false) {
                gp.mob[i].invincible = true;
                gp.mob[i].life -= 1;

                gp.platSE(1);

                killedMob = true;

                if (gp.mob[i].life <= 0) {
                    gp.mob[i].dying = true;
                }
            }
        }
    }

    public void pickUpObject(int i) {
        String objectName = gp.obj.get(i).name;

        switch (objectName) {
            case "Sword":
                hasSword = true;
                gp.platSE(9);
                if (inventory.size() == 1) {
                    inventory.add(new OBJ_Sword(gp));
                } else {
                    inventory.set(1, new OBJ_Sword(gp));
                }
                break;

            case "Bow":
                hasArrow = true;
                gp.platSE(9);
                if (inventory.size() == 1) {
                    inventory.add(new OBJ_Null(gp));
                    inventory.add(new OBJ_Bow(gp));
                } else {
                    inventory.add(new OBJ_Bow(gp));
                }
                break;

            case "Arrow":
                gp.platSE(9);
                hasArrow = true;
                break;

            case "Chair":
                hasSat = true;
                break;

            case "Portal":
                hasteleported = true;
                moving = false;
                teleportInProgress = true;
                gp.eventH.teleport(gp.playState, gp.obj.get(i).worldX / gp.tileSize,
                        gp.obj.get(i).worldY / gp.tileSize);
                break;

            default:
                break;
        }
    }

    public void pickUpArrow(Entity arrow) {
        this.solidArea.x = this.worldX + this.solidArea.x;
        this.solidArea.y = this.worldY + this.solidArea.y;
        arrow.solidArea.x = arrow.worldX + arrow.solidArea.x;
        arrow.solidArea.y = arrow.worldY + arrow.solidArea.y;

        if (this.solidArea.intersects(arrow.solidArea)) {
            gp.obj.remove(arrow);
            gp.steps--;
            hasArrow = true;
            saveState();
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
