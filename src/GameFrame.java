import javax.swing.*;
import java.awt.EventQueue;

//inicjalizacja i wywolanie okna gry
public class GameFrame extends JFrame {
    Board plansza = new Board(35, 18);

    public GameFrame() {
        add(plansza);
        setTitle("Moving hero for now");
        setSize(1505, 817);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //        main, watek
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GameFrame ex = new GameFrame();
            ex.setVisible(true);
        });
    }

}