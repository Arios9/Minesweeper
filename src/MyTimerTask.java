import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private int secondsPassed = 0;
    private HeaderLabel timeHeaderLabel;

    public MyTimerTask(MinesweeperGame minesweeperGame) {
        timeHeaderLabel = minesweeperGame.getTimeLabel();
    }

    @Override
    public void run() {
        timeHeaderLabel.setText(String.valueOf(secondsPassed++));
    }
}