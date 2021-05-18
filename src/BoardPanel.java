import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel {

    public BoardPanel() {
        GameLevel gameLevel = MinesweeperGame.gameLevel;
        int boardHeight = gameLevel.getNumberOfSquaresInHeight() * 50;
        int boardWidth = gameLevel.getNumberOfSquaresInWidth() * 50;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setLayout(new GridLayout(gameLevel.getNumberOfSquaresInHeight(), gameLevel.getNumberOfSquaresInWidth()));
    }

    public void setTheBoard() {
        for(int i = 0; i< MinesweeperGame.gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< MinesweeperGame.gameLevel.getNumberOfSquaresInWidth(); j++)
                add(MinesweeperGame.squares[i][j]=new Square(i,j));
    }
}
