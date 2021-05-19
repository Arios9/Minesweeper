import javax.swing.*;
import java.awt.*;

public class HeaderLabel extends JLabel {

    public HeaderLabel() {
        setPreferredSize(new Dimension((MinesweeperFrame.frameWidth - RestartButton.width)/2,MinesweeperFrame.headerHeight));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 50));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
