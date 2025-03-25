package api;

import boards.TicTacToeBoard;
import game.*;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class AIEngine {

    RuleEngine ruleEngine = new RuleEngine();

    public Move suggestMove(Player player, Board board) {
        if(board instanceof TicTacToeBoard ticTacToeBoard){
            int threshold = 3;
            Cell suggestion;
            if(countMoves(ticTacToeBoard) < threshold){
                suggestion = getBasicMove(ticTacToeBoard);
            } else if(countMoves(board) < threshold +   1){
                suggestion =  getCellToPlay(player,ticTacToeBoard);
            } else {
                suggestion = optimalMove(player,ticTacToeBoard);
            }
            return new Move(suggestion,player);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Cell optimalMove(Player player, TicTacToeBoard board) {
        Placement placement = OffensivePlacement.get();
        while (placement.next() != null){
            Optional<Cell> cellOptional = placement.place(board,player);
            if(cellOptional.isPresent()){
                return cellOptional.get();
            }
            placement = placement.next();
        }
        return null;
    }

    /**
     * <p>Can AI win the game with this move? Make the move.</p>
     * <p>Will human win with their next move? Block human from winning</p>
     * @param player
     * @param board
     * @return
     */
    private Cell getCellToPlay(Player player, Board board) {
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
        //Victorious Moves
        Cell cell = offence(player, ticTacToeBoard, ruleEngine);
        if (cell != null) return cell;
        //Defensive Moves
        cell = defence(player, ticTacToeBoard, ruleEngine);
        if (cell != null) return cell;
        return getBasicMove(board);
    }

    private static Cell defence(Player player, TicTacToeBoard ticTacToeBoard, RuleEngine ruleEngine) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j), player.flip());
                    TicTacToeBoard boardCopy = (TicTacToeBoard) ticTacToeBoard.getCopy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()){
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }

    private Cell offence(Player player, TicTacToeBoard ticTacToeBoard, RuleEngine ruleEngine) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j), player);
                    TicTacToeBoard boardCopy = (TicTacToeBoard) ticTacToeBoard.getCopy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()){
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }

    private Cell getBasicMove(Board board) {
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    return new Cell(i,j);
                }
            }
        }
        throw new IllegalStateException();
    }

    private int countMoves(Board board) {
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
        int count = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) != null){
                    count++;
                }
            }
        }
        return count;
    }
}
