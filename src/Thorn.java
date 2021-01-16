import java.awt.Color;

public class Thorn extends Plant {
    public Thorn(int posX, int posY, World world) {
        this.myColor = new Color(255, 255, 51);
        this.whoIAm = 'c';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(2);
    }

    public void action() {
        boolean var1 = true;
        super.action();
        super.action();
        super.action();
        super.action();
        super.action();
    }
}
