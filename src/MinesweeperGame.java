import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class MinesweeperGame extends JFrame{
    
    private BoardPanel boardPanel;
    private TopLabel flagsLabel, timeLabel;
    private RestartButton restartButton;
    private final int height, width, numberOfSquaresInHeight, numberOfSquaresInWidth, numberOfBombs;
    private int numberOfUnusedFlags, remainingButtons;
    private final Square[][] squares;
    private Timer timer;
    private final ImageIcon bombIcon=new ImageIcon(".\\src\\images\\bomb100px.png"),flagIcon=new ImageIcon(".\\src\\images\\flag100px.png");
    

    public MinesweeperGame(int numberOfSquaresInHeight, int numberOfSquaresInWidth, int numberOfBombs) {
        this.numberOfSquaresInHeight = numberOfSquaresInHeight;
        this.numberOfSquaresInWidth = numberOfSquaresInWidth;
        this.numberOfBombs = numberOfBombs;
        squares = new Square[numberOfSquaresInHeight][numberOfSquaresInWidth];
        height = numberOfSquaresInHeight * 50;
        width = numberOfSquaresInWidth * 50;
        setFrame();
    }

    private void setFrame() {
        setResizable(false);
        setSize(width, height + 100);
        setLocationRelativeTo(null);
        setComponents();
        startNewGame();
    }

    private void setComponents() {
        add(boardPanel =new BoardPanel(),BorderLayout.PAGE_END);
        add(flagsLabel =new TopLabel(),BorderLayout.LINE_START);
        add(timeLabel =new TopLabel(),BorderLayout.LINE_END);
        add(restartButton =new RestartButton(),BorderLayout.CENTER);
    }

    private void startNewGame() {
       numberOfUnusedFlags = numberOfBombs;
       remainingButtons = numberOfSquaresInHeight * numberOfSquaresInWidth;
       setTheBoard();
       createBombs();
       countBombsAroundButtons();
       createTimer();
       setComponentsContent();
    }

    private void setComponentsContent() {
        restartButton.setIcon(restartButton.smileFace);
        flagsLabel.setText(Integer.toString(numberOfUnusedFlags));
    }

    private void createTimer() {
        timer=new Timer();
        timer.schedule(new MyTimerTask(),0,1000);
    }

    private void setTheBoard() {
        for(int i = 0; i< numberOfSquaresInHeight; i++)
            for(int j = 0; j< numberOfSquaresInWidth; j++)
                boardPanel.add(squares[i][j]=new Square(i,j));
    }
    
    private void createBombs() {
        Random rand = new Random();
        int bombs=0;
        while(bombs < numberOfBombs){
            int i=rand.nextInt(numberOfSquaresInHeight);
            int j=rand.nextInt(numberOfSquaresInWidth);
            if(!squares[i][j].hasBomb){
                squares[i][j].hasBomb = true;
                bombs++;
            }    
        }
    }

    private void countBombsAroundButtons() {
        for(int i = 0; i< numberOfSquaresInHeight; i++)
            for(int j = 0; j< numberOfSquaresInWidth; j++)
                for(int k=i-1; k<=i+1; k++)
                    for(int s=j-1; s<=j+1; s++)
                         if((k>=0&&s>=0&&k< numberOfSquaresInHeight &&s< numberOfSquaresInWidth)&&!(k==i&&s==j))
                             if(squares[k][s].hasBomb)
                                 squares[i][j].bombsAroundIt++;
    }
    
    private void setBombsEverywhere(){
        for(int i = 0; i< numberOfSquaresInHeight; i++)
            for(int j = 0; j< numberOfSquaresInWidth; j++)
                if(squares[i][j].hasBomb && !squares[i][j].hasFlag)
                    squares[i][j].setIcon(bombIcon);
        gameOver(restartButton.loseFace);
    }
    
    
    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< numberOfSquaresInHeight; i++)
            for(int j = 0; j< numberOfSquaresInWidth; j++)
                squares[i][j].removeMouseListener(squares[i][j]);
        timer.cancel();
        restartButton.setIcon(icon);
    }

    
    private void recursion(Square square) {
        if(square.hasFlag)return;
        square.cancelButton();
        if(square.bombsAroundIt !=0) square.setText(String.valueOf(square.bombsAroundIt));
        else recursionForButtonsAround(square);
        if(--remainingButtons == numberOfBombs) gameOver(restartButton.winFace);//Win!
    }

    private void recursionForButtonsAround(Square square) {
        int i=square.i,j=square.j;
        for(int k=i-1; k<=i+1; k++){
            if(k<0||k == numberOfSquaresInHeight)continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s == numberOfSquaresInWidth)continue;
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
                                    flagsLabel.setText(String.valueOf(--numberOfUnusedFlags));
                                }else{
                                    setIcon(null);
                                    hasFlag = false;
                                    flagsLabel.setText(String.valueOf(++numberOfUnusedFlags));
                                }
                            }
                            if(SwingUtilities.isLeftMouseButton(me)){
                                if(!hasFlag)
                                if(hasBomb) setBombsEverywhere();
                                else recursion(this); 
                            }         
                }
                @Override public void mousePressed(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))
                    restartButton.setIcon(restartButton.pressedFace);}
                @Override public void mouseReleased(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))
                    restartButton.setIcon(restartButton.smileFace);}
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
            
            
            private class TopLabel extends JLabel{
                private TopLabel(){
                    setPreferredSize(new Dimension((width - 100)/2,100));
                    setOpaque(true);
                    setFont(new Font("Arial", Font.PLAIN, 50));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setForeground(Color.RED);
                }   
            }
            
            private class BoardPanel extends JPanel{
                private BoardPanel() {
                    setPreferredSize(new Dimension(width, height));
                    setLayout(new GridLayout(numberOfSquaresInHeight, numberOfSquaresInWidth));
                }   
            }
            
            private class RestartButton extends JButton implements ActionListener{
                private final ImageIcon smileFace =new ImageIcon(".\\src\\images\\smileface.png");
                private final ImageIcon pressedFace =new ImageIcon(".\\src\\images\\pressedface.png");
                private final ImageIcon loseFace =new ImageIcon(".\\src\\images\\loseface.png");
                private final ImageIcon winFace =new ImageIcon(".\\src\\images\\winface.png");
                
                private RestartButton(){    
                    setPreferredSize(new Dimension(100,100));
                    addActionListener(this);
                    setBackground(Color.WHITE);
                }   
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer.cancel();
                    boardPanel.removeAll();
                    startNewGame();
                    boardPanel.revalidate();
                    boardPanel.repaint();
                }
            }
            
            private class MyTimerTask extends TimerTask{
                private int secondsPassed = 0;
                @Override
                public void run() {
                    timeLabel.setText(String.valueOf(secondsPassed++));
                }
            } 

  
}
