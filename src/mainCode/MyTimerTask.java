package mainCode;

import java.util.TimerTask;

import static MinesweeperFramePackage.MinesweeperFrame.timeLabel;

public class MyTimerTask extends TimerTask {

    public static int secondsPassed;

    public MyTimerTask() {
        secondsPassed = 0;
    }

    @Override
    public void run() {
        timeLabel.reFresh(++secondsPassed);
    }
}