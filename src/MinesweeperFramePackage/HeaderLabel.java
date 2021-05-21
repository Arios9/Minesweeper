package MinesweeperFramePackage;

import javax.swing.*;
import java.awt.*;

import static MinesweeperFramePackage.MinesweeperFrame.*;
import static MinesweeperFramePackage.RestartButton.buttonWidth;

public class HeaderLabel extends JLabel {

    public HeaderLabel() {
        setPreferredSize(new Dimension((frameWidth - buttonWidth)/2,headerHeight));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 50));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void reFresh(int number) {
        setText(String.valueOf(number));
    }
}
