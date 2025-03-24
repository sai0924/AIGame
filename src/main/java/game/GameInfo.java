package game;

public class GameInfo {

    private final boolean isOver;
    private final String winner;
    private final boolean hasFork;
    private final Player player;

    public GameInfo(boolean isOver, String winner, boolean hasFork, Player player) {
        this.hasFork = hasFork;
        this.player = player;
        this.isOver = isOver;
        this.winner = winner;
    }
}
