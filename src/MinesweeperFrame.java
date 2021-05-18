import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame {

    private BoardPanel boardPanel;
    private HeaderLabel flagsLabel;
    private HeaderLabel timeLabel;
    private RestartButton restartButton;
    private final int frameHeight, frameWidth, headerHeight;

    private MinesweeperGame minesweeperGame;

    public MinesweeperFrame(MinesweeperGame minesweeperGame) {
        this.minesweeperGame = minesweeperGame;
        headerHeight = 100;
        frameHeight = minesweeperGame.getGameLevel().getNumberOfSquaresInHeight() * 50 + headerHeight;
        frameWidth = minesweeperGame.getGameLevel().getNumberOfSquaresInWidth() * 50;
        setFrame();
    }

    private void setFrame() {
        setResizable(false);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setComponents();
        setVisible(true);
    }

    private void setComponents() {
        add(boardPanel =new BoardPanel(minesweeperGame), BorderLayout.PAGE_END);
        add(flagsLabel =new HeaderLabel(getWidth()),BorderLayout.LINE_START);
        add(timeLabel =new HeaderLabel(getWidth()),BorderLayout.LINE_END);
        add(restartButton =new RestartButton(minesweeperGame),BorderLayout.CENTER);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public HeaderLabel getFlagsLabel() {
        return flagsLabel;
    }

    public HeaderLabel getTimeLabel() {
        return timeLabel;
    }

    public RestartButton getRestartButton() {
        return restartButton;
    }
}
