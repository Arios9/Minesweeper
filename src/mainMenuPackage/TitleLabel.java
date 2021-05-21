package mainMenuPackage;

import java.awt.*;

import static mainMenuPackage.MainMenu.mainFrameWidth;

public class TitleLabel extends Label {
    public TitleLabel() {
        setText("Minesweeper");
        setPreferredSize(new Dimension(mainFrameWidth, 80));
        setFont(new Font("Arial",Font.BOLD,40));
        setAlignment(Label.CENTER);
    }
}
