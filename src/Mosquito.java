import java.awt.Color;
import java.util.Random;

public class Mosquito extends Animal {
    public Mosquito(int posX, int posY, World world) {
        this.myColor = new Color(255, 0, 0);
        this.whoIAm = 'K';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(1);
        this.setInitiative(1);
        this.moveLength = 1;
    }

    public boolean defenced(Organism organism) {
        Random random = new Random();
        int result = random.nextInt(101);
        return result <= 50;
    }
}
