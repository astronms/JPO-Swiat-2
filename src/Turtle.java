import java.awt.Color;
import java.util.Random;

public class Turtle extends Animal {
    public Turtle(int posX, int posY, World world) {
        this.myColor = new Color(76, 153, 0);
        this.whoIAm = 'Z';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(2);
        this.setInitiative(1);
    }

    public void action() {
        Random random = new Random();
        int var2 = random.nextInt(101);
        if (var2 < 25) {
            super.action();
        }

    }

    public boolean defenced(Organism organism) {
        return organism.getPower() < 5;
    }
}
