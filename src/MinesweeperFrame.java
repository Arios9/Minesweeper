import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame{

    public static BoardPanel boardPanel;
    public static HeaderLabel flagsLabel;
    public static HeaderLabel timeLabel;
    public static RestartButton restartButton;
    private final int frameHeight, frameWidth, headerHeight;

    public MinesweeperFrame() {
        headerHeight = 100;
        frameHeight = MinesweeperGame.gameLevel.getNumberOfSquaresInHeight() * 50 + headerHeight;
        frameWidth = MinesweeperGame.gameLevel.getNumberOfSquaresInWidth() * 50;
        setFrame();
    }

    private void setFrame() {
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setComponents();
        setVisible(true);
        setOnCloseListener();
    }

    private void setOnCloseListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                MinesweeperGame.FinishCurrentGame();
            }
        });
    }

    private void setComponents() {
        add(boardPanel =new BoardPanel(), BorderLayout.PAGE_END);
        add(flagsLabel =new HeaderLabel(getWidth()),BorderLayout.LINE_START);
        add(timeLabel =new HeaderLabel(getWidth()),BorderLayout.LINE_END);
        add(restartButton =new RestartButton(),BorderLayout.CENTER);
    }

}
