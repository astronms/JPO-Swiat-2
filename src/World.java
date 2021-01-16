import java.lang.Exception;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class World {
    private int sizeX, sizeY, input;
    private Organism board[][];
    public ArrayList<Organism> listOfOrganisms = new ArrayList<Organism>();
    public Queue<String> commentList  = new LinkedList<String>();
    public final int squareSize = 20, LCDWidth = 1366, LCDHeight = 768;
    private JFrame frame;
    private JButton boardButtons[][];
    private JPopupMenu popUpMenu = new JPopupMenu();
    private JLabel commentsLabel = new JLabel("OSTATNIE WYDARZENIA");
    private JLabel humanLabel = new JLabel();
    private JLabel humanMovesTo = new JLabel();
    private JTextArea commentsTextArea = new JTextArea();
    private JScrollPane scrollComments = new JScrollPane(commentsTextArea);
    private JButton nextRoundBtn = new JButton("Nastepny ruch");
    private boolean playerIsActive = false;
    private JMenuItem addGrass, addGuarana, addThorn, addMosquito, addBear, addSheep, addTurtle, addWolf;
    private int screenPositionX, screenPositionY, frameWidth, frameHeight;


    public World() {
        try {
            int sizeBoardX, sizeBoardY, organismCount;
            int tmpX, tmpY, powerTmp, abilityMovesTmp;
            char tmpType;
            Organism tmpOrg;

            File saveFile = new File("save.txt");
            Scanner scanner = new Scanner(saveFile);

            sizeBoardX = scanner.nextInt();
            sizeBoardY = scanner.nextInt();
            this.sizeX = sizeBoardX;
            this.sizeY = sizeBoardY;

            board = new Organism[sizeBoardY][sizeBoardX];
            for (int i = 0; i < sizeBoardY; i++)
                for (int j = 0; j < sizeBoardX; j++)
                    board[i][j] = null;

            organismCount = scanner.nextInt();

            for (int i = 0; i < organismCount; i++) {
                tmpType = scanner.next().charAt(0);
                tmpX = scanner.nextInt();
                tmpY = scanner.nextInt();
                powerTmp = scanner.nextInt();
                switch (tmpType) {
                    case 'B':
                        tmpOrg = new Bear(tmpX, tmpY, this);
                        tmpOrg.setPower(powerTmp);
                        listOfOrganisms.add(tmpOrg);
                        break;
                    case 'W':
                        tmpOrg = new Wolf(tmpX, tmpY, this);
                        tmpOrg.setPower(powerTmp);
                        listOfOrganisms.add(tmpOrg);
                        break;
                    case 'K':
                        tmpOrg = new Mosquito(tmpX, tmpY, this);
                        tmpOrg.setPower(powerTmp);
                        listOfOrganisms.add(tmpOrg);
                        break;
                    case 'O':
                        tmpOrg = new Sheep(tmpX, tmpY, this);
                        tmpOrg.setPower(powerTmp);
                        listOfOrganisms.add(tmpOrg);
                        break;
                    case 'Z':
                        tmpOrg = new Turtle(tmpX, tmpY, this);
                        tmpOrg.setPower(powerTmp);
                        listOfOrganisms.add(tmpOrg);
                        break;
                    case 'c':
                        listOfOrganisms.add(new Thorn(tmpX, tmpY, this));
                        break;
                    case 'g':
                        listOfOrganisms.add(new Guarana(tmpX, tmpY, this));
                        break;
                    case 't':
                        listOfOrganisms.add(new Grass(tmpX, tmpY, this));
                        break;
                }
            }
            for (int i = 0; i < listOfOrganisms.size(); i++)
                board[listOfOrganisms.get(i).getPosY()][listOfOrganisms.get(i).getPosX()] = listOfOrganisms.get(i);
            scanner.close();

            frameMaker();
            drawTheWorld();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public World(int sizeX, int sizeY) {
        board = new Organism[sizeY][sizeX];
        OrganismFactory factory = new OrganismFactory();
        Random rnd = new Random();
        int uniqOrganismCount, tmpX, tmpY;
        final int HUMAN_CODE = 2;

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        for (int i = 0; i < sizeY; i++)
            for (int j = 0; j < sizeX; j++)
                board[i][j] = null;

        uniqOrganismCount = ((sizeX * sizeY) / 20) / 7;
        tmpY = rnd.nextInt(sizeY);
        tmpX = rnd.nextInt(sizeX);

        if ((sizeX * sizeY) < 200 && (sizeX * sizeY) >= 7)
            uniqOrganismCount = 1;

        for (int i = 0; i < uniqOrganismCount * 7; i++) {
            if (i / uniqOrganismCount != HUMAN_CODE) {
                while (board[tmpY][tmpX] != null) {
                    tmpY = rnd.nextInt(sizeY);
                    tmpX = rnd.nextInt(sizeX);
                }
                board[tmpY][tmpX] = factory.createNewOrganism(i / uniqOrganismCount, tmpX, tmpY, this);
                listOfOrganisms.add(board[tmpY][tmpX]);
            } else if (i % uniqOrganismCount == 0) {
                while (board[tmpY][tmpX] != null) {
                    tmpY = rnd.nextInt(sizeY);
                    tmpX = rnd.nextInt(sizeX);
                }
                board[tmpY][tmpX] = factory.createNewOrganism(HUMAN_CODE, tmpX, tmpY, this);
                listOfOrganisms.add(board[tmpY][tmpX]);
                playerIsActive = false;
            }
        }

        frameMaker();
        drawTheWorld();
    }

    public JFrame frameMaker() {
        final int spaceForButtons = 100, spaceForComments = 250;
        final int buttonWidth = 150, buttonHeight = 30, buttonInterval = 10;
        frameWidth = squareSize * this.sizeX + spaceForComments;
        frameHeight = squareSize * this.sizeY + spaceForButtons;
        screenPositionX = (LCDWidth - frameWidth) / 2;
        screenPositionY = (LCDHeight - frameHeight) / 2;

        if (screenPositionX < 0)
            screenPositionX = 0;
        if (screenPositionY < 0)
            screenPositionY = 0;

        frame =  new JFrame("Projekt JPO JAVA Maciej Stępień 148339");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setBounds(screenPositionX, screenPositionY, frameWidth, frameHeight);

        nextRoundBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpMenu.setVisible(false);
                nextRound();
                drawTheWorld();
            }
        });
        JButton saveGameBtn = new JButton("Zapisz gre");
        saveGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpMenu.setVisible(false);
                saveGame();
                JOptionPane.showMessageDialog(null, "Zapisano!", "Zapis gry", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        nextRoundBtn.setBounds(buttonInterval, this.sizeY * squareSize + spaceForButtons / 2 - buttonHeight, buttonWidth, buttonHeight);
        saveGameBtn.setBounds(buttonInterval * 3 + buttonWidth * 2+145, this.sizeY * squareSize + spaceForButtons / 2 - buttonHeight, buttonWidth, buttonHeight);

        humanMovesTo.setBounds(buttonInterval * 4 + buttonWidth * 3, this.sizeY * squareSize + spaceForButtons / 2 - buttonHeight, 200, 25);
        commentsLabel.setBounds(squareSize * this.sizeX + 36, buttonInterval - 1, frameWidth - spaceForComments, buttonInterval);
        humanLabel.setBounds(squareSize * this.sizeX + 5, (int) (buttonInterval * 2.5), spaceForComments, buttonInterval + 3);

        commentsTextArea.setEditable(false);
        scrollComments.setBounds(squareSize * this.sizeX + 5, (int) (buttonInterval * 4.1), spaceForComments - 15, squareSize * this.sizeY - (int) (buttonInterval * 4.1));

        if (playerIsActive == true)
            nextRoundBtn.setEnabled(false);
        else
            nextRoundBtn.setEnabled(true);

        frame.add(nextRoundBtn);
        frame.add(saveGameBtn);
        frame.add(commentsLabel);
        frame.add(humanLabel);
        frame.add(scrollComments);
        frame.add(humanMovesTo);

        addGrass = new JMenuItem("Trawa");
        addGuarana = new JMenuItem("Guarana");
        addThorn = new JMenuItem("Ciern");
        addMosquito = new JMenuItem("Komar");
        addBear = new JMenuItem("Niedźwiedź");
        addSheep = new JMenuItem("Owca");
        addTurtle = new JMenuItem("Zolw");
        addWolf = new JMenuItem("Wilk");

        popUpMenu.add(new JMenuItem("Dodaj rosline:"));
        popUpMenu.addSeparator();
        popUpMenu.add(addGrass);
        popUpMenu.add(addGuarana);
        popUpMenu.add(addThorn);
        popUpMenu.addSeparator();
        popUpMenu.add(new JMenuItem("Dodaj zwierze:"));
        popUpMenu.addSeparator();
        popUpMenu.add(addMosquito);
        popUpMenu.add(addBear);
        popUpMenu.add(addSheep);
        popUpMenu.add(addTurtle);
        popUpMenu.add(addWolf);

        final World innerWorld = this;

        addGrass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");
                board[indexY][indexX] = new Grass(indexX, indexY, innerWorld);
                listOfOrganisms.add(board[indexY][indexX]);
                popUpMenu.setVisible(false);
                addGrass.setBackground(null);
                drawTheWorld();
            }
        });
        addGuarana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");
                board[indexY][indexX] = new Guarana(indexX, indexY, innerWorld);
                listOfOrganisms.add(board[indexY][indexX]);
                popUpMenu.setVisible(false);
                addGuarana.setBackground(null);
                drawTheWorld();
            }
        });
        addThorn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");
                board[indexY][indexX] = new Thorn(indexX, indexY, innerWorld);
                listOfOrganisms.add(board[indexY][indexX]);
                popUpMenu.setVisible(false);
                addThorn.setBackground(null);
                drawTheWorld();
            }
        });
        addMosquito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");

                board[indexY][indexX] = new Mosquito(indexX, indexY, innerWorld);
                for (int i = 0; i < listOfOrganisms.size(); i++) {
                    if (listOfOrganisms.get(i).getInitiative() < board[indexY][indexX].getInitiative()) {
                        listOfOrganisms.add(i, board[indexY][indexX]);
                        break;
                    } else if (i == listOfOrganisms.size() - 1) {
                        listOfOrganisms.add(board[indexY][indexX]);
                        break;
                    }
                }
                popUpMenu.setVisible(false);
                addMosquito.setBackground(null);
                drawTheWorld();
            }
        });
        addBear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");

                board[indexY][indexX] = new Bear(indexX, indexY, innerWorld);
                for (int i = 0; i < listOfOrganisms.size(); i++) {
                    if (listOfOrganisms.get(i).getInitiative() < board[indexY][indexX].getInitiative()) {
                        listOfOrganisms.add(i, board[indexY][indexX]);
                        break;
                    } else if (i == listOfOrganisms.size() - 1) {
                        listOfOrganisms.add(board[indexY][indexX]);
                        break;
                    }
                }
                popUpMenu.setVisible(false);
                addBear.setBackground(null);
                drawTheWorld();
            }
        });
        addSheep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");

                board[indexY][indexX] = new Sheep(indexX, indexY, innerWorld);
                for (int i = 0; i < listOfOrganisms.size(); i++) {
                    if (listOfOrganisms.get(i).getInitiative() < board[indexY][indexX].getInitiative()) {
                        listOfOrganisms.add(i, board[indexY][indexX]);
                        break;
                    } else if (i == listOfOrganisms.size() - 1) {
                        listOfOrganisms.add(board[indexY][indexX]);
                        break;
                    }
                }
                popUpMenu.setVisible(false);
                addSheep.setBackground(null);
                drawTheWorld();
            }
        });
        addTurtle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");

                board[indexY][indexX] = new Turtle(indexX, indexY, innerWorld);
                for (int i = 0; i < listOfOrganisms.size(); i++) {
                    if (listOfOrganisms.get(i).getInitiative() < board[indexY][indexX].getInitiative()) {
                        listOfOrganisms.add(i, board[indexY][indexX]);
                        break;
                    } else if (i == listOfOrganisms.size() - 1) {
                        listOfOrganisms.add(board[indexY][indexX]);
                        break;
                    }
                }
                popUpMenu.setVisible(false);
                addTurtle.setBackground(null);
                drawTheWorld();
            }
        });
        addWolf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexX, indexY;
                indexX = (int) popUpMenu.getClientProperty("X");
                indexY = (int) popUpMenu.getClientProperty("Y");

                board[indexY][indexX] = new Wolf(indexX, indexY, innerWorld);
                for (int i = 0; i < listOfOrganisms.size(); i++) {
                    if (listOfOrganisms.get(i).getInitiative() < board[indexY][indexX].getInitiative()) {
                        listOfOrganisms.add(i, board[indexY][indexX]);
                        break;
                    } else if (i == listOfOrganisms.size() - 1) {
                        listOfOrganisms.add(board[indexY][indexX]);
                        break;
                    }
                }
                popUpMenu.setVisible(false);
                addWolf.setBackground(null);
                drawTheWorld();
            }
        });

        addGrass.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addGrass.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addGrass.setBackground(new Color(91, 255, 91));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addGuarana.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addGuarana.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addGuarana.setBackground(new Color(119, 255, 247));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addThorn.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addThorn.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addThorn.setBackground(new Color(255, 255, 51));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addMosquito.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addMosquito.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addMosquito.setBackground(new Color(255, 0, 0));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addBear.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addBear.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addBear.setBackground(new Color(255, 204, 153));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addSheep.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addSheep.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addSheep.setBackground(new Color(204, 153, 255));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addTurtle.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addTurtle.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addTurtle.setBackground(new Color(76, 153, 0));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        addWolf.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addWolf.setBackground(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                addWolf.setBackground(new Color(95, 95, 95));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        commentsTextArea.setFocusable(false);
        scrollComments.setFocusable(false);
        popUpMenu.setFocusable(false);
        saveGameBtn.setFocusable(false);
        nextRoundBtn.setFocusable(false);
        frame.setFocusable(true);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (playerIsActive == false) {
                    humanMovesTo.setText("");
                    nextRoundBtn.setEnabled(true);
                } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    String direction = "";
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            input = KeyEvent.VK_UP;
                            direction = "UP";
                            break;
                        case KeyEvent.VK_DOWN:
                            input = KeyEvent.VK_DOWN;
                            direction = "DOWN";
                            break;
                        case KeyEvent.VK_LEFT:
                            input = KeyEvent.VK_LEFT;
                            direction = "LEFT";
                            break;
                        case KeyEvent.VK_RIGHT:
                            input = KeyEvent.VK_RIGHT;
                            direction = "RIGHT";
                            break;
                    }
                    nextRoundBtn.setEnabled(true);
                } else {
                    humanMovesTo.setText("");
                    nextRoundBtn.setEnabled(false);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        commentsTextArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                popUpMenu.setVisible(false);
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        boardButtons = new JButton[this.sizeY][this.sizeX];
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setFocusable(false);
                boardButtons[i][j].setBounds(j * squareSize, i * squareSize, squareSize, squareSize);
                boardButtons[i][j].setMargin(new Insets(0, 0, 0, 0));
                boardButtons[i][j].setBackground(new Color(224, 224, 224));

                boardButtons[i][j].putClientProperty("X", j);
                boardButtons[i][j].putClientProperty("Y", i);

                final Integer innerI = new Integer(i);
                final Integer innerJ = new Integer(j);

                boardButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popUpMenu.setLocation(screenPositionX + (int) boardButtons[innerI][innerJ].getClientProperty("X") * squareSize, screenPositionY + (int) boardButtons[innerI][innerJ].getClientProperty("Y") * squareSize);
                        popUpMenu.putClientProperty("Y", innerI);
                        popUpMenu.putClientProperty("X", innerJ);
                        popUpMenu.setVisible(true);
                    }
                });
                frame.add(boardButtons[i][j]);
            }
        }
        frame.setVisible(true);
        return frame;
    }

    public void nextRound() {
        for (int i = 0; i < listOfOrganisms.size(); i++) {
            if (listOfOrganisms.get(i).getJustBorn() == true) {
                listOfOrganisms.get(i).setJustBorn(false);
            } else {
                if (listOfOrganisms.get(i).isDead() == false) {
                    listOfOrganisms.get(i).action();
                } else {
                    if (board[listOfOrganisms.get(i).getPosY()][listOfOrganisms.get(i).getPosX()] == listOfOrganisms.get(i))
                        board[listOfOrganisms.get(i).getPosY()][listOfOrganisms.get(i).getPosX()] = null;
                    listOfOrganisms.remove(i);
                }
            }
        }
    }

    public void drawTheWorld() {
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                if (this.board[i][j] != null)
                    board[i][j].drawing();
                else {
                    boardButtons[i][j].setBackground(new Color(224, 224, 224));
                    boardButtons[i][j].setText(null);
                    for(ActionListener al : boardButtons[i][j].getActionListeners()) {
                        boardButtons[i][j].removeActionListener(al);
                    }
                    final Integer innerI = i;
                    final Integer innerJ = j;
                    boardButtons[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            popUpMenu.setLocation(screenPositionX + (int) boardButtons[innerI][innerJ].getClientProperty("X") * squareSize, screenPositionY + (int) boardButtons[innerI][innerJ].getClientProperty("Y") * squareSize);
                            popUpMenu.putClientProperty("Y", innerI);
                            popUpMenu.putClientProperty("X", innerJ);
                            popUpMenu.setVisible(true);
                        }
                    });
                }
            }
        }
        printComments();
    }

    public void saveGame() {
        PrintWriter saveFile;
        try {
            saveFile = new PrintWriter("save.txt", "UTF-8");
            saveFile.println(this.getSizeX() + " " + this.getSizeY());
            saveFile.println(this.listOfOrganisms.size());

            for (int i = 0; i < listOfOrganisms.size(); i++)
                saveFile.println(listOfOrganisms.get(i).organismInfo());

            saveFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printComments() {
        humanLabel.setText("");
        if (playerIsActive == false)
            humanMovesTo.setText("");
        String tmpTxt;
        for (int i = 0; i < listOfOrganisms.size(); i++) {
            if (listOfOrganisms.get(i).whoIAm == 'H') {
            }
        }
        tmpTxt = "";
        while (commentList.isEmpty() == false) {
            tmpTxt += commentList.poll() + "\n";
        }
        commentsTextArea.setText(tmpTxt);
    }

    public int getInput() {
        return this.input;
    }
    public JLabel getHumanLabel() {
        return this.humanLabel;
    }
    public void setPlayerIsActive(boolean val) {
        this.playerIsActive = false;
    }
    public boolean getPlayerIsActive() {
        return this.playerIsActive;
    }
    public Organism[][] getBoard() {
        return this.board;
    }
    public ArrayList<Organism> getVector() {
        return this.listOfOrganisms;
    }
    public JButton[][] getboardButtons() {
        return this.boardButtons;
    }
    public int getSizeX() {
        return this.sizeX;
    }
    public int getSizeY() {
        return this.sizeY;
    }
    public JPopupMenu getPopUp() {
        return this.popUpMenu;
    }
    public JFrame getFrame() {
        return this.frame;
    }
}
