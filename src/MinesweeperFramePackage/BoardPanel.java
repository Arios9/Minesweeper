package MinesweeperFramePackage;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperGame.*;

public class BoardPanel extends JPanel {
    public BoardPanel() {
        setPreferredSize(new Dimension(arrayWidth * 50, arrayHeight * 50));
        setLayout(new GridLayout(arrayHeight, arrayWidth));
    }
}
