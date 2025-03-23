import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        AIEngine aiEngine = new AIEngine();
        RuleEngine ruleEngine = new RuleEngine();
        Board board = gameEngine.start("TicTacToe");
        System.out.println("Start playing");
        //make moves
        Scanner scanner = new Scanner(System.in);
        while (!ruleEngine.getState(board).isOver()) {
            Player computer = new Player("O");
            Player human = new Player("X");
            System.out.println("Make your move");
            System.out.println(board);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            Move humanMove = new Move(new Cell(row,col),human);
            gameEngine.move(board, humanMove);
            if(!ruleEngine.getState(board).isOver()){
                Move computerMove = aiEngine.suggestMove(computer,board);
                gameEngine.move(board,computerMove);
            }
        }
        System.out.println("GameState: " + ruleEngine.getState(board));
        System.out.println(board);
    }
}
