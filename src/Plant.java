//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Random;

public class Plant extends Organism {
    public Plant() {
        this.setInitiative(0);
    }

    public void action() {
        byte var1 = 5;
        Random random = new Random();
        int var2 = random.nextInt(101);
        if (var2 <= var1) {
            int var4 = random.nextInt(5);
            switch(var4) {
                case 0:
                    if (this.isItFreeSquare(this.getPosX(), this.getPosY() - 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() + 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                    } else if (this.isItFreeSquare(this.getPosX() - 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() - 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX() + 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() + 1, this.getPosY());
                    }
                    break;
                case 1:
                    if (this.isItFreeSquare(this.getPosX(), this.getPosY() + 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() - 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                    } else if (this.isItFreeSquare(this.getPosX() - 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() - 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX() + 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() + 1, this.getPosY());
                    }
                    break;
                case 2:
                    if (this.isItFreeSquare(this.getPosX() - 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() - 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX() + 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() + 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() - 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() + 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                    }
                    break;
                case 3:
                    if (this.isItFreeSquare(this.getPosX() + 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() + 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX() - 1, this.getPosY())) {
                        this.addNewPlant(this.whoIAm, this.getPosX() - 1, this.getPosY());
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() + 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                    } else if (this.isItFreeSquare(this.getPosX(), this.getPosY() - 1)) {
                        this.addNewPlant(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                    }
            }
        }

    }

    public collisionResult collision(int var1, int var2) {
        return null;
    }

    public boolean defenced(Organism var1) {
        return false;
    }

    boolean isItFreeSquare(int var1, int var2) {
        int var3 = this.world.getSizeX() - 1;
        int var4 = this.world.getSizeY() - 1;
        if (var1 >= 0 && var1 <= var3 && var2 >= 0 && var2 <= var4) {
            return this.world.getBoard()[var2][var1] == null;
        } else {
            return false;
        }
    }

    void addNewPlant(char var1, int var2, int var3) {
        Plant var4;
        switch(var1) {
            case 'c':
                var4 = new Thorn(var2, var3, this.world);
                break;
            case 'g':
                var4 = new Guarana(var2, var3, this.world);
                break;
            default:
                var4 = new Grass(var2, var3, this.world);
        }

        ((Plant)var4).setJustBorn(true);
        this.world.getBoard()[((Plant)var4).getPosY()][((Plant)var4).getPosX()] = (Organism)var4;
        this.world.listOfOrganisms.add(var4);
    }
}
