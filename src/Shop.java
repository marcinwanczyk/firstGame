import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop extends JFrame{
    private JPanel panel1;
    private JButton upgradeWeaponButton;
    private JButton repairButton;
    private JButton healthButton;
    private JButton goBackButton;
    private JLabel kosztupgrade;
    private JLabel kosztzdrowie;
    private JLabel kosztnaprawa;

    public Shop(Hero hero) {
        setContentPane(panel1);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hero.setFreezeGameParameter(false);
                dispose();
            }
        });
        repairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hero.getGold() >= 4){
                    hero.getWeapon().setUsage(250);
                    hero.setGold(hero.getGold() - 4);
                }
            }
        });
        healthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hero.getGold() >= 5){
                    hero.setHealth(hero.getHealth() + 50);
                    hero.setGold(hero.getGold() - 5);
                }
            }
        });
        upgradeWeaponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hero.getGold() >= 5){
                    hero.getWeapon().setPower(hero.getWeapon().getPower() + 5);
                    hero.setGold(hero.getGold() - 5);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shop");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
