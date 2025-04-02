package boards;

import game.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History {

    List<Representation> representations = new ArrayList<>();

    public Representation getBoardAtMove(int moveIndex){
        if (representations.size() > moveIndex + 1) {
            representations.subList(moveIndex + 1, representations.size()).clear();
        }
        return representations.get(moveIndex);
    }

    public Representation undo() {
        representations.remove(representations.size()-1);
        return representations.get(representations.size()-1);
    }

    public void add(Representation board){
        representations.add(board);
    }
}
