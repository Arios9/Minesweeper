import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel {

    public BoardPanel(MinesweeperGame minesweeperGame) {
        GameLevel gameLevel = minesweeperGame.getGameLevel();
        int boardHeight = gameLevel.getNumberOfSquaresInHeight() * 50;
        int boardWidth = gameLevel.getNumberOfSquaresInWidth() * 50;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setLayout(new GridLayout(gameLevel.getNumberOfSquaresInHeight(), gameLevel.getNumberOfSquaresInWidth()));
    }

}
