import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartButton extends JButton implements ActionListener {

    public static final ImageIcon smileFace =new ImageIcon(".\\src\\images\\smileface.png");
    public static final ImageIcon pressedFace =new ImageIcon(".\\src\\images\\pressedface.png");
    public static final ImageIcon loseFace =new ImageIcon(".\\src\\images\\loseface.png");
    public static final ImageIcon winFace =new ImageIcon(".\\src\\images\\winface.png");

    private MinesweeperGame minesweeperGame;

    public RestartButton(MinesweeperGame minesweeperGame){
        this.minesweeperGame = minesweeperGame;
        setPreferredSize(new Dimension(100,100));
        addActionListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        minesweeperGame.getTimer().cancel();
        minesweeperGame.getMinesweeperFrame().getBoardPanel().removeAll();
        minesweeperGame.startNewGame();
        minesweeperGame.getMinesweeperFrame().getBoardPanel().revalidate();
        minesweeperGame.getMinesweeperFrame().getBoardPanel().repaint();
    }
}
