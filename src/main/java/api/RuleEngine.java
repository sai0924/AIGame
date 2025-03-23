package api;

import boards.TicTacToeBoard;
import game.*;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RuleEngine {

    public GameInfo getInfo(Board board){
        if(board instanceof TicTacToeBoard){
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            GameState gameState = getState(board);
            String players[] = new String[]{"X","O"};
            for(int index=0;index<2;index++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        TicTacToeBoard boardCopy = (TicTacToeBoard) board.getCopy();
                        if (boardCopy.getSymbol(i, j) == null) {
                            Player player = new Player(players[index]);
                            board.move(new Move(new Cell(i, j), player));
                            boolean canStillWin = false;
                            for (int k = 0; k < 3; k++) {
                                for (int l = 0; l < 3; l++) {
                                    TicTacToeBoard boardCopy1 = (TicTacToeBoard) boardCopy.getCopy();
                                    if (boardCopy1.getSymbol(k, l) == null) {
                                        boardCopy1.move(new Move(new Cell(k, l), player.flip()));
                                        if (getState(boardCopy1).getWinner().equals(player.flip().symbol())) {
                                            canStillWin = true;
                                            break;
                                        }
                                    }
                                }
                                if (canStillWin) {
                                    break;
                                }
                            }
                            if (canStillWin) {
                                return new GameInfo(gameState, true, player.flip());
                            }
                        }
                    }
                }
            }
            return new GameInfo(gameState,false,null);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GameState getState(Board board){
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;

            GameState rowWin = findStreak((i,j) -> ticTacToeBoard.getSymbol(i,j));
            if(rowWin != null) return rowWin;

            GameState colWin = findStreak((i,j) -> ticTacToeBoard.getSymbol(j,i));
            if(colWin != null) return colWin;

            GameState diagWin = findDiagStreak(i -> ticTacToeBoard.getSymbol(i,i));
            if (diagWin != null) return diagWin;

            GameState revDiagWin = findDiagStreak(i -> ticTacToeBoard.getSymbol(i,2-i));
            if (revDiagWin != null) return revDiagWin;

            int countOfFilledCells = 0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(ticTacToeBoard.getSymbol(i,j) != null){
                        countOfFilledCells++;
                    }
                }
            }
            if(countOfFilledCells == 9) {
                return new GameState(true,"-");
            } else {
                return new GameState(false,"-");
            }
        }
        throw new IllegalArgumentException("Invalid board");
    }

    private  GameState findDiagStreak(Function<Integer, String> diag) {
        boolean possibleStreak = true;
        for (int i = 0; i < 3; i++) {
            if (diag.apply(0) == null || !diag.apply(0).equals(diag.apply(i))) {
                possibleStreak = false;
                break;
            }
        }
        if(possibleStreak){
            return new GameState(true, diag.apply(0));
        }
        return null;
    }

    private GameState findStreak(BiFunction<Integer, Integer, String> next) {
        for(int i = 0; i<3; i++){
            boolean possibleStreak = true;
            for (int j = 0; j < 3; j++) {
                if (next.apply(i,j) == null || !next.apply(i,0).equals(next.apply(i, j))) {
                    possibleStreak = false;
                    break;
                }
            }
            if (possibleStreak) {
                return new GameState(true, next.apply(i,0));
            }
        }
        return null;
    }
}
