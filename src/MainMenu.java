import javax.swing.*;
import java.awt.*;


public class MainMenu extends JFrame {

    private GameLevel easyLevel;
    private GameLevel mediumLevel;
    private GameLevel hardLevel;

    public static void main(String[] args) {
        new MainMenu().setVisible(true);
    }

    public MainMenu() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600,200);
        setLocationRelativeTo(null);
        setGameLevels();
        setLayout(new GridLayout(1,3));
        setComponents();
    }

    private void setGameLevels() {
        easyLevel = new GameLevel("Easy", 9, 9, 10);
        mediumLevel = new GameLevel("Medium", 16, 16, 40);
        hardLevel = new GameLevel("Hard", 16, 30, 99);
    }

    private void setComponents() {
        add(new GameLevelButton(easyLevel));
        add(new GameLevelButton(mediumLevel));
        add(new GameLevelButton(hardLevel));
    }


}
