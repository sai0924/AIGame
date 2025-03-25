package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class OffensivePlacement implements Placement{

    private static OffensivePlacement offensivePlacement;

    private OffensivePlacement() {}

    public static synchronized Placement get() {
        offensivePlacement = (OffensivePlacement) Utils.getIfNull(offensivePlacement, OffensivePlacement::new);
        return offensivePlacement;
    }
    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(offence(player, board, board));
    }

    @Override
    public Placement next() {
        return DefensivePlacement.get();
    }

    private Cell offence(Player player, Board board, TicTacToeBoard ticTacToeBoard) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j), player);
                    TicTacToeBoard boardCopy = (TicTacToeBoard) board.getCopy();
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
