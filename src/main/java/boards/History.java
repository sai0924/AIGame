package boards;

import game.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History {

    List<Board> boards = new ArrayList<>();

    public Board getBoardAtMove(int moveIndex){
        if (boards.size() > moveIndex + 1) {
            boards.subList(moveIndex + 1, boards.size()).clear();
        }
        return boards.get(moveIndex);
    }

    public Board undo() {
        boards.remove(boards.size()-1);
        return boards.get(boards.size()-1);
    }

    public void add(Board board){
        boards.add(board);
    }
}
