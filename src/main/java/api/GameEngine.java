package api;

import boards.TicTacToeBoard;
import game.*;

public class GameEngine {

    public Board start(String type){
        if("TicTacToe".equals(type)) {
            return new TicTacToeBoard();
        } else {
            throw new IllegalArgumentException("Invalid board type");
        }
    }

    public void move(Board board, Move move){
        if(board instanceof TicTacToeBoard){
            board.move(move);
        } else {
            throw new IllegalArgumentException("Invalid board");
        }
    }
}
