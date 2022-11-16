import javax.swing.*;
import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.util.Random;

public class Enemy {

    //    atrybuty
    private int health;
    private int level = 1;
    private int strength;
    private int xMap;
    private int yMap;
    private int dx;
    private int dy;
    private Image image;
    private int w;
    private int h;
    private int xBoard;
    private int yBoard;
    private Image[] images = new Image[12];
    private Image[] actualImage = new Image[3];
    private boolean isMoving;

    public Enemy(int health, int level, int strength, Object[][] board) {
        this.health = health;
        this.level = level;
        this.strength = strength;
        loadImage();
        isMoving = false;
        do {
            Random xrand = new Random();
            Random yrand = new Random();
            xBoard = xrand.nextInt(35);
            yBoard = yrand.nextInt(18);
            xMap = xBoard * 43;
            yMap = yBoard * 43;
        } while (board[yBoard][xBoard] != null || (xBoard >= 30 && yBoard >= 14));
    }

    //    get/set dla atrybutow

    public int getX() {
        return xMap;
    }

    public int getY() {
        return yMap;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Image getImage() {
        return image;
    }

    public int getxBoard() {
        return xBoard;
    }

    public int getyBoard() {
        return yBoard;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Image[] getActualImage() {
        return actualImage;
    }

    //    metody
    public void attack(Object[][] board) {
        //System.out.println(this.health);
        int liczba = new Random().nextInt(20);
        if(liczba < 10) {
            char zwrot = this.turn();
            switch (zwrot) {
                case 'l':
                    if (xBoard > 0)
                        if (board[yBoard][xBoard - 1] instanceof Hero) {
                            if (((Hero) board[yBoard][xBoard - 1]).getHealth() > 0)
                                if (!((Hero) board[yBoard][xBoard - 1]).isBlockParameter())
                                    ((Hero) board[yBoard][xBoard - 1]).setHealth(((Hero) board[yBoard][xBoard - 1]).getHealth() - 2);
                                else
                                    System.out.println("attack blocked!");
                            if (((Hero) board[yBoard][xBoard - 1]).getHealth() == 0) {
                                board[yBoard][xBoard - 1] = null;
                            }

                        }
                    break;
                case 'p':
                    if (xBoard < 34)
                        if (board[yBoard][xBoard + 1] instanceof Hero) {
                            if (((Hero) board[yBoard][xBoard + 1]).getHealth() > 0)
                                if (!((Hero) board[yBoard][xBoard + 1]).isBlockParameter())
                                    ((Hero) board[yBoard][xBoard + 1]).setHealth(((Hero) board[yBoard][xBoard + 1]).getHealth() - 2);
                                else
                                    System.out.println("attack blocked!");
                            if (((Hero) board[yBoard][xBoard + 1]).getHealth() == 0) {
                                board[yBoard][xBoard + 1] = null;
                            }

                        }
                    break;
                case 'g':
                    if (yBoard > 0)
                        if (board[yBoard - 1][xBoard] instanceof Hero) {
                            if (((Hero) board[yBoard - 1][xBoard]).getHealth() > 0)
                                if (!((Hero) board[yBoard - 1][xBoard]).isBlockParameter())
                                    ((Hero) board[yBoard - 1][xBoard]).setHealth(((Hero) board[yBoard - 1][xBoard]).getHealth() - 2);
                                else
                                    System.out.println("attack blocked!");
                            if (((Hero) board[yBoard - 1][xBoard]).getHealth() == 0) {
                                board[yBoard - 1][xBoard] = null;
                            }

                        }
                    break;
                case 'd':
                    if (yBoard < 17)
                        if (board[yBoard + 1][xBoard] instanceof Hero) {
                            if (((Hero) board[yBoard + 1][xBoard]).getHealth() > 0)
                                if (!((Hero) board[yBoard + 1][xBoard]).isBlockParameter())
                                    ((Hero) board[yBoard + 1][xBoard]).setHealth(((Hero) board[yBoard + 1][xBoard]).getHealth() - 2);
                                else
                                    System.out.println("attack blocked!");
                            if (((Hero) board[yBoard + 1][xBoard]).getHealth() == 0) {
                                board[yBoard + 1][xBoard] = null;
                            }

                        }
                    break;
            }
        }
    }

    public char turn(){
        char result;
        if(actualImage[0] == images[6] || actualImage[0] == images[7] || actualImage[0] == images[8] )
            result = 'l';
        else if(actualImage[0] == images[3] || actualImage[0] == images[4] || actualImage[0] == images[5] )
            result = 'd';
        else if(actualImage[0] == images[9] || actualImage[0] == images[10] || actualImage[0] == images[11] )
            result = 'p';
        else
            result = 'g';

        return result;
    }

    public void block() {
    }

    private void loadImage() {
        ImageIcon enemy = new ImageIcon("PROJEKT2/icons/Goblin/D3.png");
        image = enemy.getImage();
        w = image.getWidth(null);
        h = image.getHeight(null);
        int indexHelper = 0;
        for (int i = 1; i <= 3; i++) {
            ImageIcon enemys = new ImageIcon("PROJEKT2/icons/Goblin/D" + i + ".png");
            int temp = i - 1;
            images[temp] = enemys.getImage();
            w = images[0].getWidth(null);
            h = images[0].getHeight(null);
            //w[temp] = images[temp].getWidth(null);
            //h[temp] = images[temp].getHeight(null);
        }
        indexHelper = 3;
        for (int i = 1; i <= 3; i++) {
            ImageIcon enemys = new ImageIcon("PROJEKT2/icons/Goblin/G" + i + ".png");
            int temp = i - 1;
            images[indexHelper + temp] = enemys.getImage();
            w = images[0].getWidth(null);
            h = images[0].getHeight(null);
        }
        indexHelper = 6;
        for (int i = 1; i <= 3; i++) {
            ImageIcon enemys = new ImageIcon("PROJEKT2/icons/Goblin/L" + i + ".png");
            int temp = i - 1;
            images[indexHelper + temp] = enemys.getImage();
            w = images[0].getWidth(null);
            h = images[0].getHeight(null);
        }
        indexHelper = 9;
        for (int i = 1; i <= 3; i++) {
            ImageIcon enemys = new ImageIcon("PROJEKT2/icons/Goblin/P" + i + ".png");
            int temp = i - 1;
            images[indexHelper + temp] = enemys.getImage();
            w = images[0].getWidth(null);
            h = images[0].getHeight(null);
        }
        actualImage[0] = images[0];
    }

    public void turnToHero(Object[][] board){
        if(checkIfHeroIsNearby(board)){
            if (xBoard > 0)
                if (board[yBoard][xBoard - 1] instanceof Hero){
                    actualImage[0] = images[8];
                    actualImage[1] = images[8];
                    actualImage[2] = images[8];
                }
            if (xBoard < 34)
                if (board[yBoard][xBoard + 1] instanceof Hero){
                    actualImage[0] = images[11];
                    actualImage[1] = images[11];
                    actualImage[2] = images[11];
                }
            if (yBoard > 0)
                if (board[yBoard - 1][xBoard] instanceof Hero){
                    actualImage[0] = images[5];
                    actualImage[1] = images[5];
                    actualImage[2] = images[5];
                }
            if (yBoard < 17)
                if (board[yBoard + 1][xBoard] instanceof Hero){
                    actualImage[0] = images[2];
                    actualImage[1] = images[2];
                    actualImage[2] = images[2];
                }
        }
    }

    public boolean checkIfHeroIsNearby(Object[][] board) {
        boolean result = false;
        if (xBoard > 0)
            if (board[yBoard][xBoard - 1] instanceof Hero)
                result = true;
        if (xBoard < 34)
            if (board[yBoard][xBoard + 1] instanceof Hero)
                result = true;
        if (yBoard > 0)
            if (board[yBoard - 1][xBoard] instanceof Hero)
                result = true;
        if (yBoard < 17)
            if (board[yBoard + 1][xBoard] instanceof Hero)
                result = true;
        return result;
    }

    public void standingImage(){
        if(isMoving == false){
            actualImage[0] = actualImage[2];
            actualImage[1] = actualImage[2];
        }
    }

    public void move(Object[][] plansza, Hero hero) {
        Random rand = new Random();
        int ifMove = rand.nextInt(20);
        isMoving = false;
        if (ifMove < 3) {
            isMoving = true;
            Random direction = new Random();
            int dir = direction.nextInt(2);
            int r = -2;
            switch (dir) {
                case 0: // wybor x jako celu
                    if (hero.getxBoard() < xBoard) //ruch w lewo
                        r = 0;
                    else //ruch w prawo
                        r = 2;
                    break;
                case 1: //wybor y jako celu
                    if (hero.getyBoard() < yBoard) // ruch w gore
                        r = 1;
                    else //ruch w dol
                        r = 3;
                    break;
            }
            switch (r) {
                case 0:
                    dx = -1;
                    actualImage[0] = images[6];
                    actualImage[1] = images[7];
                    actualImage[2] = images[8];
                    break;
                case 1:
                    dy = -1;
                    actualImage[0] = images[3];
                    actualImage[1] = images[4];
                    actualImage[2] = images[5];
                    break;
                case 2:
                    dx = 1;
                    actualImage[0] = images[9];
                    actualImage[1] = images[10];
                    actualImage[2] = images[11];
                    break;
                case 3:
                    dy = 1;
                    actualImage[0] = images[0];
                    actualImage[1] = images[1];
                    actualImage[2] = images[2];
                    break;
            }
            if (dx != 0) {
                if ((xBoard + dx >= 0 && xBoard + dx <= 34 && yBoard < 14) || (yBoard >=14 && xBoard + dx >=0 && xBoard + dx <30) )
                    if (plansza[yBoard][xBoard + dx] == null) {
                        plansza[yBoard][xBoard] = null;
                        xBoard += dx;
                        plansza[yBoard][xBoard] = this;
                        xMap += dx * 43;
                    }
            }
            if (dy != 0) {
                if ((yBoard + dy >= 0 && yBoard + dy <= 17 & xBoard < 30) || (yBoard +dy >=0 && yBoard + dy < 14 && xBoard >= 30))
                    if (plansza[yBoard + dy][xBoard] == null) {
                        plansza[yBoard][xBoard] = null;
                        yBoard += dy;
                        plansza[yBoard][xBoard] = this;
                        yMap += dy * 43;
                    }

            }
        }
    }


}