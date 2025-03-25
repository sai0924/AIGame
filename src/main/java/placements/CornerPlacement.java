package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CornerPlacement implements Placement{

    private static CornerPlacement cornerPlacement;

    private CornerPlacement() {
    }

    private static synchronized CornerPlacement get(){
        cornerPlacement = (CornerPlacement) Utils.getIfNull(cornerPlacement,CornerPlacement::new);
        return cornerPlacement;
    }
    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        Cell cell = null;
        int[][] corners = new int[][]{{0,0},{0,2},{2,0},{2,2}};
        for(int i=0;i<4;i++){
            if(board.getSymbol(corners[i][0],corners[i][1]) == null){
                cell =  new Cell(corners[i][0],corners[i][1]);
            }
        }
        return Optional.ofNullable(cell);
    }

    @Override
    public Placement next() {
        return null;
    }
}
