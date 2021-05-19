package mainCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static mainCode.MinesweeperFrame.*;
import static mainCode.MinesweeperGame.*;


public class Square extends JButton implements MouseListener {

    private final ImageIcon bombIcon=new ImageIcon(".\\src\\images\\bomb100px.png");
    private final ImageIcon flagIcon=new ImageIcon(".\\src\\images\\flag100px.png");


    private final int i,j;
    private int bombsAroundIt = 0;
    private boolean hasBomb = false, hasFlag = false, hasDoneRecursion = false;

    public Square(int i, int j) {
        this.i=i; this.j=j;
        addMouseListener(this);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.GRAY);
    }

    @Override public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isRightMouseButton(me)){
            if(hasFlag)
                removeFlag();
            else
                addFlag();
            flagsLabel.reFresh(numberOfUnusedFlags);
        }
        if(SwingUtilities.isLeftMouseButton(me)){
            if(hasFlag) return;
            if(hasBomb)
                GameInstance().setBombsEverywhere();
            else
                GameInstance().recursion(this);
        }
    }

    @Override public void mousePressed(MouseEvent me) {
        if(SwingUtilities.isLeftMouseButton(me))
        restartButton.setIcon(RestartButton.pressedFace);
    }
    @Override public void mouseReleased(MouseEvent me) {
        if(SwingUtilities.isLeftMouseButton(me))
        restartButton.setIcon(RestartButton.smileFace);
    }
    @Override public void mouseEntered(MouseEvent me) {}
    @Override public void mouseExited(MouseEvent me) {}

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

    public void incrementBombsAroundIt(){
        bombsAroundIt++;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getBombsAroundIt() {
        return bombsAroundIt;
    }

    public boolean HasBomb() {
        return hasBomb;
    }

    public boolean HasFlag() {
        return hasFlag;
    }

    public boolean HasDoneRecursion() {
        return hasDoneRecursion;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public void setBombIcon() {
        setIcon(bombIcon);
    }

    public boolean HasBombsAroundIt(){
        return bombsAroundIt != 0;
    }

    public void setNumberText() {
        setText(String.valueOf(bombsAroundIt));
    }

    private void addFlag() {
        setIcon(flagIcon);
        hasFlag = true;
        numberOfUnusedFlags--;
    }

    private void removeFlag() {
        setIcon(null);
        hasFlag = false;
        numberOfUnusedFlags++;
    }

    public void cancelIt() {
        hasDoneRecursion = true;
        setBackground(Color.LIGHT_GRAY);
        removeMouseListener(this);
        remainingButtons--;
    }
}
