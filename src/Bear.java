import java.awt.Color;
import java.util.Random;

public class Bear extends Animal {
    private boolean isSleep = false;
    private int daysOfSleep = 0;

    public Bear(int posX, int posY, World world) {
        this.myColor = new Color(94, 67, 20);
        this.whoIAm = 'B';
        this.setPosX(posX);
        this.setPosY(posY);
        this.world = world;
        this.setPower(11);
        this.setInitiative(4);
        int DaysOfSleep = 0;
    }

    @Override
    public void action() {
        if(!isSleep){
            Random random = new Random();
            int result = random.nextInt(101);
            if (result < 30) {
                isSleep=true;
                daysOfSleep++;
            }else {
                isSleep = false;
                daysOfSleep = 0;
                super.action();
            }
        }else {
            daysOfSleep++;
            if (daysOfSleep > 3) {
                isSleep = false;
                daysOfSleep = 0;
                super.action();
            }
        }
    }
}