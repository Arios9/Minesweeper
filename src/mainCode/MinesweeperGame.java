package mainCode;

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
            int i=rand.nextInt(currentGameLevel.getNumberOfSquaresInHeight());
            int j=rand.nextInt(currentGameLevel.getNumberOfSquaresInWidth());
            if(!squares[i][j].HasBomb()){
                squares[i][j].setHasBomb(true);
                bombs++;
            }    
        }
    }

    private void countBombsAroundButtons() {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                countBombsAroundButton(squares[i][j]);
    }

    private void countBombsAroundButton(Square square) {
        int i=square.getI(), j=square.getJ();
        for(int a=i-1; a<=i+1 && a<arrayHeight; a++){
            if(a<0)continue;
            for(int b=j-1; b<=j+1 && b<arrayWidth; b++){
                if(b<0)continue;
                if(squares[a][b].equals(square))continue;
                if(squares[a][b].HasBomb())
                    squares[i][j].incrementBombsAroundIt();
            }
        }
    }

    public void setBombsEverywhere(){
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                if(squares[i][j].HasBomb())
                    squares[i][j].setBombIcon();
        gameOver(RestartButton.loseFace);
    }

    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                squares[i][j].removeMouseListener(squares[i][j]);
        timer.cancel();
        restartButton.setIcon(icon);
    }

    public void checkForWin() {
        if(remainingButtons == currentGameLevel.getNumberOfBombs()){
            gameOver(RestartButton.winFace);
            HighScore.checkForHighScore();
        }
    }

    
    public void recursion(Square square) {
        if(square.canDoRecursion()){
            square.cancelIt();
            if(square.HasBombsAroundIt())
                square.setNumberText();
            else
                recursionForButtonsAround(square);
        }
    }

    private void recursionForButtonsAround(Square square) {
        int i=square.getI(), j=square.getJ();
        for(int a=i-1; a<=i+1 && a<arrayHeight; a++){
            if(a<0)continue;
            for(int b=j-1; b<=j+1 && b<arrayWidth; b++){
                if(b<0)continue;
                if(squares[a][b].equals(square))continue;
                recursion(squares[a][b]);
            }
        }
    }


}
