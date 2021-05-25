package mainCode;

import MinesweeperFramePackage.Square;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
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

    private static final int milliseconds = 100;

    public void setBombsEverywhere(Square clickedSquare){
        bombSquares.remove(clickedSquare);
        bombExplosion(clickedSquare);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(bombSquares.isEmpty()){
                    cancel();
                }else{
                    Square square = bombSquares.remove(0);
                    if(!square.HasFlag())
                    bombExplosion(square);
                }
            }
        }, milliseconds, milliseconds);
    }

    private void bombExplosion(Square square) {
        square.setBombIcon();
        playExplosionAudio();
    }

    private static final String BOOM_FILE_PATH = "src/files/boom.wav";
    private static final File audioFile = new File(BOOM_FILE_PATH);

    private void playExplosionAudio() {
        AudioManager.playAudio(audioFile);
    }

}
