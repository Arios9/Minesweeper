import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartButton extends JButton implements ActionListener {

    public static final ImageIcon smileFace =new ImageIcon(".\\src\\images\\smileface.png");
    public static final ImageIcon pressedFace =new ImageIcon(".\\src\\images\\pressedface.png");
    public static final ImageIcon loseFace =new ImageIcon(".\\src\\images\\loseface.png");
    public static final ImageIcon winFace =new ImageIcon(".\\src\\images\\winface.png");

    public RestartButton(){
        setPreferredSize(new Dimension(100,100));
        addActionListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MinesweeperGame.timer.cancel();
        MinesweeperFrame.boardPanel.removeAll();
        MinesweeperGame.GameInstance().startNewGame();
        MinesweeperFrame.boardPanel.revalidate();
        MinesweeperFrame.boardPanel.repaint();
    }
}
