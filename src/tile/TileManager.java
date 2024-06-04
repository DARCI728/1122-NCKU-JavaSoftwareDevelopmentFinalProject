package tile;

import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[][] tiles;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[gp.maxMap][250];

        setUpTile("/tiles/map_1/map_01_", 0);
        loadCollision("/maps/map_1.txt", 0);

        setUpTile("/tiles/map_2/map_2_", 1);
        loadCollision("/maps/map_2.txt", 1);

        setUpTile("/tiles/map_3/map_3_", 2);
        loadCollision("/maps/map_3.txt", 2);
    }

    public void setUpTile(String path, int mapNum) {
        for (int i = 0; i < gp.maxScreenCol * gp.maxScreenRow; i++) {
            getTileImage(path + (i + 1) + ".png", i, mapNum);
        }
    }

    public void getTileImage(String path, int index, int mapNum) {
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[mapNum][index] = new Tile();
            tiles[mapNum][index].image = ImageIO.read(getClass().getResourceAsStream(path));
            tiles[mapNum][index].image = uTool.scaleImage(tiles[mapNum][index].image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCollision(String path, int mapNum) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    if (num == 1) {
                        tiles[mapNum][row * gp.maxScreenCol + col].collision = true;
                    }
                    col++;
                }

                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, int mapNum) {
        int col = 0;
        int x = 0;
        int y = 0;

        for (int i = 0; i < gp.maxScreenCol * gp.maxScreenRow; i++) {
            g2d.drawImage(tiles[mapNum][i].image, x, y, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                y += gp.tileSize;
            }
        }
    }
}
