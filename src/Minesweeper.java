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
import javax.swing.WindowConstants;


public class Minesweeper extends JFrame{
    
    private BoardPanel boardPanel;
    private TopLabel flagsLabel, timeLabel;
    private RestartButton restartButton;
    private int numberOfUnusedFlags, remainingButtons;
    private final int boardSize =16, numberOfBombs =40;
    private final Buttons[][] buttons=new Buttons[boardSize][boardSize];
    private Timer timer;
    private final ImageIcon bombIcon=new ImageIcon(".\\src\\images\\bomb100px.png"),flagIcon=new ImageIcon(".\\src\\images\\flag100px.png");
    
    public static void main(String[] args) {
        new Minesweeper().setVisible(true);
    }

    public Minesweeper() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(900,1000);
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
       remainingButtons = boardSize * boardSize;
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
        for(int i = 0; i< boardSize; i++)
            for(int j = 0; j< boardSize; j++)
                boardPanel.add(buttons[i][j]=new Buttons(i,j));
    }
    
    private void createBombs() {
        Random rand = new Random(); 
        int bombs=0;
        while(bombs< numberOfBombs){
            int i=rand.nextInt(boardSize);
            int j=rand.nextInt(boardSize);
            if(!buttons[i][j].hasBomb){
                buttons[i][j].hasBomb = true;
                bombs++;
            }    
        }
    }

    private void countBombsAroundButtons() {
        for(int i = 0; i< boardSize; i++)
            for(int j = 0; j< boardSize; j++)
                for(int k=i-1; k<=i+1; k++)
                    for(int s=j-1; s<=j+1; s++)
                         if((k>=0&&s>=0&&k< boardSize &&s< boardSize)&&!(k==i&&s==j))
                             if(buttons[k][s].hasBomb)
                                 buttons[i][j].bombsAroundIt++;
    }
    
    private void setBombsEverywhere(){
        for(int i = 0; i< boardSize; i++)
            for(int j = 0; j< boardSize; j++)
                if(buttons[i][j].hasBomb &&!buttons[i][j].hasFlag)
                    buttons[i][j].setIcon(bombIcon);
    gameOver(restartButton.loseFace);
    }
    
    
    private void gameOver(ImageIcon icon) {
        for(int i = 0; i< boardSize; i++)
            for(int j = 0; j< boardSize; j++)
                buttons[i][j].removeMouseListener(buttons[i][j]);
        timer.cancel();
        restartButton.setIcon(icon);
    }

    
    private void recursion(Buttons button) {
        if(button.hasFlag)return;
        button.cancelButton();
        if(button.bombsAroundIt !=0) button.setText(String.valueOf(button.bombsAroundIt));
        else recursionForButtonsAround(button);
        if(--remainingButtons == numberOfBombs) gameOver(restartButton.winFace);//Win!
    }

    private void recursionForButtonsAround(Buttons button) {
        int i=button.i,j=button.j;
        for(int k=i-1; k<=i+1; k++){
            if(k<0||k == boardSize)continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s == boardSize)continue;
                if(!buttons[k][s].hasDoneRecursion)
                recursion(buttons[k][s]);
            }
        }
    }
    

            private class Buttons extends JButton implements MouseListener{
                
                private final int i,j;
                private int bombsAroundIt = 0;
                private boolean hasBomb = false, hasFlag = false, hasDoneRecursion = false;

                private Buttons(int i, int j) {
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
                    setFont(new Font("Arial",Font.BOLD,450/ boardSize));
                }  

                private void cancelButton() {
                    hasDoneRecursion =true;
                    setBackground(Color.LIGHT_GRAY);
                    removeMouseListener(this);
                }
            }
            
            
            private class TopLabel extends JLabel{
                private TopLabel(){
                    setPreferredSize(new Dimension(400,100));
                    setOpaque(true);
                    setFont(new Font("Arial", Font.PLAIN, 50));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setForeground(Color.RED);
                }   
            }
            
            private class BoardPanel extends JPanel{
                private BoardPanel() {
                    setPreferredSize(new Dimension(900,900));
                    setLayout(new GridLayout(boardSize, boardSize));
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
                private int secondsPassed =0;
                @Override
                public void run() {
                    timeLabel.setText(String.valueOf(secondsPassed++));
                }
            } 

  
}
