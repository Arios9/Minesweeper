package mainCode;

import MinesweeperFramePackage.ArrayLoop;
import MinesweeperFramePackage.MinesweeperFrame;
import MinesweeperFramePackage.RestartButton;
import MinesweeperFramePackage.Square;
import mainMenuPackage.GameLevel;

import java.util.Random;
import java.util.Timer;
import javax.swing.ImageIcon;

import static MinesweeperFramePackage.MinesweeperFrame.*;

public class MinesweeperGame {

    private static MinesweeperGame minesweeperGame = null;
    public static MinesweeperFrame minesweeperFrame;
    public static GameLevel currentGameLevel;
    public static Square[][] squares;
    public static Timer timer;
    public static int numberOfUnusedFlags, remainingButtons;
    public static int arrayHeight, arrayWidth;

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

    private MinesweeperGame(GameLevel gl) {
        currentGameLevel = gl;
        arrayHeight = currentGameLevel.getNumberOfSquaresInHeight();
        arrayWidth = currentGameLevel.getNumberOfSquaresInWidth();
        squares = new Square[arrayHeight][arrayWidth];
    }


    public void startNewGame() {
        boardPanel.setTheBoard();
        createBombs();
        countBombsAroundButtons();
        createTimer();
        initializeCountingVariables();
        setComponentsContent();
    }

    private void initializeCountingVariables() {
        numberOfUnusedFlags = currentGameLevel.getNumberOfBombs();
        remainingButtons = currentGameLevel.getNumberOfSquaresInHeight() * currentGameLevel.getNumberOfSquaresInWidth();
    }

    private void setComponentsContent() {
        restartButton.setIcon(RestartButton.smileFace);
        flagsLabel.setText(Integer.toString(numberOfUnusedFlags));
    }

    private void createTimer() {
        timer=new Timer();
        timer.schedule(new MyTimerTask(),0,1000);
    }
    
    private void createBombs() {
        Random rand = new Random();
        int bombs=0;
        while(bombs < currentGameLevel.getNumberOfBombs()){
            int i=rand.nextInt(arrayHeight);
            int j=rand.nextInt(arrayWidth);
            if(!squares[i][j].HasBomb()){
                squares[i][j].setHasBomb(true);
                bombs++;
            }    
        }
    }

    private void countBombsAroundButtons() {
        loopTheArray((i, j) -> squares[i][j].countBombsAroundButtonIt());
    }

    public void setBombsEverywhere(){
        loopTheArray((i, j) -> squares[i][j].setBombIcon());
        gameOver(RestartButton.loseFace);
    }

    private void gameOver(ImageIcon icon) {
        loopTheArray((i, j) -> squares[i][j].removeMouseListener(squares[i][j]));
        timer.cancel();
        restartButton.setIcon(icon);
    }

    private void loopTheArray(ArrayLoop arrayLoop){
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                arrayLoop.method(i,j);
    }

    public void checkForWin() {
        if(remainingButtons == currentGameLevel.getNumberOfBombs()){
            gameOver(RestartButton.winFace);
            HighScore.checkForHighScore();
        }
    }


}
