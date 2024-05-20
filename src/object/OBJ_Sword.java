package object;

import javax.imageio.ImageIO;

public class OBJ_Sword extends SuperObject {

    public OBJ_Sword() {
        name = "Sword";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
