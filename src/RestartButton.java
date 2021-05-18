import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartButton extends JButton implements ActionListener {

    public final ImageIcon smileFace =new ImageIcon(".\\src\\images\\smileface.png");
    public final ImageIcon pressedFace =new ImageIcon(".\\src\\images\\pressedface.png");
    public final ImageIcon loseFace =new ImageIcon(".\\src\\images\\loseface.png");
    public final ImageIcon winFace =new ImageIcon(".\\src\\images\\winface.png");

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
        minesweeperGame.getBoardPanel().removeAll();
        minesweeperGame.startNewGame();
        minesweeperGame.getBoardPanel().revalidate();
        minesweeperGame.getBoardPanel().repaint();
    }
}
