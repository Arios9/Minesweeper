
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


public class MinesweeperGame {

    private final MinesweeperFrame minesweeperFrame;
    private final GameLevel gameLevel;
    private final Square[][] squares;
    private Timer timer;
    private int numberOfUnusedFlags, remainingButtons;
    private final ImageIcon bombIcon=new ImageIcon(".\\src\\images\\bomb100px.png"),flagIcon=new ImageIcon(".\\src\\images\\flag100px.png");


    public MinesweeperGame(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        squares = new Square[gameLevel.getNumberOfSquaresInHeight()][gameLevel.getNumberOfSquaresInWidth()];
        minesweeperFrame = new MinesweeperFrame(this);
        startNewGame();
    }

    public void startNewGame() {
       initializeCountingVariables();
       setTheBoard();
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
        minesweeperFrame.getRestartButton().setIcon(RestartButton.smileFace);
        minesweeperFrame.getFlagsLabel().setText(Integer.toString(numberOfUnusedFlags));
    }

    private void createTimer() {
        timer=new Timer();
        timer.schedule(new MyTimerTask(this),0,1000);
    }

    private void setTheBoard() {
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                minesweeperFrame.getBoardPanel().add(squares[i][j]=new Square(i,j));
    }
    
    private void createBombs() {
        Random rand = new Random();
        int bombs=0;
        while(bombs < gameLevel.getNumberOfBombs()){
            int i=rand.nextInt(gameLevel.getNumberOfSquaresInHeight());
            int j=rand.nextInt(gameLevel.getNumberOfSquaresInWidth());
            if(!squares[i][j].hasBomb){
                squares[i][j].hasBomb = true;
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
                             if(squares[k][s].hasBomb)
                                 squares[i][j].bombsAroundIt++;
    }
    
    private void setBombsEverywhere(){
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                if(squares[i][j].hasBomb && !squares[i][j].hasFlag)
                    squares[i][j].setIcon(bombIcon);
        gameOver(RestartButton.loseFace);
    }
    
    
    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< gameLevel.getNumberOfSquaresInHeight(); i++)
            for(int j = 0; j< gameLevel.getNumberOfSquaresInWidth(); j++)
                squares[i][j].removeMouseListener(squares[i][j]);
        timer.cancel();
        minesweeperFrame.getRestartButton().setIcon(icon);
    }

    
    private void recursion(Square square) {
        if(square.hasFlag)return;
        square.cancelButton();
        if(square.bombsAroundIt !=0) square.setText(String.valueOf(square.bombsAroundIt));
        else recursionForButtonsAround(square);
        if(--remainingButtons == gameLevel.getNumberOfBombs()) gameOver(RestartButton.winFace);//Win!
    }

    private void recursionForButtonsAround(Square square) {
        int i=square.i,j=square.j;
        for(int k=i-1; k<=i+1; k++){
            if(k<0||k == gameLevel.getNumberOfSquaresInHeight())continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s == gameLevel.getNumberOfSquaresInWidth())continue;
                if(!squares[k][s].hasDoneRecursion)
                recursion(squares[k][s]);
            }
        }
    }
    

            private class Square extends JButton implements MouseListener{

                private final int i,j;
                private int bombsAroundIt = 0;
                private boolean hasBomb = false, hasFlag = false, hasDoneRecursion = false;

                private Square(int i, int j) {
                    this.i=i; this.j=j;
                    addMouseListener(this);
                    setBorder(BorderFactory.createLineBorder(Color.black));
                    setBackground(Color.GRAY);
                }

                @Override public void mouseClicked(MouseEvent me) {
                            if(SwingUtilities.isRightMouseButton(me)){
                                if(!hasFlag){
                                    setIcon(flagIcon);
                                    hasFlag = true;
                                    minesweeperFrame.getFlagsLabel().setText(String.valueOf(--numberOfUnusedFlags));
                                }else{
                                    setIcon(null);
                                    hasFlag = false;
                                    minesweeperFrame.getFlagsLabel().setText(String.valueOf(++numberOfUnusedFlags));
                                }
                            }
                            if(SwingUtilities.isLeftMouseButton(me)){
                                if(!hasFlag)
                                if(hasBomb) setBombsEverywhere();
                                else recursion(this); 
                            }         
                }
                @Override public void mousePressed(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))
                    minesweeperFrame.getRestartButton().setIcon(RestartButton.pressedFace);}
                @Override public void mouseReleased(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))
                    minesweeperFrame.getRestartButton().setIcon(RestartButton.smileFace);}
                @Override public void mouseEntered(MouseEvent me) {setBackground(Color.LIGHT_GRAY);}
                @Override public void mouseExited(MouseEvent me) {setBackground(Color.GRAY);}

                private Color getColor() {
                    return switch (bombsAroundIt) {
                        case 1 -> Color.BLUE;
                        case 2 -> Color.GREEN;
                        case 3 -> Color.RED;
                        case 4 -> Color.ORANGE;
                        default -> Color.BLACK;
                    };
                }
                           
                @Override
                public void setText(String string){
                    super.setText(string);
                    setForeground(getColor());
                    setFont(new Font("Arial",Font.BOLD,25));
                }  

                private void cancelButton() {
                    hasDoneRecursion = true;
                    setBackground(Color.LIGHT_GRAY);
                    removeMouseListener(this);
                }
            }


    public MinesweeperFrame getMinesweeperFrame() {
        return minesweeperFrame;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public Timer getTimer() {
        return timer;
    }
}
