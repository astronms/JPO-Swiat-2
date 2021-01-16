public class OrganismFactory {
    public Organism createNewOrganism(int code, int posX, int posY, World world) {
        switch (code) {
            case 0:
                return new Bear(posX, posY, world);
            case 1:
                return new Wolf(posX, posY, world);
            case 2:
                return new Mosquito(posX, posY, world);
            case 3:
                return new Sheep(posX, posY, world);
            case 4:
                return new Turtle(posX, posY, world);
            case 5:
                return new Thorn(posX, posY, world);
            case 6:
                return new Guarana(posX, posY, world);
            case 7:
                return new Grass(posX, posY, world);
        }
        return null;
    }
}