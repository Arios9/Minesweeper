package mainMenuPackage;

import mainCode.HighScore;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MainMenu extends JFrame {

    static MainMenu mainMenu;
    private JPanel jPanel;
    static final int mainFrameWidth = 600;
    private static final int mainFrameHeight = 500;
    public static ArrayList <GameLevel> gameLevels;
    private static ArrayList <RecordLabel> recordLabels;

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
    }

    private void setComponents() {
        jPanel = new JPanel();
        add(jPanel);
        jPanel.add(new TitleLabel("Play Minesweeper"));
        jPanel.add(new TitleLabel("Levels"));
        setGameLevels();
        jPanel.add(new TitleLabel("Records"));
        setRecordLabels();
        setRecordsToLabels();
    }

    private void setGameLevels() {
        gameLevels = new ArrayList<>();
        gameLevels.add(new GameLevel("Easy", 9, 9, 10));
        gameLevels.add(new GameLevel("Medium", 16, 16, 40));
        gameLevels.add(new GameLevel("Hard", 16, 30, 99));
        gameLevels.forEach(gameLevel -> jPanel.add(new GameLevelButton(gameLevel)));
    }

    private void setRecordLabels() {
        recordLabels = new ArrayList<>();
        gameLevels.forEach(gameLevel -> recordLabels.add(new RecordLabel("No Record")));
        recordLabels.forEach(recordLabel -> jPanel.add(recordLabel));
    }

    public static void setRecordsToLabels() {
        try {
            ArrayList <String> records = HighScore.getRecords();
            for(int i=0; i<records.size(); i++){
                if(!records.get(i).equals(""))
                    recordLabels.get(i).setText(records.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
