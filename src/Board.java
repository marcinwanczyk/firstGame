import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ColorModel;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    public Hero hero;
    private Image map;
    private Image shop;
    private final int DELAY = 10;
    //deklaracja zmiennych
    private int SCREEN_WIDTH = 1860;
    private int SCREEN_HIGHT = 1020;
    private Object[][] board;
    private Wave wave;

    //    inicjalizacja planszy
    public Board(int width, int heigth) {
        ImageIcon mapka = new ImageIcon("PROJEKT2/map/map.png");
        ImageIcon sklep = new ImageIcon("PROJEKT2/shop/shop.png");
        map = mapka.getImage();
        shop = sklep.getImage();
        addKeyListener(new Movement());
        setFocusable(true);
        hero = new Hero(100, 100000, 7, new Weapon(3, 250));
        timer = new Timer(DELAY, this);
        timer.start();
        //tworzymy nowa plansze o rozmiarach podanych w konstruktorze
        this.board = new Object[heigth][width];
        //inicjalizacja podstawowa planszy, wszystkie komorki ustawione na 0
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                this.board[i][j] = null;
            }
        }
        wave = new Wave(5, board);
    }

    public void enemyMove() {
        for (int i = 0; i < wave.getEnemyBoard().length; i++) {
            if (wave.getEnemyBoard()[i] instanceof Enemy) {
                if (wave.getEnemyBoard()[i].getHealth() != 0) {
                    wave.getEnemyBoard()[i].attack(board);
//                    wave.getEnemyBoard()[i].move(board, hero);
                    if (!wave.getEnemyBoard()[i].checkIfHeroIsNearby(board)) {
                        wave.getEnemyBoard()[i].move(board, hero);
                        repaint(wave.getEnemyBoard()[i].getX() - 43, wave.getEnemyBoard()[i].getY() - 43,
                                wave.getEnemyBoard()[i].getW() + 86, wave.getEnemyBoard()[i].getH() + 86);
                    }
                    if(wave.getEnemyBoard()[i].checkIfHeroIsNearby(board)){
                        wave.getEnemyBoard()[i].turnToHero(board);
                        wave.getEnemyBoard()[i].standingImage();
                        repaint(wave.getEnemyBoard()[i].getX() - 43, wave.getEnemyBoard()[i].getY() - 43,
                                wave.getEnemyBoard()[i].getW() + 86, wave.getEnemyBoard()[i].getH() + 86);
                    }
                } else
                    wave.getEnemyBoard()[i] = null;
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!hero.isFreezeGameParameter()) {
            step();
            enemyMove();
        }
        hero.attack(board);

        wave.isAlive(board);
        if (wave.isStateOfWave() == false) {
            int number = wave.getNumber();
            wave = new Wave(number + 1, board);
        }
        System.out.println(hero.getHealth());
        if(hero.getHealth() <= 0)
            System.exit(0  );
    }

    //    malowanie mapy oraz postaci
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int fontSize = 20;
        Font f = new Font("Arial", Font.BOLD, fontSize);

        g2d.setFont(f);
        g2d.setColor(Color.BLACK);
//mapa
        g2d.drawImage(map, 0, 0, null);
        g2d.drawImage(shop, 1300, 600, this);

//postac
            g2d.drawImage(hero.getActualImage()[0], hero.getX(), hero.getY(), this);
//ememy
        for (int i = 0; i < wave.getEnemyBoard().length; i++)
            if (wave.getEnemyBoard()[i] instanceof Enemy) {
                g2d.drawImage(wave.getEnemyBoard()[i].getActualImage()[0], wave.getEnemyBoard()[i].getX(), wave.getEnemyBoard()[i].getY(), this);
                Image pomoc;
                wave.getEnemyBoard()[i].standingImage();
                pomoc = wave.getEnemyBoard()[i].getActualImage()[0];
                wave.getEnemyBoard()[i].getActualImage()[0] = wave.getEnemyBoard()[i].getActualImage()[1];
                wave.getEnemyBoard()[i].getActualImage()[1] = pomoc;
            }
//exp gold level
        g2d.drawString("Hero: Knight", 1300, 30);

        g2d.drawString("Gold: " + hero.getGold(), 1300, 50);

        g2d.drawString("Exp: " + hero.getExp(), 1300, 70);

        g2d.drawString("Level: " + hero.getLevel(), 1300, 90);

        g2d.drawString("Weapon usage:" + hero.getWeapon().getUsage(), 1300, 110);

        g2d.drawString("Health " + hero.getHealth(), 1300, 130);

        g2d.drawString("Stamina " + hero.getStamina(), 1300, 150);
        Toolkit.getDefaultToolkit().sync();
    }

    //odswiezanie postaci co kazdy ruch
    private void step() {
//            try {
                hero.move(board);
//                Thread.sleep(50);
//            } catch (InterruptedException exc) {
//            }

            Image temp = hero.getActualImage()[0];
            hero.getActualImage()[0] = hero.getActualImage()[1];
            hero.getActualImage()[1] = temp;
            repaint(hero.getX() - 43, hero.getY() - 43,
                    hero.getWidth()[0] + 86, hero.getHeight()[0] + 86);
            repaint();
    }

    //    przypisanie sterowania do naszego obiektu
    private class Movement extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            hero.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            hero.keyPressed(e);
        }
    }

    public int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    public int getSCREEN_HIGHT() {
        return SCREEN_HIGHT;
    }

    public Object[][] getBoard() {
        return board;
    }
}