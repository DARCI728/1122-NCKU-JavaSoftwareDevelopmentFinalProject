package prototype;

public class Status {
    char[][] map;
    int playerX;
    int playerY;
    boolean getSword;
    boolean getBow;
    int monstersCount;

    // Default constructor
    public Status() {
        this.getSword = false;
        this.getBow = false;
        this.monstersCount = 0;
    }
}
