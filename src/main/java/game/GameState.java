package game;

public class GameState {
    boolean isOver;
    String winner;

    public GameState(boolean isOver, String winner) {
        this.isOver = isOver;
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isOver() {
        return isOver;
    }

    @Override
    public String toString() {
        return "isOver=" + isOver + ", winner='" + winner + '\'' ;
    }
}
