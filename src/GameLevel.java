public class GameLevel {

    private final String levelText;
    private final int numberOfSquaresInHeight, numberOfSquaresInWidth, numberOfBombs;

    public GameLevel(String levelText, int numberOfSquaresInHeight, int numberOfSquaresInWidth, int numberOfBombs) {
        this.levelText = levelText;
        this.numberOfSquaresInHeight = numberOfSquaresInHeight;
        this.numberOfSquaresInWidth = numberOfSquaresInWidth;
        this.numberOfBombs = numberOfBombs;
    }

    public String getLevelText() {
        return levelText;
    }

    public int getNumberOfSquaresInHeight() {
        return numberOfSquaresInHeight;
    }

    public int getNumberOfSquaresInWidth() {
        return numberOfSquaresInWidth;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}
