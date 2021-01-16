import java.awt.Color;

public class Sheep extends Animal {
    public Sheep(int posX, int posY, World world) {
        this.myColor = new Color(204, 153, 255);
        this.whoIAm = 'O';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(4);
        this.setInitiative(4);
    }
}
