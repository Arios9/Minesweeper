package mainCode;

import javax.swing.*;
import java.awt.*;

import static mainCode.MinesweeperFrame.frameWidth;
import static mainCode.MinesweeperFrame.headerHeight;
import static mainCode.RestartButton.buttonWidth;

public class HeaderLabel extends JLabel {

    public HeaderLabel() {
        setPreferredSize(new Dimension((frameWidth - buttonWidth)/2,headerHeight));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 50));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
