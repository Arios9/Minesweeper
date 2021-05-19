import javax.swing.*;
import java.awt.*;

public class HeaderLabel extends JLabel {

    public HeaderLabel() {
        setPreferredSize(new Dimension((MinesweeperGame.minesweeperFrame.getWidth() - 100)/2,100));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 50));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
