package api;

import boards.CellBoard;
import boards.TicTacToeBoard;
import game.*;
import placements.DefensivePlacement;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RuleEngine {

    Map<String, RuleSet<TicTacToeBoard>> ruleMap = new HashMap<>();

    public RuleEngine() {
        ruleMap.put(TicTacToeBoard.class.getName(),TicTacToeBoard.getRules());
    }

    public GameInfo getInfo(CellBoard board){
        if(board instanceof TicTacToeBoard){
            GameState gameState = getState(board);
            for(TicTacToeBoard.Symbol symbol : TicTacToeBoard.Symbol.values()) {
                Player player = new Player(symbol.name());
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        TicTacToeBoard boardCopy = (TicTacToeBoard) board.getCopy();
                        if (boardCopy.getSymbol(i, j) == null) {
                            boardCopy = boardCopy.move(new Move(new Cell(i, j), player));
                            //force opponent to make a defensive move
                            //we still win after that
                            DefensivePlacement defensivePlacement = DefensivePlacement.get();
                            Optional<Cell> defenciveCell = defensivePlacement.place(boardCopy,player.flip());
                            if(defenciveCell.isPresent()) {
                                boardCopy = boardCopy.move(new Move(defenciveCell.get(),player.flip()));
                                OffensivePlacement offensivePlacement = OffensivePlacement.get();
                                Optional<Cell> offensiveCell = offensivePlacement.place(boardCopy, player);
                                if(offensiveCell.isPresent()) {
                                    return new GameInfoBuilder()
                                            .isOver(gameState.isOver())
                                            .hasFork(true)
                                            .forkCell(new Cell(i, j))
                                            .player(player.flip())
                                            .winner(gameState.getWinner())
                                            .build();
                                }
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

