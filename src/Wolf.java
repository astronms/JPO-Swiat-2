import java.awt.Color;

public class Wolf extends Animal {
    public Wolf(int posX, int posY, World world) {
        this.myColor = new Color(95, 95, 95);
        this.whoIAm = 'W';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(9);
        this.setInitiative(5);
    }
}
