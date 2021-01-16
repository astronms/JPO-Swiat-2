import java.util.Random;

public class Animal extends Organism {
    protected int moveLength;

    public Animal() {
        this.moveLength = 1;
    }

    @Override
    public void action() {
        int prevPosX, prevPosY;

        prevPosX = this.getPosX();
        prevPosY = this.getPosY();

        newCoordinates();
        collision(prevPosX, prevPosY);
    }

    @Override
    public collisionResult collision(int pX, int pY) {
        int currentX, currentY;
        String tmp;
        currentX = this.getPosX();
        currentY = this.getPosY();

        if (currentX == pX && currentY == pY) {
            return collisionResult.NO_CHANGES;
        } else if (world.getBoard()[this.getPosY()][this.getPosX()] == null) {
            world.getBoard()[pY][pX] = null;
            world.getBoard()[this.getPosY()][this.getPosX()] = this;
            return collisionResult.MOVE_TO_NULL;
        }
        if (world.getBoard()[this.getPosY()][this.getPosX()].whoIAm == this.whoIAm) {
            this.setPosX(pX);
            this.setPosY(pY);
            if (makeNewChild() == true) {
                tmp = this.whoIAm + " stworzyl nowego osobnika z " + this.whoIAm;
                this.world.commentList.add(tmp);
                return collisionResult.NEW_CHILD;
            } else
                return collisionResult.NO_NEW_CHILD;
        }
        if (world.getBoard()[this.getPosY()][this.getPosX()].defenced(this) == true) {
            if (world.getBoard()[this.getPosY()][this.getPosX()].whoIAm == 'Z') {
                this.setPosX(pX);
                this.setPosY(pY);
                tmp = "Z odparl atak " + this.whoIAm;
                this.world.commentList.add(tmp);
                return collisionResult.DEFENCED;
            } else if (world.getBoard()[this.getPosY()][this.getPosX()].whoIAm == 'K') {
                Organism killer, escaper;
                killer = this;
                escaper = world.getBoard()[this.getPosY()][this.getPosX()];
                world.getBoard()[pY][pX] = escaper;
                escaper.setPosX(pX);
                escaper.setPosY(pY);
                world.getBoard()[this.getPosY()][this.getPosX()] = killer;

                tmp = "K przezyl atak " + this.whoIAm;
                this.world.commentList.add(tmp);

                return collisionResult.DEFENCED;
            }
        } else {
            if (this.getPower() >= world.getBoard()[this.getPosY()][this.getPosX()].getPower()) {
                if (world.getBoard()[this.getPosY()][this.getPosX()].whoIAm == 'g') {
                    this.setPower(this.getPower() + 3);
                }
                tmp = this.whoIAm + " zjadl " + world.getBoard()[this.getPosY()][this.getPosX()].whoIAm;

                if (world.getBoard()[this.getPosY()][this.getPosX()].whoIAm == 'H')
                    this.world.setPlayerIsActive(false);

                this.world.commentList.add(tmp);

                world.getBoard()[this.getPosY()][this.getPosX()].setIsDead(true);
                world.getBoard()[this.getPosY()][this.getPosX()] = this;
                world.getBoard()[pY][pX] = null;
                return collisionResult.WIN;
            } else {
                char tmpType;
                tmp = this.whoIAm + " zostal zabity przez " + world.getBoard()[this.getPosY()][this.getPosX()].whoIAm;
                this.world.commentList.add(tmp);
                tmpType = world.getBoard()[this.getPosY()][this.getPosX()].whoIAm;

                this.setPosX(pX);
                this.setPosY(pY);
                this.world.getBoard()[pY][pX] = null;
                this.dead = true;
                return collisionResult.DEAD;
            }
        }
        return collisionResult.NO_CHANGES;
    }

    @Override
    public boolean defenced(Organism enemy) {
        return false;
    }

    public void newCoordinates() {
        int moveTo, newPosX, newPosY, worldSizeX, worldSizeY;
        Random rnd = new Random();

        worldSizeX = world.getSizeX() - 1;
        worldSizeY = world.getSizeY() - 1;
        newPosX = this.getPosX();
        newPosY = this.getPosY();

        if (newPosX == 0 && newPosY == 0) {
            moveTo = rnd.nextInt(2);
            switch (moveTo) {
                case 0: //down
                    newPosY = this.moveLength;
                    break;
                case 1: //right
                    newPosX = this.moveLength;
                    break;
            }
        }
        else if (newPosX == worldSizeX && newPosY == 0) {
            moveTo = rnd.nextInt(2);
            switch (moveTo) {
                case 0: //down
                    newPosY = this.moveLength;
                    break;
                case 1: //left
                    newPosX -= this.moveLength;
                    break;
            }
        }
        else if (newPosX == 0 && newPosY == worldSizeY) {
            moveTo = rnd.nextInt(2);
            switch (moveTo) {
                case 0: //up
                    newPosY -= this.moveLength;
                    break;
                case 1: //right
                    newPosX = this.moveLength;
                    break;
            }
        }
        else if (newPosX == worldSizeX && newPosY == worldSizeY) {
            moveTo = rnd.nextInt(2);
            switch (moveTo) {
                case 0: //up
                    newPosY -= this.moveLength;
                    break;
                case 1: //left
                    newPosX -= this.moveLength;
                    break;
            }
        }
        else if (newPosX == 0) {
            moveTo = rnd.nextInt(3);
            switch (moveTo) {
                case 0: //up
                    if (newPosY < this.moveLength)
                        newPosY -= 1;
                    else
                        newPosY -= this.moveLength;
                    break;
                case 1: //down
                    if (newPosY > worldSizeY - this.moveLength)
                        newPosY += 1;
                    else
                        newPosY += this.moveLength;
                    break;
                case 2: //right
                    newPosX = this.moveLength;
                    break;
            }
        }
        else if (newPosX == worldSizeX) {
            moveTo = rnd.nextInt(3);
            switch (moveTo) {
                case 0: //up
                    if (newPosY < this.moveLength)
                        newPosY -= 1;
                    else
                        newPosY -= this.moveLength;
                    break;
                case 1: //down
                    if (newPosY > worldSizeY - this.moveLength)
                        newPosY += 1;
                    else
                        newPosY += this.moveLength;
                    break;
                case 2: //left
                    newPosX -= this.moveLength;
                    break;
            }
        }
        else if (newPosY == 0) {
            moveTo = rnd.nextInt(3);
            switch (moveTo) {
                case 0: //down
                    newPosY = this.moveLength;
                    break;
                case 1: //left
                    if (newPosX < this.moveLength)
                        newPosX -= 1;
                    else
                        newPosX -= this.moveLength;
                    break;
                case 2: //right
                    if (newPosX > worldSizeX - this.moveLength)
                        newPosX += 1;
                    else
                        newPosX += this.moveLength;
                    break;
            }
        }
        else if (newPosY == worldSizeY) {
            moveTo = rnd.nextInt(3);
            switch (moveTo) {
                case 0: //up
                    if (newPosY < this.moveLength)
                        newPosY -= 1;
                    else
                        newPosY -= this.moveLength;
                    break;
                case 1: //left
                    if (newPosX < this.moveLength)
                        newPosX -= 1;
                    else
                        newPosX -= this.moveLength;
                    break;
                case 2: //right
                    if (newPosX > worldSizeX - this.moveLength)
                        newPosX += 1;
                    else
                        newPosX += this.moveLength;
                    break;
            }
        }
        else {
            moveTo = rnd.nextInt(4);
            switch (moveTo) {
                case 0: //up
                    if (newPosY < this.moveLength)
                        newPosY -= 1;
                    else
                        newPosY -= this.moveLength;
                    break;
                case 1: //down
                    if (newPosY > worldSizeY - this.moveLength)
                        newPosY += 1;
                    else
                        newPosY += this.moveLength;
                    break;
                case 2: //left
                    if (newPosX < this.moveLength)
                        newPosX -= 1;
                    else
                        newPosX -= this.moveLength;
                    break;
                case 3: //right
                    if (newPosX > worldSizeX - this.moveLength)
                        newPosX += 1;
                    else
                        newPosX += this.moveLength;
                    break;
            }
        }
        this.setPosX(newPosX);
        this.setPosY(newPosY);
    }

    public boolean makeNewChild() {
        Random rnd = new Random();
        int bornIn = rnd.nextInt(5);
        switch (bornIn) {
            case 0: //up
                if (isItFreeSquare(this.getPosX(), this.getPosY() - 1) == true) {
                    addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                    return true;
                } else {
                    if (isItFreeSquare(this.getPosX(), this.getPosY() + 1) == true) {
                        addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                        return true;
                    } else {
                        if (isItFreeSquare(this.getPosX() - 1, this.getPosY()) == true) {
                            addNewAnimal(this.whoIAm, this.getPosX() - 1, this.getPosY());
                            return true;
                        } else {
                            if (isItFreeSquare(this.getPosX() + 1, this.getPosY()) == true) {
                                addNewAnimal(this.whoIAm, this.getPosX() + 1, this.getPosY());
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: // down
                if (isItFreeSquare(this.getPosX(), this.getPosY() + 1) == true) {
                    addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                    return true;
                } else {
                    if (isItFreeSquare(this.getPosX(), this.getPosY() - 1) == true) {
                        addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                        return true;
                    } else {
                        if (isItFreeSquare(this.getPosX() - 1, this.getPosY()) == true) {
                            addNewAnimal(this.whoIAm, this.getPosX() - 1, this.getPosY());
                            return true;
                        } else {
                            if (isItFreeSquare(this.getPosX() + 1, this.getPosY()) == true) {
                                addNewAnimal(this.whoIAm, this.getPosX() + 1, this.getPosY());
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //left
                if (isItFreeSquare(this.getPosX() - 1, this.getPosY()) == true) {
                    addNewAnimal(this.whoIAm, this.getPosX() - 1, this.getPosY());
                    return true;
                } else {
                    if (isItFreeSquare(this.getPosX() + 1, this.getPosY()) == true) {
                        addNewAnimal(this.whoIAm, this.getPosX() + 1, this.getPosY());
                        return true;
                    } else {
                        if (isItFreeSquare(this.getPosX(), this.getPosY() - 1) == true) {
                            addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                            return true;
                        } else {
                            if (isItFreeSquare(this.getPosX(), this.getPosY() + 1) == true) {
                                addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //right
                if (isItFreeSquare(this.getPosX() + 1, this.getPosY()) == true) {
                    addNewAnimal(this.whoIAm, this.getPosX() + 1, this.getPosY());
                    return true;
                } else {
                    if (isItFreeSquare(this.getPosX() - 1, this.getPosY()) == true) {
                        addNewAnimal(this.whoIAm, this.getPosX() - 1, this.getPosY());
                        return true;
                    } else {
                        if (isItFreeSquare(this.getPosX(), this.getPosY() + 1) == true) {
                            addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() + 1);
                            return true;
                        } else {
                            if (isItFreeSquare(this.getPosX(), this.getPosY() - 1) == true) {
                                addNewAnimal(this.whoIAm, this.getPosX(), this.getPosY() - 1);
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;

    }

    public boolean isItFreeSquare(int x, int y) {
        int boardSizeX = world.getSizeX() - 1;
        int boardSizeY = world.getSizeY() - 1;
        if (x < 0 || x > boardSizeX || y < 0 || y > boardSizeY)
            return false;
        if (this.world.getBoard()[y][x] == null)
            return true;
        return false;
    }

    public void addNewAnimal(char type, int x, int y) {
        Animal newAnimal;
        switch (type) {
            case 'Y':
                newAnimal = new Bear(x, y, this.world);
                break;
            case 'K':
                newAnimal = new Mosquito(x, y, this.world);
                break;
            case 'O':
                newAnimal = new Sheep(x, y, this.world);
                break;
            case 'Z':
                newAnimal = new Turtle(x, y, this.world);
                break;
            default:
                newAnimal = new Wolf(x, y, this.world);
                break;
        }
        newAnimal.setJustBorn(true);
        this.world.getBoard()[newAnimal.getPosY()][newAnimal.getPosX()] = newAnimal;

        for (int i = 0; i < this.world.listOfOrganisms.size(); i++) {
            if (this.world.listOfOrganisms.get(i).getInitiative() < newAnimal.getInitiative()) {
                this.world.listOfOrganisms.add(i, newAnimal);
                break;
            } else if (i == this.world.listOfOrganisms.size() - 1) {
                this.world.listOfOrganisms.add(newAnimal);
                break;
            }
        }
    }

}