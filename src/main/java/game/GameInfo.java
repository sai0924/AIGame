package game;

public class GameInfo {

    private final boolean isOver;
    private final String winner;
    private final boolean hasFork;
    private final Player player;
    private final Cell forkCell;

    public GameInfo(boolean isOver, String winner, boolean hasFork, Player player, Cell forkCell) {
        this.hasFork = hasFork;
        this.player = player;
        this.isOver = isOver;
        this.winner = winner;
        this.forkCell = forkCell;
    }

    public boolean isOver() {
        return isOver;
    }

    public String getWinner() {
        return winner;
    }

    public boolean hasFork() {
        return hasFork;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell getForkCell() {
        return forkCell;
    }
}
