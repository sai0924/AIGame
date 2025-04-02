package api;

import boards.TicTacToeBoard;
import game.*;
import java.util.HashMap;
import java.util.Map;

public class RuleEngine {

    Map<String, RuleSet<TicTacToeBoard>> ruleMap = new HashMap<>();

    public RuleEngine() {
        ruleMap.put(TicTacToeBoard.class.getName(),TicTacToeBoard.getRules());
    }

    public GameInfo getInfo(Board board){
        if(board instanceof TicTacToeBoard){
            GameState gameState = getState(board);
            String[] players = new String[]{"X","O"};
            Cell forkCell = null;
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
                                    TicTacToeBoard boardCopy1 = boardCopy.getCopy();
                                    if (boardCopy1.getSymbol(k, l) == null) {
                                        forkCell = new Cell(k, l);
                                        boardCopy1.move(new Move(forkCell, player.flip()));
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
                                 return new GameInfoBuilder()
                                         .isOver(gameState.isOver())
                                         .hasFork(true)
                                         .forkCell(forkCell)
                                         .player(player.flip())
                                         .winner(gameState.getWinner())
                                         .build();
                            }
                        }
                    }
                }
            }
            return new GameInfoBuilder()
                    .isOver(gameState.isOver())
                    .winner(gameState.getWinner())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GameState getState(Board board){
        if(board instanceof TicTacToeBoard ticTacToeBoard) {
            RuleSet<TicTacToeBoard> rules = ruleMap.get(TicTacToeBoard.class.getName());
            for(Rule rule : rules){
                GameState gameState = rule.condition.apply(ticTacToeBoard);
                if(gameState != null && gameState.isOver()){
                    return gameState;
                }
            }
            return new GameState(false,"-");
        }
        throw new IllegalArgumentException("Invalid board");
    }
}

