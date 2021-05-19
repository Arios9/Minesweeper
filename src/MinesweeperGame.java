
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
        minesweeperFrame.setComponents();
    }

    public static MinesweeperGame GameInstance()   {
        return minesweeperGame;
    }

    public static void FinishCurrentGame(){
        minesweeperGame = null;
    }

    private MinesweeperGame(GameLevel gl) {
        gameLevel = gl;
        arrayHeight = gameLevel.getNumberOfSquaresInHeight();
        arrayWidth = gameLevel.getNumberOfSquaresInWidth();
        squares = new Square[arrayHeight][arrayWidth];
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
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                for(int k=i-1; k<=i+1; k++)
                    for(int s=j-1; s<=j+1; s++)
                         if((k>=0&&s>=0&&k< arrayHeight &&s< arrayWidth)&&!(k==i&&s==j))
                             if(squares[k][s].HasBomb())
                                 squares[i][j].incrementBombsAroundIt();
    }
    
    public void setBombsEverywhere(){
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
                if(squares[i][j].HasBomb() && !squares[i][j].HasFlag())
                    squares[i][j].setBombIcon();
        gameOver(RestartButton.loseFace);
    }
    
    
    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< arrayHeight; i++)
            for(int j = 0; j< arrayWidth; j++)
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
            if(k<0||k == arrayHeight)continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s == arrayWidth)continue;
                if(!squares[k][s].HasDoneRecursion())
                recursion(squares[k][s]);
            }
        }
    }

}
