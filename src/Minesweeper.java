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
    
    
    private BoardPanel board_panel;
    private TopLabel flagslabel,timelabel;
    private RestartButton restartbutton;
    private int number_unused_flags,remaining_buttons;
    private  int board_size=9,number_of_bombs=10;
    private Buttons buttons[][]=new Buttons[board_size][board_size];
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
        start_new_game();
    }
    
    private void setComponents() {
        add(board_panel=new BoardPanel(),BorderLayout.PAGE_END);
        add(flagslabel=new TopLabel(),BorderLayout.LINE_START); 
        add(timelabel=new TopLabel(),BorderLayout.LINE_END); 
        add(restartbutton=new RestartButton(),BorderLayout.CENTER);        
    }

    private void start_new_game() {
       number_unused_flags=number_of_bombs;
       remaining_buttons=board_size*board_size;
       flagslabel.setText(Integer.toString(number_unused_flags));
       set_the_board();
       create_bombs();
       count_bombs_around_buttons();
       timer=new Timer();
       timer.schedule(new MyTimerTask(),0,1000);
       restartbutton.setIcon(restartbutton.smileface);
    }
    
    private void set_the_board() {
        for(int i=0; i<board_size; i++)
            for(int j=0; j<board_size; j++)
                board_panel.add(buttons[i][j]=new Buttons(i,j));
    }
    
    private void create_bombs() {
        Random rand = new Random(); 
        int bombs=0;
        while(bombs<number_of_bombs){
            int i=rand.nextInt(board_size);
            int j=rand.nextInt(board_size);
            if(!buttons[i][j].hasbomb){
            buttons[i][j].hasbomb=true;
            bombs++;
            }    
        }
    }

    private void count_bombs_around_buttons() {
        for(int i=0; i<board_size; i++)
            for(int j=0; j<board_size; j++)
                for(int k=i-1; k<=i+1; k++)
                    for(int s=j-1; s<=j+1; s++)
                         if((k>=0&&s>=0&&k<board_size&&s<board_size)&&!(k==i&&s==j))
                             if(buttons[k][s].hasbomb)
                                 buttons[i][j].bombsaroundme++;        
    }
    
    private void set_bombs_everywhere(){
        for(int i=0; i<board_size; i++)
            for(int j=0; j<board_size; j++)
                if(buttons[i][j].hasbomb&&!buttons[i][j].hasflag)
                    buttons[i][j].setIcon(bombIcon);
    setgameover(restartbutton.loseface);     
    }
    
    
    private void setgameover(ImageIcon icon) {
        for(int i=0; i<board_size; i++)
            for(int j=0; j<board_size; j++)
                buttons[i][j].removeMouseListener(buttons[i][j]);
        timer.cancel();
        restartbutton.setIcon(icon);
    }

    
    private void recursion(Buttons button) {
        if(button.hasflag)return;
        button.cancel_it();
        if(button.bombsaroundme!=0) button.setText(String.valueOf(button.bombsaroundme)); 
        else recursion_for_buttons_around(button);
        if(--remaining_buttons==number_of_bombs)setgameover(restartbutton.winface);//Win!
    }

    private void recursion_for_buttons_around(Buttons button) {
        int i=button.i,j=button.j;
        for(int k=i-1; k<=i+1; k++){
            if(k<0||k==board_size)continue;
            for(int s=j-1; s<=j+1; s++){
                if(s<0||s==board_size)continue;
                if(!buttons[k][s].done_recursion)
                recursion(buttons[k][s]);
            }
        }
    }
    

            private class Buttons extends JButton implements MouseListener{
                
                private final int i,j;
                private int bombsaroundme=0;
                private boolean hasbomb=false,hasflag=false,done_recursion=false;

                private Buttons(int i, int j) {
                    this.i=i;this.j=j;
                    addMouseListener(this);
                    setBorder(BorderFactory.createLineBorder(Color.black));
                    setBackground(Color.GRAY);
                }

                @Override public void mouseClicked(MouseEvent me) {
                            if(SwingUtilities.isRightMouseButton(me)){
                                if(!hasflag){   
                                setIcon(flagIcon);
                                hasflag=true;
                                flagslabel.setText(String.valueOf(--number_unused_flags));
                                }else{
                                setIcon(null);
                                hasflag=false; 
                                flagslabel.setText(String.valueOf(++number_unused_flags));
                                }
                            }
                            if(SwingUtilities.isLeftMouseButton(me)){
                                if(!hasflag)
                                if(hasbomb)set_bombs_everywhere();   
                                else recursion(this); 
                            }         
                }
                @Override public void mousePressed(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))restartbutton.setIcon(restartbutton.pressedface);}
                @Override public void mouseReleased(MouseEvent me) {if(SwingUtilities.isLeftMouseButton(me))restartbutton.setIcon(restartbutton.smileface);}
                @Override public void mouseEntered(MouseEvent me) {setBackground(Color.LIGHT_GRAY);}
                @Override public void mouseExited(MouseEvent me) {setBackground(Color.GRAY);}

                private Color getMycolor() {
                switch (bombsaroundme) {
                    case 1: return Color.BLUE;
                    case 2: return Color.GREEN;
                    case 3: return Color.RED;
                    case 4: return Color.ORANGE;
                    default: return Color.BLACK;
                }}
                           
                @Override
                public void setText(String string){
                    super.setText(string);
                    setForeground(getMycolor());
                    setFont(new Font("Arial",Font.BOLD,450/board_size));
                }  

                private void cancel_it() {
                    done_recursion=true;
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
                    setLayout(new GridLayout(board_size,board_size));
                }   
            }
            
            private class RestartButton extends JButton implements ActionListener{
                private final ImageIcon smileface=new ImageIcon(".\\src\\images\\smileface.png");
                private final ImageIcon pressedface=new ImageIcon(".\\src\\images\\pressedface.png");
                private final ImageIcon loseface=new ImageIcon(".\\src\\images\\loseface.png");
                private final ImageIcon winface=new ImageIcon(".\\src\\images\\winface.png");
                
                private RestartButton(){    
                    setPreferredSize(new Dimension(100,100));
                    addActionListener(this);
                    setBackground(Color.WHITE);
                }   
                @Override
                public void actionPerformed(ActionEvent ae) {
                    timer.cancel();
                    board_panel.removeAll();
                    start_new_game();
                    board_panel.revalidate();
                    board_panel.repaint();
                }
            }
            
            private class MyTimerTask extends TimerTask{
                private int seconds_count=0; 
                @Override
                public void run() {
                    timelabel.setText(String.valueOf(seconds_count++));
                }
            } 

  
}
