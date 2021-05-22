package mainMenuPackage;

import javax.swing.*;
import java.awt.*;

public class RecordLabel extends JLabel {
    public RecordLabel(String text) {
        super(text);
        setFont(new Font("Arial",Font.BOLD,20));
        setPreferredSize(new Dimension(150 , 60));
        setHorizontalAlignment(CENTER);
    }
}
