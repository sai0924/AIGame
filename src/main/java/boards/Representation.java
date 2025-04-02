package boards;


public class Representation {
    private String representation;

    public Representation(TicTacToeBoard board){
        representation = board.toString();
    }
}
