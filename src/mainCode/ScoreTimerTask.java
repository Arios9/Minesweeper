package mainCode;

import java.util.TimerTask;

import static MinesweeperFramePackage.MinesweeperFrame.timeLabel;

public class ScoreTimerTask extends TimerTask {

    public static int secondsPassed;

    public ScoreTimerTask() {
        secondsPassed = 0;
    }

    @Override
    public void run() {
        timeLabel.reFresh(++secondsPassed);
    }
}