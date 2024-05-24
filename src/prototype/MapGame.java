package prototype;

import java.util.Scanner;

public class MapGame {
    private int playerX;
    private int playerY;
    private int step;
    private char[][] map;
    private Status[] status;
    private int currentStep;
    private boolean hasSword;
    private int monstersCount;

    public MapGame(int x, int y, int step) {
        playerX = x;
        playerY = y;
        this.step = step;
        this.hasSword = false;
        currentStep = 0;
        map = new char[10][10]; // Assuming a 10x10 map for example
        status = new Status[step + 1]; // Array to store statuses of each step
        initializeMap();
        recordStatus(currentStep); // Record initial status
        printMap();
    }

    private void initializeMap() {
        // Initialize the map with some default values
        char[][] initialMap = {
                { '-', '-', '-', '-', 'S', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' },
                { '-', '-', '-', '-', 'M', '-', '-', '#', '-', 'E' }
        };

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = initialMap[i][j];
                if (map[i][j] == 'M') {
                    monstersCount++;
                }
            }
        }
        map[playerX][playerY] = 'P'; // Set initial player position
    }

    private boolean isStop(int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return true;
        } else if (map[x][y] == '#' || map[x][y] == 'M') {
            return true;
        }
        return false;
    }

    private void foundSword() {
        hasSword = true;
        System.out.println("You found a sword!");
    }

    private void attackMonsters() {
        if (hasSword) {
            // Check the four adjacent cells
            if (playerX > 0 && map[playerX - 1][playerY] == 'M') {
                map[playerX - 1][playerY] = '-';
                monstersCount--;
                step--;
                System.out.println("You attacked a monster to the north and defeated it!");
            }
            if (playerX < map.length - 1 && map[playerX + 1][playerY] == 'M') {
                map[playerX + 1][playerY] = '-';
                monstersCount--;
                step--;
                System.out.println("You attacked a monster to the south and defeated it!");
            }
            if (playerY > 0 && map[playerX][playerY - 1] == 'M') {
                map[playerX][playerY - 1] = '-';
                monstersCount--;
                step--;
                System.out.println("You attacked a monster to the west and defeated it!");
            }
            if (playerY < map[0].length - 1 && map[playerX][playerY + 1] == 'M') {
                map[playerX][playerY + 1] = '-';
                monstersCount--;
                step--;
                System.out.println("You attacked a monster to the east and defeated it!");
            }
        }
    }

    public void movePlayer(char direction) {
        int tempX = playerX;
        int tempY = playerY;

        switch (direction) {
            case 'w':
                while (!isStop(tempX - 1, playerY)) {
                    tempX--;
                    if (map[tempX][playerY] == 'S') {
                        foundSword();
                        break;
                    }
                }
                break;
            case 'a':
                while (!isStop(playerX, tempY - 1)) {
                    tempY--;
                    if (map[playerX][tempY] == 'S') {
                        foundSword();
                        break;
                    }
                }
                break;
            case 's':
                while (!isStop(tempX + 1, playerY)) {
                    tempX++;
                    if (map[tempX][playerY] == 'S') {
                        foundSword();
                        break;
                    }
                }
                break;
            case 'd':
                while (!isStop(playerX, tempY + 1)) {
                    tempY++;
                    if (map[playerX][tempY] == 'S') {
                        foundSword();
                        break;
                    }
                }
                break;
            case 'r':
                undoMove();
                return;
            case '1':
                if (hasSword) {
                    attackMonsters();
                } else {
                    System.out.println("You don't have a sword.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid input! Please enter 'w', 'a', 's', 'd','1' or 'r'.");
                return;
        }

        if (map[tempX][tempY] == 'E') {
            if (monstersCount == 0) {
                System.out.println("You win!");
                System.exit(0);
            } else {
                System.out.println("The exit is locked! Defeat all monsters first.");
                return;
            }
        }

        map[playerX][playerY] = '-';
        playerX = tempX;
        playerY = tempY;
        map[playerX][playerY] = 'P';
        step--;
        if (step == 0) {
            System.out.println("Game over, you lose");
            System.exit(0);
        }

        currentStep++;
        recordStatus(currentStep);
        printMap();
    }

    private void undoMove() {
        if (currentStep == 0) {
            System.out.println("No more steps to undo.");
            return;
        }
        currentStep--;
        step++;
        Status prevStatus = status[currentStep];
        playerX = prevStatus.playerX;
        playerY = prevStatus.playerY;
        hasSword = prevStatus.getSword;
        monstersCount = prevStatus.monstersCount;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = prevStatus.map[i][j];
            }
        }
        printMap();
    }

    private void recordStatus(int step) {
        Status s = new Status();
        s.playerX = playerX;
        s.playerY = playerY;
        s.getSword = hasSword;
        s.monstersCount = monstersCount;
        s.map = new char[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                s.map[i][j] = map[i][j];
            }
        }
        status[step] = s;
    }

    private void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println(); // Print a newline at the end of each row
        }
        System.out.println("Steps left: " + step);
        System.out.println("Monsters remaining: " + monstersCount);
    }

    public static void main(String[] args) {
        MapGame game = new MapGame(0, 0, 6); // Starting position at (0, 0)
        Scanner scanner = new Scanner(System.in);
        while (true) {
            char input = scanner.next().charAt(0);
            game.movePlayer(input);
        }
    }
}
