
import java.util.Random;
import java.util.Timer;
import javax.swing.ImageIcon;

public class MinesweeperGame {

    private static MinesweeperGame minesweeperGame = null;
    public static MinesweeperFrame minesweeperFrame;
    public static GameLevel gameLevel;
    public static Square[][] squares;
    public static Timer timer;
    public static int numberOfUnusedFlags, remainingButtons;

    public static void createNewGame(GameLevel gameLevel) throws Exception {
        if(minesweeperGame != null)
            throw new Exception();
        minesweeperGame = new MinesweeperGame(gameLevel);
        minesweeperFrame = new MinesweeperFrame();
        minesweeperGame.startNewGame();
    }

    public static MinesweeperGame GameInstance()   {
        return minesweeperGame;
    }

    public static void FinishCurrentGame(){
        minesweeperGame = null;
    }


    private MinesweeperGame(GameLevel gl) {
        gameLevel = gl;
        squares = new Square[gameLevel.getNumberOfSquaresInHeight()][gameLevel.getNumberOfSquaresInWidth()];
    }


    public void startNewGame() {
        initializeCountingVariables();
        MinesweeperFrame.boardPanel.setTheBoard();
        createBombs();
        countBombsAroundButtons();
        createTimer();
        setComponentsContent();
    }

    private void initializeCountingVariables() {
        numberOfUnusedFlags = gameLevel.getNumberOfBombs();
        remainingButtons = gameLevel.getNumberOfSquaresInHeight() * gameLevel.getNumberOfSquaresInWidth();
    }

    private void setComponentsContent() {
        MinesweeperFrame.restartButton.setIcon(RestartButton.smileFace);
        MinesweeperFrame.flagsLabel.setText(Integer.toString(numberOfUnusedFlags));
    }

    private void createTimer() {
        timer=new Timer();
        timer.schedule(new MyTimerTask(),0,1000);
    }
    
    private void createBombs() {
        Random rand = new Random();
        int bombs=0;
        while(bombs < gameLevel.getNumberOfBombs()){
            int i=rand.nextInt(gameLevel.getNumberOfSquaresInHeight());
            int j=rand.nextInt(gameLevel.getNumberOfSquaresInWidth());
            if(!squares[i][j].HasBomb()){
                squares[i][j].setHasBomb(true);
                bombs++;
            }    
        }
    }

    private void countBombsAroundButtons() {
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                for(int k=i-1; k<=i+1; k++)
                    for(int s=j-1; s<=j+1; s++)
                         if((k>=0&&s>=0&&k< gameLevel.getNumberOfSquaresInHeight() &&s< gameLevel.getNumberOfSquaresInWidth())&&!(k==i&&s==j))
                             if(squares[k][s].HasBomb())
                                 squares[i][j].incrementBombsAroundIt();
    }
    
    public void setBombsEverywhere(){
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                if(squares[i][j].HasBomb() && !squares[i][j].HasFlag())
                    squares[i][j].setBombIcon();
        gameOver(RestartButton.loseFace);
    }
    
    
    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                squares[i][j].removeMouseListener(squares[i][j]);
        timer.cancel();
        MinesweeperFrame.restartButton.setIcon(icon);
    }

    
    public void recursion(Square square) {
        if(square.HasFlag())return;
        square.cancelButton();
        if(square.getBombsAroundIt() !=0) square.setText(String.valueOf(square.getBombsAroundIt()));
        else recursionForButtonsAround(square);
        if(--remainingButtons == gameLevel.getNumberOfBombs()) gameOver(RestartButton.winFace);
    }

    private void recursionForButtonsAround(Square square) {
        int i=square.getI(),j=square.getJ();
        for(int k=i-1; k<=i+1; k++){
            if(k<0||k == gameLevel.getNumberOfSquaresInHeight())continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s == gameLevel.getNumberOfSquaresInWidth())continue;
                if(!squares[k][s].HasDoneRecursion())
                recursion(squares[k][s]);
            }
        }
    }

}
