package api;

import boards.TicTacToeBoard;
import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RuleEngine {

    Map<String, List<Rule<TicTacToeBoard>>> ruleMap = new HashMap<>();

    public RuleEngine() {
        ruleMap.put(TicTacToeBoard.class.getName(),new ArrayList<>());
        ruleMap.get(TicTacToeBoard.class.getName()).add(new Rule<>(board -> outerTraversals(board::getSymbol)));
        ruleMap.get(TicTacToeBoard.class.getName()).add(new Rule<>(board -> outerTraversals((row, col) -> board.getSymbol(col, row))));
        ruleMap.get(TicTacToeBoard.class.getName()).add(new Rule<>(board -> traverse(i -> board.getSymbol(i,i))));
        ruleMap.get(TicTacToeBoard.class.getName()).add(new Rule<>(board -> traverse(i -> board.getSymbol(i,2-i))));
        ruleMap.get(TicTacToeBoard.class.getName()).add(new Rule<>(RuleEngine::countMoves));
    }

    public GameInfo getInfo(Board board){
        if(board instanceof TicTacToeBoard){
            GameState gameState = getState(board);
            String[] players = new String[]{"X","O"};
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
                                 return new GameInfoBuilder()
                                         .isOver(gameState.isOver())
                                         .hasFork(true)
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
            List<Rule<TicTacToeBoard>> rules = ruleMap.get(TicTacToeBoard.class.getName());
            for(Rule<TicTacToeBoard> rule : rules){
                GameState gameState = rule.condition.apply(ticTacToeBoard);
                if(gameState != null && gameState.isOver()){
                    return new GameState(true,"X");
                }
            }
            return new GameState(false,"-");
        }
        throw new IllegalArgumentException("Invalid board");
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

    private  GameState traverse(Function<Integer, String> diag) {
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

    private GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
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

class Rule<T extends Board>{
    Function<T,GameState> condition;

    public Rule(Function<T, GameState> condition) {
        this.condition = condition;
    }
}