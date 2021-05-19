package mainCode;

import java.util.TimerTask;

import static mainCode.MinesweeperFrame.timeLabel;

public class MyTimerTask extends TimerTask {

    private int secondsPassed = 0;

    @Override
    public void run() {
        timeLabel.reFresh(secondsPassed++);
    }
}