package game;

public class Player {

    private long timeUsedInMillis;

    private User user;
    private String playerSymbol;

    public Player(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String symbol() {
        return playerSymbol;
    }

    public Player flip() {
        return new Player(playerSymbol.equals("X") ? "O" : "X");
    }

    public void setTimeUsedInMillis(long timeUsedInMillis){
        this.timeUsedInMillis+=timeUsedInMillis;
    }

    public long getTimeUsedInMillis() {
        return timeUsedInMillis;
    }
}
