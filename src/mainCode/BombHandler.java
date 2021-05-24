package mainCode;

import MinesweeperFramePackage.RestartButton;
import MinesweeperFramePackage.Square;

import java.util.*;

import static mainCode.MinesweeperGame.*;

public class BombHandler {

    private List<Square> bombSquares;

    public BombHandler() {
        bombSquares = new ArrayList<>();
    }

    public void createBombs() {
        Random rand = new Random();
        int bombs=0;
        while(bombs < numberOfBombs){
            int i=rand.nextInt(arrayHeight);
            int j=rand.nextInt(arrayWidth);
            if(!squares[i][j].HasBomb()){
                squares[i][j].setHasBomb(true);
                bombSquares.add(squares[i][j]);
                bombs++;
            }
        }
    }

    private static final int milliseconds = 50;

    public void setBombsEverywhere(Square clickedSquare){
        clickedSquare.setBombIcon();
        bombSquares.remove(clickedSquare);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(bombSquares.isEmpty()){
                    cancel();
                }else{
                    Square square = bombSquares.remove(0);
                    square.setBombIcon();
                }
            }
        }, milliseconds, milliseconds);
    }
}
