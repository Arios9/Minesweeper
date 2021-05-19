public class GameInProgressException extends Exception{
    public GameInProgressException() {
        super("A game is still in progress");
    }
}
