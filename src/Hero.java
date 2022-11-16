import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class Hero extends Thread {
    //deklaracja podstawowych atrybutow
    private int health;
    private int strength;
    private int dmg;
    private int level;
    private int nextLevelEXP;
    private int exp;
    private int stamina;
    private int gold;
    private int dx;
    private int dy;
    private int xMap;
    private int yMap;
    private int xBoard;
    private int yBoard;
    private int[] w = new int[24];
    private int[] h = new int[24];
    private Weapon weapon;
    private Image[] image = new Image[24];
    private Image[] actualImage = new Image[3];
    private boolean attackParameter;
    private boolean blockParameter = false;
    private Image[] tempImage = new Image[3];
    private boolean freezeGameParameter = false;
    //kontruktor klasy Hero, gold i exp na start = 0, usunalem chwilowo z konstruktora weapon weapon
    public Hero(int health, int stamina, int strength, Weapon weapon) {
        this.exp = 0;
        this.health = health;
        this.stamina = 100;
        this.weapon = weapon;
        this.strength = strength;
        this.gold = 0;
        dmg = strength + weapon.getPower();
        loadImage();
        xBoard = 0;
        yBoard = 0;
        start();
        nextLevelEXP = 500;
    }


    @Override
    public String toString() {
        return "hero";
    }

    public void run() { //watek ładujący staminę co 500ms
        loadStamina();
    }

    //zaladowanie ikony dla obiektu
    private void loadImage() { //metoda załadowania obrazów z plików gry do tablicy
        for (int i = 1; i <= 24; i++) {
            ImageIcon knight = new ImageIcon("PROJEKT2/icons/Knight/D" + i + ".png");
            int temp = i - 1;
            image[temp] = knight.getImage();
            w[temp] = image[temp].getWidth(null);
            h[temp] = image[temp].getHeight(null);
        }
        actualImage[0] = image[0]; // startowy obrazek przy spawnie bohatera
    }

    // przesuniecie obiektu
    public void move(Object[][] plansza) {
        try{
            if (dx != 0) { //jesli ruch po osi x nie nastapil, nie wykona się metoda
                dx = dx / Math.abs(dx); // klikniecie W A S D powoduje duze przesuniecie, a my interesujemy się przeskokiem o jeden więc dzielimy przez wartosc bzwgl
                if (xBoard + dx >= 0 && xBoard + dx <= 34) //granice mapy
                    if (plansza[yBoard][xBoard + dx] == null) { //sprawdzenie dostepnosci obszaru
                        plansza[yBoard][xBoard] = null; //zwolnienie komorki tablicy
                        xBoard += dx; // update x
                        plansza[yBoard][xBoard] = this; //umieszczenie obiektu w komorce
                        xMap += 43 * dx; //przemieszczenie na mapie x, *43 bo 43x43 jest obrazek
                    }
            } else if (dy != 0) { // to samo co z X
                dy = dy / Math.abs(dy);
                if (yBoard + dy >= 0 && yBoard + dy <= 17)
                    if (plansza[yBoard + dy][xBoard] == null) {
                        plansza[yBoard][xBoard] = null;
                        yBoard += dy;
                        plansza[yBoard][xBoard] = this;
                        yMap += (dy * 43);
                    }
            }
        Thread.sleep(50);
        }catch (InterruptedException exc){}
    }

    public void loadStamina() {

        while (true) {
            try {
                level_UP();
                if (actualImage[0] == image[2] || actualImage[0] == image[5] || actualImage[0] == image[8] || actualImage[0] == image[11]) { //sprawdzenie czy obiekt stoi w miejscu
                    if(getStamina() < 400)
                    setStamina(getStamina() + 10);
                    //System.out.println(getStamina());
                }
                Thread.sleep(500);
            } catch (InterruptedException exc) {
            }
        }
    }

    public void addGold() {
        Random random = new Random(); //randomowa wartosc golda
//        this.gold += random.nextInt(10);
        this.gold += 5;
    }

    public void addEXP(Enemy oneEnemy) {
        this.exp += 200;
    }

    public void level_UP(){
        if(exp >= nextLevelEXP) {
            this.health += 5 * getLevel();
            this.strength += 2;
            setExp(this.exp - this.nextLevelEXP);
            this.nextLevelEXP = (getLevel() * 500);
            level++;
        }
    }

    public void attack(Object[][] board) {
        System.out.println(weapon.getUsage() + " " + dmg + " " + stamina);
        if (attackParameter == true) {// attackParameter jest true gdy nacisniemy przycisk odpowiedzialny za atak
            int zwrot = -1;
            if (actualImage[0] == image[6] || actualImage[0] == image[7] || actualImage[0] == image[8] || actualImage[0] == image[18] || actualImage[0] == image[19] || actualImage[0] == image[20] ) //lewo
                zwrot = 0;
            else if (actualImage[0] == image[9] || actualImage[0] == image[10] || actualImage[0] == image[11] || actualImage[0] == image[21] || actualImage[0] == image[22] || actualImage[0] == image[23] ) //prawo
                zwrot = 1;
            else if (actualImage[0] == image[3] || actualImage[0] == image[4] || actualImage[0] == image[5] || actualImage[0] == image[15] || actualImage[0] == image[16] || actualImage[0] == image[17] ) //gora
                zwrot = 2;
            else if (actualImage[0] == image[0] || actualImage[0] == image[1] || actualImage[0] == image[2] || actualImage[0] == image[12] || actualImage[0] == image[13] || actualImage[0] == image[14] )// dol
                zwrot = 3;
            switch (zwrot) {
                case 0:
                    if (xBoard > 0) // granice mapy
                        if (board[yBoard][xBoard - 1] instanceof Enemy) { //sprawdzenie czy obok stoi wróg
                            if (((Enemy) board[yBoard][xBoard - 1]).getHealth() > 0) //sprawdzenie zdrowia wroga
                                if (getStamina() >= 15 && weapon.getUsage() >= 10) { //sprawdzenie mozliwosci ataku
                                    ((Enemy) board[yBoard][xBoard - 1]).setHealth(((Enemy) board[yBoard][xBoard - 1]).getHealth() - dmg); // zadanie obrazen
                                    setStamina(getStamina() - 10); // zmniejszenie staminy
                                    weapon.breakOnHit(); // zuzywanie miecza
//                                    actualImage[0] = image[18];
//                                    actualImage[1] = image[19];
//                                    actualImage[2] = image[20];
                                }
                            if (((Enemy) board[yBoard][xBoard - 1]).getHealth() == 0) {
                                addEXP((Enemy) board[yBoard][xBoard - 1]);
                                board[yBoard][xBoard - 1] = null;
                                addGold();
                            }

                        }
                    break;
                case 1:
                    if (xBoard < 34)
                        if (board[yBoard][xBoard + 1] instanceof Enemy) {
                            if (((Enemy) board[yBoard][xBoard + 1]).getHealth() > 0)
                                if (getStamina() >= 15 && weapon.getUsage() >= 10) {
                                    ((Enemy) board[yBoard][xBoard + 1]).setHealth(((Enemy) board[yBoard][xBoard + 1]).getHealth() - dmg);
                                    setStamina(getStamina() - 10);
                                    weapon.breakOnHit();
//                                    actualImage[0] = image[21];
//                                    actualImage[1] = image[22];
//                                    actualImage[2] = image[23];
                                }
                            if (((Enemy) board[yBoard][xBoard + 1]).getHealth() == 0) {
                                addEXP((Enemy) board[yBoard][xBoard + 1]);
                                board[yBoard][xBoard + 1] = null;
                                addGold();
                            }
                        }

                    break;
                case 2:
                    if (yBoard > 0)
                        if (board[yBoard - 1][xBoard] instanceof Enemy) {
                            if (((Enemy) board[yBoard - 1][xBoard]).getHealth() > 0)
                                if (getStamina() >= 15 && weapon.getUsage() >= 10) {
                                    ((Enemy) board[yBoard - 1][xBoard]).setHealth(((Enemy) board[yBoard - 1][xBoard]).getHealth() - dmg);
                                    setStamina(getStamina() - 10);
                                    weapon.breakOnHit();
//                                    actualImage[0] = image[15];
//                                    actualImage[1] = image[16];
//                                    actualImage[2] = image[17];
                                }
                            if (((Enemy) board[yBoard - 1][xBoard]).getHealth() == 0) {
                                addEXP((Enemy) board[yBoard - 1][xBoard]);
                                board[yBoard - 1][xBoard] = null;
                                addGold();
                            }
                        }

                    break;
                case 3:
                    if (yBoard < 17)
                        if (board[yBoard + 1][xBoard] instanceof Enemy) {
                            if (((Enemy) board[yBoard + 1][xBoard]).getHealth() > 0)
                                if (getStamina() >= 15 && weapon.getUsage() >= 10) {
                                    ((Enemy) board[yBoard + 1][xBoard]).setHealth(((Enemy) board[yBoard + 1][xBoard]).getHealth() - dmg);
                                    setStamina(getStamina() - 10);
                                    weapon.breakOnHit();
//                                    actualImage[0] = image[12];
//                                    actualImage[1] = image[13];
//                                    actualImage[2] = image[14];
                                }
                            if (((Enemy) board[yBoard + 1][xBoard]).getHealth() == 0) {
                                addEXP((Enemy) board[yBoard + 1][xBoard]);
                                board[yBoard + 1][xBoard] = null;
                                addGold();
                            }
                        }
                    break;
            }

        }
    }
    //    metoda movementu klawiszami
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = -1;
            actualImage[0] = image[6];
            actualImage[1] = image[7];
        }

        if (key == KeyEvent.VK_D) {
            dx = 1;
            actualImage[0] = image[9];
            actualImage[1] = image[10];
        }

        if (key == KeyEvent.VK_W) {
            dy = -1;
            actualImage[0] = image[3];
            actualImage[1] = image[4];
        }

        if (key == KeyEvent.VK_S) {
            dy = 1;
            actualImage[0] = image[0];
            actualImage[1] = image[1];
        }
        if (key == KeyEvent.VK_SPACE) {
            attackParameter = true;
//            tempImage[0] = actualImage[0];
//            tempImage[1] = actualImage[1];
//            tempImage[2] = actualImage[2];
        }
        if(key == KeyEvent.VK_B){
            blockParameter = true;
        }
        if(key == KeyEvent.VK_G){
            if(xBoard >=30 && yBoard >=14) {
                this.freezeGameParameter = true;
                JFrame frame = new Shop(this);
                frame.pack();
                frame.setVisible(true);
            }
        }
        if(key == KeyEvent.VK_P) {
            this.freezeGameParameter = false;
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
            actualImage[0] = image[8];
            actualImage[1] = image[8];
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
            actualImage[0] = image[11];
            actualImage[1] = image[11];
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
            actualImage[0] = image[5];
            actualImage[1] = image[5];
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
            actualImage[0] = image[2];
            actualImage[1] = image[2];
        }
        if (key == KeyEvent.VK_SPACE) {
            attackParameter = false;
//            try{Thread.sleep(2);}catch (InterruptedException exc){}
//            actualImage[0] = tempImage[0];
//            actualImage[1] = tempImage[1];
//            actualImage[2] = tempImage[2];
        }
        if(key == KeyEvent.VK_B){
            blockParameter = false;
        }
    }

    // Gettery i Settery dla wszystkich atrybutow
    public Image[] getActualImage() {
        return actualImage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isFreezeGameParameter() {
        return freezeGameParameter;
    }

    public void setFreezeGameParameter(boolean freezeGameParameter) {
        this.freezeGameParameter = freezeGameParameter;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getX() {
        return xMap;
    }

    public int getY() {
        return yMap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int[] getWidth() {
        return w;
    }

    public int[] getHeight() {
        return h;
    }

    public boolean isBlockParameter() {
        return blockParameter;
    }

    public Image[] getImage() {
        return image;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getxBoard() {
        return xBoard;
    }

    public int getyBoard() {
        return yBoard;
    }
}