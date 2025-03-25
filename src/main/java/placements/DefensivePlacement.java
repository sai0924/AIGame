package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.Move;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class DefensivePlacement implements Placement{

    private static DefensivePlacement defensivePlacement;

    private DefensivePlacement() {}

    public static synchronized Placement get() {
        defensivePlacement = (DefensivePlacement) Utils.getIfNull(defensivePlacement,DefensivePlacement::new);
        return defensivePlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(defence(player, board));
    }

    @Override
    public Placement next() {
        return ForkPlacement.get();
    }

    private Cell defence(Player player, TicTacToeBoard ticTacToeBoard) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j), player.flip());
                    TicTacToeBoard boardCopy = (TicTacToeBoard) ticTacToeBoard.getCopy();
                    boardCopy.move(move);
                    if(Placement.ruleEngine.getState(boardCopy).isOver()){
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }
}
