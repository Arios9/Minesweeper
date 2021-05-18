import javax.swing.*;
import java.awt.*;

public class HeaderLabel extends JLabel {

    public HeaderLabel(int frameWidth) {
        setPreferredSize(new Dimension((frameWidth - 100)/2,100));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 50));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
