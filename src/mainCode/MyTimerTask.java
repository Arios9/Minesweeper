package mainCode;

import java.util.TimerTask;

import static mainCode.MinesweeperFrame.timeLabel;

public class MyTimerTask extends TimerTask {

    private int secondsPassed = 0;
    private HeaderLabel timeHeaderLabel;

    public MyTimerTask() {
        timeHeaderLabel = timeLabel;
    }

    @Override
    public void run() {
        timeHeaderLabel.reFresh(secondsPassed++);
    }
}