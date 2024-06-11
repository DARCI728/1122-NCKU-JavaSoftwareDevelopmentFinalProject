package entity;

public class MonsterState {
    public int worldX, worldY;
    public int life;
    public boolean alive;
    public boolean invincible;
    public boolean dying; 
    public String type;

    public MonsterState(int worldX, int worldY, int life, boolean alive, boolean invincible, boolean dying, String type) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.life = life;
        this.alive = alive;
        this.invincible = invincible;
        this.dying = dying;
        this.type = type;
    }
}


