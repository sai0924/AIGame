package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CenterPlacement implements Placement{
    private static CenterPlacement centerPlacement;

    private CenterPlacement() {
    }
    public static synchronized CenterPlacement get(){
        centerPlacement = (CenterPlacement) Utils.getIfNull(centerPlacement,CenterPlacement::new);
        return centerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        Cell cell = null;
        if(board.getSymbol(1,1) == null){
            cell =  new Cell(1,1);
        }
        return Optional.ofNullable(cell);
    }

    @Override
    public Placement next() {
        return CenterPlacement.get();
    }
}
