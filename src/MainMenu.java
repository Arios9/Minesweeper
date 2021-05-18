import javax.swing.*;
import java.awt.*;


public class MainMenu extends JFrame {

    public static void main(String[] args) {
        new MainMenu().setVisible(true);
    }


    public MainMenu() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600,200);
        setLocationRelativeTo(null);
        setComponents();
        setLayout(new GridLayout(1,3));
    }

    private void setComponents() {

        GameLevel easyLevel, mediumLevel, hardLevel;
        easyLevel = new GameLevel("Easy", 9, 9, 10);
        mediumLevel = new GameLevel("Medium", 16, 16, 40);
        hardLevel = new GameLevel("Hard", 16, 30, 99);

        JButton easy, medium, hard;
        easy = new JButton("Easy");
        easy.addActionListener(e -> {
            new MinesweeperGame(easyLevel).setVisible(true);
        });
        add(easy);
        medium = new JButton("Medium");
        medium.addActionListener(e -> {
            new MinesweeperGame(mediumLevel).setVisible(true);
        });
        add(medium);
        hard = new JButton("Hard");
        hard.addActionListener(e -> {
            new MinesweeperGame(hardLevel).setVisible(true);
        });
        add(hard);
    }


}
