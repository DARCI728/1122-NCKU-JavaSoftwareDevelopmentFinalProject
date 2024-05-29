package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[250];

        setUpTile();
    }

    public void setUpTile() {
        for (int i = 0; i < gp.maxScreenCol * gp.maxScreenRow; i++) {
            getTileImage(i, "map_01_" + (i + 1));
        }
        
        loadCollision("/maps/map01.txt");
    }

    public void getTileImage(int index, String path) {
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_01/" + path + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCollision(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    if (num == 1) {
                        tiles[row * gp.maxScreenCol + col].collision = true;
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

    public void draw(Graphics2D g2d) {
        int col = 0;
        int x = 0;
        int y = 0;

        for (int i = 0; i < gp.maxScreenCol * gp.maxScreenRow; i++) {
            g2d.drawImage(tiles[i].image, x, y, null);
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
