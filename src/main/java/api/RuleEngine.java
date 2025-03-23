package api;

import boards.TicTacToeBoard;
import game.Board;
import game.GameState;

public class RuleEngine {

    public GameState getState(Board board){
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            boolean rowComplete;
            boolean colComplete;
            String firstCharacter;
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
                        return new GameState(true, firstCharacter);
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
                        return new GameState(true, firstCharacter);
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
                return new GameState(true,firstCharacter);
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
                return new GameState(true,firstCharacter);
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
                return new GameState(true,"-");
            } else {
                return new GameState(false,"-");
            }
        }
        throw new IllegalArgumentException("Invalid board");
    }
}
