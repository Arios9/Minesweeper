package mainCode;

import MinesweeperFramePackage.MinesweeperFrame;
import MinesweeperFramePackage.RestartButton;
import MinesweeperFramePackage.Square;
import mainMenuPackage.GameLevel;

import java.util.Timer;
import java.util.function.Consumer;
import javax.swing.ImageIcon;

import static MinesweeperFramePackage.MinesweeperFrame.*;

public class MinesweeperGame {

    private static MinesweeperGame minesweeperGame = null;
    public static MinesweeperFrame minesweeperFrame;
    public static GameLevel currentGameLevel;
    public static Square[][] squares;
    public static Timer timer;
    public static int numberOfUnusedFlags, remainingButtons;
    public static int arrayHeight, arrayWidth ,numberOfBombs;
    public static BombHandler bombHandler;
    public static ScoreTimerTask scoreTimerTask;

    public static void createNewGame(GameLevel gameLevel) throws GameInProgressException {
        if(minesweeperGame != null)
            throw new GameInProgressException();
        minesweeperGame = new MinesweeperGame(gameLevel);
        setFrame();
        minesweeperGame.startNewGame();
    }

    private static void setFrame() {
        minesweeperFrame = new MinesweeperFrame();
    }

    public static MinesweeperGame GameInstance()   {
        return minesweeperGame;
    }

    public static void FinishCurrentGame(){
        minesweeperGame = null;
        timer.cancel();
    }

    private MinesweeperGame(GameLevel gameLevel) {
        currentGameLevel = gameLevel;
        numberOfBombs = currentGameLevel.getNumberOfBombs();
        arrayHeight = currentGameLevel.getNumberOfSquaresInHeight();
        arrayWidth = currentGameLevel.getNumberOfSquaresInWidth();
        squares = new Square[arrayHeight][arrayWidth];
    }

    public void startNewGame() {
        boardPanel.setTheBoard();
        createBombsAndHandler();
        countBombsAroundButtons();
        createTimer();
        initializeCountingVariables();
        setComponentsContent();
    }

    private void createBombsAndHandler() {
        bombHandler = new BombHandler();
        bombHandler.createBombs();
    }

    private void initializeCountingVariables() {
        numberOfUnusedFlags = numberOfBombs;
        remainingButtons = arrayHeight * arrayWidth;
    }

    private void setComponentsContent() {
        restartButton.setIcon(RestartButton.smileFace);
        flagsLabel.setText(Integer.toString(numberOfUnusedFlags));
    }

    private void createTimer() {
        timer=new Timer();
        scoreTimerTask = new ScoreTimerTask();
        timer.schedule(scoreTimerTask,0,1000);
    }

    private void countBombsAroundButtons() {
        loopTheArray(Square::countBombsAroundButtonIt);
    }

    public void gameOver(ImageIcon icon) {
        scoreTimerTask.cancel();
        restartButton.setIcon(icon);
        loopTheArray(square -> square.removeMouseListener(square));
    }

    public void loopTheArray(Consumer<Square> consumer){
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                consumer.accept(squares[i][j]);
    }

    public void checkForWin() {
        if(winExists()){
            gameOver(RestartButton.winFace);
            HighScore.checkForHighScore();
        }
    }

    private boolean winExists() {
        return remainingButtons == numberOfBombs;
    }

}


