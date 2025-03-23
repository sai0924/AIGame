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

    public void move(Board board, Player player, Move move){
        if(board instanceof TicTacToeBoard){
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            ticTacToeBoard.setCell(move.getCell(),player.symbol());
        } else {
            throw new IllegalArgumentException("Invalid board");
        }
    }

    public GameResult isComplete(Board board){
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            boolean rowComplete;
            boolean colComplete;
            String firstCharacter = "-";
            for(int i=0;i<3;i++){
                firstCharacter = ticTacToeBoard.getCell(i,0);
                rowComplete = firstCharacter != null;
                if(firstCharacter != null) {
                    for (int j = 1; j < 3; j++) {
                        if (!firstCharacter.equals(ticTacToeBoard.getCell(i, j))) {
                            rowComplete = false;
                            break;
                        }
                    }
                    if (rowComplete) {
                        return new GameResult(true, firstCharacter);
                    }
                }
            }

            for(int i=0;i<3;i++){
                firstCharacter = ticTacToeBoard.getCell(0,i);
                colComplete = firstCharacter != null;
                if(firstCharacter != null) {
                    for (int j = 1; j < 3; j++) {
                        if (!firstCharacter.equals(ticTacToeBoard.getCell(j, i))) {
                            colComplete = false;
                            break;
                        }
                    }
                    if (colComplete) {
                        return new GameResult(true, firstCharacter);
                    }
                }
            }


            firstCharacter = ticTacToeBoard.getCell(0,0);
            boolean diagComplete = firstCharacter != null;
            for (int i = 0; i < 3; i++) {
                if (firstCharacter != null && !firstCharacter.equals(ticTacToeBoard.getCell(i, i))) {
                    diagComplete = false;
                    break;
                }
            }

            if(diagComplete){
                return new GameResult(true,firstCharacter);
            }


            firstCharacter = ticTacToeBoard.getCell(0,2);
            boolean revDiagComplete = firstCharacter != null;
            for (int i = 0; i < 3; i++) {
                if (firstCharacter !=null && !firstCharacter.equals(ticTacToeBoard.getCell(i, 2 - i))) {
                    revDiagComplete = false;
                    break;
                }
            }
            if(revDiagComplete) {
                return new GameResult(true,firstCharacter);
            }

            int countOfFilledCells = 0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(ticTacToeBoard.getCell(i,j) != null){
                        countOfFilledCells++;
                    }
                }
            }
            if(countOfFilledCells == 9) {
                return new GameResult(true,"-");
            } else {
                return new GameResult(false,"-");
            }
        }
        throw new IllegalArgumentException("Invalid board");
    }

    public Move suggestMove(Player computer, Board board) {
        if(board instanceof TicTacToeBoard ticTacToeBoard){
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(ticTacToeBoard.getCell(i,j) == null){
                        return new Move(new Cell(i,j));
                    }
                }
            }
            throw new IllegalStateException();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
