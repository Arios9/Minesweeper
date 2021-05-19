package mainCode;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperGame.*;

public class BoardPanel extends JPanel {

    public BoardPanel() {
        int boardHeight = gameLevel.getNumberOfSquaresInHeight() * 50;
        int boardWidth = gameLevel.getNumberOfSquaresInWidth() * 50;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setLayout(new GridLayout(gameLevel.getNumberOfSquaresInHeight(), gameLevel.getNumberOfSquaresInWidth()));
    }

    public void setTheBoard() {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                add(squares[i][j]=new Square(i,j));
    }
}
