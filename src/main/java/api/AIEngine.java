package api;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

public class AIEngine {

    public Move suggestMove(Player computer, Board board) {
        if(board instanceof TicTacToeBoard ticTacToeBoard){
            int threshold = 3;
            if(countMoves(board) < threshold){
                return getBasicMove(computer,board);
            } else {
                return getSmartMove(computer,board);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * <p>Can AI win the game with this move? Make the move.</p>
     * <p>Will human win with their next move? Block human from winning</p>
     * @param player
     * @param board
     * @return
     */
    private Move getSmartMove(Player player, Board board) {
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
        RuleEngine ruleEngine = new RuleEngine();
        //Victorious Moves
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j),player);
                    TicTacToeBoard boardCopy = (TicTacToeBoard) board.getCopy();
                    board.move(move);
                    if(ruleEngine.getState(board).isOver()){
                        return move;
                    }
                }
            }
        }

        //Defensive Moves
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    Move move = new Move(new Cell(i,j),player.flip());
                    board.move(move);
                    if(ruleEngine.getState(board).isOver()){
                        return move;
                    }
                }
            }
        }
        return getBasicMove(player,board);
    }

    private Move getBasicMove(Player player, Board board) {
        TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) == null){
                    return new Move(new Cell(i,j),player);
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
