import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLevelButton extends JButton implements ActionListener {

    GameLevel gameLevel;

    public GameLevelButton(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        setBackground(Color.BLACK);
        setText(gameLevel.getLevelText());
        addActionListener(this);
    }

    @Override
    public void setText(String string){
        super.setText(string);
        setForeground(Color.WHITE);
        setFont(new Font("Arial",Font.BOLD,40));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MinesweeperGame.createNewGame(gameLevel);
        } catch (GameInProgressException exception) {
            showErrorMessage(exception.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                MainMenu.mainMenu,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
