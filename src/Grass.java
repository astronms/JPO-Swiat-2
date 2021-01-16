import java.awt.Color;

public class Grass extends Plant {
    public Grass(int posX, int posY, World world) {
        this.myColor = new Color(91, 255, 91);
        this.whoIAm = 't';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(0);
    }
}