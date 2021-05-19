package mainCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mainCode.MinesweeperFrame.boardPanel;
import static mainCode.MinesweeperGame.GameInstance;
import static mainCode.MinesweeperGame.timer;


public class RestartButton extends JButton implements ActionListener {

    public static final int buttonWidth = 100;

    public static final ImageIcon smileFace =new ImageIcon(".\\src\\images\\smileface.png");
    public static final ImageIcon pressedFace =new ImageIcon(".\\src\\images\\pressedface.png");
    public static final ImageIcon loseFace =new ImageIcon(".\\src\\images\\loseface.png");
    public static final ImageIcon winFace =new ImageIcon(".\\src\\images\\winface.png");

    public RestartButton(){
        setPreferredSize(new Dimension(buttonWidth, MinesweeperFrame.headerHeight));
        addActionListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.cancel();
        boardPanel.removeAll();
        GameInstance().startNewGame();
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
