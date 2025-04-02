package boards;

import api.Rule;
import api.RuleEngine;
import api.RuleSet;
import game.Board;
import game.Cell;
import game.GameState;
import game.Move;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TicTacToeBoard implements CellBoard {
    String[][] cells = new String[3][3];

    private History history;

    public String getSymbol(int row, int col) {
        return cells[row][col];
    }

    public void setCell(Cell cell, String symbol) {
        if(cells[cell.getRow()][cell.getCol()] == null) {
            cells[cell.getRow()][cell.getCol()] = symbol;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(cells[i][j]!=null) {
                    result.append(cells[i][j]);
                } else {
                    result.append("-");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public TicTacToeBoard move(Move move) {
        TicTacToeBoard board = getCopy();
        board.setCell(move.getCell(),move.getPlayer().symbol());
        history.add(new Representation(board));
        return board;
    }

    @Override
    public TicTacToeBoard getCopy() {
        TicTacToeBoard board = new TicTacToeBoard();
        for(int i=0;i<3;i++){
            System.arraycopy(this.cells[i], 0, board.cells[i], 0, 3);
        }
        board.history = history;
        return board;
    }

    public static RuleSet<TicTacToeBoard> getRules(){
        RuleSet<TicTacToeBoard> ruleSet = new RuleSet<>();
        ruleSet.add(new Rule(board -> outerTraversals(board::getSymbol)));
        ruleSet.add(new Rule(board -> outerTraversals((row, col) -> board.getSymbol(col, row))));
        ruleSet.add(new Rule(board -> traverse(i -> board.getSymbol(i,i))));
        ruleSet.add(new Rule(board -> traverse(i -> board.getSymbol(i,2-i))));
        ruleSet.add(new Rule(board -> countMoves((TicTacToeBoard) board)));
        return ruleSet;
    }

    private static GameState traverse(Function<Integer, String> diag) {
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

    private static GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
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

    private static GameState countMoves(TicTacToeBoard ticTacToeBoard) {
        int countOfFilledCells = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(ticTacToeBoard.getSymbol(i,j) != null){
                    countOfFilledCells++;
                }
            }
        }
        if(countOfFilledCells == 9) {
            return new GameState(true, "-");
        }
        return null;
    }

    public enum Symbol{
        X("X"),O("O");

        final String marker;
        Symbol(String marker) {
            this.marker = marker;
        }
    }
}

