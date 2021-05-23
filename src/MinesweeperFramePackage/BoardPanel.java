package MinesweeperFramePackage;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperGame.*;

public class BoardPanel extends JPanel {
    public BoardPanel() {
        setPreferredSize(new Dimension(arrayWidth * 50, arrayHeight * 50));
        setLayout(new GridLayout(arrayHeight, arrayWidth));
    }

    public void setTheBoard() {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                add(squares[i][j]=new Square(i,j));
    }
}
