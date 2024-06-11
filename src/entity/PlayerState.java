package entity;

import java.util.List;

public class PlayerState {
    public int worldX, worldY;
    public String direction;
    public List<Entity> inventory;
    public List<MonsterState> monsters;
    public List<ObjectState> objects;
    public List<ProjectileState> projectiles;
    public boolean attacking, shooting, hasteleported, hasArrow;
    public int steps;
    public int slotCol;

    public PlayerState(int worldX, int worldY, String direction, List<Entity> inventory, List<MonsterState> monsters,
                       List<ObjectState> objects, List<ProjectileState> projectiles, boolean attacking, boolean shooting,
                       int steps, int slotCol, boolean hasteleported, boolean hasArrow) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.inventory = inventory;
        this.monsters = monsters;
        this.objects = objects;
        this.projectiles = projectiles;
        this.attacking = attacking;
        this.shooting = shooting;
        this.steps = steps;
        this.slotCol = slotCol;
        this.hasteleported = hasteleported;
        this.hasArrow = hasArrow;
    }
}
