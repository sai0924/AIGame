package game;

public class Game {

    private GameConfig gameConfig;
    private Board board;

    private Player winner;
    private long lastMoveTimeInMillis;

    public void move(Move move, long timeStampInMillis){
        move.getPlayer().setTimeUsedInMillis(timeStampInMillis - lastMoveTimeInMillis);
        if(gameConfig.isTimed()){
            if(moveMadeInTime(move.getPlayer())){
                board.move(move);
            }else {
                winner = move.getPlayer().flip();
            }
        } else {
            board.move(move);
        }
    }

    private boolean moveMadeInTime(Player player) {
        return player.getTimeUsedInMillis() < gameConfig.getTotalTimePerPlayer();
    }

}
