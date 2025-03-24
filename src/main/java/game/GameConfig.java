package game;

public class GameConfig {
    private long totalTimePerPlayer;
    private boolean timed;

    public boolean isTimed() {
        return timed;
    }

    public long getTotalTimePerPlayer() {
        return totalTimePerPlayer;
    }
}
