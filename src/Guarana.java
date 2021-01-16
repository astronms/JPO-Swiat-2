import java.awt.Color;

public class Guarana extends Plant {
    public Guarana(int posX, int posY, World world) {
        this.myColor = new Color(119, 255, 247);
        this.whoIAm = 'g';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(0);
    }
}