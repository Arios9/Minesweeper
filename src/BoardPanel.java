import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel {

    public BoardPanel(GameLevel gameLevel) {
        int boardHeight = gameLevel.getNumberOfSquaresInHeight() * 50;
        int boardWidth = gameLevel.getNumberOfSquaresInWidth() * 50;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setLayout(new GridLayout(gameLevel.getNumberOfSquaresInHeight(), gameLevel.getNumberOfSquaresInWidth()));
    }

}
