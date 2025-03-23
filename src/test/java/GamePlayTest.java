import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GamePlayTest {

    GameEngine gameEngine;
    AIEngine aiEngine;
    RuleEngine ruleEngine;

    @BeforeEach
    public void setup(){
        gameEngine = new GameEngine();
        aiEngine = new AIEngine();
        ruleEngine = new RuleEngine();

    }

    @Test
    public void checkForRowWin(){
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][]{{1,0},{1,1},{1,2}};
        int[][] secondPlayerMoves = new int[][]{{0,0},{0,1},{0,2}};
        playGame(board, firstPlayerMoves,secondPlayerMoves);
        Assertions.assertTrue(ruleEngine.getState(board).isOver());
        Assertions.assertEquals(ruleEngine.getState(board).getWinner(),"X");
    }

    @Test
    public void checkForColWin(){
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][]{{0,0},{1,0},{2,0}};
        int[][] secondPlayerMoves = new int[][]{{0,1},{1,1},{2,2}};
        playGame(board, firstPlayerMoves,secondPlayerMoves);
        Assertions.assertTrue(ruleEngine.getState(board).isOver());
        Assertions.assertEquals(ruleEngine.getState(board).getWinner(),"X");
    }

    @Test
    public void checkForDiagonalWin(){
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][]{{0,0},{1,1},{2,2}};
        int[][] secondPlayerMoves = new int[][]{{0,1},{0,2},{1,0}};
        playGame(board,firstPlayerMoves,secondPlayerMoves);
        Assertions.assertTrue(ruleEngine.getState(board).isOver());
        Assertions.assertEquals(ruleEngine.getState(board).getWinner(),"X");
    }

    @Test
    public void checkForRevDiagonalWin(){
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][]{{0,2},{1,1},{2,0}};
        int[][] secondPlayerMoves = new int[][]{{0,1},{1,0},{2,1}};
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assertions.assertTrue(ruleEngine.getState(board).isOver());
        Assertions.assertEquals(ruleEngine.getState(board).getWinner(),"X");
    }

    @Test
    public void checkForComputerWin(){
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = new int[][]{{1,0},{1,2},{2,0}};
        int[][] secondPlayerMoves = new int[][]{{0,0},{0,1},{0,2}};
        playGame(board, firstPlayerMoves,secondPlayerMoves);
        Assertions.assertTrue(ruleEngine.getState(board).isOver());
        Assertions.assertEquals(ruleEngine.getState(board).getWinner(),"O");
    }

    private void playGame(Board board, int[][] firstPlayerMoves, int[][] secondPlayerMoves) {
        int next = 0;
        while (!ruleEngine.getState(board).isOver()) {
            Player firstPlayer = new Player("X");
            Player secondPlayer = new Player("O");
            int row = firstPlayerMoves[next][0];
            int col = firstPlayerMoves[next][1];
            Move firstPlayerMove = new Move(new Cell(row,col),firstPlayer);
            gameEngine.move(board, firstPlayerMove);
            if(!ruleEngine.getState(board).isOver()){
                int srow = secondPlayerMoves[next][0];
                int scol = secondPlayerMoves[next][1];
                Move secondPlayerMove = new Move(new Cell(srow,scol),secondPlayer);
                gameEngine.move(board,secondPlayerMove);
            }
            next++;
        }
    }

}
