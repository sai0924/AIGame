import api.GameEngine;
import game.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        Board board = gameEngine.start("TicTacToe");
        System.out.println("Start playing");
        //make moves
        Scanner scanner = new Scanner(System.in);
        while (!gameEngine.isComplete(board).isOver()) {
            Player computer = new Player("O");
            Player opponent = new Player("X");
            System.out.println("Make your move");
            System.out.println(board);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            Move oppMove = new Move(new Cell(row,col));
            gameEngine.move(board, opponent, oppMove);
            if(!gameEngine.isComplete(board).isOver()){
                Move computerMove = gameEngine.suggestMove(computer,board);
                gameEngine.move(board,computer,computerMove);
            }
        }
        System.out.println("GameResult: " + gameEngine.isComplete(board));
        System.out.println(board);
    }
}
