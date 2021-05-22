package mainMenuPackage;

import mainCode.GameInProgressException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mainMenuPackage.MainMenu.mainMenu;
import static mainCode.MinesweeperGame.createNewGame;

public class GameLevelButton extends JButton implements ActionListener {

    GameLevel gameLevel;

    public GameLevelButton(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(150 , 60));
        setText(gameLevel.getLevelText());
        addActionListener(this);
    }

    @Override
    public void setText(String string){
        super.setText(string);
        setForeground(Color.WHITE);
        setFont(new Font("Arial",Font.BOLD,30));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            createNewGame(gameLevel);
        } catch (GameInProgressException exception) {
            showErrorMessage(exception.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                mainMenu,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
