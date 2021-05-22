package mainMenuPackage;

import javax.swing.*;
import java.awt.*;

import static mainMenuPackage.MainMenu.mainFrameWidth;

public class TitleLabel extends JLabel {
    public TitleLabel(String text) {
        super(text);
        setPreferredSize(new Dimension(mainFrameWidth, 80));
        setFont(new Font("Arial",Font.BOLD,40));
        setHorizontalAlignment(CENTER);
    }
}
