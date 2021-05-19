package mainCode;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperGame.FinishCurrentGame;
import static mainCode.MinesweeperGame.gameLevel;

public class MinesweeperFrame extends JFrame{

    public static BoardPanel boardPanel;
    public static HeaderLabel flagsLabel;
    public static HeaderLabel timeLabel;
    public static RestartButton restartButton;
    public static int frameHeight, frameWidth, headerHeight;

    public MinesweeperFrame() {
        headerHeight = 100;
        frameHeight = gameLevel.getNumberOfSquaresInHeight() * 50 + headerHeight;
        frameWidth = gameLevel.getNumberOfSquaresInWidth() * 50;
        setFrame();
    }

    private void setFrame() {
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setVisible(true);
        setOnCloseListener();
    }

    private void setOnCloseListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FinishCurrentGame();
            }
        });
    }

    public void setComponents() {
        add(boardPanel =new BoardPanel(), BorderLayout.PAGE_END);
        add(flagsLabel =new HeaderLabel(),BorderLayout.LINE_START);
        add(timeLabel =new HeaderLabel(),BorderLayout.LINE_END);
        add(restartButton =new RestartButton(),BorderLayout.CENTER);
    }

}
