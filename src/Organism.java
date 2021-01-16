import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;

public abstract class Organism {
    private int power, initiative, posX, posY;
    private boolean justBorn = false;
    protected World world;
    protected boolean dead = false;
    protected Color myColor = new Color(224, 224, 224);
    protected Color myTextColor = Color.BLACK;
    public char whoIAm;

    abstract public void action();
    abstract public collisionResult collision(int pX, int pY);
    abstract public boolean defenced(Organism enemy);


    public void drawing() {
        this.world.getboardButtons()[this.posY][this.posX].setBackground(myColor);
        this.world.getboardButtons()[this.posY][this.posX].setForeground(myTextColor);
        this.world.getboardButtons()[this.posY][this.posX].setText(""+this.whoIAm);
        for(ActionListener al : this.world.getboardButtons()[this.posY][this.posX].getActionListeners()) {
            this.world.getboardButtons()[this.posY][this.posX].removeActionListener(al);
        }

        final JPopupMenu innerMenu = this.world.getPopUp();
        this.world.getboardButtons()[this.posY][this.posX].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                innerMenu.setVisible(false);
            }
        });
    }

    public String organismInfo() {
        return this.whoIAm + " " + this.getPosX() + " " + this.getPosY() + " " + this.getPower();
    }

    public int getPower() {
        return this.power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public int getInitiative() {
        return this.initiative;
    }
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public int getPosX() {
        return this.posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return this.posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public boolean getJustBorn() {
        return this.justBorn;
    }
    public void setJustBorn(boolean val) {
        this.justBorn = val;
    }
    public boolean isDead() {
        if (this.dead == true)
            return true;
        return false;
    }
    public void setIsDead(boolean val) {
        this.dead = val;
    }
}