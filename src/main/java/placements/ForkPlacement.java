package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.GameInfo;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class ForkPlacement implements Placement{

    private static ForkPlacement forkPlacement;

    private ForkPlacement() { }

    public static synchronized Placement get() {
        forkPlacement =  (ForkPlacement) Utils.getIfNull(forkPlacement,ForkPlacement::new);
        return forkPlacement;
    }
    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        GameInfo gameInfo = ruleEngine.getInfo(board);
        return Optional.ofNullable(gameInfo.getForkCell());
    }

    @Override
    public Placement next() {
        return CenterPlacement.get();
    }
}
