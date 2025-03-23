package boards;

import game.Board;
import game.Cell;
import game.Move;

public class TicTacToeBoard implements Board {
    String[][] cells = new String[3][3];

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
    public void move(Move move) {
        setCell(move.getCell(),move.getPlayer().symbol());
    }

    @Override
    public Board getCopy() {
        TicTacToeBoard board = new TicTacToeBoard();
        for(int i=0;i<3;i++){
            System.arraycopy(this.cells[i], 0, board.cells[i], 0, 3);
        }
        return board;
    }
}
