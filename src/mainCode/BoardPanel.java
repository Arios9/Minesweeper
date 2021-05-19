package mainCode;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperGame.*;

public class BoardPanel extends JPanel {

    public BoardPanel() {
        int squaresInHeight = gameLevel.getNumberOfSquaresInHeight();
        int squaresInWidth = gameLevel.getNumberOfSquaresInWidth();
        int boardHeight = squaresInHeight * 50;
        int boardWidth = squaresInWidth * 50;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setLayout(new GridLayout(squaresInHeight, squaresInWidth));
    }

    public void setTheBoard() {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                add(squares[i][j]=new Square(i,j));
    }
}
