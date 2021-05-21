package mainMenuPackage;

import javax.swing.*;


public class MainMenu extends JFrame {

    static MainMenu mainMenu;
    static final int mainFrameWidth = 600;
    private static final int mainFrameHeight = 300;

    private JPanel jPanel;

    public static void main(String[] args) {
        mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }

    public MainMenu() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(mainFrameWidth,mainFrameHeight);
        setLocationRelativeTo(null);
        setComponents();
        setGameLevels();
    }

    private void setComponents() {
        jPanel = new JPanel();
        add(jPanel);
        jPanel.add(new TitleLabel());
    }

    private void setGameLevels() {
        GameLevel easyLevel = new GameLevel("Easy", 9, 9, 10);
        GameLevel mediumLevel = new GameLevel("Medium", 16, 16, 40);
        GameLevel hardLevel = new GameLevel("Hard", 16, 30, 99);

        jPanel.add(new GameLevelButton(easyLevel));
        jPanel.add(new GameLevelButton(mediumLevel));
        jPanel.add(new GameLevelButton(hardLevel));
    }


}
